
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProblemRepository;
import security.Authority;
import domain.Company;
import domain.Problem;

@Service
@Transactional
public class ProblemService {

	//Managed repository

	@Autowired
	private ProblemRepository	problemRepository;

	//Supporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Problem create() {
		final Problem p = new Problem();

		p.setCompany((Company) this.actorService.findByPrincipal());
		p.setFinalMode(false);

		return p;
	}
	public Problem findOne(final int id) {
		Assert.notNull(id);

		return this.problemRepository.findOne(id);
	}

	public Collection<Problem> findAll() {
		return this.problemRepository.findAll();
	}

	public Problem save(final Problem p, final boolean b) {
		Assert.notNull(p);

		//Assertion to make sure that the problem is not on final mode.
		Assert.isTrue(p.getFinalMode() == false);

		//Assertion to make sure that the position associated with the problem is not cancelled.
		Assert.isTrue(p.getPosition().getCancelled() == false);

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getCompany().getId());

		//A problem can only be final mode if it has at least 2 problems
		if (b == true)
			p.setFinalMode(true);

		final Problem saved = this.problemRepository.save(p);

		return saved;
	}

	public void delete(final Problem p) {
		Assert.notNull(p);

		//Assertion that the user deleting this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getPosition().getCompany().getId());

		this.problemRepository.delete(p);
	}

	//Reconstruct

	public Problem reconstruct(final Problem p, final BindingResult binding) {
		Assert.notNull(p);
		Problem result;
		final Authority authCompany = new Authority();
		authCompany.setAuthority(Authority.COMPANY);

		if (p.getId() == 0)
			result = this.create();
		else
			result = this.problemRepository.findOne(p.getId());
		result.setTitle(p.getTitle());
		result.setStatement(p.getStatement());
		result.setHint(p.getHint());
		result.setAttachments(p.getAttachments());
		result.setFinalMode(p.getFinalMode());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion to make sure that the position associated with the problem is not cancelled.
		Assert.isTrue(p.getPosition().getCancelled() == false);

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getCompany().getId());

		return result;

	}
	//Other methods

	//Retrieves the problems for a certain problem
	public Collection<Problem> problemsOfAPosition(final int id) {
		return this.problemRepository.problemsOfAPosition(id);
	}

	public void flush() {
		this.problemRepository.flush();
	}

	//Problems in final mode by position
	public Collection<Problem> problemsInFinalModeByPosition(final int id) {
		return this.problemRepository.problemsInFinalModeByPosition(id);
	}
}
