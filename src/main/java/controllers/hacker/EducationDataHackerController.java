
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
import services.CurriculumService;
import services.EducationDataService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.EducationData;

@Controller
@RequestMapping("educationData/hacker")
public class EducationDataHackerController extends AbstractController {

	//Services

	@Autowired
	private EducationDataService	educationDataService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private ActorService			actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int varId) {
		final ModelAndView result;
		EducationData educationData;
		final Curriculum c = this.curriculumService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != c.getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		educationData = this.educationDataService.create(varId);
		result = this.createEditModelAndView(educationData);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final EducationData educationData = this.educationDataService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != educationData.getCurriculum().getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		Assert.notNull(educationData);
		result = this.createEditModelAndView(educationData);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(EducationData educationData, final BindingResult binding) {
		ModelAndView result;

		final Collection<Curriculum> curriculums = this.curriculumService.getCurriculumsForHacker(this.actorService.findByPrincipal().getId());

		if (!curriculums.contains(educationData.getCurriculum()))
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			educationData = this.educationDataService.reconstruct(educationData, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(educationData);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(educationData, "educationData.commit.error");
		}
		try {
			final EducationData saved = this.educationDataService.save(educationData);
			result = new ModelAndView("redirect:/curriculum/hacker/display.do?varId=" + saved.getCurriculum().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(educationData, "educationData.commit.error");
		}
		return result;
	}

	//Deleting

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(EducationData educationData, final BindingResult binding) {
		ModelAndView result;

		final Collection<Curriculum> curriculums = this.curriculumService.getCurriculumsForHacker(this.actorService.findByPrincipal().getId());

		if (!curriculums.contains(educationData.getCurriculum()))
			return new ModelAndView("redirect:/welcome/index.do");

		educationData = this.educationDataService.findOne(educationData.getId());

		try {
			this.educationDataService.delete(educationData);
			result = new ModelAndView("redirect:/curriculum/hacker/display.do?varId=" + educationData.getCurriculum().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(educationData, "educationData.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final EducationData educationData = this.educationDataService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != educationData.getCurriculum().getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.educationDataService.delete(educationData);
			result = new ModelAndView("redirect:/curriculum/hacker/display.do?varId=" + educationData.getCurriculum().getId());

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(educationData, "educationData.commit.error");
		}
		return result;
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<EducationData> educationDatas;

		final Curriculum c = this.curriculumService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != c.getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		educationDatas = this.curriculumService.getEducationDataForCurriculum(c.getId());

		result = new ModelAndView("educationData/list");
		result.addObject("curriculum", c);
		result.addObject("educationDatas", educationDatas);
		result.addObject("requestURI", "educationData/list.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		EducationData educationData;

		educationData = this.educationDataService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != educationData.getCurriculum().getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("educationData/display");
		result.addObject("educationData", educationData);
		result.addObject("requestURI", "educationData/display.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final EducationData educationData) {
		ModelAndView result;

		result = this.createEditModelAndView(educationData, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationData educationData, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("educationData/edit");
		result.addObject("educationData", educationData);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "educationData/hacker/edit.do");

		return result;

	}
}
