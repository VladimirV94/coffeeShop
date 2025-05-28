package app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Command {

	MENU_COMMAND("menu"), ORDER_COMMAND("order"), EXIT("exit"), PAY_ORDER("pay"),
	MENU_ITEM("^add\\s+(.+?)\\s+count\\s+(\\d+)$"), PROMOCODE("^promocode\\s+(\\w+)$");

	private String name;

	Command(String name) {
		this.name = name;
	}

	public static Command fromString(String input) {
		// Ищем по названию
		for (Command command : Command.values()) {
			if (command.getName().equalsIgnoreCase(input.trim()))
				return command;
		}
		// Если не нашли по названию, то ищем по паттерну через регулярные выражения
		Command command = getFromPattern(input);
		if (command != null)
			return command;
		throw new IllegalArgumentException("Unknown command: " + input);
	}

	static Command getFromPattern(String input) {
		if (isMatch(MENU_ITEM.getName(), input)) {
			return MENU_ITEM;
		}
		if (isMatch(PROMOCODE.getName(), input)) {
			return PROMOCODE;
		}
		throw new IllegalArgumentException("Unknown command: " + input);
	}

	String getName() {
		return name;
	}

	static String getMenuItemNameFromString(String input) {
		Matcher matcher = getMatcher(MENU_ITEM.getName(), input);
		if (matcher.matches())
			return matcher.group(1);
		throw new IllegalArgumentException("Unknown command: " + input);
	}

	static int getMenuItemCountFromString(String input) {
		Matcher matcher = getMatcher(MENU_ITEM.getName(), input);
		if (matcher.matches())
			return Integer.parseInt(matcher.group(2));
		throw new IllegalArgumentException("Unknown command: " + input);
	}

	static String getPromocodeFromString(String input) {
		Matcher matcher = getMatcher(PROMOCODE.getName(), input);
		if (matcher.matches())
			return matcher.group(1);
		throw new IllegalArgumentException("Unknown command: " + input);
	}

	static boolean isMatch(String name, String input) {
		return getMatcher(name, input).matches();
	}

	static Matcher getMatcher(String name, String input) {
		return Pattern.compile(name, Pattern.CASE_INSENSITIVE).matcher(input);
	}
}
