package com.a_smart_cookie.util.tag;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class RedirectIfInSessionTag extends SimpleTagSupport {

	private static final long serialVersionUID = 8915588365838201611L;

	private String sessionAttribute;
	private String redirectPath;

	public String getSessionAttribute() {
		return sessionAttribute;
	}

	public void setSessionAttribute(String sessionAttribute) {
		this.sessionAttribute = sessionAttribute;
	}

	public String getRedirectPath() {
		return redirectPath;
	}

	public void setRedirectPath(String redirectPath) {
		this.redirectPath = redirectPath;
	}

	@Override
	public void doTag() throws IOException {
		PageContext pageContext = (PageContext) getJspContext();
		HttpSession session = pageContext.getSession();

		if (session != null && session.getAttribute(sessionAttribute) != null) {
			((HttpServletResponse)pageContext.getResponse()).sendRedirect(redirectPath);
		}

	}
}
