
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CompanyService;
import services.ConfigurationService;
import domain.Company;
import domain.Configuration;
import forms.FormObjectCompany;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

	//Services

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	//Listing all

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Company> companies;

		companies = this.companyService.findAll();

		result = new ModelAndView("company/list");
		result.addObject("companies", companies);
		result.addObject("requestURI", "company/list.do");

		return result;
	}

	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final Company company = this.companyService.findOne(varId);

		if (company == null)
			return new ModelAndView("redirect:/welcome/index.do");

		result = new ModelAndView("company/display");
		result.addObject("company", company);
		result.addObject("requestURI", "company/display.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final FormObjectCompany foc;
		final Configuration config = this.configurationService.findAll().iterator().next();

		foc = new FormObjectCompany();
		foc.setPhone(config.getCountryCode());
		result = this.createEditModelAndView(foc);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		Company company;

		company = (Company) this.actorService.findByPrincipal();
		Assert.notNull(company);
		result = this.editModelAndView(company);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "create")
	public ModelAndView save(final FormObjectCompany foc, final BindingResult binding) {
		ModelAndView result;
		Company company;

		try {
			company = this.companyService.reconstruct(foc, binding);
		} catch (final ConstraintDefinitionException oops) {
			return this.createEditModelAndView(foc, "company.expirationDate.error");
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(foc, "company.validation.error");
		} catch (final Throwable oops) {
			return this.createEditModelAndView(foc, "company.reconstruct.error");
		}
		try {
			this.companyService.save(company);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(foc, "company.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Company company, final BindingResult binding) {
		ModelAndView result;

		try {
			company = this.companyService.reconstructPruned(company, binding);
		} catch (final ConstraintDefinitionException oops) {
			return this.editModelAndView(company, "company.expirationDate.error");
		} catch (final ValidationException oops) {
			return this.editModelAndView(company);
		} catch (final Throwable oops) {
			return this.editModelAndView(company, "company.commit.error");
		}

		try {
			this.companyService.save(company);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.editModelAndView(company, "company.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final FormObjectCompany foc) {
		ModelAndView result;

		result = this.createEditModelAndView(foc, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormObjectCompany foc, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("company/create");
		result.addObject("foc", foc);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "company/create.do");

		return result;
	}

	protected ModelAndView editModelAndView(final Company company) {
		ModelAndView result;

		result = this.editModelAndView(company, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Company company, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("company/edit");
		result.addObject("company", company);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "company/edit.do");

		return result;
	}

}
