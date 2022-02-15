package com.a_smart_cookie.filter;

import com.a_smart_cookie.util.CookieHandler;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Filter that sets locale value for i18n into cookie.
 *
 */
public class LocaleFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(LocaleFilter.class);

	private String locale;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		LOG.debug("Locale filter starts filtering");

		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse resp = (HttpServletResponse) servletResponse;

		LOG.debug("Cookie 'lang' before init -->" + CookieHandler.readCookieValue(req, "lang").get());

		if (CookieHandler.readCookieValue(req, "lang").isEmpty()) {
			LOG.trace("Cookie 'lang' is empty");
			resp.addCookie(CookieHandler.createCookie("lang", locale, 60 * 60 * 24 * 7, req.getContextPath()));
		}

		if (req.getParameter("lang") != null) {
			String lang = req.getParameter("lang");
			LOG.trace("Request param lang --> " + lang);
			CookieHandler.updateCookie("lang", lang, 60*60*24*7, req.getContextPath(), req, resp);
		}

		LOG.debug("Cookie 'lang' after init -->" + CookieHandler.readCookieValue(req, "lang").get());

		LOG.debug("Locale filter finished filtering");
		filterChain.doFilter(req, servletResponse);
	}

	@Override
	public void init(FilterConfig fConfig) {
		LOG.debug("Filter initialization starts");
		locale = fConfig.getInitParameter("locale");
		LOG.trace("Locale from web.xml --> " + locale);
		LOG.debug("Filter initialization finished");
	}

}
