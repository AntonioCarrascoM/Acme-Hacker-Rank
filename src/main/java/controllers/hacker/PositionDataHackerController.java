
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
import services.PositionDataService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.PositionData;

@Controller
@RequestMapping("positionData/hacker")
public class PositionDataHackerController extends AbstractController {

	//Services

	@Autowired
	private PositionDataService	positionDataService;

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private ActorService		actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int varId) {
		final ModelAndView result;
		PositionData positionData;
		final Curriculum c = this.curriculumService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != c.getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		positionData = this.positionDataService.create(varId);
		result = this.createEditModelAndView(positionData);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final PositionData positionData = this.positionDataService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != positionData.getCurriculum().getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		Assert.notNull(positionData);
		result = this.createEditModelAndView(positionData);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(PositionData positionData, final BindingResult binding) {
		ModelAndView result;

		final Collection<Curriculum> curriculums = this.curriculumService.getCurriculumsForHacker(this.actorService.findByPrincipal().getId());

		if (!curriculums.contains(positionData.getCurriculum()))
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			positionData = this.positionDataService.reconstruct(positionData, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(positionData);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(positionData, "positionData.commit.error");
		}
		try {
			final PositionData saved = this.positionDataService.save(positionData);
			result = new ModelAndView("redirect:/curriculum/hacker/display.do?varId=" + saved.getCurriculum().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(positionData, "positionData.commit.error");
		}
		return result;
	}

	//Deleting

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(PositionData positionData, final BindingResult binding) {
		ModelAndView result;

		final Collection<Curriculum> curriculums = this.curriculumService.getCurriculumsForHacker(this.actorService.findByPrincipal().getId());

		if (!curriculums.contains(positionData.getCurriculum()))
			return new ModelAndView("redirect:/welcome/index.do");

		positionData = this.positionDataService.findOne(positionData.getId());

		try {
			this.positionDataService.delete(positionData);
			result = new ModelAndView("redirect:/curriculum/hacker/display.do?varId=" + positionData.getCurriculum().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(positionData, "positionData.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final PositionData positionData = this.positionDataService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != positionData.getCurriculum().getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.positionDataService.delete(positionData);
			result = new ModelAndView("redirect:/curriculum/hacker/display.do?varId=" + positionData.getCurriculum().getId());

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(positionData, "positionData.commit.error");
		}
		return result;
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<PositionData> positionDatas;

		final Curriculum c = this.curriculumService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != c.getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		positionDatas = this.curriculumService.getPositionDataForCurriculum(c.getId());

		result = new ModelAndView("positionData/list");
		result.addObject("curriculum", c);
		result.addObject("positionDatas", positionDatas);
		result.addObject("requestURI", "positionData/list.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		PositionData positionData;

		positionData = this.positionDataService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != positionData.getCurriculum().getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("positionData/display");
		result.addObject("positionData", positionData);
		result.addObject("requestURI", "positionData/display.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final PositionData positionData) {
		ModelAndView result;

		result = this.createEditModelAndView(positionData, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PositionData positionData, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("positionData/edit");
		result.addObject("positionData", positionData);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "positionData/hacker/edit.do");

		return result;

	}
}
