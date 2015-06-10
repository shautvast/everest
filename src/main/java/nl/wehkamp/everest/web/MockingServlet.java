package nl.wehkamp.everest.web;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.wehkamp.everest.model.Prediction;
import nl.wehkamp.everest.service.ResponseFinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Mocks http requests
 *
 */
@SuppressWarnings("serial")
@Controller
public class MockingServlet extends DispatcherServlet {
	@Autowired
	private ResponseFinder responseFinder;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Optional<Prediction> someRequestResponse = responseFinder.find(req);
		if (someRequestResponse.isPresent()) {
			Prediction requestResponse = someRequestResponse.get();
			resp.getWriter().print(requestResponse.getResponse());
			resp.setStatus(requestResponse.getResponseStatus());
		} else {
			resp.getWriter().print("Not found");
			resp.setStatus(404);
		}

	}
}
