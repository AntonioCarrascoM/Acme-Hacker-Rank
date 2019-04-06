
package services;

import java.util.Arrays;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.UserAccount;
import domain.Administrator;
import forms.FormObjectAdministrator;

@Service
@Transactional
public class AdministratorService {

	//Managed repository ---------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	//Simple CRUD Methods --------------------------------

	public Administrator create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));
		account.setBanned(false);
		account.setInactive(false);

		final Administrator administrator = new Administrator();
		administrator.setSpammer(false);
		administrator.setUserAccount(account);

		return administrator;
	}

	public Collection<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	public Administrator findOne(final int id) {
		Assert.notNull(id);

		return this.administratorRepository.findOne(id);
	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);
		Administrator saved2;

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkAdminEmail(administrator.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(administrator.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(administrator.getPhone()));

		//Checking if the actor is bannable according to the "bannableActors" query.
		if (this.actorService.isBannable(administrator) == true)
			administrator.setSpammer(true);

		if (administrator.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == administrator.getId());
			saved2 = this.administratorRepository.save(administrator);
		} else {
			final Administrator saved = this.administratorRepository.save(administrator);
			this.actorService.hashPassword(saved);
			saved2 = this.administratorRepository.save(saved);
		}

		return saved2;
	}

	public void delete(final Administrator administrator) {
		Assert.notNull(administrator);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == administrator.getId());

		this.administratorRepository.delete(administrator);
	}

	public Administrator reconstruct(final FormObjectAdministrator foa, final BindingResult binding) {
		final Administrator result = this.create();

		Assert.isTrue(foa.getAcceptedTerms());
		Assert.isTrue(foa.getPassword().equals(foa.getSecondPassword()));

		result.setName(foa.getName());
		result.setSurnames(foa.getSurnames());
		result.setVatNumber(foa.getVatNumber());
		result.setCreditCard(foa.getCreditCard());
		result.setPhoto(foa.getPhoto());
		result.setEmail(foa.getEmail());
		result.setPhone(foa.getPhone());
		result.setAddress(foa.getAddress());
		result.getUserAccount().setUsername(foa.getUsername());
		result.getUserAccount().setPassword(foa.getPassword());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkAdminEmail(result.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(result.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(result.getPhone()));

		return result;

	}

	public Administrator reconstructPruned(final Administrator administrator, final BindingResult binding) {
		Administrator result;

		result = this.administratorRepository.findOne(administrator.getId());

		result.setName(administrator.getName());
		result.setSurnames(administrator.getSurnames());
		result.setVatNumber(administrator.getVatNumber());
		result.setCreditCard(administrator.getCreditCard());
		result.setPhoto(administrator.getPhoto());
		result.setEmail(administrator.getEmail());
		result.setPhone(administrator.getPhone());
		result.setAddress(administrator.getAddress());

		this.validator.validate(result, binding);

		//Assertion the user has the correct privilege
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getId());

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkAdminEmail(result.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(result.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(result.getPhone()));

		return result;

	}

	//Other methods
	public void flush() {
		this.administratorRepository.flush();
	}
}
