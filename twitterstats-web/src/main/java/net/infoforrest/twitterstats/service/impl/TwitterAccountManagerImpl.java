package net.infoforrest.twitterstats.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import net.infoforrest.twitterstats.dao.RegisteredAccountDao;
import net.infoforrest.twitterstats.dao.TwitterAccountDao;
import net.infoforrest.twitterstats.model.RegisteredAccount;
import net.infoforrest.twitterstats.model.TwitterAccount;
import net.infoforrest.twitterstats.service.TwitterAccountManager;

@Service
public class TwitterAccountManagerImpl implements TwitterAccountManager {

	@PersistenceContext(unitName="twitterstatsPersistenceUnit")
    private EntityManager em;

	@Autowired
	private RegisteredAccountDao registeredAccountDao;

	@Autowired
	private TwitterAccountDao twitterAccountDao;

	@Transactional
	public void accountActivationOrRenew(Twitter twitter, AccessToken accessToken) throws IllegalStateException, TwitterException {

		String screenName = twitter.getScreenName();

		RegisteredAccount rAccount = registeredAccountDao.findRegisteredAccountByScreenName(screenName);

		IDs ids = twitter.getFriendsIDs(twitter.getId(), -1);

		if (rAccount == null) {
			rAccount = registeredAccountDao.insertRegisteredAccount(screenName,
					accessToken.getToken(),
					accessToken.getTokenSecret());
		} else {
			rAccount.setAccessToken(accessToken.getToken());
			rAccount.setAccessTokenSecret(accessToken.getTokenSecret());
		}

		final Set<TwitterAccount> following = new HashSet<TwitterAccount>();

		for (long id : ids.getIDs()) {
			final Date now = new Date();
			final String friendScrnName = twitter.showUser(id).getScreenName();

			TwitterAccount tAccount = twitterAccountDao.findByScreenName(friendScrnName);
			if (tAccount == null) {
				tAccount = new TwitterAccount();
				tAccount.setScreenName(friendScrnName);
				tAccount.setTwitterId(id);
				tAccount.setFollowStartTs(now);
				em.persist(tAccount);
			}

			following.add(tAccount);
		}

		rAccount.setFollowing(following);

		em.persist(rAccount);
	}

}
