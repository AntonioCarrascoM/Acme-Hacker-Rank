
package controllers;

import java.util.Collection;

import javax.validation.ConstraintDefinitionException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.HackerService;
import domain.Configuration;
import domain.Hacker;
import forms.FormObjectHacker;

@Controller
@RequestMapping("hacker")
public class HackerController extends AbstractController {

	//Services

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final FormObjectHacker foh;
		final Configuration config = this.configurationService.findAll().iterator().next();

		foh = new FormObjectHacker();
		foh.setPhone(config.getCountryCode());
		result = this.createEditModelAndView(foh);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		Hacker hacker;

		hacker = (Hacker) this.actorService.findByPrincipal();
		Assert.notNull(hacker);
		result = this.editModelAndView(hacker);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "create")
	public ModelAndView save(final FormObjectHacker foh, final BindingResult binding) {
		ModelAndView result;
		Hacker hacker;

		try {
			hacker = this.hackerService.reconstruct(foh, binding);
		} catch (final ConstraintDefinitionException oops) {
			return this.createEditModelAndView(foh, "hacker.expirationDate.error");
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(foh, "hacker.validation.error");
		} catch (final Throwable oops) {
			return this.createEditModelAndView(foh, "hacker.reconstruct.error");
		}
		try {
			this.hackerService.save(hacker);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(foh, "hacker.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Hacker hacker, final BindingResult binding) {
		ModelAndView result;

		try {
			hacker = this.hackerService.reconstructPruned(hacker, binding);
		} catch (final ConstraintDefinitionException oops) {
			return this.editModelAndView(hacker, "hacker.expirationDate.error");
		} catch (final ValidationException oops) {
			return this.editModelAndView(hacker);
		} catch (final Throwable oops) {
			return this.editModelAndView(hacker, "hacker.commit.error");
		}

		try {
			this.hackerService.save(hacker);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.editModelAndView(hacker, "hacker.commit.error");
		}
		return result;
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Hacker> hackers;

		hackers = this.hackerService.findAll();

		result = new ModelAndView("hacker/list");
		result.addObject("hackers", hackers);
		result.addObject("requestURI", "hacker/list.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final FormObjectHacker foh) {
		ModelAndView result;

		result = this.createEditModelAndView(foh, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormObjectHacker foh, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("hacker/create");
		result.addObject("foh", foh);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "hacker/create.do");

		return result;
	}

	protected ModelAndView editModelAndView(final Hacker hacker) {
		ModelAndView result;

		result = this.editModelAndView(hacker, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Hacker hacker, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("hacker/edit");
		result.addObject("hacker", hacker);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "hacker/edit.do");

		return result;
	}

}
