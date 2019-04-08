
package controllers.hacker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.PersonalDataService;
import controllers.AbstractController;
import domain.PersonalData;

@Controller
@RequestMapping("personalData/hacker")
public class PersonalDataHackerController extends AbstractController {

	//Services

	@Autowired
	private PersonalDataService	personalDataService;

	@Autowired
	private ActorService		actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		final ModelAndView result;
		PersonalData personalData;

		personalData = this.personalDataService.create(curriculumId);
		result = this.createEditModelAndView(personalData);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalDataId) {
		final ModelAndView result;
		PersonalData personalData;

		personalData = this.personalDataService.findOne(personalDataId);
		Assert.notNull(personalData);
		result = this.createEditModelAndView(personalData);

		return result;
	}

	//Edit POST
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(PersonalData personalData, final int curriculumId, final BindingResult binding) {
		ModelAndView result;

		try {
			personalData = this.personalDataService.reconstruct(personalData, binding);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(personalData, "personalData.commit.error");
		}

		if (binding.hasErrors())
			result = this.createEditModelAndView(personalData);
		else
			try {
				this.personalDataService.save(personalData);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(personalData, "personalData.commit.error");
			}
		return result;
	}

	//Delete POST
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(PersonalData personalData, final BindingResult binding) {
		ModelAndView result;

		personalData = this.personalDataService.findOne(personalData.getId());

		if (personalData.getCurriculum().getHacker().getId() != this.actorService.findByPrincipal().getId())
			result = this.createEditModelAndView(personalData, "personalData.delete.error");
		else
			try {
				this.personalDataService.delete(personalData);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(personalData, "personalData.commit.error");
			}
		return result;
	}

	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final PersonalData personalData = this.personalDataService.findOne(varId);

		try {
			this.personalDataService.delete(personalData);
			result = new ModelAndView("redirect:/personalData/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(personalData, "personalData.commit.error");
		}
		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		PersonalData personalData;

		personalData = this.personalDataService.findOne(varId);

		result = new ModelAndView("personalData/display");
		result.addObject("personalData", personalData);
		result.addObject("requestURI", "personalData/display.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final PersonalData personalData) {
		ModelAndView result;

		result = this.createEditModelAndView(personalData, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PersonalData personalData, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("personalData/edit");
		result.addObject("personalData", personalData);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "personalData/edit.do");

		return result;

	}
}
