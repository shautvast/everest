package nl.wehkamp.everest.web;

import static java.util.Collections.singletonMap;
import static nl.wehkamp.everest.web.TestClient.get;
import static nl.wehkamp.everest.web.TestClient.post;
import static org.testng.Assert.assertEquals;
import nl.wehkamp.everest.WebServer;
import nl.wehkamp.everest.dao.RequestResponseMemoryRepository;
import nl.wehkamp.everest.model.Prediction;
import nl.wehkamp.everest.service.ResponseFinder;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * Executes tests against a running webserver.
 */
public class MockingServletTests {

	private RequestResponseMemoryRepository requestResponseRepository;

	@BeforeSuite
	public void setup() {
		WebServer.start();
		ResponseFinder responseFinder = WebServer.instance.getBean(ResponseFinder.class);
		requestResponseRepository = new RequestResponseMemoryRepository();
		responseFinder.setRequestResponseRepository(requestResponseRepository);
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
		assertEquals(get("/testget"), "200:get successful");
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
		assertEquals(get("/testget", singletonMap("Content-Type", "application/json")), "200:getWithHeader successful");
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
		assertEquals(get("/testget", singletonMap("Content-Type", "application/json")), "404:Not found");
	}

	@Test
	public void testGetWildcard() {
		Prediction record = new Prediction();
		record.setMethod("GET");
		record.setUrl("/testget/.*");
		record.setResponse("get wildcard successful");
		record.setResponseStatus(200);
		requestResponseRepository.save(record);
		assertEquals(get("/testget/foo"), "200:get wildcard successful");
	}

	@Test
	public void testPost() {
		Prediction record = new Prediction();
		record.setMethod("POST");
		record.setUrl("/testpost");
		record.setResponse("post successful");
		record.setResponseStatus(200);
		requestResponseRepository.save(record);
		assertEquals(post("/testpost", "body"), "200:post successful");
	}
}
