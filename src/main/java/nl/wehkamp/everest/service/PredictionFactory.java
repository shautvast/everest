package nl.wehkamp.everest.service;

import static nl.wehkamp.everest.model.Prediction.fromDto;

import java.io.IOException;

import nl.wehkamp.everest.model.Prediction;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PredictionFactory {
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static Prediction newPrediction(String json) throws IOException {
		return fromDto(objectMapper.readValue(json, Prediction.Dto.class));
	}
}
