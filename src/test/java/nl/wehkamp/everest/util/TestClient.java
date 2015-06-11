package nl.wehkamp.everest.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;

public class TestClient {
	private static HttpClient httpclient = HttpClients.createDefault();
	private static HttpHost target = new HttpHost("localhost", 8080, "http");

	public static String httpGet(String url) {
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpclient.execute(target, httpGet);
			String body = IOUtils.toString(httpResponse.getEntity().getContent());
			return httpResponse.getStatusLine().getStatusCode() + ":" + body;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String httpGet(String url, Map<String, String> headers) {
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

	public static String httpPost(String url, String postBody) {
		try {
			HttpPost httpPost = createPost(url, postBody, null);
			HttpResponse httpResponse = httpclient.execute(target, httpPost);
			String responseBody = IOUtils.toString(httpResponse.getEntity().getContent());
			return httpResponse.getStatusLine().getStatusCode() + ":" + responseBody;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String httpPost(String url, String postBody, Map<String, String> headers) {
		try {
			HttpPost httpPost = createPost(url, postBody, headers);

			HttpResponse httpResponse = httpclient.execute(target, httpPost);
			String body = IOUtils.toString(httpResponse.getEntity().getContent());
			return httpResponse.getStatusLine().getStatusCode() + ":" + body;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static HttpPost createPost(String url, String postBody, Map<String, String> headers) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);
		HttpEntity entity = new ByteArrayEntity(postBody.getBytes("UTF-8"));
		httpPost.setEntity(entity);
		if (headers != null) {
			for (Map.Entry<String, String> header : headers.entrySet()) {
				httpPost.addHeader(header.getKey(), header.getValue());
			}
		}
		return httpPost;
	}

}
