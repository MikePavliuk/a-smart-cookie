package com.a_smart_cookie.controller.route;

public class HttpPath {
	private final Routable path;
	private final HttpHandlerType httpHandlerType;

	public HttpPath(Routable path, HttpHandlerType httpHandlerType) {
		this.path = path;
		this.httpHandlerType = httpHandlerType;
	}

	public Routable getPath() {
		return path;
	}

	public HttpHandlerType getHttpHandlerType() {
		return httpHandlerType;
	}

}
