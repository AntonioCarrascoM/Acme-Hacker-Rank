
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.EducationData;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EducationDataServiceTest extends AbstractTest {

	// System under test: Hacker ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private EducationDataService	educationDataService;


	@Test
	public void EducationDataPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.3% | Covered Instructions 83 | Missed Instructions 6 | Total Instructions 89

			{
				"hacker1", null, "curriculum1", "create", null
			},
			/*
			 * 
			 * Positive test: A hacker registers a new educationData
			 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
			 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : We created a new personalData with valid data.
			 * Exception expected: None. A hacker can edit his educationData.
			 */{
				"hacker1", null, "educationData1", "editPositive", null
			}

		/*
		 * Positive test: A hacker edit his educationData.
		 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
		 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage : From 5 editable attributes we tried to edit 1 attribute (degree) with valid data.
		 * Exception expected: None. A hacker can edit his educationDatas.
		 */

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void EducationDataNegativeTest() {
		final Object testingData[][] = {
			//Total Sentence Coverage: Coverage 92.5% | Covered Instructions 74 | Missed Instructions 6 | Total Instructions 80
			{
				"hacker1", "", "educationData1", "editNegative", ConstraintViolationException.class
			},

		/*
		 * Negative test: A hacker tries to edit the educationData with invalid data.
		 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
		 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage: From 5 editable attributes we tried to edit 1 attribute (institution).
		 * Exception expected: IllegalArgumentException A hacker cannot edit an educationData with invalid data.
		 */
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	protected void template(final String username, final String st, final String id, final String operation, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			//			if (operation.equals("create")) {
			//				//final Hacker hacker = this.hackerService.findOne(this.getEntityId(username));
			//				final Curriculum c = this.curriculumService.findOne(this.getEntityId(id));
			//				//c.setHacker(hacker);
			//
			//				final EducationData educationData = this.educationDataService.create(c.getId());
			//				educationData.setCurriculum(c);
			//				educationData.setDegree("Manuel Jesus");
			//				educationData.setInstitution("This is a my curriculum");
			//				educationData.setMark("666666666");
			//				educationData.setStartDate("15/05/2019 12:30");
			//				educationData.setEndDate("19/05/2019 13:00");
			//
			//				this.educationDataService.save(educationData);

			//			} else 
			if (operation.equals("editPositive")) {
				final EducationData educationData = this.educationDataService.findOne(this.getEntityId(id));
				educationData.setDegree("DegreeTest");

				this.educationDataService.save(educationData);

			} else if (operation.equals("editNegative")) {
				final EducationData educationData = this.educationDataService.findOne(this.getEntityId(id));
				educationData.setInstitution(st);
				this.educationDataService.save(educationData);

			}
			this.educationDataService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
