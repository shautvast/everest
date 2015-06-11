package nl.wehkamp.everest.dao;

import java.util.Set;

import nl.wehkamp.everest.model.Prediction;

public interface PredictionRepository {

	void save(Prediction prediction);

	Set<Prediction> findAll();

	void clear();
}
