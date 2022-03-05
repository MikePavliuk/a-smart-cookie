package com.a_smart_cookie.tag;

import javax.servlet.http.HttpSession;

/**
 * Custom jstl tag for removing attribute from session
 *
 */
public class SessionAttributeRemoverTag {

	/**
	 * Removes attribute from session.
	 *
	 * @param session Session to get rid of attribute.
	 * @param attribute String attribute name to be removed.
	 */
	public static void remove(HttpSession session, String attribute) {
		if (session != null && attribute != null) {
			session.removeAttribute(attribute);
		}
	}

}
