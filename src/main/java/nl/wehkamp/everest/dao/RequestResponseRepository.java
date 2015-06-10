package nl.wehkamp.everest.dao;

import java.util.Set;

import nl.wehkamp.everest.model.Prediction;

public interface RequestResponseRepository {

	void save(Prediction record);

	Set<Prediction> findAll();

	void clear();
}
