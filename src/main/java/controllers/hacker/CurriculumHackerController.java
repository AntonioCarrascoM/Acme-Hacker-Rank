
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
import domain.Hacker;

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
		final ModelAndView result;
		Curriculum curriculum;

		curriculum = this.curriculumService.create();
		result = this.createEditModelAndView(curriculum);

		return result;
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

		if (curriculum.getHacker().getId() != this.actorService.findByPrincipal().getId())
			result = this.createEditModelAndView(curriculum, "curriculum.delete.error");
		else
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
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Curriculum> curriculums;

		final Hacker h = (Hacker) this.actorService.findByPrincipal();
		curriculums = this.curriculumService.getCurriculumsForHacker(h.getId());

		if (curriculums == null || h.getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

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

		curriculum = this.curriculumService.findOne(varId);

		if (curriculum == null || curriculum.getHacker().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("curriculum/display");
		result.addObject("curriculum", curriculum);
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
