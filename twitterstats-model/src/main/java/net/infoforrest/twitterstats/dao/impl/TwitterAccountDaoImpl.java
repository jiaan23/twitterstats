package net.infoforrest.twitterstats.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.infoforrest.twitterstats.dao.TwitterAccountDao;
import net.infoforrest.twitterstats.model.TwitterAccount;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TwitterAccountDaoImpl implements TwitterAccountDao {

	@PersistenceContext(unitName="twitterstatsPersistenceUnit")
    private EntityManager em;

	@Transactional
	public TwitterAccount findByScreenName(String screenName) {
		if (screenName == null) return null;

		List<TwitterAccount> accountList = em.createNamedQuery("TwitterAccount.findByScreentName", TwitterAccount.class)
				.setParameter("screenName", screenName)
				.setMaxResults(1).getResultList();

			if (accountList != null && accountList.size() > 0) {
				return accountList.get(0);
			}

			return null;
	}

}
