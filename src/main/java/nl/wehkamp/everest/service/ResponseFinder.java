/*
 * Project: Wehkamp everest
 * Copyright (c) 2015 Wehkamp B.V.
 *
 * Versie : $LastChangedRevision: $
 * Datum  : $LastChangedDate: $
 * Door   : $LastChangedBy: $
 */
package nl.wehkamp.everest.service;

import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import nl.wehkamp.everest.dao.RequestResponseRepository;
import nl.wehkamp.everest.model.Prediction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Matches incoming requests against setup predictions
 */
@Service
public class ResponseFinder {
	@Autowired
	private RequestResponseRepository requestResponseRepository;

	public Optional<Prediction> find(HttpServletRequest request) {
		Optional<Prediction> response = Optional.empty();

		Set<Prediction> all = requestResponseRepository.findAll();
		for (Prediction prediction : all) {
			response = matchUrlAndMethodAndHeaders(request, response, prediction);
		}
		return response;
	}

	private Optional<Prediction> matchUrlAndMethodAndHeaders(HttpServletRequest request, Optional<Prediction> response, Prediction rr) {
		if (rr.requestMatches(request.getPathInfo())) {
			response = matchMethodAndHeaders(request, response, rr);
		}
		return response;
	}

	private Optional<Prediction> matchMethodAndHeaders(HttpServletRequest request, Optional<Prediction> response, Prediction rr) {
		if (rr.getMethod().equals(request.getMethod())) {
			if (rr.containsHeaders()) {
				response = matchHeaders(request, response, rr);
			} else {
				response = Optional.of(rr);
			}
		}
		return response;
	}

	private Optional<Prediction> matchHeaders(HttpServletRequest request, Optional<Prediction> response, Prediction prediction) {
		if (prediction.getHeaders().satisfies(request)) {
			response = Optional.of(prediction);
		}
		return response;
	}

	public void setRequestResponseRepository(RequestResponseRepository requestResponseRepository) {
		this.requestResponseRepository = requestResponseRepository;
	}
}
