package nl.wehkamp.everest.util;

import java.util.Optional;
import java.util.UUID;

public class Uuids {
	private static Optional<String> testValue = Optional.empty();

	public static String create() {
		if (!testValue.isPresent()) {
			return UUID.randomUUID().toString();
		} else {
			return testValue.get();
		}
	}

	public static void removeTestValue() {
		testValue = Optional.empty();
	}

	public static void setTestValue(String testValue) {
		Uuids.testValue = Optional.of(testValue);
	}
}
