
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Application;
import domain.CreditCard;
import domain.Hacker;
import domain.Status;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HackerServiceTest extends AbstractTest {

	// System under test: Hacker ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private HackerService		hackerService;

	@Autowired
	private ApplicationService	applicationService;


	@Test
	public void HackerPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 92.9% | Covered Instructions 79 | Missed Instructions 6 | Total Instructions 85

			{
				null, "testHacker1", null, "create", null
			},
			/*
			 * 
			 * Positive test: An user registers as a new hacker
			 * Requisite tested: Functional requirement - 7.1. An actor who is not authenticated must be able to:Register to the system as a hacker or a hacker
			 * Data coverage : We created a new hacker with valid data.
			 * Exception expected: None. A hacker can edit his data.
			 */{
				"hacker1", null, "application1", "editPositive", null
			}

		/*
		 * Positive test: A hacker update an application.
		 * Requisite tested: Functional requirement - 10.1 An actor who is authenticated as hacker must be able to manage his or her applications,
		 * wich includes listing them grouped by status, showing them, creating them, and updating them.
		 * Data coverage : From 5 editable attributes we tried to edit 1 attribute (status) with valid data.
		 * Exception expected: None. A hacker can edit his applications.
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
	public void HackerNegativeTest() {
		final Object testingData[][] = {
			//Total Sentence Coverage: Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"hacker1", null, "hacker2", "editNegative", IllegalArgumentException.class
			},
			/*
			 * Negative test: A hacker tries to edit the another hacker personal data.
			 * Requisite tested: Functional requirement - An actor who is authenticated must be able to edit his personal data.
			 * Data coverage: From 9 editable attributes we tried to edit 3 attributes (name, surnames, address) of another user.
			 * Exception expected: IllegalArgumentException A hacker cannot edit others personal data.
			 */

			{
				"hacker2", "", null, "editNegative1", ConstraintViolationException.class
			},

		/*
		 * Negative test: A hacker tries to edit its profile with invalid data.
		 * Requisite tested: Functional requirement - An actor who is authenticated as a hacker must be able to manage
		 * their parades, which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage: From 9 editable attributes we tried to edit 1 attributes (username).
		 * Exception expected: IllegalArgumentException A hacker cannot edit with invalid data.
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
				final Hacker hacker = this.hackerService.create();

				hacker.setName("nombre");
				hacker.setSurnames("sname");
				hacker.setAddress("calle");
				hacker.setPhoto("https://www.photos.com");
				hacker.setPhone("666666666");
				hacker.getUserAccount().setPassword("test");
				hacker.getUserAccount().setUsername(st);
				hacker.setEmail("email@email.com");
				hacker.setVatNumber("ATU1412345");
				final CreditCard creditCard1 = new CreditCard();
				creditCard1.setCvv(115);
				creditCard1.setHolder("Test Man");
				creditCard1.setMake("VISA");
				creditCard1.setNumber("5564157826282522");
				creditCard1.setExpMonth(10);
				creditCard1.setExpYear(2030);
				hacker.setCreditCard(creditCard1);

				this.hackerService.save(hacker);

			} else if (operation.equals("editPositive")) {
				final Application application = this.applicationService.findOne(this.getEntityId(id));
				application.setStatus(Status.SUBMITTED);

				this.applicationService.save(application);
			} else if (operation.equals("editNegative")) {
				final Hacker hacker = this.hackerService.findOne(this.getEntityId(id));
				hacker.setName("Test negative name");
				hacker.setSurnames("Test negative surnames");
				hacker.setAddress("Test address");
				this.hackerService.save(hacker);

			} else if (operation.equals("editNegative1")) {
				final Hacker hacker = this.hackerService.findOne(this.getEntityId(username));
				hacker.getUserAccount().setUsername(st);
				this.hackerService.save(hacker);
			}
			this.hackerService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
