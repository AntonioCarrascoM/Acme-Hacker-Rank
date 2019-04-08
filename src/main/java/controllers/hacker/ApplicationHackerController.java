
package controllers.hacker;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import controllers.AbstractController;
import domain.Application;
import domain.Hacker;

@Controller
@RequestMapping("application/hacker")
public class ApplicationHackerController extends AbstractController {

	//Services

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ActorService		actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Application> applications;

		applications = this.applicationService.applicationsOfAHacker(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/hacker/list.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final Application application = this.applicationService.findOne(varId);
		Assert.notNull(application);

		result = new ModelAndView("application/display");
		result.addObject("application", application);
		result.addObject("requestURI", "application/hacker/display.do");

		return result;
	}
	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int positionId) {
		final ModelAndView result;
		Application application;

		application = this.applicationService.create(positionId);
		result = this.createEditModelAndView(application);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Application application, final BindingResult binding) {
		ModelAndView result;
		Application saved;

		try {
			application = this.applicationService.reconstruct(application, application.getPosition().getId(), binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(application);
		} catch (final Throwable oops) {
			final Collection<Application> applications = this.applicationService.applicationsOfAHacker(this.actorService.findByPrincipal().getId());
			result = new ModelAndView("applications/list");
			result.addObject("applications", applications);
			result.addObject("message", "application.reconstruct.error");
			return result;
		}

		if (application.getId() != 0)
			result = this.createEditModelAndView(application);

		try {
			saved = this.applicationService.save(application);
			result = new ModelAndView("redirect:/application/hacker/display.do?varId=" + saved.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(application, "application.commit.error");
		}
		return result;
	}

	//Applications cannot be deleted

	//Other methods

	//Reject
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView remove(@RequestParam final int varId) {
		ModelAndView result;
		Collection<Application> applications;
		Application application;

		result = new ModelAndView("application/hacker/list");

		final Hacker hacker = (Hacker) this.actorService.findByPrincipal();
		applications = this.applicationService.applicationsOfAHacker(hacker.getId());
		application = this.applicationService.findOne(varId);

		if (application.getHacker().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.applicationService.reject(application);
			applications = this.applicationService.applicationsOfAHacker(hacker.getId());
			result.addObject("applications", applications);
			result.addObject("requestURI", "application/hacker/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(application, "application.reject.error");
		}

		return result;
	}

	//Accept
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int varId) {
		ModelAndView result;
		Collection<Application> applications;
		Application application;

		result = new ModelAndView("application/hacker/list");

		final Hacker hacker = (Hacker) this.actorService.findByPrincipal();
		applications = this.applicationService.applicationsOfAHacker(hacker.getId());
		application = this.applicationService.findOne(varId);

		if (application.getHacker().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.applicationService.accept(application);
			applications = this.applicationService.applicationsOfAHacker(hacker.getId());
			result.addObject("applications", applications);
			result.addObject("requestURI", "application/hacker/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(application, "application.reject.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("application/edit");
		result.addObject("application", application);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "application/hacker/edit.do");

		return result;

	}
}
