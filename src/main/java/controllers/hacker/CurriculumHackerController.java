
package controllers.hacker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
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

	@Autowired
	private ApplicationService	applicationService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		final Curriculum c = this.curriculumService.create();
		this.curriculumService.save(c);
		return new ModelAndView("redirect:list.do");
	}

	//Deleting
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result = new ModelAndView("curriculum/list");

		final Hacker h = (Hacker) this.actorService.findByPrincipal();
		final Collection<Curriculum> curriculums = this.curriculumService.getCurriculumsForHacker(h.getId());

		final Curriculum curriculum = this.curriculumService.findOne(varId);

		if (curriculum == null || curriculum.getHacker().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		if (!this.applicationService.applicationsWithCurriculum(curriculum.getId()).isEmpty()) {
			result.addObject("curriculums", curriculums);
			result.addObject("message", "curriculum.delete.error");
			return result;
		}
		try {
			this.curriculumService.delete(curriculum);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result.addObject("curriculums", curriculums);
			result.addObject("message", "curriculum.commit.error");
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

}
