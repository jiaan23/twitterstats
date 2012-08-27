package net.infoforrest.twitterstats.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.infoforrest.twitterstats.dao.RegisteredAccountDao;
import net.infoforrest.twitterstats.model.RegisteredAccount;

@Service
public class RegsteredAccountDaoImpl implements RegisteredAccountDao {

	@PersistenceContext(unitName="twitterstatsPersistenceUnit")
    private EntityManager em;

	@Transactional
	public RegisteredAccount insertRegisteredAccount(String screenName, String accessToken, String accessTokenSecret) {
		Assert.notNull(screenName);
		Assert.notNull(accessToken);
		Assert.notNull(accessTokenSecret);

		final RegisteredAccount account = new RegisteredAccount();
		account.setScreenName(screenName);
		account.setAccessToken(accessToken);
		account.setAccessTokenSecret(accessTokenSecret);

		em.persist(account);

		return account;
	}

	public List<RegisteredAccount> getAllRegisteredAccounts() {
		return em.createQuery("SELECT rAccount FROM RegisteredAccount rAccount ORDER BY rAccount.id",
				RegisteredAccount.class)
			.getResultList();
	}

	public RegisteredAccount findRegisteredAccountByScreenName(String screenName) {
		if (screenName == null) return null;

		List<RegisteredAccount> accountList = em.createNamedQuery("RegisteredAccount.findByScreentName", RegisteredAccount.class)
			.setParameter("screenName", screenName)
			.setMaxResults(1).getResultList();

		if (accountList != null && accountList.size() > 0) {
			return accountList.get(0);
		}

		return null;
	}

	@Transactional
	public RegisteredAccount insertOrUpdateRegisteredAccount(String screenName, String accessToken,
			String accessTokenSecret) {

		RegisteredAccount rAccount = findRegisteredAccountByScreenName(screenName);

		if (rAccount == null) {
			rAccount = insertRegisteredAccount(screenName, accessToken, accessTokenSecret);
		} else {
			rAccount.setAccessToken(accessToken);
			rAccount.setAccessTokenSecret(accessTokenSecret);
			em.persist(rAccount);
		}

		return rAccount;
	}
}
