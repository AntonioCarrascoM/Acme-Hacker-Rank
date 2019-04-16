
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.PositionData;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PositionDataServiceTest extends AbstractTest {

	// System under test: Hacker ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private PositionDataService	positionDataService;


	@Test
	public void PositionDataPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.3% | Covered Instructions 83 | Missed Instructions 6 | Total Instructions 89

			{
				"hacker1", null, "curriculum1", "create", null
			},
			/*
			 * 
			 * Positive test: A hacker registers a new positionData
			 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
			 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : We created a new personalData with valid data.
			 * Exception expected: None. A hacker can edit his positionData.
			 */{
				"hacker1", null, "positionData1", "editPositive", null
			}

		/*
		 * Positive test: A hacker edit his positionData.
		 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
		 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage : From 4 editable attributes we tried to edit 1 attribute (title) with valid data.
		 * Exception expected: None. A hacker can edit his positionDatas.
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
	public void PositionDataNegativeTest() {
		final Object testingData[][] = {
			//Total Sentence Coverage: Coverage 92.5% | Covered Instructions 74 | Missed Instructions 6 | Total Instructions 80
			{
				"hacker1", "", "positionData1", "editNegative", ConstraintViolationException.class
			},

		/*
		 * Negative test: A hacker tries to edit the positionData with invalid data.
		 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
		 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage: From 4 editable attributes we tried to edit 1 attribute (description).
		 * Exception expected: IllegalArgumentException A hacker cannot edit a positionData with invalid data.
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
			//				final PositionData positionData = this.positionDataService.create(c.getId());
			//				positionData.setCurriculum(c);
			//				positionData.setTitle("PositionDataTitle");
			//				positionData.setStartDate(12/04/2019 12:30);
			//				positionData.setEndDate(15/05/2019 13:00);
			//
			//				this.positionDataService.save(positionData);

			//			} else 
			if (operation.equals("editPositive")) {
				final PositionData positionData = this.positionDataService.findOne(this.getEntityId(id));
				positionData.setTitle("Titulo");

				this.positionDataService.save(positionData);

			} else if (operation.equals("editNegative")) {
				final PositionData positionData = this.positionDataService.findOne(this.getEntityId(id));
				positionData.setDescription(st);
				this.positionDataService.save(positionData);

			}
			this.positionDataService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
