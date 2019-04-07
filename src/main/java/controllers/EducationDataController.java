
package controllers;

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
import services.EducationDataService;
import domain.EducationData;

@Controller
@RequestMapping("/educationData")
public class EducationDataController extends AbstractController {

	//Services

	@Autowired
	private EducationDataService	educationDataService;

	@Autowired
	private ActorService			actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<EducationData> educationDatas;

		educationDatas = this.educationDataService.findAll();

		result = new ModelAndView("educationData/list");
		result.addObject("educationDatas", educationDatas);
		result.addObject("requestURI", "educationData/list.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int educationDataId) {
		ModelAndView result;
		EducationData educationData;

		educationData = this.educationDataService.findOne(educationDataId);

		result = new ModelAndView("educationData/display");
		result.addObject("educationData", educationData);
		result.addObject("requestURI", "educationData/display.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		final ModelAndView result;
		EducationData educationData;

		educationData = this.educationDataService.create(curriculumId);
		result = this.createEditModelAndView(educationData);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationDataId) {
		final ModelAndView result;
		EducationData educationData;

		educationData = this.educationDataService.findOne(educationDataId);
		Assert.notNull(educationData);
		result = this.createEditModelAndView(educationData);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(EducationData educationData, final int curriculumId, final BindingResult binding) {
		ModelAndView result;

		try {
			educationData = this.educationDataService.reconstruct(educationData, curriculumId, binding);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(educationData, "educationData.commit.error");
		}

		if (binding.hasErrors())
			result = this.createEditModelAndView(educationData);
		else
			try {
				this.educationDataService.save(educationData);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(educationData, "educationData.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(EducationData educationData, final BindingResult binding) {
		ModelAndView result;

		educationData = this.educationDataService.findOne(educationData.getId());

		if (educationData.getCurriculum().getHacker().getId() != this.actorService.findByPrincipal().getId())
			result = this.createEditModelAndView(educationData, "educationData.delete.error");
		else
			try {
				this.educationDataService.delete(educationData);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(educationData, "educationData.commit.error");
			}
		return result;
	}

	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final EducationData educationData = this.educationDataService.findOne(varId);

		try {
			this.educationDataService.delete(educationData);
			result = new ModelAndView("redirect:/educationData/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(educationData, "educationData.commit.error");
		}
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
		result.addObject("requestURI", "educationData/edit.do");

		return result;

	}
}
