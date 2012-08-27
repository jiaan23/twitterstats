package net.infoforrest.twitterstats.dao;

import java.util.List;

import net.infoforrest.twitterstats.model.RegisteredAccount;

public interface RegisteredAccountDao {

	abstract public RegisteredAccount insertRegisteredAccount(String screenName, String accessToken, String accessTokenSecret);

	abstract public List<RegisteredAccount> getAllRegisteredAccounts();

	abstract public RegisteredAccount findRegisteredAccountByScreenName(String screenName);

	abstract public RegisteredAccount insertOrUpdateRegisteredAccount(String screenName, String accessToken, String accessTokenSecret);
}
