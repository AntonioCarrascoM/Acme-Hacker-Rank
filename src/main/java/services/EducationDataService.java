
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EducationDataRepository;
import domain.Curriculum;
import domain.EducationData;

@Service
@Transactional
public class EducationDataService {

	//Managed service

	@Autowired
	private EducationDataRepository	educationDataRepository;

	//Supporting service

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	//Simple CRUD methods

	public EducationData create(final int curriculumId) {
		final EducationData er = new EducationData();

		final Curriculum c = this.curriculumService.findOne(curriculumId);
		er.setCurriculum(c);

		return er;
	}

	public EducationData findOne(final int id) {
		Assert.notNull(id);

		return this.educationDataRepository.findOne(id);
	}

	public Collection<EducationData> findAll() {
		return this.educationDataRepository.findAll();
	}

	public EducationData save(final EducationData er) {
		Assert.notNull(er);

		//Assertion that the user modifying this education record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == er.getCurriculum().getHacker().getId());

		//Assertion that the start date is before end date.
		Assert.isTrue(er.getStartDate().before(er.getEndDate()));

		final EducationData saved = this.educationDataRepository.save(er);

		return saved;
	}

	public void delete(final EducationData er) {
		Assert.notNull(er);

		//Assertion that the user deleting this education record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == er.getCurriculum().getHacker().getId());

		this.educationDataRepository.delete(er);
	}

	//Other methods--------------------------

	//Reconstruct

	public EducationData reconstruct(final EducationData p, final BindingResult binding) {
		Assert.notNull(p);
		EducationData result;

		if (p.getId() == 0)
			result = this.create(p.getCurriculum().getId());
		else
			result = this.educationDataRepository.findOne(p.getId());
		result.setDegree(p.getDegree());
		result.setInstitution(p.getInstitution());
		result.setMark(p.getMark());
		result.setStartDate(p.getStartDate());
		result.setEndDate(p.getEndDate());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getCurriculum().getHacker().getId());

		//Assertion that the start date is before end date.
		Assert.isTrue(result.getStartDate().before(result.getEndDate()));

		return result;

	}

	//Copy

	public void copy(final Curriculum orig, final Curriculum copy) {
		EducationData nueva;
		final Collection<EducationData> pdOrig = this.curriculumService.getEducationDataForCurriculum(orig.getId());
		if (pdOrig != null && !pdOrig.isEmpty())
			for (final EducationData pd : pdOrig) {
				nueva = this.create(copy.getId());
				nueva.setDegree(pd.getDegree());
				nueva.setInstitution(pd.getInstitution());
				nueva.setMark(pd.getInstitution());
				nueva.setStartDate(pd.getStartDate());
				nueva.setEndDate(pd.getEndDate());
				this.save(nueva);
			}
	}

}
