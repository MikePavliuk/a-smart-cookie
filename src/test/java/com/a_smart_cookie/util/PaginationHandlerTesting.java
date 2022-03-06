package com.a_smart_cookie.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaginationHandlerTesting {

	@Mock
	private HttpServletRequest request;

	@Test
	void givenNullRequest_whenSetPaginationAttributes_thenThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class,
				() -> PaginationHandler.setPaginationAttributes(null, 1, 2, 3));
	}

	@Test
	void givenValidInput_whenSetPaginationAttributes_thenSetAttributesToRequest() {
		doNothing().when(request).setAttribute(anyString(), any());

		PaginationHandler.setPaginationAttributes(request, 1, 2, 3);

		verify(request, times(1)).setAttribute("numberOfPages", 1);
		verify(request, times(1)).setAttribute("currentPage", 2);
		verify(request, times(1)).setAttribute("itemsPerPage", 3);
	}

	@Test
	void givenNegativeItemsPerPage_whenGetRequestedNumberOfPages_thenThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class,
				() -> PaginationHandler.getRequestedNumberOfPages(-10, 1));
	}

	@Test
	void givenZeroItemsPerPage_whenGetRequestedNumberOfPages_thenThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class,
				() -> PaginationHandler.getRequestedNumberOfPages(0, 1));
	}

	@Test
	void givenNegativeTotalNumberOfItems_whenGetRequestedNumberOfPages_thenThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class,
				() -> PaginationHandler.getRequestedNumberOfPages(1, -10));
	}

	@ParameterizedTest
	@MethodSource("provideArgumentsForGetRequestedNumberOfPages")
	void givenValidInput_whenGetRequestedNumberOfPages_thenReturnExpectedValue(int itemsPerPage, int totalNumberOfItems, int expectedNumberOfPages) {
		assertEquals(expectedNumberOfPages, PaginationHandler.getRequestedNumberOfPages(itemsPerPage, totalNumberOfItems));
	}

	private static Stream<Arguments> provideArgumentsForGetRequestedNumberOfPages() {
		return Stream.of(
				Arguments.of(1, 10, 10),
				Arguments.of(10, 10, 1),
				Arguments.of(5, 1, 1)
		);
	}

	@Test
	void givenNullRequest_whenGetRequestedPageNumber_thenThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class,
				() -> PaginationHandler.getRequestedPageNumber(null, 1));
	}

	@ParameterizedTest
	@MethodSource("provideArgumentsForGetRequestedPageNumber")
	void givenPossibleValues_whenGetRequestedPageNumber_thenReturnExpectedValue(String pageParam, int numberOfPages, int expectedValue) {
		when(request.getParameter("page")).thenReturn(pageParam);

		assertEquals(expectedValue, PaginationHandler.getRequestedPageNumber(request, numberOfPages));

		verify(request, times(1)).getParameter("page");
	}

	private static Stream<Arguments> provideArgumentsForGetRequestedPageNumber() {
		return Stream.of(
				Arguments.of(null, 1, 1),
				Arguments.of("-1", 1, 1),
				Arguments.of("10", 2, 1),
				Arguments.of("5", 6, 5)
		);
	}

}
