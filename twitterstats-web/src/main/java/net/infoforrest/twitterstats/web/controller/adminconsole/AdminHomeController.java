package net.infoforrest.twitterstats.web.controller.adminconsole;

import javax.servlet.http.HttpSession;

import net.infoforrest.twitterstats.dao.RegisteredAccountDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
public class AdminHomeController {

	@Autowired
	private RegisteredAccountDao registeredAccountDao;

	@RequestMapping(value = "/home")
	public ModelAndView home(HttpSession session) {
		final ModelAndView mav = new ModelAndView();

		mav.addObject("allAccounts", registeredAccountDao.getAllRegisteredAccounts());
		mav.setViewName("admin/home");

		return mav;
	}
}
