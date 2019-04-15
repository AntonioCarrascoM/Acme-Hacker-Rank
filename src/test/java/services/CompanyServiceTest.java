
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Company;
import domain.CreditCard;
import domain.Position;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CompanyServiceTest extends AbstractTest {

	// System under test: Company ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private CompanyService	companyService;

	@Autowired
	private PositionService	positionService;


	@Test
	public void CompanyPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 92.9% | Covered Instructions 79 | Missed Instructions 6 | Total Instructions 85

			{
				null, "testCompany1", null, "create", null
			},
			/*
			 * 
			 * Positive test: An user registers as a new company
			 * Requisite tested: Functional requirement - 7.1. An actor who is not authenticated must be able to:Register to the system as a company or a hacker
			 * Data coverage : We created a new company with valid data.
			 * Exception expected: None. A company can edit his data.
			 */{
				"company1", null, "position1", "editPositive", null
			}

		/*
		 * Positive test: A company edit its positions.
		 * Requisite tested: Functional requirement - 9.1 An actor who is authenticated must be able to edit his personal data.
		 * Data coverage : From 12 editable attributes we tried to edit 2 attributes (name, surname) with valid data.
		 * Exception expected: None. A company can edit his data.
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
	public void CompanyNegativeTest() {
		final Object testingData[][] = {
			//Total Sentence Coverage: Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"company1", null, "company2", "editNegative", IllegalArgumentException.class
			},
			/*
			 * Negative test: A company tries to edit the another company personal data.
			 * Requisite tested: Functional requirement - An actor who is authenticated must be able to edit his personal data.
			 * Data coverage: From 12 editable attributes we tried to edit 2 attributes (name, surnames) of another user.
			 * Exception expected: IllegalArgumentException A company cannot edit others personal data.
			 */

			{
				"company2", "", null, "editNegative1", ConstraintViolationException.class
			},

		/*
		 * Negative test: A company tries to edit its profile with invalid data.
		 * Requisite tested: Functional requirement - An actor who is authenticated as a company must be able to manage
		 * their parades, which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage: From 9 editable attributes we tried to edit 2 attributes (maxRow, maxColumn).
		 * Exception expected: IllegalArgumentException A company cannot edit others company parade.
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
				final Company company = this.companyService.create();

				company.setName("nombre");
				company.setSurnames("sname");
				company.setAddress("calle");
				company.setPhoto("https://www.photos.com");
				company.setPhone("666666666");
				company.getUserAccount().setPassword("test");
				company.getUserAccount().setUsername(st);
				company.setEmail("email@email.com");
				company.setCommercialName("Company Name Test");
				company.setVatNumber("ATU1412345");
				final CreditCard creditCard1 = new CreditCard();
				creditCard1.setCvv(115);
				creditCard1.setHolder("Test Man");
				creditCard1.setMake("VISA");
				creditCard1.setNumber("5564157826282522");
				creditCard1.setExpMonth(10);
				creditCard1.setExpYear(2030);
				company.setCreditCard(creditCard1);

				this.companyService.save(company);

			} else if (operation.equals("editPositive")) {
				final Position position = this.positionService.findOne(this.getEntityId(id));
				position.setRequiredSkills("New Skills");

				this.positionService.save(position);
			} else if (operation.equals("editNegative")) {
				final Company company = this.companyService.findOne(this.getEntityId(id));
				company.setName("Test negative name");
				company.setSurnames("Test negative surnames");
				company.setCommercialName("Test title");
				company.setAddress("Test address");
				this.companyService.save(company);

			} else if (operation.equals("editNegative1")) {
				final Company company = this.companyService.findOne(this.getEntityId(username));
				company.getUserAccount().setUsername(st);
				this.companyService.save(company);
			}
			this.companyService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
