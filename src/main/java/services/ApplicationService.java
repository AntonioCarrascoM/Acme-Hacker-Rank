
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;
import domain.Curriculum;
import domain.Hacker;
import domain.Position;
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
	private CurriculumService		curriculumService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private MessageService			messageService;


	//Simple CRUD Methods --------------------------------

	public Application create(final int id) {

		final Application a = new Application();

		a.setStatus(Status.PENDING);

		final Position position = this.positionService.findOne(id);
		a.setPosition(position);
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

	public void delete(final Application application) {
		Assert.notNull(application);

		//Assertion that the user deleting this application has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == application.getHacker().getId());

		this.applicationRepository.delete(application);
	}

	//Reject method
	public void reject(final Application app) {
		Assert.notNull(app);

		//Assertion that the user deleting this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == app.getPosition().getCompany().getId());

		app.setStatus(Status.REJECTED);

		this.applicationRepository.save(app);
	}

	//Reject method
	public void accept(final Application app) {
		Assert.notNull(app);

		//Assertion that the user deleting this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == app.getPosition().getCompany().getId());

		app.setStatus(Status.ACCEPTED);

		this.applicationRepository.save(app);
	}

	//Time for motion and queries

}
