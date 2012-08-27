package net.infoforrest.twitterstats.service;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

public interface TwitterAccountManager {
	abstract public void accountActivationOrRenew(Twitter twitter, AccessToken accessToken) throws IllegalStateException, TwitterException;
}
