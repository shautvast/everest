package nl.wehkamp.everest.dao;

import java.util.HashSet;
import java.util.Set;

import nl.wehkamp.everest.model.Prediction;

import org.springframework.stereotype.Repository;

@Repository("requestResponseMemoryRepository")
public class RequestResponseMemoryRepository implements RequestResponseRepository {
	protected Set<Prediction> cache = new HashSet<>();

	@Override
	public Set<Prediction> findAll() {
		return new HashSet<>(cache);
	}

	@Override
	public void save(Prediction record) {
		cache.add(record);
	}

	public void clear() {
		cache.clear();
	}
}
