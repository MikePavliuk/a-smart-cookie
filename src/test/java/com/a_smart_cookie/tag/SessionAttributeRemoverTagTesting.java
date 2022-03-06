package com.a_smart_cookie.tag;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionAttributeRemoverTagTesting {

	@Mock
	private HttpSession session;

	@Test
	void givenSessionAndAttribute_whenRemove_thenRemoveAttribute() {
		doNothing().when(session).removeAttribute(anyString());

		SessionAttributeRemoverTag.remove(session, anyString());

		verify(session, times(1)).removeAttribute(anyString());

	}

}
