package net.infoforrest.twitterstats.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import net.infoforrest.twitterstats.model.MongoTest;
import net.infoforrest.twitterstats.service.impl.TestMongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/mongoTest")
public class MongoTestController {

	@Autowired
	private TestMongodb testMongodb;

	@RequestMapping(value = "/")
	public @ResponseBody List<MongoTest> twitterAuthorizeRedirect(HttpSession session) {
		return testMongodb.getAllTests();
	}
}
