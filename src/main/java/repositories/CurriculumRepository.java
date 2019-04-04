
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;
import domain.EducationData;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {

	//Listing of personal datas for a certain curriculum
	@Query("select p from PersonalData p where p.curriculum.id=?1")
	PersonalData getPersonalDataForCurriculum(int id);

	//Listing of position datas for a certain curriculum
	@Query("select p from PositionData p where p.curriculum.id=?1")
	Collection<PositionData> getPositionDataForCurriculum(int id);

	//Listing of education datas for a certain curriculum
	@Query("select p from EducationData p where p.curriculum.id=?1")
	Collection<EducationData> getEducationDataForCurriculum(int id);

	//Listing of miscellaneous datas for a certain curriculum
	@Query("select p from MiscellaneousData p where p.curriculum.id=?1")
	Collection<MiscellaneousData> getMiscellaneousDataForCurriculum(int id);

}
