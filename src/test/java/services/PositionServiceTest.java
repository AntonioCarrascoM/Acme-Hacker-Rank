
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Company;
import domain.Position;
import domain.Problem;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PositionServiceTest extends AbstractTest {

	// System under test: Position ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private PositionService	positionService;

	@Autowired
	private CompanyService	companyService;


	@Test
	public void PositionPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.3% | Covered Instructions 100 | Missed Instructions 6 | Total Instructions 106

			{
				"company1", null, null, "create", null
			},
			/*
			 * Positive: A company tries to create a Positon.
			 * Requisite tested: 9.1. An actor who is authenticated as a company must be able to:
			 * Manage their positions, which includes listing, showing, creating, updating, and deleting them
			 * Data coverage : We created a position with 11 out of 11 valid parameters.
			 * Exception expected: None. A Company can create positions.
			 */
			{
				"company1", null, "position1", "edit", null
			},
		/*
		 * Positive test: A company edits his positions.
		 * Requisite tested: Functional requirement - 9.1. An actor who is authenticated as a company must be able to:
		 * Manage their positions, which includes listing, showing, creating, updating, and deleting them
		 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
		 * Exception expected: None. A Company can edit his positions.
		 */
		//			{
		//				"company1", null, "position4", "delete", null
		//			},
		/*
		 * Positive test: A company edits his positions.
		 * Requisite tested: Functional requirement - 9.1. An actor who is authenticated as a company must be able to:
		 * Manage their positions, which includes listing, showing, creating, updating, and deleting them
		 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
		 * Exception expected: None. A Company can edit his positions.
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
	public void PositionNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.9% | Covered Instructions 112 | Missed Instructions 6 | Total Instructions 118

			{
				"company1", null, null, "createNegative", ConstraintViolationException.class
			},
			/*
			 * Positive: A company tries to create a Miscellaneous Record.
			 * Requisite tested: Functional requirement - 9.1. An actor who is authenticated as a company must be able to:
			 * Manage their positions, which includes listing, showing, creating, updating, and deleting them
			 * Data coverage : We created a position with 10 out of 11 valid parameters.
			 * Exception expected: ConstraintViolationException. Subject cannot be blank.
			 */
			{
				"company2", null, "position1", "edit", IllegalArgumentException.class
			},
		/*
		 * // * Negative: A company tries to edit a position that not owns.
		 * // * Requisite tested: Functional requirement - 9.1. An actor who is authenticated as a company must be able to:
		 * * Manage their positions, which includes listing, showing, creating, updating, and deleting them
		 * // * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with a user that is not the owner.
		 * // * Exception expected: IllegalArgumentException. A Company can not edit positions from another company.
		 * //
		 */
		//			{
		//				"company1", null, "position4", "delete", IllegalArgumentException.class
		//			},
		/*
		 * Negative: A company tries to delete a position that not owns.
		 * Requisite tested: * Requisite tested: Functional requirement - 9.1. An actor who is authenticated as a company must be able to:
		 * * Manage their positions, which includes listing, showing, creating, updating, and deleting them
		 * Data coverage :We tried to delete a position with an user that is not the owner.
		 * Exception expected: IllegalArgumentException. A Company can not delete positions from another company.
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
				final Position position = this.positionService.create();
				position.setTicker("Code-6666");

				position.setTitle("testing stuff");
				position.setDescription("Tester profesional");
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date moment = sdf.parse("21/12/2020 12:34");
				position.setDeadline(moment);
				position.setRequiredSkills("none");
				position.setRequiredProfile("none");
				position.setOfferedSalary(10.0);
				position.setRequiredTech("noneTech");
				final Collection<Problem> problems = new ArrayList<Problem>();
				position.setProblems(problems);
				position.setCancelled(false);
				position.setFinalMode(false);

				this.positionService.save(position);
			} else if (operation.equals("edit")) {
				final Position position = this.positionService.findOne(this.getEntityId(id));
				position.setDescription("Testing body edition");

				this.positionService.save(position);

			} else if (operation.equals("delete")) {
				final Position position = this.positionService.findOne(this.getEntityId(id));

				this.positionService.delete(position);

			} else if (operation.equals("createNegative")) {
				final Position position = this.positionService.create();
				final Company company = this.companyService.findOne(this.getEntityId(username));
				position.setCompany(company);
				position.setTitle("");
				position.setDescription("Tester profesional");
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date moment = sdf.parse("21/12/2020 12:34");
				position.setDeadline(moment);
				position.setRequiredSkills("");
				position.setRequiredProfile("");
				position.setOfferedSalary(10.0);
				position.setRequiredTech("");

				this.positionService.save(position);

			}

			this.positionService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
