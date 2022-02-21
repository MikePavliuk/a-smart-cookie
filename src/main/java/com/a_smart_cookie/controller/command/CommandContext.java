package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.controller.command.admin.ChangeUserStatusCommand;
import com.a_smart_cookie.controller.command.admin.UsersCommand;
import com.a_smart_cookie.controller.command.common.LogoutCommand;
import com.a_smart_cookie.controller.command.guest.LoginCommand;
import com.a_smart_cookie.controller.command.guest.RegistrationCommand;
import com.a_smart_cookie.controller.command.guest.SignInPageCommand;
import com.a_smart_cookie.controller.command.guest.SignUpPageCommand;
import com.a_smart_cookie.controller.command.shared.CatalogCommand;
import com.a_smart_cookie.controller.command.subscriber.PaymentCommand;
import com.a_smart_cookie.controller.command.subscriber.SubscribeCommand;
import com.a_smart_cookie.controller.command.subscriber.SubscriptionsCommand;
import com.a_smart_cookie.controller.command.subscriber.UnsubscribeCommand;
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
		commandMap.put("unsubscribe", new UnsubscribeCommand());
		commandMap.put("subscriptions", new SubscriptionsCommand());
		
		commandMap.put("users", new UsersCommand());
		commandMap.put("change_user_status", new ChangeUserStatusCommand());

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
