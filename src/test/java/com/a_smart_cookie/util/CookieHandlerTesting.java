package com.a_smart_cookie.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CookieHandlerTesting {

	@Mock
	private HttpServletRequest request;
	private final Cookie cookie = new Cookie("key", "value");

	@Test
	void givenNullRequest_whenReadCookie_thenThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class,
				() -> CookieHandler.readCookie(null, "some key"));
	}

	@Test
	void givenNotExistedCookieKey_whenReadCookie_thenReturnEmptyOptional() {
		when(request.getCookies()).thenReturn(new Cookie[]{cookie});

		assertEquals(Optional.empty(), CookieHandler.readCookie(request, "key2"));

		verify(request, times(1)).getCookies();
	}

	@Test
	void givenExistedCookieKey_whenReadCookie_thenReturnThatCookie() {
		Cookie expected = cookie;
		when(request.getCookies()).thenReturn(new Cookie[]{expected});

		assertEquals(expected, CookieHandler.readCookie(request, "key").orElse(null));

		verify(request, times(1)).getCookies();
	}

	@Test
	void givenNotExistedCookieKey_whenReadCookieValue_thenReturnEmptyOptional() {
		when(request.getCookies()).thenReturn(new Cookie[]{cookie});

		assertEquals(Optional.empty(), CookieHandler.readCookieValue(request, "key2"));

		verify(request, times(1)).getCookies();
	}

	@Test
	void givenExistedCookieKey_whenReadCookieValue_thenReturnValueOfCookie() {
		when(request.getCookies()).thenReturn(new Cookie[]{cookie});

		assertEquals("value", CookieHandler.readCookieValue(request, "key").orElse(null));

		verify(request, times(1)).getCookies();
	}

	@Test
	void givenCookieValues_whenCreateCookie_thenReturnCreatedCookie() {
		String key = "key";
		String value = "value";
		int maxAge = 10;
		String path = "/*";

		Cookie cookie = CookieHandler.createCookie(key, value, maxAge, path);

		assertTrue(
				Objects.equals(cookie.getName(), key) &&
						Objects.equals(cookie.getValue(), value) &&
						cookie.getMaxAge() == maxAge &&
						Objects.equals(cookie.getPath(), path)
		);
	}

	@Test
	void givenNotExistedCookieKey_whenUpdateCookie_thenNotUpdateCookieAndReturnFalse() {
		when(request.getCookies()).thenReturn(new Cookie[]{});

		HttpServletResponse response = mock(HttpServletResponse.class);

		assertFalse(CookieHandler.updateCookie("key", "value", 10, "/*", request, response));

		verify(request, times(1)).getCookies();
		verify(response, times(0)).addCookie(any());
	}

	@Test
	void givenExistedCookieKey_whenUpdateCookie_thenUpdateCookieAndReturnTrue() {
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getCookies()).thenReturn(new Cookie[]{cookie});
		doNothing().when(response).addCookie(any(Cookie.class));

		assertTrue(CookieHandler.updateCookie("key", "value", 10, "/*", request, response));

		verify(request, times(1)).getCookies();
		verify(response, times(1)).addCookie(any());
	}

}
