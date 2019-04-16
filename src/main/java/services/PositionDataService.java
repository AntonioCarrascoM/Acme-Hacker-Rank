
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionDataRepository;
import domain.Curriculum;
import domain.PositionData;

@Service
@Transactional
public class PositionDataService {

	//Managed service

	@Autowired
	private PositionDataRepository	positionDataRepository;

	//Supporting service

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	//Simple CRUD methods

	public PositionData create(final int curriculumId) {
		final PositionData er = new PositionData();

		final Curriculum c = this.curriculumService.findOne(curriculumId);
		er.setCurriculum(c);

		return er;
	}

	public PositionData findOne(final int id) {
		Assert.notNull(id);

		return this.positionDataRepository.findOne(id);
	}

	public Collection<PositionData> findAll() {
		return this.positionDataRepository.findAll();
	}

	public PositionData save(final PositionData er) {
		Assert.notNull(er);

		//Assertion that the user modifying this education record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == er.getCurriculum().getHacker().getId());

		//Assertion that the start date is before end date.
		Assert.isTrue(er.getStartDate().before(er.getEndDate()));

		final PositionData saved = this.positionDataRepository.save(er);

		return saved;
	}

	public void delete(final PositionData er) {
		Assert.notNull(er);

		//Assertion that the user deleting this education record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == er.getCurriculum().getHacker().getId());

		this.positionDataRepository.delete(er);
	}

	//Other methods--------------------------

	//Reconstruct

	public PositionData reconstruct(final PositionData p, final BindingResult binding) {
		Assert.notNull(p);
		PositionData result;

		if (p.getId() == 0)
			result = this.create(p.getCurriculum().getId());
		else
			result = this.positionDataRepository.findOne(p.getId());
		result.setTitle(p.getTitle());
		result.setDescription(p.getDescription());
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

	public void flush() {
		this.positionDataRepository.flush();
	}

	//Copy

	public void copy(final Curriculum orig, final Curriculum copy) {
		PositionData nueva;
		final Collection<PositionData> pdOrig = this.curriculumService.getPositionDataForCurriculum(orig.getId());
		if (pdOrig != null && !pdOrig.isEmpty())
			for (final PositionData pd : pdOrig) {
				nueva = this.create(copy.getId());
				nueva.setTitle(pd.getTitle());
				nueva.setDescription(pd.getDescription());
				nueva.setStartDate(pd.getStartDate());
				nueva.setEndDate(pd.getEndDate());
				this.save(nueva);
			}
	}

}
