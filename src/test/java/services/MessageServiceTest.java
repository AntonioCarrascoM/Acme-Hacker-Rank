
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
import domain.Message;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest extends AbstractTest {

	// System under test: Message ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private MessageService	messageService;

	@Autowired
	private CompanyService	companyService;


	@Test
	public void MessagePositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.3% | Covered Instructions 100 | Missed Instructions 6 | Total Instructions 106

			{
				"company1", null, "company2", "create", null
			},
			/*
			 * Positive: A company tries to create a Miscellaneous Record.
			 * Requisite tested: Functional requirement - 23.2 An actor who is authenticated must be able to Manage his or her messages,
			 * which includes listing them grouped by tag, showing them, sending a message to an actor, deleting a message that he or she got.
			 * Data coverage : We created a miscellaneousRecord with 5 out of 5 valid parameters.
			 * Exception expected: None. A Company can create messages.
			 */
			{
				"company3", null, "message1", "edit", null
			},
			/*
			 * Positive test: A company edits his messages.
			 * Requisite tested: Functional requirement - 23.2 An actor who is authenticated must be able to Manage his or her messages,
			 * which includes listing them grouped by tag, showing them, sending a message to an actor, deleting a message that he or she got.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
			 * Exception expected: None. A Company can edit his messages.
			 */
			{
				"company3", null, "message1", "delete", null
			},
		/*
		 * Positive test: A company edits his messages.
		 * Requisite tested: Functional requirement - 23.2 An actor who is authenticated must be able to Manage his or her messages,
		 * which includes listing them grouped by tag, showing them, sending a message to an actor, deleting a message that he or she got.
		 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
		 * Exception expected: None. A Company can edit his messages.
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
	public void MessageNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.9% | Covered Instructions 112 | Missed Instructions 6 | Total Instructions 118

			{
				"company1", null, "company2", "createNegative", ConstraintViolationException.class
			},
			/*
			 * Positive: A company tries to create a Miscellaneous Record.
			 * Requisite tested: Functional requirement - 23.2 An actor who is authenticated must be able to Manage his or her messages,
			 * which includes listing them grouped by tag, showing them, sending a message to an actor, deleting a message that he or she got.
			 * Data coverage : We created a message with 2 out of 2 valid parameters.
			 * Exception expected: ConstraintViolationException. Subject cannot be blank.
			 */
			{
				"company1", null, "message1", "edit", IllegalArgumentException.class
			},
			/*
			 * Negative: A company tries to edit a message that not owns.
			 * Requisite tested: Functional requirement - 23.2 An actor who is authenticated must be able to Manage his or her messages,
			 * which includes listing them grouped by tag, showing them, sending a message to an actor, deleting a message that he or she got.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with a user that is not the owner.
			 * Exception expected: IllegalArgumentException. A Company can not edit messages from another company.
			 */
			{
				"company1", null, "message1", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A company tries to delete a message that not owns.
		 * Requisite tested: Functional requirement - 23.2 An actor who is authenticated must be able to Manage his or her messages,
		 * which includes listing them grouped by tag, showing them, sending a message to an actor, deleting a message that he or she got.
		 * Data coverage :We tried to delete a message with an user that is not the owner.
		 * Exception expected: IllegalArgumentException. A Company can not delete messages from another company.
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
				final Message message = this.messageService.create();

				message.setSubject("Test subject");
				message.setBody("testing stuff");
				final Company companyS = this.companyService.findOne(this.getEntityId(username));
				message.setRecipient(companyS);
				final Company companyR = this.companyService.findOne(this.getEntityId(id));
				message.setRecipient(companyR);
				message.setTags("Test tags");

				this.messageService.save(message);

			} else if (operation.equals("edit")) {
				final Message message = this.messageService.findOne(this.getEntityId(id));
				message.setBody("Testing body edition");

				this.messageService.save(message);

			} else if (operation.equals("delete")) {
				final Message message = this.messageService.findOne(this.getEntityId(id));

				this.messageService.delete(message);

			} else if (operation.equals("createNegative")) {
				final Message message = this.messageService.create();

				message.setSubject("");
				message.setBody("testing stuff");
				final Company companyS = this.companyService.findOne(this.getEntityId(username));
				message.setRecipient(companyS);
				final Company companyR = this.companyService.findOne(this.getEntityId(id));
				message.setRecipient(companyR);
				message.setTags("Test tags");

				this.messageService.save(message);

			}

			this.messageService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
