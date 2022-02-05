package com.a_smart_cookie.dao;

public final class ResourceReleaser {

    private ResourceReleaser() {}

    public static void close(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                // log
            }
        }
    }

}
