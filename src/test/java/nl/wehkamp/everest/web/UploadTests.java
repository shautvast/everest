package nl.wehkamp.everest.web;

import static java.util.Collections.singletonMap;
import static nl.wehkamp.everest.util.FileLoader.load;
import static nl.wehkamp.everest.util.TestClient.httpPost;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Optional;

import nl.wehkamp.everest.WebServer;
import nl.wehkamp.everest.dao.PredictionMemoryRepository;
import nl.wehkamp.everest.model.Prediction;
import nl.wehkamp.everest.service.ResponseFinder;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class UploadTests {
	private PredictionMemoryRepository predictionMemoryRepository;

	@BeforeSuite
	public void setup() {
		WebServer.start();
		ResponseFinder responseFinder = WebServer.instance.getBean(ResponseFinder.class);
		predictionMemoryRepository = new PredictionMemoryRepository();
		responseFinder.setPredictionRepository(predictionMemoryRepository);

		PredictableResponseServlet servlet = WebServer.instance.getBean(PredictableResponseServlet.class);
		servlet.setPredictionRepository(predictionMemoryRepository);
		predictionMemoryRepository.clear();
	}

	@Test
	public void test() {
		httpPost("/__api/upload", load("uploadtest.json"), singletonMap("Content-Type", "application/json"));

		Optional<Prediction> prediction = predictionMemoryRepository.findByName("test.nl");
		assertTrue(prediction.isPresent());
		assertEquals(prediction.get().getUrl(), "/entry");
	}
}
