
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Administrator;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	// System under test: Administrator ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private AdministratorService	administratorService;


	@Test
	public void AdministratorPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.2% | Covered Instructions 62 | Missed Instructions 6 | Total Instructions 68
			{
				"admin", null, null, "editPositive", null
			}
		/*
		 * Positive test: An administrator edit his data.
		 * Requisite tested: Functional requirement - 9.2 An actor who is authenticated must be able to edit his personal data.
		 * Data coverage : From 9 editable attributes we tried to edit 2 attributes (name, middleName) with valid data.
		 * Exception expected: None. An administrator can edit his data.
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
	public void AdministratorNegativeTest() {
		final Object testingData[][] = {
			//Total Sentence Coverage: Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72
			{
				"admin", null, null, "editNegative", ConstraintViolationException.class
			},
		/*
		 * Negative test: An administrator tries to edit his personal data with an invalid photo.
		 * Requisite tested: Functional requirement - 9.2 An actor who is authenticated must be able to edit his personal data.
		 * Data coverage: From 9 editable attributes we provide 1 wrong attribute (photo).
		 * Exception expected: IllegalArgumentException. Photo must be an URL.
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
			if (operation.equals("editPositive")) {
				final Administrator admin = this.administratorService.findOne(this.getEntityId(username));
				admin.setName("Test name");
				admin.setSurnames("Test middlename");
				this.administratorService.save(admin);
			} else if (operation.equals("editNegative")) {
				final Administrator admin = this.administratorService.findOne(this.getEntityId(username));
				admin.setPhoto("Wrong photo");
				this.administratorService.save(admin);
			}
			this.administratorService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
