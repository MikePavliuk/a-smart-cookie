package com.a_smart_cookie.controller.route;

/**
 * Transfer object for containing address of request and the way of its handling.
 *
 */
public class HttpPath {
	private final String path;
	private final HttpHandlerType httpHandlerType;

	public HttpPath(String path, HttpHandlerType httpHandlerType) {
		this.path = path;
		this.httpHandlerType = httpHandlerType;
	}

	public HttpPath(Routable routable, HttpHandlerType httpHandlerType) {
		this.path = routable.getValue();
		this.httpHandlerType = httpHandlerType;
	}

	public String getPath() {
		return path;
	}

	public HttpHandlerType getHttpHandlerType() {
		return httpHandlerType;
	}

	@Override
	public String toString() {
		return "HttpPath{" +
				"path=" + path +
				", httpHandlerType=" + httpHandlerType +
				'}';
	}
}
