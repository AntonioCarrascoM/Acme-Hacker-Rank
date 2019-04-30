
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

import javax.validation.ConstraintDefinitionException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.HackerRepository;
import security.Authority;
import security.UserAccount;
import domain.Hacker;
import forms.FormObjectHacker;

@Service
@Transactional
public class HackerService {

	//Managed repository ---------------------------------

	@Autowired
	private HackerRepository	hackerRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	//Simple CRUD Methods --------------------------------

	public Hacker create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.HACKER);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));
		account.setBanned(false);
		account.setInactive(false);

		final Hacker hacker = new Hacker();
		hacker.setSpammer(false);
		hacker.setEvaluated(false);
		hacker.setUserAccount(account);

		return hacker;
	}

	public Collection<Hacker> findAll() {
		return this.hackerRepository.findAll();
	}

	public Hacker findOne(final int id) {
		Assert.notNull(id);

		return this.hackerRepository.findOne(id);
	}

	public Hacker save(final Hacker hacker) {
		Assert.notNull(hacker);
		Hacker saved2;

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(hacker.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(hacker.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(hacker.getPhone()));

		//Checking if the actor is bannable according to the "bannableActors" query.
		if (this.actorService.isBannable(hacker) == true)
			hacker.setSpammer(true);

		if (hacker.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == hacker.getId());
			saved2 = this.hackerRepository.save(hacker);
		} else {
			final Hacker saved = this.hackerRepository.save(hacker);
			this.actorService.hashPassword(saved);
			saved2 = this.hackerRepository.save(saved);
		}

		return saved2;
	}

	public void delete(final Hacker hacker) {
		Assert.notNull(hacker);

		//Assertion that the user deleting this hacker has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == hacker.getId());

		this.hackerRepository.delete(hacker);
	}

	public Hacker reconstruct(final FormObjectHacker foh, final BindingResult binding) {
		final Hacker result = this.create();

		Assert.isTrue(foh.getAcceptedTerms());
		Assert.isTrue(foh.getPassword().equals(foh.getSecondPassword()));

		result.setName(foh.getName());
		result.setSurnames(foh.getSurnames());
		result.setVatNumber(foh.getVatNumber());
		result.setCreditCard(foh.getCreditCard());
		result.setPhoto(foh.getPhoto());
		result.setEmail(foh.getEmail());
		result.setPhone(foh.getPhone());
		result.setAddress(foh.getAddress());
		result.getUserAccount().setUsername(foh.getUsername());
		result.getUserAccount().setPassword(foh.getPassword());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		final int year = Calendar.getInstance().get(Calendar.YEAR);
		final int month = Calendar.getInstance().get(Calendar.MONTH) + 1;

		//Assertion to make sure that the credit card has a valid expiration date.
		if (result.getCreditCard() != null) {
			if (result.getCreditCard().getExpYear() < year)
				throw new ConstraintDefinitionException();
			if (result.getCreditCard().getExpYear() == year && result.getCreditCard().getExpMonth() < month)
				throw new ConstraintDefinitionException();
		}

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(result.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(result.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(result.getPhone()));

		return result;

	}

	public Hacker reconstructPruned(final Hacker hacker, final BindingResult binding) {
		Hacker result;

		result = this.hackerRepository.findOne(hacker.getId());

		result.setName(hacker.getName());
		result.setSurnames(hacker.getSurnames());
		result.setVatNumber(hacker.getVatNumber());
		result.setCreditCard(hacker.getCreditCard());
		result.setPhoto(hacker.getPhoto());
		result.setEmail(hacker.getEmail());
		result.setPhone(hacker.getPhone());
		result.setAddress(hacker.getAddress());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		final int year = Calendar.getInstance().get(Calendar.YEAR);
		final int month = Calendar.getInstance().get(Calendar.MONTH) + 1;

		//Assertion to make sure that the credit card has a valid expiration date.
		if (result.getCreditCard() != null) {
			if (result.getCreditCard().getExpYear() < year)
				throw new ConstraintDefinitionException();
			if (result.getCreditCard().getExpYear() == year && result.getCreditCard().getExpMonth() < month)
				throw new ConstraintDefinitionException();
		}

		//Assertion the user has the correct privilege
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getId());

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(result.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(result.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(result.getPhone()));

		return result;

	}

	//Other methods
	public void flush() {
		this.hackerRepository.flush();
	}

	public Hacker hackerByFinder(final int id) {
		return this.hackerRepository.hackerByFinder(id);
	}

	//The hackers who have made more applications
	public Collection<String> hackersWithMoreApplications() {
		Collection<String> results = new ArrayList<>();
		final Collection<String> hackers = this.hackerRepository.hackersWithMoreApplications();
		final int maxResults = 1;
		if (hackers.size() > maxResults)
			results = new ArrayList<String>(((ArrayList<String>) hackers).subList(0, maxResults));
		else
			results = hackers;
		return results;
	}

}
