
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.MiscellaneousData;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MiscellaneousDataServiceTest extends AbstractTest {

	// System under test: Hacker ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;


	@Test
	public void MiscellaneousDataPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.3% | Covered Instructions 83 | Missed Instructions 6 | Total Instructions 89

			{
				"hacker1", null, "curriculum1", "create", null
			},
			/*
			 * 
			 * Positive test: A hacker registers a new miscellaneousData
			 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
			 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : We created a new personalData with valid data.
			 * Exception expected: None. A hacker can edit his miscellaneousData.
			 */{
				"hacker1", null, "miscellaneousData1", "editPositive", null
			}

		/*
		 * Positive test: A hacker edit his miscellaneousData.
		 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
		 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage : From 2 editable attributes we tried to edit 1 attribute (text) with valid data.
		 * Exception expected: None. A hacker can edit his miscellaneousDatas.
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
	public void MiscellaneousDataNegativeTest() {
		final Object testingData[][] = {
			//Total Sentence Coverage: Coverage 92.5% | Covered Instructions 74 | Missed Instructions 6 | Total Instructions 80
			{
				"hacker1", "", "miscellaneousData1", "editNegative", ConstraintViolationException.class
			},

		/*
		 * Negative test: A hacker tries to edit the miscellaneousData with invalid data.
		 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
		 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage: From 2 editable attributes we tried to edit 1 attribute (text).
		 * Exception expected: IllegalArgumentException A hacker cannot edit an miscellaneousData with invalid data.
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
			//				final MiscellaneousData miscellaneousData = this.miscellaneousDataService.create(c.getId());
			//				miscellaneousData.setCurriculum(c);
			//				miscellaneousData.setText("MiscellaneousDataText");
			//				miscellaneousData.setAttachments("attachments");
			//				this.miscellaneousDataService.save(miscellaneousData);

			//			} else 
			if (operation.equals("editPositive")) {
				final MiscellaneousData miscellaneousData = this.miscellaneousDataService.findOne(this.getEntityId(id));
				miscellaneousData.setText("This is a text");

				this.miscellaneousDataService.save(miscellaneousData);

			} else if (operation.equals("editNegative")) {
				final MiscellaneousData miscellaneousData = this.miscellaneousDataService.findOne(this.getEntityId(id));
				miscellaneousData.setText(st);
				this.miscellaneousDataService.save(miscellaneousData);

			}
			this.miscellaneousDataService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
