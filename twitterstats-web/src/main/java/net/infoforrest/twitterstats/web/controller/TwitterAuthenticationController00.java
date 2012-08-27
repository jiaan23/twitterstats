package net.infoforrest.twitterstats.web.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import net.oauth.OAuthServiceProvider;
import net.oauth.client.OAuthClient;
import net.oauth.client.httpclient4.HttpClient4;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/twitter00")
public class TwitterAuthenticationController00 {

	private String baseURL = "https://api.twitter.com";
	private String requestTokenURL = baseURL + "/oauth/request_token";
	private String authorizationURL = baseURL + "/oauth/authorize";
	private String accessTokenURL = baseURL + "/oauth/access_token";

	private String applicationName = "tweet_stats";

	private String consumerKey = "BLUFjtdNLeFUZGcMUQ";
	private String consumerSecret = "pyddXpFolCWBPz7vKw2auqo7EXXGmLGyh3HhxvwVAc";

	@RequestMapping(value = "/auth")
	public ModelAndView twitterAuthorizeRedirect() throws IOException, OAuthException, URISyntaxException {
		AccessToken accessToken = requestAccessToken();

		OAuthMessage oAuthMessage = authorize(accessToken);

		final ModelAndView mav = new ModelAndView("redirect:" +
				authorizationURL + "?oauth_token=" + accessToken.getRequestToken());

		return mav;
	}

	// TODO: the code should be put in the service layer
	protected AccessToken requestAccessToken() throws IOException,
			OAuthException, URISyntaxException {
		OAuthAccessor accessor = createOAuthAccessor();
		OAuthClient client = new OAuthClient(new HttpClient4());
		client.getRequestToken(accessor);

		return new AccessToken(accessor.requestToken, accessor.tokenSecret);
	}

	protected OAuthMessage authorize(AccessToken accessToken) throws IOException,
			OAuthException, URISyntaxException {
		Map<String, String> paramProps = new HashMap<String, String>();
		paramProps.put("application_name", applicationName);
		paramProps.put("oauth_token", accessToken.getRequestToken());

		OAuthAccessor accessor = createOAuthAccessor();

		OAuthMessage response = sendRequest(paramProps,
				accessor.consumer.serviceProvider.userAuthorizationURL,
				accessToken.getTokenSecret());

		return response;
	}

	protected OAuthMessage sendRequest(Map<String, String> map, String url,
			String tokenSecret) throws IOException, URISyntaxException,
			OAuthException {
		List<Map.Entry<String, String>> params = new ArrayList<Map.Entry<String, String>>();
		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, String> p = it.next();
			params.add(new OAuth.Parameter(p.getKey(), p.getValue()));
		}

		OAuthAccessor accessor = createOAuthAccessor();

		accessor.tokenSecret = tokenSecret;

		OAuthClient client = new OAuthClient(new HttpClient4());
		return client.invoke(accessor, "GET", url, params);
	}

	protected OAuthAccessor createOAuthAccessor() throws IOException,
			OAuthException, URISyntaxException {

		OAuthServiceProvider provider = new OAuthServiceProvider(
				requestTokenURL, authorizationURL, accessTokenURL);
		OAuthConsumer consumer = new OAuthConsumer(null, consumerKey,
				consumerSecret, provider);

		OAuthAccessor accessor = new OAuthAccessor(consumer);

		return accessor;
	}

	public static class AccessToken {
		private String requestToken;
		private String tokenSecret;

		public AccessToken(String requestToken, String tokenSecret) {
			super();
			this.requestToken = requestToken;
			this.tokenSecret = tokenSecret;
		}

		public String getRequestToken() {
			return requestToken;
		}

		public void setRequestToken(String requestToken) {
			this.requestToken = requestToken;
		}

		public String getTokenSecret() {
			return tokenSecret;
		}

		public void setTokenSecret(String tokenSecret) {
			this.tokenSecret = tokenSecret;
		}

		@Override
		public String toString() {
			return "AccessToken [requestToken=" + requestToken
					+ ", tokenSecret=" + tokenSecret + "]";
		}
	}

}
