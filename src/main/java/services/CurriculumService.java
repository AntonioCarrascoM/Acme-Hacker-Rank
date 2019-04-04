
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
	private CurriculumRepository	curriculumRepository;

	//Supporting services

	@Autowired
	private ActorService			actorService;

	//TODO Ajustar cuando se creen todos
	//	@Autowired
	//	private PersonalDataService			personalDataService;

	@Autowired
	private PositionDataService		positionDataService;


	//	@Autowired
	//	private EducationDataService		educationDataService;
	//
	//	@Autowired
	//	private MiscellaneousDataService	miscellaneousDataService;

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

		//TODO descomentar cuando estén los services de los datas
		//		if (this.getPersonalDataForCurriculum(c.getId()) != null)
		//			this.personalDataService.delete(this.getPersonalDataForCurriculum(c.getId()));
		//
		if (!(this.getPositionDataForCurriculum(c.getId()).isEmpty()))
			for (final PositionData pd : this.getPositionDataForCurriculum(c.getId()))
				this.positionDataService.delete(pd);
		//
		//		if (!(this.getEducationDataForCurriculum(c.getId()).isEmpty()))
		//			for (final EducationData ed : this.getEducationDataForCurriculum(c.getId()))
		//				this.educationDataService.delete(ed);
		//
		//		if (!(this.getMiscellaneousDataForCurriculum(c.getId()).isEmpty()))
		//			for (final MiscellaneousData md : this.getMiscellaneousDataForCurriculum(c.getId()))
		//				this.miscellaneousData.delete(md);

		this.curriculumRepository.delete(c);
	}

	//Copy method
	public Curriculum copy(final Curriculum orig) {
		final Curriculum copy = this.create();

		//TODO método copiar de cada data. Setear en dicho metodo el curriculum  de la data en cuestion y guardar dicho data, al final del todo (las 4 datas diferentes) devolver este curriculum

		this.positionDataService.copy(orig, copy);

		return copy;
	}

	//Time for motion and queries

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
}
