package nl.wehkamp.everest.model;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import nl.wehkamp.everest.util.Uuids;

public class Prediction {
	private Pattern urlPattern;

	private String id;
	private String name;

	private String url;
	private String method;
	private Headers requestHeaders;

	private String response;
	private int responseStatus;

	public boolean requestMatches(String requesturl) {
		return urlPattern.matcher(requesturl).matches();
	}

	public Prediction() {
		id = Uuids.create();
	}

	private Prediction(Dto predictionDto) {
		this.id = predictionDto.id;
		this.name = predictionDto.name;
		setUrl(predictionDto.url);
		this.method = predictionDto.method;
		this.requestHeaders = new Headers(predictionDto.requestHeaders);
		this.response = predictionDto.response;
		this.responseStatus = predictionDto.responseStatus;
	}

	public String getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		this.urlPattern = Pattern.compile(url);
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Headers getHeaders() {
		if (requestHeaders == null) {
			requestHeaders = new Headers();
		}
		return requestHeaders;
	}

	public void setHeaders(Headers headers) {
		this.requestHeaders = headers;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prediction other = (Prediction) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public boolean containsHeaders() {
		return requestHeaders != null && requestHeaders.areSet();
	}

	@Override
	public String toString() {
		return "RequestResponse [url=" + url + ", method=" + method + ", headers=" + requestHeaders + "]";
	}

	public Dto toDto() {
		return new Dto(id, name, url, method, requestHeaders.getContent(), response, responseStatus);
	}

	public static Prediction fromDto(Dto predictionDto) {
		return new Prediction(predictionDto);
	}

	public static class Dto {
		private String id;
		private String name;

		private String url;
		private String method;
		private Map<String, List<String>> requestHeaders;

		private String response;
		private int responseStatus;

		public Dto() {
		}

		private Dto(String id, String name, String url, String method, Map<String, List<String>> requestHeaders, String response, int responseStatus) {
			super();
			this.id = id;
			this.name = name;
			this.url = url;
			this.method = method;
			this.requestHeaders = requestHeaders;
			this.response = response;
			this.responseStatus = responseStatus;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public Map<String, List<String>> getRequestHeaders() {
			return requestHeaders;
		}

		public void setRequestHeaders(Map<String, List<String>> requestHeaders) {
			this.requestHeaders = requestHeaders;
		}

		public String getResponse() {
			return response;
		}

		public void setResponse(String response) {
			this.response = response;
		}

		public int getResponseStatus() {
			return responseStatus;
		}

		public void setResponseStatus(int responseStatus) {
			this.responseStatus = responseStatus;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Dto other = (Dto) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}

	}
}
