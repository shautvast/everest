package nl.wehkamp.everest.dao;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import nl.wehkamp.everest.model.Prediction;

import org.springframework.stereotype.Repository;

@Repository
public class PredictionMemoryRepository implements PredictionRepository {
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

	public Optional<Prediction> findByName(String name) {
		for (Prediction p : cache) {
			if (p.getName().equals(name)) {
				return Optional.of(p);
			}
		}
		return Optional.empty();
	}
}
