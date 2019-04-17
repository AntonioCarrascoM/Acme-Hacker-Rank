
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PositionService;
import domain.Position;

@Controller
@RequestMapping("position")
public class PositionController extends AbstractController {

	//Services

	@Autowired
	private PositionService	positionService;


	//Listing all

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Position> positions;

		positions = this.positionService.getPublicPositions();

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		result.addObject("requestURI", "position/list.do");

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam final String keyword) {
		final ModelAndView result;
		final Collection<Position> positions;

		if (keyword == null || keyword.isEmpty())
			return this.list();
		else
			positions = this.positionService.searchPosition(keyword);

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		result.addObject("requestURI", "position/list.do");
		result.addObject("keyword", keyword);

		return result;
	}

	//Listing by company

	@RequestMapping(value = "/listByCompany", method = RequestMethod.GET)
	public ModelAndView listByCompany(@RequestParam final int varId) {
		final ModelAndView result;
		final Collection<Position> positions;

		positions = this.positionService.getPublicPositionsForCompany(varId);

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		result.addObject("requestURI", "position/list.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final Position position = this.positionService.findOne(varId);
		Assert.notNull(position);

		result = new ModelAndView("position/display");
		result.addObject("position", position);
		result.addObject("requestURI", "position/display.do");

		return result;
	}

}
