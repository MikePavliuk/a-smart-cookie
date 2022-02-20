package com.a_smart_cookie.controller.command;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Holder for all implemented commands.
 *
 */
public final class CommandContext {

	private static final Logger LOG = Logger.getLogger(CommandContext.class);

	private static final Map<String, Command> commandMap = new HashMap<>();

	static {
		commandMap.put("sign-in", new SignInPageCommand());
		commandMap.put("sign-up", new SignUpPageCommand());

		commandMap.put("registration", new RegistrationCommand());
		commandMap.put("login", new LoginCommand());
		commandMap.put("logout", new LogoutCommand());

		commandMap.put("catalog", new CatalogCommand());
		commandMap.put("payment", new PaymentCommand());
		commandMap.put("subscribe", new SubscribeCommand());
		commandMap.put("subscriptions", new SubscriptionsCommand());

		LOG.debug("Command context was successfully initialized");
		LOG.trace("Number of commands --> " + commandMap.size());
	}

	private CommandContext() {}

	/**
	 * Returns command object with the given name.
	 *
	 * @param commandName Name of the command.
	 * @return Command object.
	 */
	public static Command getCommand(String commandName) {
		if (commandName != null && commandMap.containsKey(commandName)) {
			return commandMap.get(commandName);
		}

		return null;
	}

}
