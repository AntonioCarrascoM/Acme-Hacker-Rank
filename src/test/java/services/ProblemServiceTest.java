
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Problem;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProblemServiceTest extends AbstractTest {

	// System under test: Problem ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private ProblemService	problemService;


	@Test
	public void ProblemPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.1% | Covered Instructions 96 | Missed Instructions 6 | Total Instructions 102

			{
				"company1", null, null, "create", null
			},
			/*
			 * Positive: A company tries to create a problem.
			 * Requisite tested: 9.2. An actor who is authenticated as a company must be able to:
			 * Manage their database of problems, which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : We created a problem with 5 out of 5 valid parameters.
			 * Exception expected: None. A Company can create problems.
			 */
			{
				"company1", null, "problem1", "edit", null
			},
			/*
			 * Positive test: A company edits his problems.
			 * Requisite tested: Functional requirement - 9.2. An actor who is authenticated as a company must be able to:
			 * Manage their database of problems, which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (statement) with valid data.
			 * Exception expected: None. A Company can edit his problems.
			 */
			{
				"company1", null, "problem3", "delete", null
			},
		/*
		 * Positive test: A company edits his problems.
		 * Requisite tested: Functional requirement - 9.2. An actor who is authenticated as a company must be able to:
		 * Manage their database of problems, which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage : We tried to delete a problem which is not in final mode
		 * Exception expected: None. A Company can delete his problems.
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
	public void ProblemNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.7% | Covered Instructions 108 | Missed Instructions 6 | Total Instructions 114

			{
				"company1", " ", null, "createNegative", ConstraintViolationException.class
			},
			/*
			 * Positive: A company tries to create a Miscellaneous Record.
			 * Requisite tested: Functional requirement - 9.2. An actor who is authenticated as a company must be able to:
			 * Manage their database of problems, which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : We created a problem with 4 out of 5 valid parameters.
			 * Exception expected: ConstraintViolationException. Title cannot be blank.
			 */
			{
				"company2", null, "problem1", "edit", IllegalArgumentException.class
			},
			/*
			 * Negative: A company tries to edit a problem that not owns.
			 * Requisite tested: Functional requirement - 9.2. An actor who is authenticated as a company must be able to:
			 * Manage their database of problems, which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with a user that is not the owner.
			 * Exception expected: IllegalArgumentException. A Company can not edit problems from another company.
			 */
			{
				"company1", null, "problem1", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A company tries to delete a problem that is saved in final mode.
		 * Requisite tested: Functional requirement - 9.2. An actor who is authenticated as a company must be able to:
		 * Manage their database of problems, which includes listing, showing, creating, updating, and deleting them. Problems can be saved in draft mode; once they are saved
		 * in final mode, they cannot not be edited
		 * Data coverage :We tried to delete a problem which is in final mode.
		 * Exception expected: IllegalArgumentException. A Company can not delete problems which are in final mode.
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
			if (operation.equals("create")) {
				final Problem problem = this.problemService.create();
				problem.setTitle("Problem title");
				problem.setStatement("This is the statement ain't it cool?");
				problem.setHint(null);
				problem.setAttachments("Testing attachments");
				problem.setFinalMode(false);

				this.problemService.save(problem);
			} else if (operation.equals("edit")) {
				final Problem problem = this.problemService.findOne(this.getEntityId(id));
				problem.setStatement("Testing body edition");

				this.problemService.save(problem);

			} else if (operation.equals("delete")) {
				final Problem problem = this.problemService.findOne(this.getEntityId(id));

				this.problemService.delete(problem);

			} else if (operation.equals("createNegative")) {
				final Problem problem = this.problemService.create();
				problem.setTitle(st);
				problem.setStatement("This is the statement ain't it cool?");
				problem.setHint(null);
				problem.setAttachments("Testing attachments");
				problem.setFinalMode(false);

				this.problemService.save(problem);

			}

			this.problemService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
