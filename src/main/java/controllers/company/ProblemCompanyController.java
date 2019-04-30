
package controllers.company;

import java.util.Collection;

import javax.validation.ConstraintDefinitionException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ProblemService;
import controllers.AbstractController;
import domain.Company;
import domain.Problem;

@Controller
@RequestMapping("problem/company")
public class ProblemCompanyController extends AbstractController {

	//Services

	@Autowired
	private ProblemService	problemService;

	@Autowired
	private ActorService	actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Problem> problems;

		final Company c = (Company) this.actorService.findByPrincipal();
		problems = this.problemService.problemsOfACompany(c.getId());

		result = new ModelAndView("problem/list");
		result.addObject("problems", problems);
		result.addObject("requestURI", "problem/company/list.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final Problem problem = this.problemService.findOne(varId);

		if (problem == null || problem.getCompany().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("problem/display");
		result.addObject("problem", problem);
		result.addObject("requestURI", "problem/company/display.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Problem problem;

		problem = this.problemService.create();
		result = this.createEditModelAndView(problem);

		return result;
	}

	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		final ModelAndView result;
		Problem problem;
		Collection<Problem> problems;

		problem = this.problemService.findOne(varId);

		if (problem == null || problem.getFinalMode() == true || problem.getCompany().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		this.problemService.delete(problem);

		result = new ModelAndView("problem/list");

		problems = this.problemService.problemsOfACompany(this.actorService.findByPrincipal().getId());

		result.addObject("problems", problems);
		result.addObject("requestURI", "problem/company/list");

		return result;
	}

	//Edit GET

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Problem problem;

		problem = this.problemService.findOne(varId);

		if (problem == null || problem.getCompany().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = this.createEditModelAndView(problem);

		return result;
	}

	//Edit POST

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Problem problem, final BindingResult binding) {
		ModelAndView result;

		try {
			problem = this.problemService.reconstruct(problem, binding);
		} catch (final ConstraintDefinitionException oops) {
			return this.createEditModelAndView(problem, "problem.attachments.error");
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(problem);
		} catch (final Throwable oops) {
			final Collection<Problem> problems = this.problemService.problemsOfACompany(this.actorService.findByPrincipal().getId());
			result = new ModelAndView("problem/list");
			result.addObject("problems", problems);
			result.addObject("requestURI", "problem/company/list");
			result.addObject("message", "problem.reconstruct.error");
			return result;
		}

		try {
			this.problemService.save(problem);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(problem, "problem.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Problem problem) {
		ModelAndView result;

		result = this.createEditModelAndView(problem, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Problem problem, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("problem/edit");
		result.addObject("problem", problem);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "problem/company/edit.do");

		return result;

	}
}
