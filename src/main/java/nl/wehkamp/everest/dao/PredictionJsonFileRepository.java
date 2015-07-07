/*
 * Project: Wehkamp everest
 * Copyright (c) 2015 Wehkamp B.V.
 *
 * Versie : $LastChangedRevision: $
 * Datum  : $LastChangedDate: $
 * Door   : $LastChangedBy: $
 */
package nl.wehkamp.everest.dao;

import static java.nio.file.Files.newDirectoryStream;
import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;
import static java.nio.file.Paths.get;
import static nl.wehkamp.everest.model.Prediction.fromDto;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import nl.wehkamp.everest.model.Prediction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PredictionJsonFileRepository extends PredictionMemoryRepository {
	private static final Logger log = LoggerFactory.getLogger(PredictionJsonFileRepository.class);

	private String dataDirectoryName = System.getProperty("everest.data", "everest_data");

	private ObjectMapper jackson = new ObjectMapper();

	public PredictionJsonFileRepository() {
		createDataDirectoryIfAbsent();
		fillCacheIfEmpty();
	}

	@Override
	public void save(Prediction prediction) {
		super.save(prediction);
		OutputStream os = null;
		try {
			os = trySaveJson(prediction);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
			}
		}
	}

	private OutputStream trySaveJson(Prediction record) throws IOException, JsonGenerationException, JsonMappingException {
		OutputStream os;
		String filename = record.getName() + ".json";
		os = newOutputStream(get(dataDirectoryName, filename));
		jackson.writeValue(os, record.toDto());
		return os;
	}

	private void fillCacheIfEmpty() {
		if (cache.isEmpty()) {
			readDataFilesIntoMemory();
		}
	}

	private void readDataFilesIntoMemory() {
		try {
			DirectoryStream<Path> directoryStream = newDirectoryStream(get(dataDirectoryName));
			for (Path pathForJsonFile : directoryStream) {
				if (pathForJsonFile.toString().endsWith(".json")) {
					log.info("reading {}", pathForJsonFile);
					Prediction requestResponse = readRequestResponseFromJson(pathForJsonFile);
					requestResponse.setName(filename(pathForJsonFile));
					cache.add(requestResponse);
					log.info("reading {} into memory, mapping path {}", pathForJsonFile, requestResponse.getUrl());
				}
			}
		} catch (IOException x) {
			throw new RuntimeException(x);
		}
	}

	private String filename(Path path) {
		return path.getFileName().toString();
	}

	private Prediction readRequestResponseFromJson(Path path) throws IOException, JsonParseException, JsonMappingException {
		return fromDto(jackson.readValue(newInputStream(path), Prediction.Dto.class));
	}

	private void createDataDirectoryIfAbsent() {
		try {
			if (!Files.exists(Paths.get(dataDirectoryName))) {
				Files.createDirectory(Paths.get(dataDirectoryName));
			}
			System.out.println(Paths.get(dataDirectoryName).toFile().getAbsolutePath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
