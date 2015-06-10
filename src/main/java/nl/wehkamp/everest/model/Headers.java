package nl.wehkamp.everest.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class Headers {
	private Map<String, List<String>> headers = new HashMap<String, List<String>>();

	public Headers() {
	}

	public Headers(Map<String, List<String>> headers) {
		this.headers = headers;
	}

	public boolean areSet() {
		return !headers.isEmpty();
	}

	/**
	 * Returns header value for given name or null if none found.
	 */
	public String get(String name) {
		List<String> list = headers.get(name);
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	/**
	 * returns all header values for a given name. Or null if none set
	 */
	public List<String> getAll(String name) {
		return headers.get(name);
	}

	public Map<String, List<String>> getContent() {
		return headers;
	}

	public void add(String name, String value) {
		List<String> list = headers.get(name);
		if (list == null) {
			list = new ArrayList<String>();
			headers.put(name, list);
		}
		list.add(value);
	}

	public boolean satisfies(HttpServletRequest request) {
		for (Map.Entry<String, List<String>> header : headers.entrySet()) {
			List<String> headersInRequest = getHeaderValues(request, header.getKey());
			List<String> headersInExpectation = header.getValue();
			if (headersInExpectation.containsAll(headersInRequest)) {
				return true;
			}
		}
		return false;
	}

	private List<String> getHeaderValues(HttpServletRequest request, String name) {
		List<String> requestHeaderValues = new ArrayList<String>();
		for (Enumeration<String> requestheaderlist = request.getHeaders(name); requestheaderlist.hasMoreElements();) {
			requestHeaderValues.add(requestheaderlist.nextElement());
		}
		return requestHeaderValues;
	}

}
