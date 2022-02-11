package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.controller.route.HttpPath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

public abstract class Command implements Serializable {

	private static final long serialVersionUID = 4513060755527075000L;

	public abstract HttpPath execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}

}
