
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CompanyRepository;
import security.Authority;
import security.UserAccount;
import domain.Company;

@Service
@Transactional
public class CompanyService {

	//Managed repository ---------------------------------

	@Autowired
	private CompanyRepository	companyRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	//Simple CRUD Methods --------------------------------

	public Company create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.COMPANY);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));
		account.setBanned(false);
		account.setInactive(false);

		final Company company = new Company();
		company.setSpammer(false);
		company.setUserAccount(account);

		return company;
	}

	public Collection<Company> findAll() {
		return this.companyRepository.findAll();
	}

	public Company findOne(final int id) {
		Assert.notNull(id);

		return this.companyRepository.findOne(id);
	}

	public Company save(final Company company) {
		Assert.notNull(company);
		Company saved2;

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(company.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(company.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(company.getPhone()));

		//Checking if the actor is bannable according to the "bannableActors" query.
		if (this.actorService.isBannable(company) == true)
			company.setSpammer(true);

		if (company.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == company.getId());
			saved2 = this.companyRepository.save(company);
		} else {
			final Company saved = this.companyRepository.save(company);
			this.actorService.hashPassword(saved);
			saved2 = this.companyRepository.save(saved);
		}

		return saved2;
	}

	public void delete(final Company company) {
		Assert.notNull(company);

		//Assertion that the user deleting this company has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == company.getId());

		this.companyRepository.delete(company);
	}

	//	public Company reconstruct(final FormObjectAdministrator foc, final BindingResult binding) {
	//		final Company result = this.create();
	//
	//		Assert.isTrue(foc.getAcceptedTerms());
	//		Assert.isTrue(foc.getPassword().equals(foc.getSecondPassword()));
	//
	//		result.setName(foc.getName());
	//		result.setMiddleName(foc.getMiddleName());
	//		result.setSurname(foc.getSurname());
	//		result.setPhoto(foc.getPhoto());
	//		result.setEmail(foc.getEmail());
	//		result.setPhone(foc.getPhone());
	//		result.setAddress(foc.getAddress());
	//		result.getUserAccount().setUsername(foc.getUsername());
	//		result.getUserAccount().setPassword(foc.getPassword());
	//
	//		this.validator.validate(result, binding);
	//
	//		if (binding.hasErrors())
	//			throw new ValidationException();
	//
	//		//Assertion that the email is valid according to the checkAdminEmail method.
	//		Assert.isTrue(this.actorService.checkAdminEmail(result.getEmail()));
	//
	//		//Assertion to check that the address isn't just a white space.
	//		Assert.isTrue(this.actorService.checkAddress(result.getAddress()));
	//
	//		//Assertion that the phone is valid according to the checkPhone method.
	//		Assert.isTrue(this.actorService.checkPhone(result.getPhone()));
	//
	//		return result;
	//
	//	}

	public Company reconstructPruned(final Company company, final BindingResult binding) {
		Company result;

		result = this.companyRepository.findOne(company.getId());

		result.setName(company.getName());
		result.setSurnames(company.getSurnames());
		result.setPhoto(company.getPhoto());
		result.setEmail(company.getEmail());
		result.setPhone(company.getPhone());
		result.setAddress(company.getAddress());
		result.setCommercialName(company.getCommercialName());

		this.validator.validate(result, binding);

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
		this.companyRepository.flush();
	}

	//The companies that have offered more positions
	public Collection<Company> companiesWithMoreOfferedPossitions() {
		Collection<Company> results = new ArrayList<>();
		final Collection<Company> companies = this.companyRepository.companiesWithMoreOfferedPossitions();
		final int maxResults = 1;
		if (companies.size() > maxResults)
			results = new ArrayList<Company>(((ArrayList<Company>) companies).subList(0, maxResults));
		else
			results = companies;
		return results;
	}
}
