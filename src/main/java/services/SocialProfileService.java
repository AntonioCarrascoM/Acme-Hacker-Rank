
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SocialProfileRepository;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	// Managed service
	@Autowired
	private SocialProfileRepository	socialProfileRepository;

	// Supporting service

	//@Autowired
	//private ActorService			actorService;

	@Autowired
	private Validator				validator;


	// Simple CRUD methods

	public SocialProfile create() {
		final SocialProfile sp = new SocialProfile();
		//final Actor a = this.actorService.findByPrincipal();
		//sp.setActor(a);

		return sp;
	}

	public SocialProfile findOne(final int id) {
		Assert.notNull(id);

		return this.socialProfileRepository.findOne(id);
	}

	public Collection<SocialProfile> findAll() {
		return this.socialProfileRepository.findAll();
	}

	public SocialProfile save(final SocialProfile sp) {
		Assert.notNull(sp);

		//Assertion that the user deleting this social profile has the correct privilege.
		//Assert.isTrue(this.actorService.findByPrincipal().getId() == sp.getActor().getId());

		final SocialProfile saved = this.socialProfileRepository.save(sp);

		return saved;
	}

	public void delete(final SocialProfile sp) {
		Assert.notNull(sp);

		//Assertion that the user deleting this social identity has the correct privilege.
		//Assert.isTrue(this.actorService.findByPrincipal().getId() == sp.getActor().getId());

		this.socialProfileRepository.delete(sp);

	}

	//Reconstruct

	public SocialProfile reconstruct(final SocialProfile sp, final BindingResult binding) {
		SocialProfile result;

		if (sp.getId() == 0)
			result = this.create();
		else
			result = this.findOne(sp.getId());

		result.setProfileLink(sp.getProfileLink());
		result.setNick(sp.getNick());
		result.setSocialNetwork(sp.getSocialNetwork());

		//Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getActor().getId());

		this.validator.validate(result, binding);

		return result;

	}
}
