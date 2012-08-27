package net.infoforrest.twitterstats.service.impl;

import java.util.List;

import net.infoforrest.twitterstats.model.MongoTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
public class TestMongodb {

	@Autowired
	private MongoOperations mongoOperation;

	public List<MongoTest> getAllTests() {
		if (mongoOperation.collectionExists("test")) {
			return mongoOperation.findAll(MongoTest.class, "test");
		} else {
			return null;
		}
	}
}
