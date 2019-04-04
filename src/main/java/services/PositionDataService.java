
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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

		final PositionData saved = this.positionDataRepository.save(er);

		return saved;
	}

	public void delete(final PositionData er) {
		Assert.notNull(er);

		//Assertion that the user deleting this education record has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == er.getCurriculum().getHacker().getId());

		this.positionDataRepository.delete(er);
	}

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
