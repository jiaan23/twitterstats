package net.infoforrest.twitterstats.dao.impl;


import java.util.List;

import net.infoforrest.twitterstats.SessionFactoryHelper;
import net.infoforrest.twitterstats.model.RegisteredAccount;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import junit.framework.TestCase;

public class TestTwitterAccountDaoImpl extends TestCase {
	private SessionFactory sessionFactory;

    @Override
	public void setUp() {
		this.sessionFactory = SessionFactoryHelper.getSessionFactory();
	}

    @Override
    public void tearDown(){
        this.sessionFactory.getCurrentSession().close();
        this.sessionFactory.close();
    }

    public void testInsertTwitterAccount() {
		// final Session session = sessionFactory.getCurrentSession();
		// final RegsteredAccountDaoImpl dao = new
		// RegsteredAccountDaoImpl(sessionFactory);
		//
		// session.beginTransaction();
		//
		// dao.insertTwitterAccount("testScreenName", "", "");
		// Query query =
		// session.createQuery("Select tAccount From RegisteredAccount tAccount Where tAccount.screenName=:screenName");
		// query.setParameter("screenName", "testScreenName");
		// List result = query.list();
		//
		// assertNotNull(result);
		// assertTrue(result.size() == 1);
		// RegisteredAccount tAccount = (RegisteredAccount) result.get(0);
		// assertTrue(tAccount.getScreenName().equals("testScreenName"));
    }
}
