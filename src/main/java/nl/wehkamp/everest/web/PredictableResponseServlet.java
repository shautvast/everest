package nl.wehkamp.everest.web;

import static nl.wehkamp.everest.service.PredictionFactory.newPrediction;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.wehkamp.everest.dao.PredictionRepository;
import nl.wehkamp.everest.model.Prediction;
import nl.wehkamp.everest.service.ResponseFinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Mocks http requests using prefabricated predictions
 *
 */
@SuppressWarnings("serial")
@Controller
public class PredictableResponseServlet extends DispatcherServlet {
	private final static Logger log = LoggerFactory.getLogger(PredictableResponseServlet.class);

	@Autowired
	private ResponseFinder responseFinder;

	@Autowired
	private PredictionRepository predictionJsonFileRepository;

	@Override
	protected void service(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
		if (isUploadRequest(httpRequest)) {
			handleUpload(httpRequest, httpResponse);
		} else {
			handleMockRequest(httpRequest, httpResponse);
		}
	}

	private void handleMockRequest(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
		Optional<Prediction> someRequestResponse = responseFinder.find(httpRequest);

		if (someRequestResponse.isPresent()) {
			Prediction prediction = someRequestResponse.get();
			setPredictedResponseValues(httpResponse, prediction);
		} else {
			httpResponse.getWriter().print("Not found");
			httpResponse.setStatus(404);
		}
	}

	private void setPredictedResponseValues(HttpServletResponse httpResponse, Prediction prediction) throws IOException {
		httpResponse.getWriter().print(prediction.getResponse());
		httpResponse.setStatus(prediction.getResponseStatus());
		if (prediction.hasReponseHeaders()) {
			setPredictedResponseHeaders(httpResponse, prediction);
		}
	}

	private void setPredictedResponseHeaders(HttpServletResponse httpResponse, Prediction prediction) {
		for (String headername : prediction.getResponseHeaders().getHeaderNames()) {
			for (String headervalue : prediction.getResponseHeaders().getAll(headername)) {
				httpResponse.addHeader(headername, headervalue);
			}
		}
	}

	private void handleUpload(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
		try {
			predictionJsonFileRepository.save(newPrediction(readJsonFromHttpBody(httpRequest)));
			httpResponse.setStatus(201);
		} catch (IOException e) {
			httpResponse.setStatus(500);
			httpResponse.getWriter().print(e.getClass().getName() + ":" + e.getMessage());
		}
	}

	private String readJsonFromHttpBody(HttpServletRequest httprequest) throws IOException {
		BufferedReader reader = httprequest.getReader();
		String line;
		StringBuilder builder = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}
		String json = builder.toString();

		log.info("upload of json {}", json);

		return json;
	}

	private boolean isUploadRequest(HttpServletRequest req) {
		return req.getMethod().equals("POST") && req.getPathInfo().startsWith("/__api/upload") && req.getHeader("Content-Type").equals("application/json");
	}

	public void setPredictionRepository(PredictionRepository predictionJsonFileRepository) {
		this.predictionJsonFileRepository = predictionJsonFileRepository;
	}
}
