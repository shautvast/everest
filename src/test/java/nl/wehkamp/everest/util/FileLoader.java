package nl.wehkamp.everest.util;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

public class FileLoader {
	public static String load(String resource) {
		try {
			return IOUtils.toString(new ClassPathResource(resource).getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
