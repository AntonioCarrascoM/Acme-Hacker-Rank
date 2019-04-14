
package controllers.hacker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.FinderService;
import services.HackerService;
import services.PositionService;
import controllers.AbstractController;
import domain.Finder;
import domain.Hacker;
import domain.Position;

@Controller
@RequestMapping("finder/hacker")
public class FinderHackerController extends AbstractController {

	//Services

	@Autowired
	private FinderService			finderService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private HackerService			hackerService;


	//Edition of parameters

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		Finder finder;

		finder = this.finderService.findPrincipalFinder();
		Assert.notNull(finder);

		Collection<Position> positions = finder.getPositions();

		final Long millis = this.configurationService.findAll().iterator().next().getExpireFinderMinutes() * 60000L;

		if (finder.getMoment() == null || (new Date(System.currentTimeMillis()).getTime() - finder.getMoment().getTime()) > millis)
			positions = this.finderService.limitResults(this.positionService.findAll());

		result = this.createEditModelAndView(finder);
		result.addObject("positions", positions);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		Collection<Position> positions = new ArrayList<Position>();
		final Hacker h = this.hackerService.hackerByFinder(finder.getId());

		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				positions = this.finderService.find(finder);

				finder.setPositions(positions);

				final Finder saved = this.finderService.save(finder);
				h.setFinder(saved);
				this.hackerService.save(h);

				return this.edit();

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("finder/edit");
		result.addObject("finder", finder);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "finder/hacker/edit.do");

		return result;

	}
}
