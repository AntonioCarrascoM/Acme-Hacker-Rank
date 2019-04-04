
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.ActorService;
import services.SocialProfileService;
import domain.SocialProfile;

@Controller
@RequestMapping("/socialProfile")
public class SocialProfileController extends AbstractController {

	//Services

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private ActorService			actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<SocialProfile> socialProfiles;

		socialProfiles = this.socialProfileService.socialProfilesFromActor(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("socialProfile/list");
		result.addObject("socialProfiles", socialProfiles);
		result.addObject("requestURI", "socialProfile/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.create();
		result = this.createEditModelAndView(socialProfile);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialProfileId) {
		final ModelAndView result;
		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.findOne(socialProfileId);
		Assert.notNull(socialProfile);
		result = this.createEditModelAndView(socialProfile);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView result;

		try {
			socialProfile = this.socialProfileService.reconstruct(socialProfile, binding);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(socialProfile, "socialProfile.commit.error");
		}

		if (binding.hasErrors())
			result = this.createEditModelAndView(socialProfile);
		else
			try {
				this.socialProfileService.save(socialProfile);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(socialProfile, "socialProfile.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView result;

		socialProfile = this.socialProfileService.findOne(socialProfile.getId());

		if (socialProfile.getActor().getId() != this.actorService.findByPrincipal().getId())
			result = this.createEditModelAndView(socialProfile, "socialProfile.delete.error");
		else
			try {
				this.socialProfileService.delete(socialProfile);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(socialProfile, "socialProfile.commit.error");
			}
		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int socialProfileId) {
		ModelAndView result;
		Collection<SocialProfile> socialProfiles;
		SocialProfile socialProfile;

		result = new ModelAndView("socialProfile/list");
		socialProfiles = this.socialProfileService.socialProfilesFromActor(this.actorService.findByPrincipal().getId());

		socialProfile = this.socialProfileService.findOne(socialProfileId);
		if (socialProfile.getActor().getId() != this.actorService.findByPrincipal().getId())
			result.addObject("message", "socialProfile.delete.error");
		else
			try {
				this.socialProfileService.delete(socialProfile);
				socialProfiles = this.socialProfileService.socialProfilesFromActor(this.actorService.findByPrincipal().getId());

				result.addObject("socialProfiles", socialProfiles);
				result.addObject("requestURI", "socialProfile/list.do");
			} catch (final Throwable oops) {
				result = this.listModelAndView(socialProfile, "socialProfile.delete.error");
			}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final SocialProfile socialProfile) {
		ModelAndView result;

		result = this.createEditModelAndView(socialProfile, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialProfile socialProfile, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("socialProfile/edit");
		result.addObject("socialProfile", socialProfile);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "socialProfile/edit.do");

		return result;

	}

	protected ModelAndView listModelAndView(final SocialProfile socialProfile, final String messageCode) {
		ModelAndView result;
		Collection<SocialProfile> socialProfiles;

		socialProfiles = this.socialProfileService.socialProfilesFromActor(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("socialProfile/list");
		result.addObject("socialProfiles", socialProfiles);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "socialProfile/list.do");

		return result;

	}

}
