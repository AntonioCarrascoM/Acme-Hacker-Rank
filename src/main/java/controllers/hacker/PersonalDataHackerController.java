
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
import services.ConfigurationService;
import services.CurriculumService;
import services.PersonalDataService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.PersonalData;

@Controller
@RequestMapping("personalData/hacker")
public class PersonalDataHackerController extends AbstractController {

	//Services

	@Autowired
	private PersonalDataService		personalDataService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private ConfigurationService	configurationService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createOrDisplay(@RequestParam final int varId) {
		PersonalData personalData;
		final Curriculum c = this.curriculumService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != c.getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		personalData = this.personalDataService.create(varId);
		personalData.setPhoneNumber(this.configurationService.findAll().iterator().next().getCountryCode());
		return this.createEditModelAndView(personalData);
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		PersonalData personalData = this.personalDataService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != personalData.getCurriculum().getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		personalData = this.personalDataService.findOne(varId);
		Assert.notNull(personalData);
		result = this.createEditModelAndView(personalData);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(PersonalData personalData, final BindingResult binding) {
		ModelAndView result;
		final Collection<Curriculum> curriculums = this.curriculumService.getCurriculumsForHacker(this.actorService.findByPrincipal().getId());

		if (!curriculums.contains(personalData.getCurriculum()))
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			personalData = this.personalDataService.reconstruct(personalData, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(personalData);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(personalData, "personalData.commit.error");
		}

		try {
			final PersonalData saved = this.personalDataService.save(personalData);
			result = new ModelAndView("redirect:display.do?varId=" + saved.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(personalData, "personalData.commit.error");
		}
		return result;
	}
	//Deleting

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(PersonalData personalData, final BindingResult binding) {
		ModelAndView result;
		final Collection<Curriculum> curriculums = this.curriculumService.getCurriculumsForHacker(this.actorService.findByPrincipal().getId());

		if (!curriculums.contains(personalData.getCurriculum()))
			return new ModelAndView("redirect:/welcome/index.do");

		personalData = this.personalDataService.findOne(personalData.getId());

		try {
			this.personalDataService.delete(personalData);
			result = new ModelAndView("redirect:/curriculum/hacker/display.do?varId=" + personalData.getCurriculum().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(personalData, "personalData.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final PersonalData personalData = this.personalDataService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != personalData.getCurriculum().getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.personalDataService.delete(personalData);
			result = new ModelAndView("redirect:/curriculum/hacker/display.do?varId=" + personalData.getCurriculum().getId());

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

		if (this.actorService.findByPrincipal().getId() != personalData.getCurriculum().getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

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
		result.addObject("requestURI", "personalData/hacker/edit.do");

		return result;

	}
}
