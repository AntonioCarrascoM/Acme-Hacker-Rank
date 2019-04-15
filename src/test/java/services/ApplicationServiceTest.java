
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Application;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// System under test: Application ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private ApplicationService	applicationService;


	@Test
	public void ApplicationPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94,1% | Covered Instructions 96 | Missed Instructions 6 | Total Instructions 102
			{
				"hacker1", null, "application1", "edit", null

			},
		/*
		 * Positive test: A hacker edits his application.
		 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a hacker must be able to
		 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
		 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (description) with valid data.
		 * Exception expected: None. A Brotherhood can edit his applications.
		 */
		//
		//			{
		//				"company1", null, "application1", "delete", null
		//			}
		//			/*
		//			 * Positive: A hacker tries to delete a application.
		//			 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a hacker must be able to
		//			 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
		//			 * Data coverage : We deleted an application.
		//			 * Exception expected: None. A Brotherhood can delete his applications.
		//			 */
		//			{
		//				"hacker1", null, null, "create", null
		//			}
		/*
		 * Positive: A hacker tries to create a Miscellaneous Record.
		 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a hacker must be able to
		 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
		 * Data coverage : We created a application with 2 out of 2 valid parameters.
		 * Exception expected: None. A Brotherhood can create applications.
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
	public void ApplicationNegativeTest() {
		final Object testingData[][] = {
		//Total sentence coverage : Coverage 92.1% | Covered Instructions 70 | Missed Instructions 6 | Total Instructions 76
		//			{
		//				"hacker2", null, "application2", "edit2", IllegalArgumentException.class
		//			},
		/*
		 * Negative: A hacker tries to edit a application that not owns.
		 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a hacker must be able to
		 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
		 * Data coverage :We tried to edit 1 out of 2 miscellaneous record attributes with an user that is not the owner.
		 * Exception expected: IllegalArgumentException. A Brotherhood can not edit applications from another hacker.
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
				final Application application = this.applicationService.findOne(this.getEntityId(id));
				application.setAnswerDescription("Consider your problem solved");

				this.applicationService.save(application);
			}//else if (operation.equals("edit2")) {
				//				final Application application = this.applicationService.findOne(this.getEntityId(id));
			//				application.setTitle("Failing test");
			//
			//				this.applicationService.save(application);
			//
			//			} else if (operation.equals("create")) {
			//				final Application mr = this.applicationService.create();
			//				mr.setTitle("Positive create");
			//				mr.setAnswerDescription("description");
			//
			//				this.applicationService.save(mr);
			//}

			this.applicationService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
