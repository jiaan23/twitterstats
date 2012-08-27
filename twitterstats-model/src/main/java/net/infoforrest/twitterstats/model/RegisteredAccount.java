package net.infoforrest.twitterstats.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="RegisteredAccount")
@NamedQueries ({
	@NamedQuery(name="RegisteredAccount.findByScreentName",
			query="SELECT tAccount FROM RegisteredAccount tAccount WHERE tAccount.screenName=:screenName")
})
public class RegisteredAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="screenName", length=300)
	private String screenName;

	@Column(name="accessToken", length=200)
	private String accessToken;

	@Column(name="accessTokenSecret", length=200)
	private String accessTokenSecret;

	@ManyToMany
	@JoinTable(name="Following",
		joinColumns=@JoinColumn(name="owner"),
		inverseJoinColumns=@JoinColumn(name="following"))
	private Set<TwitterAccount> following;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	public Set<TwitterAccount> getFollowing() {
		return following;
	}

	public void setFollowing(Set<TwitterAccount> following) {
		this.following = following;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegisteredAccount other = (RegisteredAccount) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
