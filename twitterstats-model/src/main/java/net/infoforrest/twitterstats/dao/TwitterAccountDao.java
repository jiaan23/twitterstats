package net.infoforrest.twitterstats.dao;

import net.infoforrest.twitterstats.model.TwitterAccount;

public interface TwitterAccountDao {
	abstract public TwitterAccount findByScreenName(String screenName);
}
