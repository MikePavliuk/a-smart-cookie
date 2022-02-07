//package com.a_smart_cookie.service.impl;
//
//import com.a_smart_cookie.dao.EntityTransaction;
//import com.a_smart_cookie.dao.GenreDao;
//import com.a_smart_cookie.entity.Genre;
//import com.a_smart_cookie.entity.Language;
//import com.a_smart_cookie.exception.DaoException;
//import com.a_smart_cookie.exception.ServiceException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.sql.SQLException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class GenreServiceImplTesting {
//
//    private final EntityTransaction transaction = mock(EntityTransaction.class);
//    private final GenreDao genreDao = mock(GenreDao.class);
//    private final GenreServiceImpl genreService = new GenreServiceImpl(genreDao, transaction);
//
//    @Test
//    void givenSomeExceptionInInitiatingTransaction_whenFindAllGenresByLanguage_thenThrowServiceException() throws SQLException, DaoException {
//        doThrow(SQLException.class).when(transaction).init(genreDao);
//
//        assertThrows(ServiceException.class,
//                () -> genreService.findAllGenresByLanguage(Language.UKRAINIAN));
//
//        verify(transaction, times(1)).init(genreDao);
//        verify(genreDao, times(0)).findAllByLanguage(Language.UKRAINIAN);
//    }
//
//    @Test
//    void givenSomeExceptionInGenreDaoMethod_whenFindAllGenresByLanguage_thenThrowServiceException() throws SQLException, DaoException {
//        doThrow(DaoException.class).when(genreDao).findAllByLanguage(any());
//
//        assertThrows(ServiceException.class,
//                () -> genreService.findAllGenresByLanguage(Language.UKRAINIAN));
//
//        verify(transaction, times(1)).init(genreDao);
//        verify(genreDao, times(1)).findAllByLanguage(Language.UKRAINIAN);
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideLanguageAndResultList")
//    void givenLanguage_whenFindAllGenresByLanguage_thenReturnListOfGenres(Language language, List<Genre> expectedGenreList) throws ServiceException, DaoException, SQLException {
//        doNothing().when(transaction).init(genreDao);
//        when(genreDao.findAllByLanguage(language)).thenReturn(expectedGenreList);
//        List<Genre> actualGenreList = genreService.findAllGenresByLanguage(language);
//
//        assertEquals(expectedGenreList, actualGenreList);
//
//        verify(transaction, times(1)).init(genreDao);
//        verify(genreDao, times(1)).findAllByLanguage(language);
//    }
//
//    private static Stream<Arguments> provideLanguageAndResultList() {
//        return Stream.of(
//                Arguments.of(Language.UKRAINIAN, Arrays.asList(
//                        new Genre(1, "фантастика"),
//                        new Genre(2, "роман"))),
//                Arguments.of(Language.ENGLISH, Arrays.asList(
//                        new Genre(1, "fantasy"),
//                        new Genre(2, "novel")
//                ))
//        );
//    }
//}
