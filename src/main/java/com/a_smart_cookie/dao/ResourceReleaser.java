package com.a_smart_cookie.dao;

import org.apache.log4j.Logger;

/**
 * Utility class for releasing resources
 *
 */
public final class ResourceReleaser {

	private static final Logger LOG = Logger.getLogger(ResourceReleaser.class);

	private ResourceReleaser() {}

	/**
	 * Method for closing AutoCloseable objects.
	 *
	 * @param autoCloseable Any object, which class implements AutoCloseable
	 */
	public static void close(AutoCloseable autoCloseable) {
		if (autoCloseable != null) {
			try {
				LOG.debug("Resource starts to be closed");
				autoCloseable.close();
				LOG.debug("Resource finished closing");
			} catch (Exception e) {
				LOG.error("Resource can't be closed --> " + e);
			}
		}
	}

}
