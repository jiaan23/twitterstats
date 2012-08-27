package net.infoforrest.twitterstats.web.controller;

import javax.servlet.http.HttpSession;

import net.infoforrest.twitterstats.dao.RegisteredAccountDao;
import net.infoforrest.twitterstats.model.RegisteredAccount;
import net.infoforrest.twitterstats.service.TwitterAccountManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@Controller
@RequestMapping(value = "/twitter")
public class TwitterAuthenticationController {

	@Autowired
	private RegisteredAccountDao registeredAccountDao;

	@Autowired
	private TwitterAccountManager twitterAccountManager;

	@RequestMapping(value = "/auth")
	public ModelAndView twitterAuthorizeRedirect(HttpSession session) {

		Twitter twitter = new TwitterFactory().getInstance();
		session.setAttribute("twitter", twitter);
		RequestToken requestToken = null;
		try {
			// StringBuffer callbackURL = request.getRequestURL();
			// int index = callbackURL.lastIndexOf("/");
			// callbackURL.replace(index, callbackURL.length(), "").append(
			// "/callback");

			requestToken = twitter
					.getOAuthRequestToken("http://127.0.0.1:8080/twitterstats-web/twitter/callback");
			session.setAttribute("requestToken", requestToken);

		} catch (TwitterException e) {
			// TODO: handle exception properly
			throw new RuntimeException(e);
		}

		final ModelAndView mav = new ModelAndView("redirect:"
				+ requestToken.getAuthenticationURL());

		return mav;
	}

	@RequestMapping(value = "/callback")
	public ModelAndView twitterAuthorizeCallback(
			HttpSession session,
			@RequestParam(value = "oauth_token", required = false) String oauthToken,
			@RequestParam(value = "oauth_verifier", required = false) String oauthVerifier,
			@RequestParam(value = "denied", required = false) String deniedStr) {

		final ModelAndView mav = new ModelAndView();

		if ((deniedStr != null && !"".equals(deniedStr.trim()))
				|| (oauthToken == null || "".equals(oauthToken.trim())
						|| oauthVerifier == null || "".equals(oauthVerifier
						.trim()))) {

			mav.setViewName("twitter/authRequired");

		} else {
			Twitter twitter = (Twitter) session.getAttribute("twitter");
			RequestToken requestToken = (RequestToken) session
					.getAttribute("requestToken");

			if (twitter != null && requestToken != null) {
				try {

					AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier);

					twitterAccountManager.accountActivationOrRenew(twitter,
								accessToken);

					session.removeAttribute("requestToken");
				} catch (TwitterException e) {
					// TODO: handle exception properly
					throw new RuntimeException(e);
				}

			}

			mav.setViewName("redirect:/home");
		}

		return mav;
	}

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public ModelAndView twitterPost(HttpSession session,
			@RequestParam("text") String text) throws TwitterException {

		final ModelAndView mav = new ModelAndView();

		Twitter twitter = (Twitter) session.getAttribute("twitter");

		if (twitter != null && twitter.test())
		twitter.updateStatus(text);

		mav.setViewName("redirect:/home");
		return mav;
	}
}
