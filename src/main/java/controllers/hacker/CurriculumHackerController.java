
package controllers.hacker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CurriculumService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.EducationData;
import domain.Hacker;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;

@Controller
@RequestMapping("curriculum/hacker")
public class CurriculumHackerController extends AbstractController {

	//Services

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private ActorService		actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		final Curriculum c = this.curriculumService.create();
		this.curriculumService.save(c);
		return new ModelAndView("redirect:list.do");
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int curriculumId) {
		final ModelAndView result;
		Curriculum curriculum;

		curriculum = this.curriculumService.findOne(curriculumId);

		if (curriculum == null || curriculum.getHacker().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = this.createEditModelAndView(curriculum);

		return result;
	}
	//Edit POST
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Curriculum curriculum, final BindingResult binding) {
		ModelAndView result;

		try {
			curriculum = this.curriculumService.reconstruct(curriculum, binding);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(curriculum, "curriculum.commit.error");
		}

		if (binding.hasErrors())
			result = this.createEditModelAndView(curriculum);
		else
			try {
				this.curriculumService.save(curriculum);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(curriculum, "curriculum.commit.error");
			}
		return result;
	}

	//Delete POST
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Curriculum curriculum, final BindingResult binding) {
		ModelAndView result;

		curriculum = this.curriculumService.findOne(curriculum.getId());

		try {
			this.curriculumService.delete(curriculum);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(curriculum, "curriculum.commit.error");
		}
		return result;
	}

	//Deleting
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final Curriculum curriculum = this.curriculumService.findOne(varId);

		if (curriculum == null || curriculum.getHacker().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.curriculumService.delete(curriculum);
			result = new ModelAndView("redirect:/curriculum/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(curriculum, "curriculum.commit.error");
		}
		return result;
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Curriculum> curriculums;

		final Hacker h = (Hacker) this.actorService.findByPrincipal();
		curriculums = this.curriculumService.getCurriculumsForHacker(h.getId());

		result = new ModelAndView("curriculum/list");
		result.addObject("curriculums", curriculums);
		result.addObject("requestURI", "curriculum/list.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		Curriculum curriculum;
		Boolean hasPersonalData = true;

		curriculum = this.curriculumService.findOne(varId);
		final PersonalData personalData = this.curriculumService.getPersonalDataForCurriculum(curriculum.getId());
		if (personalData == null)
			hasPersonalData = false;

		final Collection<PositionData> pd = this.curriculumService.getPositionDataForCurriculum(curriculum.getId());
		final Collection<EducationData> ed = this.curriculumService.getEducationDataForCurriculum(curriculum.getId());
		final Collection<MiscellaneousData> md = this.curriculumService.getMiscellaneousDataForCurriculum(curriculum.getId());

		if (curriculum == null || curriculum.getHacker().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("curriculum/display");
		result.addObject("curriculum", curriculum);
		result.addObject("miscellaneousDatas", md);
		result.addObject("hasPersonalData", hasPersonalData);
		result.addObject("personalData", personalData);
		result.addObject("educationDatas", ed);
		result.addObject("positionDatas", pd);
		result.addObject("requestURI", "curriculum/display.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Curriculum curriculum) {
		ModelAndView result;

		result = this.createEditModelAndView(curriculum, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Curriculum curriculum, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curriculum/edit");
		result.addObject("curriculum", curriculum);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "curriculum/edit.do");

		return result;

	}
}
