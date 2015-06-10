package nl.wehkamp.everest.dao;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import nl.wehkamp.everest.model.Headers;
import nl.wehkamp.everest.model.Prediction;
import nl.wehkamp.everest.util.Uuids;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class RecordRepositoryTest {

	@AfterMethod
	public void teardown() throws IOException {
		Uuids.removeTestValue();
		Files.delete(Paths.get("everest_data", "wehkamp.nl.json"));
	}

	@Test
	public void saveShouldWriteCorrectJson() throws IOException {
		Uuids.setTestValue("91f83cd9-a0a5-49f5-b740-78ba8f504797");

		Prediction record = new Prediction();
		record.setName("wehkamp.nl");
		record.setUrl("http://www.wehkamp.nl");
		record.setMethod("GET");
		Headers headers = new Headers();
		headers.add("Accept", "application/json");
		record.setHeaders(headers);
		record.setResponseStatus(200);
		record.setResponse("<html>");
		new RequestResponseJsonFileRepository().save(record);

		List<String> lines = Files.readAllLines(Paths.get("everest_data", "wehkamp.nl.json"), StandardCharsets.UTF_8);

		assertFalse(lines.isEmpty());
		System.out.println(lines.get(0));

		assertEquals(
				lines.get(0),
				"{\"id\":\"91f83cd9-a0a5-49f5-b740-78ba8f504797\",\"name\":\"wehkamp.nl\",\"url\":\"http://www.wehkamp.nl\",\"method\":\"GET\",\"requestHeaders\":{\"Accept\":[\"application/json\"]},\"response\":\"<html>\",\"responseStatus\":200}");
	}
}
