
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.PersonalData;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PersonalDataServiceTest extends AbstractTest {

	// System under test: Hacker ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private PersonalDataService	personalDataService;
	@Autowired
	private CurriculumService	curriculumService;


	@Test
	public void PersonalDataPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.1% | Covered Instructions 96 | Missed Instructions 6 | Total Instructions 102

			{
				"hacker1", null, null, "create", null
			},
			/*
			 * 
			 * Positive test: A hacker registers a new personalData
			 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
			 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : We created a new personalData with valid data.
			 * Exception expected: None. A hacker can create his personalData.
			 */{
				"hacker1", null, "personalData1", "edit", null
			},
			/*
			 * 
			 * Positive test: A hacker edit his personalData
			 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
			 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : We created a new personalData with valid data.
			 * Exception expected: None. A hacker can edit his personalData.
			 */

			{
				"hacker1", null, "personalData1", "delete", null
			},
		/*
		 * 
		 * Positive test: A hacker delete his personalData
		 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
		 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage : We tried to delete a personalData.
		 * Exception expected: None. A hacker can delete his personalData.
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
	public void PersonalDataNegativeTest() {
		final Object testingData[][] = {
			//Total Sentence Coverage: Coverage 94.9% | Covered Instructions 112 | Missed Instructions 6 | Total Instructions 118

			{
				"hacker1", "", null, "createNegative", ConstraintViolationException.class
			},
			/*
			 * Positive: A hacker tries to create a personalData
			 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
			 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage: We created a personalData with 4 out of 5 valid parameters.
			 * Exception expected: ConstraintViolationException. Full name cannot be blank.
			 */

			{
				"hacker2", null, "personalData1", "edit", IllegalArgumentException.class
			},

			/*
			 * Negative test: A hacker tries to edit a personalData that not owns.
			 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
			 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (fullName) with a user that is not the owner.
			 * Exception expected: IllegalArgumentException. A Hacker can not edit personalDatas from another hacker.
			 */

			{
				"hacker2", null, "personalData1", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative test: A hacker tries to delete a personalData that not owns.
		 * Requisite tested: Functional requirement - 17.1. An actor who is authenticated as a hacker must be able
		 * to: Manage his or her curricula , which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage : We tried to delete a personalData with a user that is not the owner.
		 * Exception expected: IllegalArgumentException. A Hacker can not delete personalDatas from another hacker.
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
				final PersonalData personalData = this.personalDataService.findOne(this.getEntityId(id));
				personalData.setFullName("Jose");

				this.personalDataService.save(personalData);

			} else if (operation.equals("create")) {
				final Curriculum c = this.curriculumService.create();
				final Curriculum saved = this.curriculumService.save(c);
				final PersonalData personalData = this.personalDataService.create(saved.getId());

				personalData.setFullName("Manuel Jesus");
				personalData.setStatement("This is a my curriculum");
				personalData.setPhoneNumber("666666666");
				personalData.setGitHubProfile("https://www.github.com");
				personalData.setLinkedInProfile("https://www.linkedin.com");
				this.personalDataService.save(personalData);

			} else if (operation.equals("delete")) {
				final PersonalData personalData = this.personalDataService.findOne(this.getEntityId(id));
				this.personalDataService.delete(personalData);

			} else if (operation.equals("createNegative")) {
				final Curriculum c = this.curriculumService.create();
				final Curriculum saved = this.curriculumService.save(c);
				final PersonalData personalData = this.personalDataService.create(saved.getId());

				personalData.setFullName(st);
				personalData.setStatement("This is a my curriculum");
				personalData.setPhoneNumber("666666666");
				personalData.setGitHubProfile("https://www.github.com");
				personalData.setLinkedInProfile("https://www.linkedin.com");
				this.personalDataService.save(personalData);
			}
			this.personalDataService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
