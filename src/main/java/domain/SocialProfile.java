
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialProfile extends DomainEntity {

	//Attributes

	private String	nick;
	private String	socialNetwork;
	private String	profileLink;

	//Relationships

	private Actor	actor;


	//Getter

	@NotBlank
	public String getNick() {
		return this.nick;
	}

	@NotBlank
	public String getSocialNetwork() {
		return this.socialNetwork;
	}

	@NotBlank
	@URL
	public String getProfileLink() {
		return this.profileLink;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	//Setter
	public void setNick(final String nick) {
		this.nick = nick;
	}

	public void setSocialNetwork(final String socialNetwork) {
		this.socialNetwork = socialNetwork;
	}

	public void setProfileLink(final String profileLink) {
		this.profileLink = profileLink;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}
}
