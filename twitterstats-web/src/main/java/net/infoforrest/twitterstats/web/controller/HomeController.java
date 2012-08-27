package net.infoforrest.twitterstats.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.infoforrest.twitterstats.dao.RegisteredAccountDao;
import net.infoforrest.twitterstats.model.RegisteredAccount;
import net.infoforrest.twitterstats.model.TwitterAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import twitter4j.AccountSettings;
import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

@Controller
@RequestMapping(value = "/")
public class HomeController {

	@Autowired
	private RegisteredAccountDao registeredAccountDao;

	public static final String TWITTER_SCREEN_NAME_COOKIE_NAME = "twitter_screen_name";
	public static final int COOKIE_EXPIRE_TIME = 2592000; // 30 days in seconds

	@RequestMapping(value = "/home")
	public ModelAndView home(HttpServletResponse response, HttpSession session,
			@CookieValue(value=TWITTER_SCREEN_NAME_COOKIE_NAME, required=false) String screenNameCookie)
			throws IllegalStateException {

		final ModelAndView mav = new ModelAndView();

		Twitter twitter = (Twitter) session.getAttribute("twitter");

		if (twitter == null) {
			if (screenNameCookie != null && !"".equals(screenNameCookie.trim())) {
				final RegisteredAccount rAccount = registeredAccountDao
						.findRegisteredAccountByScreenName(screenNameCookie);

				if (rAccount != null) {
					ConfigurationBuilder cb = new ConfigurationBuilder();
					cb.setOAuthAccessToken(rAccount.getAccessToken())
							.setOAuthAccessTokenSecret(rAccount.getAccessTokenSecret());

					TwitterFactory tf = new TwitterFactory(cb.build());
					twitter = tf.getInstance();
				}
			}
		} else {
			boolean twitterTest = false;
			String screenName = null;

			try {
				twitterTest = twitter.test();
				screenName = twitter.getScreenName();
			} catch (TwitterException te) {
				;
			}

			if (twitterTest && screenName != null) {
				final RegisteredAccount rAccount = registeredAccountDao
						.findRegisteredAccountByScreenName(screenName);

				if (rAccount != null) {
					if (screenNameCookie == null) {
						Cookie cookie = new Cookie(TWITTER_SCREEN_NAME_COOKIE_NAME, rAccount.getScreenName());
						cookie.setMaxAge(COOKIE_EXPIRE_TIME);
						response.addCookie(cookie);
					}

					mav.addObject("screenName", rAccount.getScreenName());

					final List<String> followings = new ArrayList<String>();
					for (TwitterAccount tAccount : rAccount.getFollowing()) {
						followings.add(tAccount.getScreenName());
					}

					mav.addObject("followings", followings);
				}
			}

		}

		mav.setViewName("home");

		return mav;
	}
}
