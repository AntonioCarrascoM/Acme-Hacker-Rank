
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PersonalDataRepository;
import domain.Curriculum;
import domain.PersonalData;

@Service
@Transactional
public class PersonalDataService {

	//Managed service

	@Autowired
	private PersonalDataRepository	personalDataRepository;

	//Supporting service

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	//Simple CRUD methods

	public PersonalData create(final int curriculumId) {
		final PersonalData er = new PersonalData();

		final Curriculum c = this.curriculumService.findOne(curriculumId);
		er.setCurriculum(c);

		return er;
	}

	public PersonalData findOne(final int id) {
		Assert.notNull(id);

		return this.personalDataRepository.findOne(id);
	}

	public Collection<PersonalData> findAll() {
		return this.personalDataRepository.findAll();
	}

	public PersonalData save(final PersonalData er) {
		Assert.notNull(er);

		//Assertion that the user modifying this education record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == er.getCurriculum().getHacker().getId());

		//Assertion the phone number has the correct pattern
		Assert.isTrue(this.actorService.checkPhone(er.getPhoneNumber()));

		final PersonalData saved = this.personalDataRepository.save(er);

		return saved;
	}
	public void delete(final PersonalData er) {
		Assert.notNull(er);

		//Assertion that the user deleting this education record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == er.getCurriculum().getHacker().getId());

		this.personalDataRepository.delete(er);
	}

	//Other methods---------------

	//Reconstruct method

	public PersonalData reconstruct(final PersonalData p, final int curriculumId, final BindingResult binding) {
		Assert.notNull(p);
		PersonalData result;

		if (p.getId() == 0)
			result = this.create(curriculumId);
		else
			result = this.personalDataRepository.findOne(p.getId());
		result.setFullName(p.getFullName());
		result.setStatement(p.getStatement());
		result.setPhoneNumber(p.getPhoneNumber());
		result.setGitHubProfile(p.getGitHubProfile());
		result.setLinkedInProfile(p.getLinkedInProfile());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getCurriculum().getHacker().getId());

		//Assertion the phone number has the correct pattern
		Assert.isTrue(this.actorService.checkPhone(result.getPhoneNumber()));

		return result;

	}

	//Copy method

	public void copy(final Curriculum orig, final Curriculum copy) {
		PersonalData nueva;
		final PersonalData pdOrig = this.curriculumService.getPersonalDataForCurriculum(orig.getId());
		if (pdOrig != null) {
			nueva = this.create(copy.getId());
			nueva.setFullName(pdOrig.getFullName());
			nueva.setStatement(pdOrig.getStatement());
			nueva.setPhoneNumber(pdOrig.getPhoneNumber());
			nueva.setGitHubProfile(pdOrig.getGitHubProfile());
			nueva.setLinkedInProfile(pdOrig.getGitHubProfile());
			this.save(nueva);
		}
	}

}
