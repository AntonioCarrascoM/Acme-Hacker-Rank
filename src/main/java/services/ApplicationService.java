
package services;

import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ApplicationRepository;
import security.Authority;
import domain.Application;
import domain.Curriculum;
import domain.Hacker;
import domain.Position;
import domain.Problem;
import domain.Status;

@Service
@Transactional
public class ApplicationService {

	//Managed repository ---------------------------------

	@Autowired
	private ApplicationRepository	applicationRepository;

	//Supporting services --------------------------------

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ProblemService			problemService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private Validator				validator;


	//Simple CRUD Methods --------------------------------

	public Application create(final int id) {

		final Application a = new Application();

		a.setStatus(Status.PENDING);

		final Position position = this.positionService.findOne(id);
		a.setPosition(position);
		final Problem problem = this.problemService.randomProblemInFinalModeByPosition(position.getId());
		a.setProblem(problem);

		final Hacker hacker = (Hacker) this.actorService.findByPrincipal();
		a.setHacker(hacker);
		a.setMoment(new Date(System.currentTimeMillis() - 1));
		return a;
	}
	public Collection<Application> findAll() {

		return this.applicationRepository.findAll();
	}

	public Application findOne(final int id) {
		Assert.notNull(id);

		return this.applicationRepository.findOne(id);
	}

	public Application save(final Application application) {
		Assert.notNull(application);

		Application saved;

		//Sending message to actors involved if application status has changed
		if ((application.getStatus().equals(Status.ACCEPTED) || application.getStatus().equals(Status.SUBMITTED) || application.getStatus().equals(Status.REJECTED)))
			this.messageService.applicationStatusNotification(application);

		//Assertion that the user modifying this application has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == application.getHacker().getId() || this.actorService.findByPrincipal().getId() == application.getPosition().getCompany().getId());

		Assert.isTrue(application.getCurriculum() != null);
		if (application.getId() == 0) {
			final Curriculum orig = application.getCurriculum();

			final Curriculum copy = this.curriculumService.copy(orig);
			application.setCurriculum(copy);
		}

		if (this.actorService.findByPrincipal().getId() == application.getHacker().getId() && application.getStatus().equals(Status.PENDING))
			application.setStatus(Status.SUBMITTED);

		if (application.getMoment() == null)
			application.setMoment(new Date(System.currentTimeMillis() - 1));

		saved = this.applicationRepository.save(application);

		return saved;
	}

	//	public void delete(final Application application) {
	//		Assert.notNull(application);
	//
	//		//Assertion that the user deleting this application has the correct privilege.
	//		Assert.isTrue(this.actorService.findByPrincipal().getId() == application.getHacker().getId());
	//
	//		this.applicationRepository.delete(application);
	//	}

	//Reject method
	public void reject(final Application app) {
		Assert.notNull(app);

		//Assertion that the user deleting this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == app.getPosition().getCompany().getId());

		app.setStatus(Status.REJECTED);

		this.applicationRepository.save(app);
	}

	//Accept method
	public void accept(final Application app) {
		Assert.notNull(app);

		//Assertion that the user deleting this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == app.getPosition().getCompany().getId());

		app.setStatus(Status.ACCEPTED);

		this.applicationRepository.save(app);
	}

	//Reconstruct

	public Application reconstruct(final Application app, final BindingResult binding) {
		Application result;

		final Authority authCompany = new Authority();
		authCompany.setAuthority(Authority.COMPANY);

		final Authority authHacker = new Authority();
		authHacker.setAuthority(Authority.HACKER);

		if (app.getId() == 0)
			result = this.create(app.getPosition().getId());
		else {
			result = this.applicationRepository.findOne(app.getId());

			if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authCompany) && result.getStatus() == Status.SUBMITTED) {
				if (app.getStatus().equals(Status.ACCEPTED)) {
					result.setStatus(app.getStatus());
					this.messageService.applicationStatusNotification(result);
				}
				if (app.getStatus().equals(Status.REJECTED)) {
					result.setStatus(app.getStatus());
					this.messageService.applicationStatusNotification(result);
				}
				//				result.setAnswerDescription(app.getAnswerDescription());
				//				result.setAnswerLink(app.getAnswerLink());
				//				result.setAnswerMoment(app.getAnswerMoment());
				//				result.setProblem(app.getProblem());
				//				result.setMoment(app.getMoment());
			}
			//TODO revisar esta cacota dura
			if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authHacker) && result.getStatus() == Status.PENDING)
				result.setStatus(Status.SUBMITTED);
			result.setAnswerDescription(app.getAnswerDescription());
			result.setAnswerLink(app.getAnswerLink());
			result.setAnswerMoment(new Date(System.currentTimeMillis() - 1));
			this.messageService.applicationStatusNotification(result);
		}
		//				result.setProblem(app.getProblem());
		//				result.setMoment(app.getMoment());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this request has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getHacker().getId() || this.actorService.findByPrincipal().getId() == result.getPosition().getCompany().getId());

		return result;

	}
	//Time for motion and queries

	//The applications given a hacker id
	public Collection<Application> applicationsOfAHacker(final int id) {
		return this.applicationRepository.applicationsOfAHacker(id);
	}

	//The average, the minimum, the maximum, and the standard deviation of the number of applications per hacker
	public Double[] avgMinMaxStddevApplicationsPerHacker() {
		return this.applicationRepository.avgMinMaxStddevApplicationsPerHacker();
	}

	//Returns applications given a certain curriculum
	public Collection<Application> applicationsWithCurriculum(final int curriculumId) {
		return this.applicationRepository.applicationsWithCurriculum(curriculumId);
	}

}
