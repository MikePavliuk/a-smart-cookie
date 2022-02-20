package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.controller.command.shared.CatalogCommand;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandContextTesting {

    @ParameterizedTest
    @MethodSource("provideExistedCommandNames")
    void givenExistedCommandName_whenGetCommand_thenReturnThatCommand(String commandName, Class<?> expectedClass) {
        Class<?> actual = CommandContext.getCommand(commandName).getClass();

        assertEquals(expectedClass, actual);
    }

    private static Stream<Arguments> provideExistedCommandNames() {
        return Stream.of(
                Arguments.of("catalog", CatalogCommand.class)
        );
    }

}
