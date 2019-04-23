
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


	//	@Autowired
	//	private PositionService		positionService;
	//	@Autowired
	//	private ProblemService		problemService;

	@Test
	public void ApplicationPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94,1% | Covered Instructions 96 | Missed Instructions 6 | Total Instructions 102
			{
				"hacker1", null, "application1", "edit", null

			},

			/*
			 * Positive test: A hacker edits his application.
			 * Requisite tested: Functional requirement - 10.1 An actor who is authenticated as a hacker must be able to
			 * Manage his or her applications, which includes listing them grouped by status, showing them, creating them, and updating them
			 * Data coverage : From 5 editable attributes we tried to edit 1 attribute (answerDescription) with valid data.
			 * Exception expected: None. A Hacker can edit his applications.
			 */

			{
				"hacker1", "position1", "problem1", "create", null
			},

		/*
		 * Positive: A hacker tries to create an Application.
		 * Requisite tested: Functional requirement - 10.1 An actor who is authenticated as a hacker must be able to
		 * Manage his or her applications, which includes listing them grouped by status, showing them, creating them, and updating them
		 * Data coverage : We created a application with 2 out of 2 valid parameters.
		 * Exception expected: None. A Hacker can create applications.
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
			{
				"hacker2", null, "application1", "edit", IllegalArgumentException.class
			},
		/*
		 * Positive: A hacker tries to create an Application.
		 * Requisite tested: Functional requirement - 10.1 An actor who is authenticated as a hacker must be able to
		 * Manage his or her applications, which includes listing them grouped by status, showing them, creating them, and updating them
		 * Data coverage : From 5 editable attributes we tried to edit 1 attribute (answerDescription) with a user that is not the owner.
		 * Exception expected: IllegalArgumentException. A Hacker can not edit applications from another hacker.
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

				//			} else if (operation.equals("create")) {
				//
				//				final Application application = this.applicationService.create();
				//				final Position position = this.positionService.create();
				//				final Position saved = this.positionService.save(position);
				//				final Problem problem = this.problemService.create();
				//				final Problem savedproblem = this.problemService.save(problem);
				//
				//				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				//				final Date moment = sdf.parse("21/03/2019 12:34");
				//
				//				final SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				//				final Date moment1 = sdf1.parse("25/04/2019 12:34");
				//				application.setMoment(moment);
				//				application.setAnswerDescription("answerdescription");
				//				application.setAnswerLink("https://www.link.es");
				//				application.setAnswerMoment(moment1);
				//				application.setStatus(Status.ACCEPTED);
				//				application.setPosition(saved);
				//				application.setProblem(savedproblem);

				this.applicationService.save(application);
			}

			this.applicationService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
