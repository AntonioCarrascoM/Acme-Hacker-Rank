
package controllers.hacker;

import java.util.Collection;

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
	public ModelAndView create(@RequestParam final int curriculumId) {
		final ModelAndView result;
		MiscellaneousData miscellaneousData;

		miscellaneousData = this.miscellaneousDataService.create(curriculumId);
		result = this.createEditModelAndView(miscellaneousData);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousDataId) {
		final ModelAndView result;
		MiscellaneousData miscellaneousData;

		miscellaneousData = this.miscellaneousDataService.findOne(miscellaneousDataId);
		Assert.notNull(miscellaneousData);
		result = this.createEditModelAndView(miscellaneousData);

		return result;
	}

	//Edit POST
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(MiscellaneousData miscellaneousData, final BindingResult binding) {
		ModelAndView result;

		try {
			miscellaneousData = this.miscellaneousDataService.reconstruct(miscellaneousData, binding);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(miscellaneousData, "miscellaneousData.commit.error");
		}

		if (binding.hasErrors())
			result = this.createEditModelAndView(miscellaneousData);
		else
			try {
				this.miscellaneousDataService.save(miscellaneousData);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(miscellaneousData, "miscellaneousData.commit.error");
			}
		return result;
	}

	//Delete POST
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(MiscellaneousData miscellaneousData, final BindingResult binding) {
		ModelAndView result;

		miscellaneousData = this.miscellaneousDataService.findOne(miscellaneousData.getId());

		if (miscellaneousData.getCurriculum().getHacker().getId() != this.actorService.findByPrincipal().getId())
			result = this.createEditModelAndView(miscellaneousData, "miscellaneousData.delete.error");
		else
			try {
				this.miscellaneousDataService.delete(miscellaneousData);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(miscellaneousData, "miscellaneousData.commit.error");
			}
		return result;
	}

	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final MiscellaneousData miscellaneousData = this.miscellaneousDataService.findOne(varId);

		try {
			this.miscellaneousDataService.delete(miscellaneousData);
			result = new ModelAndView("redirect:/miscellaneousData/list.do");

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
		miscellaneousDatas = this.curriculumService.getMiscellaneousDataForCurriculum(c.getId());

		result = new ModelAndView("miscellaneousData/list");
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
		result.addObject("requestURI", "miscellaneousData/edit.do");

		return result;

	}
}
