
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CurriculumRepository;
import domain.Curriculum;
import domain.EducationData;
import domain.Hacker;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;

@Service
@Transactional
public class CurriculumService {

	//Managed repository

	@Autowired
	private CurriculumRepository		curriculumRepository;

	//Supporting services

	@Autowired
	private ActorService				actorService;

	@Autowired
	private PersonalDataService			personalDataService;

	@Autowired
	private PositionDataService			positionDataService;

	@Autowired
	private EducationDataService		educationDataService;

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	private Validator					validator;


	//Simple CRUD methods

	public Curriculum create() {
		final Curriculum c = new Curriculum();

		final Hacker h = (Hacker) this.actorService.findByPrincipal();
		c.setHacker(h);

		return c;
	}

	public Curriculum findOne(final int id) {
		Assert.notNull(id);

		return this.curriculumRepository.findOne(id);
	}

	public Collection<Curriculum> findAll() {
		return this.curriculumRepository.findAll();
	}

	public Curriculum save(final Curriculum c) {
		Assert.notNull(c);

		//Assertion that the user modifying this curriculum has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == c.getHacker().getId());

		final Curriculum saved = this.curriculumRepository.save(c);

		return saved;
	}

	public void delete(final Curriculum c) {
		Assert.notNull(c);

		//Assertion that the user deleting this curriculum has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == c.getHacker().getId());

		if (this.getPersonalDataForCurriculum(c.getId()) != null)
			this.personalDataService.delete(this.getPersonalDataForCurriculum(c.getId()));

		if (!(this.getPositionDataForCurriculum(c.getId()).isEmpty()))
			for (final PositionData pd : this.getPositionDataForCurriculum(c.getId()))
				this.positionDataService.delete(pd);

		if (!(this.getEducationDataForCurriculum(c.getId()).isEmpty()))
			for (final EducationData ed : this.getEducationDataForCurriculum(c.getId()))
				this.educationDataService.delete(ed);

		if (!(this.getMiscellaneousDataForCurriculum(c.getId()).isEmpty()))
			for (final MiscellaneousData md : this.getMiscellaneousDataForCurriculum(c.getId()))
				this.miscellaneousDataService.delete(md);

		this.curriculumRepository.delete(c);
	}

	//Reconstruct
	public Curriculum reconstruct(final Curriculum c, final BindingResult binding) {
		Assert.notNull(c);
		Curriculum result;

		if (c.getId() == 0)
			result = this.create();
		else
			result = this.findOne(c.getId());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this curriculum has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == c.getHacker().getId());

		return result;

	}

	//Copy method

	public Curriculum copy(final Curriculum orig) {
		final Curriculum copy = this.create();

		this.educationDataService.copy(orig, copy);
		this.miscellaneousDataService.copy(orig, copy);
		this.personalDataService.copy(orig, copy);
		this.positionDataService.copy(orig, copy);

		return copy;
	}

	//Time for motion and queries

	//Retrieves the curriculum for a certain hacker
	public Collection<Curriculum> getCurriculumsForHacker(final int id) {
		return this.curriculumRepository.getCurriculumsForHacker(id);
	}

	//Listing of personal datas for a certain curriculum
	public PersonalData getPersonalDataForCurriculum(final int id) {
		return this.curriculumRepository.getPersonalDataForCurriculum(id);
	}

	//Listing of position datas for a certain curriculum
	public Collection<PositionData> getPositionDataForCurriculum(final int id) {
		return this.curriculumRepository.getPositionDataForCurriculum(id);
	}

	//Listing of education datas for a certain curriculum
	public Collection<EducationData> getEducationDataForCurriculum(final int id) {
		return this.curriculumRepository.getEducationDataForCurriculum(id);
	}
	//Listing of miscellaneous datas for a certain curriculum
	public Collection<MiscellaneousData> getMiscellaneousDataForCurriculum(final int id) {
		return this.curriculumRepository.getMiscellaneousDataForCurriculum(id);
	}

	//The minimum, the maximum, the average, and the standard deviation of the number of curricula per hacker
	public Double[] minMaxAvgStddevCurriculaPerHacker() {
		return this.curriculumRepository.minMaxAvgStddevCurriculaPerHacker();
	}

}
