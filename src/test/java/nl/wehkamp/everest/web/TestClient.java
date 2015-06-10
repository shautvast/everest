package nl.wehkamp.everest.web;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

public class TestClient {
	private static HttpClient httpclient = HttpClients.createDefault();
	private static HttpHost target = new HttpHost("localhost", 8080, "http");

	public static String get(String url) {
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpclient.execute(target, httpGet);
			String body = IOUtils.toString(httpResponse.getEntity().getContent());
			return httpResponse.getStatusLine().getStatusCode() + ":" + body;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String get(String url, Map<String, String> headers) {
		HttpGet httpGet = new HttpGet(url);
		for (Map.Entry<String, String> header : headers.entrySet()) {
			httpGet.addHeader(header.getKey(), header.getValue());
		}
		try {
			HttpResponse httpResponse = httpclient.execute(target, httpGet);
			String body = IOUtils.toString(httpResponse.getEntity().getContent());
			return httpResponse.getStatusLine().getStatusCode() + ":" + body;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String post(String url, String postBody) {
		try {
			HttpPost httpPost = new HttpPost(url);
			HttpResponse httpResponse = httpclient.execute(target, httpPost);
			String body = IOUtils.toString(httpResponse.getEntity().getContent());
			return httpResponse.getStatusLine().getStatusCode() + ":" + body;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
