
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MiscellaneousDataRepository;
import domain.Curriculum;
import domain.MiscellaneousData;

@Service
@Transactional
public class MiscellaneousDataService {

	//Managed service

	@Autowired
	private MiscellaneousDataRepository	miscellaneousDataRepository;

	//Supporting service

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private Validator					validator;


	//Simple CRUD methods

	public MiscellaneousData create(final int curriculumId) {
		final MiscellaneousData er = new MiscellaneousData();

		final Curriculum c = this.curriculumService.findOne(curriculumId);
		er.setCurriculum(c);

		return er;
	}

	public MiscellaneousData findOne(final int id) {
		Assert.notNull(id);

		return this.miscellaneousDataRepository.findOne(id);
	}

	public Collection<MiscellaneousData> findAll() {
		return this.miscellaneousDataRepository.findAll();
	}

	public MiscellaneousData save(final MiscellaneousData er) {
		Assert.notNull(er);

		//Assertion that the user modifying this education record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == er.getCurriculum().getHacker().getId());

		final MiscellaneousData saved = this.miscellaneousDataRepository.save(er);

		return saved;
	}

	public void delete(final MiscellaneousData er) {
		Assert.notNull(er);

		//Assertion that the user deleting this education record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == er.getCurriculum().getHacker().getId());

		this.miscellaneousDataRepository.delete(er);
	}

	//Other methods--------------------------

	//Reconstruct

	public MiscellaneousData reconstruct(final MiscellaneousData p, final int curriculumId, final BindingResult binding) {
		Assert.notNull(p);
		MiscellaneousData result;

		if (p.getId() == 0)
			result = this.create(curriculumId);
		else
			result = this.miscellaneousDataRepository.findOne(p.getId());
		result.setText(p.getText());
		result.setAttachments(p.getAttachments());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getCurriculum().getHacker().getId());

		return result;

	}

	//Copy

	public void copy(final Curriculum orig, final Curriculum copy) {
		MiscellaneousData nueva;
		final Collection<MiscellaneousData> pdOrig = this.curriculumService.getMiscellaneousDataForCurriculum(orig.getId());
		if (pdOrig != null && !pdOrig.isEmpty())
			for (final MiscellaneousData pd : pdOrig) {
				nueva = this.create(copy.getId());
				nueva.setText(pd.getText());
				nueva.setAttachments(pd.getAttachments());
				this.save(nueva);
			}
	}

}
