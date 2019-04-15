
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
import services.MiscellaneousDataService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.MiscellaneousData;

@Controller
@RequestMapping("miscellaneousData/hacker")
public class MiscellaneousDataHackerController extends AbstractController {

	//Services

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private ActorService				actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int varId) {
		final ModelAndView result;
		MiscellaneousData miscellaneousData;
		final Curriculum c = this.curriculumService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != c.getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		miscellaneousData = this.miscellaneousDataService.create(varId);
		result = this.createEditModelAndView(miscellaneousData);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final MiscellaneousData miscellaneousData = this.miscellaneousDataService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != miscellaneousData.getCurriculum().getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		Assert.notNull(miscellaneousData);
		result = this.createEditModelAndView(miscellaneousData);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(MiscellaneousData miscellaneousData, final BindingResult binding) {
		ModelAndView result;

		final Collection<Curriculum> curriculums = this.curriculumService.getCurriculumsForHacker(this.actorService.findByPrincipal().getId());

		if (!curriculums.contains(miscellaneousData.getCurriculum()))
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			miscellaneousData = this.miscellaneousDataService.reconstruct(miscellaneousData, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(miscellaneousData);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(miscellaneousData, "miscellaneousData.commit.error");
		}
		try {
			final MiscellaneousData saved = this.miscellaneousDataService.save(miscellaneousData);
			result = new ModelAndView("redirect:/curriculum/hacker/display.do?varId=" + saved.getCurriculum().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(miscellaneousData, "miscellaneousData.commit.error");
		}
		return result;
	}

	//Deleting

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(MiscellaneousData miscellaneousData, final BindingResult binding) {
		ModelAndView result;

		final Collection<Curriculum> curriculums = this.curriculumService.getCurriculumsForHacker(this.actorService.findByPrincipal().getId());

		if (!curriculums.contains(miscellaneousData.getCurriculum()))
			return new ModelAndView("redirect:/welcome/index.do");

		miscellaneousData = this.miscellaneousDataService.findOne(miscellaneousData.getId());

		try {
			this.miscellaneousDataService.delete(miscellaneousData);
			result = new ModelAndView("redirect:/curriculum/hacker/display.do?varId=" + miscellaneousData.getCurriculum().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(miscellaneousData, "miscellaneousData.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final MiscellaneousData miscellaneousData = this.miscellaneousDataService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != miscellaneousData.getCurriculum().getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.miscellaneousDataService.delete(miscellaneousData);
			result = new ModelAndView("redirect:/curriculum/hacker/display.do?varId=" + miscellaneousData.getCurriculum().getId());

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(miscellaneousData, "miscellaneousData.commit.error");
		}
		return result;
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<MiscellaneousData> miscellaneousDatas;

		final Curriculum c = this.curriculumService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != c.getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		miscellaneousDatas = this.curriculumService.getMiscellaneousDataForCurriculum(c.getId());

		result = new ModelAndView("miscellaneousData/list");
		result.addObject("curriculum", c);
		result.addObject("miscellaneousDatas", miscellaneousDatas);
		result.addObject("requestURI", "miscellaneousData/list.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		MiscellaneousData miscellaneousData;

		miscellaneousData = this.miscellaneousDataService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != miscellaneousData.getCurriculum().getHacker().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("miscellaneousData/display");
		result.addObject("miscellaneousData", miscellaneousData);
		result.addObject("requestURI", "miscellaneousData/display.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final MiscellaneousData miscellaneousData) {
		ModelAndView result;

		result = this.createEditModelAndView(miscellaneousData, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousData miscellaneousData, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("miscellaneousData/edit");
		result.addObject("miscellaneousData", miscellaneousData);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "miscellaneousData/hacker/edit.do");

		return result;

	}
}
