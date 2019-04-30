
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
import domain.Application;
import domain.Curriculum;
import domain.Hacker;
import domain.Status;

@Service
@Transactional
public class ApplicationService {

	//Managed repository ---------------------------------

	@Autowired
	private ApplicationRepository	applicationRepository;

	//Supporting services --------------------------------

	@Autowired
	private ProblemService			problemService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private Validator				validator;


	//Simple CRUD Methods --------------------------------

	public Application create() {

		final Application a = new Application();

		a.setStatus(Status.PENDING);

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

		//Assertion that the user modifying this application has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == application.getHacker().getId() || this.actorService.findByPrincipal().getId() == application.getPosition().getCompany().getId());

		if (application.getId() == 0) {
			final Curriculum orig = application.getCurriculum();
			final Curriculum copy = this.curriculumService.copy(orig);
			application.setCurriculum(copy);
		}

		saved = this.applicationRepository.save(application);

		return saved;
	}
	//Reject method

	public void reject(final Application app) {
		Assert.notNull(app);

		//Assertion that the user rejecting this app has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == app.getPosition().getCompany().getId());

		//Assertion that application is submitted.
		Assert.isTrue(app.getStatus() == Status.SUBMITTED);

		app.setStatus(Status.REJECTED);

		final Application saved = this.applicationRepository.save(app);

		this.messageService.applicationStatusNotification(saved);
	}

	//Accept method

	public void accept(final Application app) {
		Assert.notNull(app);

		//Assertion that the user accepting this app has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == app.getPosition().getCompany().getId());

		//Assertion that application is submitted.
		Assert.isTrue(app.getStatus() == Status.SUBMITTED);

		app.setStatus(Status.ACCEPTED);

		final Application saved = this.applicationRepository.save(app);

		this.messageService.applicationStatusNotification(saved);
	}

	//Reconstruct

	public Application reconstruct(final Application app, final BindingResult binding) {
		Application result;

		if (app.getId() == 0) {
			result = this.create();
			result.setCurriculum(app.getCurriculum());
			result.setPosition(app.getPosition());

			//Assertion that the position provided is valid
			Assert.isTrue(this.positionService.positionsForRequestsByHacker(this.actorService.findByPrincipal().getId()).contains(result.getPosition()));

			result.setProblem(this.problemService.randomProblemInFinalModeByPosition(app.getPosition().getId()));
		} else {
			result = this.applicationRepository.findOne(app.getId());
			result.setStatus(Status.SUBMITTED);
			result.setAnswerDescription(app.getAnswerDescription());
			result.setAnswerLink(app.getAnswerLink());
			result.setAnswerMoment(new Date(System.currentTimeMillis() - 1));
		}

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this request has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getHacker().getId() || this.actorService.findByPrincipal().getId() == result.getPosition().getCompany().getId());

		//Assertion the user copying this curriculum has the correct privilege
		Assert.isTrue(result.getCurriculum().getHacker().getId() == this.actorService.findByPrincipal().getId());

		return result;

	}

	//Other methods

	//The applications given a hacker id
	public Collection<Application> applicationsOfAHacker(final int id) {
		return this.applicationRepository.applicationsOfAHacker(id);
	}

	//The applications given a hacker id ordered by status
	public Collection<Application> applicationsOfAHackerOrderedByStatus(final int id) {
		return this.applicationRepository.applicationsOfAHackerOrderedByStatus(id);
	}

	//The average, the minimum, the maximum, and the standard deviation of the number of applications per hacker
	public Double[] avgMinMaxStddevApplicationsPerHacker() {
		return this.applicationRepository.avgMinMaxStddevApplicationsPerHacker();
	}

	//The applications given a position id
	public Collection<Application> applicationsOfAPosition(final int id) {
		return this.applicationRepository.applicationsOfAPosition(id);
	}

	//The applications given a position id ordered by status
	public Collection<Application> applicationsOfAPositionOrderedByStatus(final int id) {
		return this.applicationRepository.applicationsOfAPositionOrderedByStatus(id);
	}

	public void flush() {
		this.applicationRepository.flush();
	}

	//Returns applications given a certain curriculum
	public Collection<Application> applicationsWithCurriculum(final int curriculumId) {
		return this.applicationRepository.applicationsWithCurriculum(curriculumId);

	}

}
