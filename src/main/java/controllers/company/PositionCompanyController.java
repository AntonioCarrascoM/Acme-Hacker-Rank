
package controllers.company;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.PositionService;
import controllers.AbstractController;
import domain.Company;
import domain.Position;

@Controller
@RequestMapping("position/company")
public class PositionCompanyController extends AbstractController {

	//Services

	@Autowired
	private PositionService	positionService;

	@Autowired
	private ActorService	actorService;


	//	//Listing
	//
	//	@RequestMapping(value = "/list", method = RequestMethod.GET)
	//	public ModelAndView list() {
	//		final ModelAndView result;
	//		final Collection<Position> positions;
	//
	//		final Company c = (Company) this.actorService.findByPrincipal();
	//		positions = this.positionService.getAllPositionsForCompany(c.getId());
	//
	//		result = new ModelAndView("position/list");
	//		result.addObject("positions", positions);
	//		result.addObject("requestURI", "position/company/list.do");
	//
	//		return result;
	//	}
	//
	//	//Display
	//
	//	@RequestMapping(value = "/display", method = RequestMethod.GET)
	//	public ModelAndView display(@RequestParam final int varId) {
	//		final ModelAndView result;
	//
	//		final Position position = this.positionService.findOne(varId);
	//		Assert.notNull(position);
	//
	//		result = new ModelAndView("position/display");
	//		result.addObject("position", position);
	//		result.addObject("requestURI", "position/company/display.do");
	//
	//		return result;
	//	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Position position;

		position = this.positionService.create();
		result = this.createEditModelAndView(position);

		return result;
	}

	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		final ModelAndView result;
		Position position;
		Collection<Position> positions;

		position = this.positionService.findOne(varId);

		if (position == null || position.getFinalMode() == true || position.getCompany().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		this.positionService.delete(position);

		result = new ModelAndView("position/list");

		positions = this.positionService.getAllPositionsForCompany(this.actorService.findByPrincipal().getId());

		result.addObject("positions", positions);
		result.addObject("requestURI", "position/company/list");

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Position position, final BindingResult binding) {
		ModelAndView result;

		try {
			position = this.positionService.reconstruct(position, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(position);
		} catch (final Throwable oops) {
			final Collection<Position> positions = this.positionService.getAllPositionsForCompany(this.actorService.findByPrincipal().getId());
			result = new ModelAndView("position/list");
			result.addObject("positions", positions);
			result.addObject("requestURI", "position/company/list");
			result.addObject("message", "position.reconstruct.error");
			return result;
		}

		if (position.getId() != 0)
			result = this.createEditModelAndView(position);

		try {
			this.positionService.save(position);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(position, "position.commit.error");
		}
		return result;
	}

	//Other methods

	//Cancel
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int varId) {
		ModelAndView result;
		Collection<Position> positions;
		Position position;

		result = new ModelAndView("position/hacker/list");

		final Company c = (Company) this.actorService.findByPrincipal();
		positions = this.positionService.getAllPositionsForCompany(c.getId());
		position = this.positionService.findOne(varId);

		if (c.getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.positionService.cancel(position);
			positions = this.positionService.getAllPositionsForCompany(c.getId());
			result.addObject("positions", positions);
			result.addObject("requestURI", "position/company/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(position, "position.reject.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Position position) {
		ModelAndView result;

		result = this.createEditModelAndView(position, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Position position, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("position/edit");
		result.addObject("position", position);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "position/company/edit.do");

		return result;

	}
}
