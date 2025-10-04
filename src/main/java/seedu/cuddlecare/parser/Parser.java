package seedu.cuddlecare.parser;

import seedu.cuddlecare.command.Command;
import seedu.cuddlecare.command.CommandWithArguments;

import java.util.Map;

/**
 * Parses user input strings into {@link Command} objects.
 *
 * The parser maps command names to command objects and can
 * handle commands with optional arguments.
 */
public class Parser {

    /** Map of available commands keyed by their string representation. */
    private Map<String, Command> commands;

    /**
     * Sets the available commands for this parser.
     *
     * @param commands Map of command names to their corresponding {@link Command} objects
     */
    public void setCommands(Map<String, Command> commands) {
        this.commands = commands;
    }

    /**
     * Parses a user input string into a {@link Command} object.
     *
     * If the input is empty or the command name is invalid,
     * prints an error message and returns {@code null}.
     *
     * @param input The user input string
     * @return A {@link CommandWithArguments} wrapping the corresponding {@link Command},
     *         or {@code null} if the input is empty or invalid
     */
    public Command parse(String input) {
        input = input.trim();

        if (input.isEmpty()) {
            System.out.println("Empty Command");
            return null;
        }

        String[] commandParts = input.split(" ", 2);
        String commandName = commandParts[0];
        String commandArguments = commandParts.length > 1 ? commandParts[1] : "";

        Command command = commands.get(commandName.toLowerCase());

        if (command == null) {
            System.out.println("Invalid Command: " + commandName);
            return null;
        }

        return new CommandWithArguments(command, commandArguments);
    }
}
