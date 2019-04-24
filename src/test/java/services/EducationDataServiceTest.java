
package services;

import java.util.GregorianCalendar;

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
			//Total sentence coverage : Coverage 94.3% | Covered Instructions 100 | Missed Instructions 6 | Total Instructions 106

			{
				"hacker1", null, "curriculum1", "create", null
			},
			/*
			 * 
			 * Positive test: A hacker registers a new educationData
			 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
			 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : We created a new educationData with valid data.
			 * Exception expected: None. A hacker can create an educationData.
			 */{
				"hacker1", null, "educationData1", "edit", null
			},

			/*
			 * Positive test: A hacker edit his educationData.
			 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
			 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : From 5 editable attributes we tried to edit 1 attribute (degree) with valid data.
			 * Exception expected: None. A hacker can edit his educationDatas.
			 */

			{
				"hacker1", null, "educationData1", "delete", null
			},
		/*
		 * 
		 * Positive test: A hacker delete his educationData
		 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
		 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage : We tried to delete a educationData.
		 * Exception expected: None. A hacker can delete his educationData.
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
			//Total Sentence Coverage: Coverage 95.1% | Covered Instructions 116 | Missed Instructions 6 | Total Instructions 122
			{
				"hacker1", "", "curriculum1", "createNegative", ConstraintViolationException.class
			},
			/*
			 * Negative: A hacker tries to create a educationData
			 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
			 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage: We created a educationData with 4 out of 5 valid parameters.
			 * Exception expected: ConstraintViolationException. Degree cannot be blank.
			 */

			{
				"hacker2", null, "educationData1", "edit", IllegalArgumentException.class
			},

			/*
			 * Negative test: A hacker tries to edit a educationData that not owns.
			 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
			 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : From 4 editable attributes we tried to edit 1 attribute (Institution) with a user that is not the owner.
			 * Exception expected: IllegalArgumentException. A Hacker can not edit educationDatas from another hacker.
			 */
			{
				"hacker2", null, "educationData1", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative test: A hacker tries to delete a educationData that not owns.
		 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
		 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage : We tried to delete a educationData with a user that is not the owner.
		 * Exception expected: IllegalArgumentException. A Hacker can not delete educationDatas from another hacker.
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

			if (operation.equals("edit")) {
				final EducationData educationData = this.educationDataService.findOne(this.getEntityId(id));
				educationData.setDegree("Institution");

				this.educationDataService.save(educationData);

			} else if (operation.equals("create")) {

				final EducationData educationData = this.educationDataService.create(this.getEntityId(id));

				educationData.setDegree("Degree");
				educationData.setInstitution("institution");
				educationData.setMark("mark");
				educationData.setStartDate(new GregorianCalendar(2018, 11, 02).getTime());
				educationData.setEndDate(new GregorianCalendar(2021, 11, 02).getTime());

				this.educationDataService.save(educationData);

			} else if (operation.equals("delete")) {
				final EducationData educationData = this.educationDataService.findOne(this.getEntityId(id));
				this.educationDataService.delete(educationData);

			} else if (operation.equals("createNegative")) {
				final EducationData educationData = this.educationDataService.create(this.getEntityId(id));

				educationData.setDegree(st);
				educationData.setInstitution("institution");
				educationData.setMark("mark");
				educationData.setStartDate(new GregorianCalendar(2018, 11, 02).getTime());
				educationData.setEndDate(new GregorianCalendar(2021, 11, 02).getTime());

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
