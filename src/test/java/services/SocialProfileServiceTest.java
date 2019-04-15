
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.SocialProfile;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

	// System under test: SocialProfile ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private SocialProfileService	socialProfileService;


	@Test
	public void SocialProfilePositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.2% | Covered Instructions 62 | Missed Instructions 6 | Total Instructions 68

			{
				"hacker1", null, null, "create", null
			}
		/*
		 * Positive test: A actor(hacker) registers his socialProfile.
		 * Requisite tested: Functional requirement - 11.1 An actor who is authenticated as a hacker must be able to manage his or her socialProfiles to march on a procession, which includes listing them by status, showing, creating them, and deleting
		 * them.
		 * Data coverage : We created a socialProfile by providing 3 out of 3 editable attributes.
		 * Exception expected: None. A actor (Hacker) can create socialProfiles.
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
	public void SocialProfileNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 92.1% | Covered Instructions 70 | Missed Instructions 6 | Total Instructions 76

			{
				"hacker1", null, "parade3", "create2", ConstraintViolationException.class
			}
		/*
		 * Negative test: Creating a socialProfile with invalid profileLink.
		 * Requisite tested: Functional requirement - 11.1 An actor who is authenticated as a hacker must be able to manage his or her socialProfiles to march on a procession, which includes listing them by status, showing, creating them, and deleting
		 * them.
		 * Data coverage : We created a socialProfile with 1 invalid out of 3 attribute.
		 * Exception expected: ConstraintViolationException. ProfileLink must be an url.
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
				final SocialProfile s = this.socialProfileService.create();

				s.setNick("nick");
				s.setSocialNetwork("socialNetWork");
				s.setProfileLink("https://www.instagram.com");
				s.setActor(this.hackerService.findOne(this.getEntityId(username)));

				this.socialProfileService.save(s);
			} else if (operation.equals("create2")) {
				final SocialProfile s = this.socialProfileService.create();

				s.setNick("nick");
				s.setSocialNetwork("socialNetWork");
				s.setProfileLink("url");
				s.setActor(this.hackerService.findOne(this.getEntityId(username)));

				this.socialProfileService.save(s);
			}
			this.socialProfileService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
