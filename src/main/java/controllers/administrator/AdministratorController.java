/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import java.util.Arrays;
import java.util.Collection;

import javax.validation.ConstraintDefinitionException;
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
import services.AdministratorService;
import services.ApplicationService;
import services.CompanyService;
import services.ConfigurationService;
import services.CurriculumService;
import services.FinderService;
import services.HackerService;
import services.PositionService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Configuration;
import forms.FormObjectAdministrator;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	//Services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private ConfigurationService	configurationService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		FormObjectAdministrator foa;

		final Configuration config = this.configurationService.findAll().iterator().next();

		foa = new FormObjectAdministrator();
		foa.setPhone(config.getCountryCode());

		result = this.createEditModelAndView(foa);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		Administrator administrator;
		administrator = (Administrator) this.actorService.findByPrincipal();
		Assert.notNull(administrator);
		result = this.editModelAndView(administrator);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Administrator administrator, final BindingResult binding) {
		ModelAndView result;

		try {
			administrator = this.administratorService.reconstructPruned(administrator, binding);
		} catch (final ConstraintDefinitionException oops) {
			return this.editModelAndView(administrator, "administrator.expirationDate.error");
		} catch (final ValidationException oops) {
			return this.editModelAndView(administrator);
		} catch (final Throwable oops) {
			return this.editModelAndView(administrator, "administrator.commit.error");
		}
		try {
			this.administratorService.save(administrator);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.editModelAndView(administrator, "administrator.commit.error");
		}
		return result;
	}
	//Create POST
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "create")
	public ModelAndView save(final FormObjectAdministrator foa, final BindingResult binding) {
		ModelAndView result;
		Administrator administrator;

		try {
			administrator = this.administratorService.reconstruct(foa, binding);
		} catch (final ConstraintDefinitionException oops) {
			return this.createEditModelAndView(foa, "administrator.expirationDate.error");
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(foa, "administrator.validation.error");
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(foa, "administrator.reconstruct.error");
		}
		try {
			this.administratorService.save(administrator);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(foa, "administrator.commit.error");
		}
		return result;
	}

	//Dashboard

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;

		result = new ModelAndView("administrator/dashboard");

		result.addObject("avgMinMaxStddevPositionsPerCompany", Arrays.toString(this.positionService.avgMinMaxStddevPositionsPerCompany()));
		result.addObject("avgMinMaxStddevApplicationsPerHacker", Arrays.toString(this.applicationService.avgMinMaxStddevApplicationsPerHacker()));
		result.addObject("companiesWithMoreOfferedPossitions", this.companyService.companiesWithMoreOfferedPossitions());
		result.addObject("hackersWithMoreApplications", this.hackerService.hackersWithMoreApplications());
		result.addObject("avgMinMaxStddevOfferedSalaries", Arrays.toString(this.positionService.avgMinMaxStddevOfferedSalaries()));
		result.addObject("bestAndWorstPositions", this.positionService.bestAndWorstPositions());
		result.addObject("minMaxAvgStddevCurriculaPerHacker", Arrays.toString(this.curriculumService.minMaxAvgStddevCurriculaPerHacker()));
		result.addObject("minMaxAvgStddevResultsFinders", Arrays.toString(this.finderService.minMaxAvgStddevResultsFinders()));
		result.addObject("ratioEmptyVersusNonEmptyFinders", this.finderService.ratioEmptyVersusNonEmptyFinders());

		result.addObject("requestURI", "administrator/dashboard.do");

		return result;
	}
	//Listing suspicious actors

	@RequestMapping(value = "/bannableList", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<Actor> actors = this.actorService.bannableActors();

		result = new ModelAndView("administrator/bannableList");
		result.addObject("actors", actors);
		result.addObject("requestURI", "administrator/bannableList.do");

		return result;
	}

	//Ban and unban actors

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int varId) {
		final ModelAndView result;
		final Actor actor = this.actorService.findOne(varId);

		final Collection<Actor> actors = this.actorService.bannableActors();

		if (actor.getId() == this.actorService.findByPrincipal().getId()) {
			result = new ModelAndView("administrator/bannableList");
			result.addObject("actors", actors);
			result.addObject("message", "administrator.selfBan.error");
			result.addObject("requestURI", "administrator/bannableList.do");

			return result;
		} else {

			if (actors.contains(actor))
				this.actorService.BanOrUnban(actor.getId());

			result = new ModelAndView("redirect:/administrator/bannableList.do");

			return result;
		}
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final FormObjectAdministrator foa) {
		ModelAndView result;

		result = this.createEditModelAndView(foa, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormObjectAdministrator foa, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("administrator/create");
		result.addObject("foa", foa);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "administrator/create.do");

		return result;

	}

	protected ModelAndView editModelAndView(final Administrator administrator) {
		ModelAndView result;

		result = this.editModelAndView(administrator, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Administrator administrator, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", administrator);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "administrator/edit.do");

		return result;
	}
}
