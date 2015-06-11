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

import nl.wehkamp.everest.dao.PredictionRepository;
import nl.wehkamp.everest.model.Prediction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Matches incoming requests against setup predictions
 */
@Service
public class ResponseFinder {
	@Autowired
	private PredictionRepository predictionJsonFileRepository;

	public Optional<Prediction> find(HttpServletRequest request) {
		Optional<Prediction> response = Optional.empty();

		Set<Prediction> all = predictionJsonFileRepository.findAll();
		for (Prediction prediction : all) {
			response = matchUrlAndMethodAndHeaders(request, response, prediction);
		}
		return response;
	}

	private Optional<Prediction> matchUrlAndMethodAndHeaders(HttpServletRequest request, Optional<Prediction> response, Prediction prediction) {
		if (prediction.requestMatches(request.getPathInfo())) {
			response = matchMethodAndHeaders(request, response, prediction);
		}
		return response;
	}

	private Optional<Prediction> matchMethodAndHeaders(HttpServletRequest request, Optional<Prediction> response, Prediction prediction) {
		if (prediction.getMethod().equals(request.getMethod())) {
			if (prediction.containsRequestHeaders()) {
				response = matchHeaders(request, response, prediction);
			} else {
				response = Optional.of(prediction);
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

	public void setPredictionRepository(PredictionRepository predictionJsonFileRepository) {
		this.predictionJsonFileRepository = predictionJsonFileRepository;
	}
}
