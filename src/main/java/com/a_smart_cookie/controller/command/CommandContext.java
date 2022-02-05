package com.a_smart_cookie.controller.command;

import java.util.HashMap;
import java.util.Map;

public final class CommandContext {

    private static final Map<String, Command> commandMap = new HashMap<>();

    static {
        commandMap.put("catalog", new CatalogCommand());
    }

    private CommandContext() {}

    public static Command getCommand(String nameOfCommand) {
        if (nameOfCommand != null && !commandMap.containsKey(nameOfCommand)) {
            return new UnknownCommand();
        }

        return commandMap.get(nameOfCommand);
    }

}
