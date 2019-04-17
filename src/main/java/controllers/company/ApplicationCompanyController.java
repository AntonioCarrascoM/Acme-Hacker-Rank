
package controllers.company;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import services.PositionService;
import controllers.AbstractController;
import domain.Application;
import domain.Position;

@Controller
@RequestMapping("application/company")
public class ApplicationCompanyController extends AbstractController {

	//Supporting services

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private PositionService		positionService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Application> applications;

		final Position position = this.positionService.findOne(varId);

		if (position.getCompany().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		applications = this.applicationService.applicationsOfAPositionOrderedByStatus(varId);

		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/company/list.do");

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
		result.addObject("requestURI", "application/company/display.do");

		return result;
	}

	//Reject

	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int varId) {

		final ModelAndView result = new ModelAndView("application/list");
		Collection<Application> applications;
		final Application application = this.applicationService.findOne(varId);
		final Position pos = application.getPosition();
		applications = this.applicationService.applicationsOfAPositionOrderedByStatus(pos.getId());

		if (pos.getCompany().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.applicationService.reject(application);
		} catch (final Throwable oops) {
			result.addObject("message", "application.commit.error");
		}

		result.addObject("applications", applications);

		return result;
	}

	//Accept

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int varId) {
		final ModelAndView result = new ModelAndView("application/list");
		Collection<Application> applications;
		final Application application = this.applicationService.findOne(varId);
		final Position pos = application.getPosition();
		applications = this.applicationService.applicationsOfAPositionOrderedByStatus(pos.getId());

		if (pos.getCompany().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.applicationService.accept(application);
		} catch (final Throwable oops) {
			result.addObject("message", "application.commit.error");
		}

		result.addObject("applications", applications);

		return result;
	}
}
