package nl.wehkamp.everest.web;

import static java.util.Collections.singletonMap;
import static nl.wehkamp.everest.util.TestClient.httpGet;
import static nl.wehkamp.everest.util.TestClient.httpPost;
import static org.testng.Assert.assertEquals;
import nl.wehkamp.everest.WebServer;
import nl.wehkamp.everest.dao.PredictionMemoryRepository;
import nl.wehkamp.everest.model.Prediction;
import nl.wehkamp.everest.service.ResponseFinder;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * Executes tests against a running webserver.
 */
public class MockingServletTests {

	private PredictionMemoryRepository requestResponseRepository;

	@BeforeSuite
	public void setup() {
		WebServer.start();
		ResponseFinder responseFinder = WebServer.instance.getBean(ResponseFinder.class);
		requestResponseRepository = new PredictionMemoryRepository();
		responseFinder.setPredictionRepository(requestResponseRepository);
	}

	@AfterMethod
	public void clearRepository() {
		requestResponseRepository.clear();
	}

	@Test
	public void testGet() {
		Prediction record = new Prediction();
		record.setMethod("GET");
		record.setUrl("/testget");
		record.setResponse("get successful");
		record.setResponseStatus(200);
		requestResponseRepository.save(record);
		assertEquals(httpGet("/testget"), "200:get successful");
	}

	@Test
	public void testGetWithHeader() {
		Prediction record = new Prediction();
		record.setMethod("GET");
		record.setUrl("/testget");
		record.getHeaders().add("Content-Type", "application/json");
		record.setResponse("getWithHeader successful");
		record.setResponseStatus(200);
		requestResponseRepository.save(record);
		assertEquals(httpGet("/testget", singletonMap("Content-Type", "application/json")), "200:getWithHeader successful");
	}

	@Test
	public void testGetWithWrongHeader() {
		Prediction record = new Prediction();
		record.setMethod("GET");
		record.setUrl("/testget");
		record.getHeaders().add("Content-Type", "application/xml");
		record.setResponse("getWithHeader successful");
		record.setResponseStatus(200);
		requestResponseRepository.save(record);
		assertEquals(httpGet("/testget", singletonMap("Content-Type", "application/json")), "404:Not found");
	}

	@Test
	public void testGetWildcard() {
		Prediction record = new Prediction();
		record.setMethod("GET");
		record.setUrl("/testget/.*");
		record.setResponse("get wildcard successful");
		record.setResponseStatus(200);
		requestResponseRepository.save(record);
		assertEquals(httpGet("/testget/foo"), "200:get wildcard successful");
	}

	@Test
	public void testPost() {
		Prediction record = new Prediction();
		record.setMethod("POST");
		record.setUrl("/testpost");
		record.setResponse("post successful");
		record.setResponseStatus(200);
		requestResponseRepository.save(record);
		assertEquals(httpPost("/testpost", "body"), "200:post successful");
	}
}
