package com.a_smart_cookie.controller.command;

import java.util.HashMap;
import java.util.Map;

public final class CommandContext {

    private static final Map<String, Command> commandMap = new HashMap<>();

    static {
        commandMap.put("catalog", new CatalogCommand());
        commandMap.put("unknown", new UnknownCommand());
    }

    private CommandContext() {}

    public static Command getCommand(String commandName) {
        if (commandName != null && commandMap.containsKey(commandName)) {
            return commandMap.get(commandName);
        }

        return commandMap.get("unknown");
    }

}
