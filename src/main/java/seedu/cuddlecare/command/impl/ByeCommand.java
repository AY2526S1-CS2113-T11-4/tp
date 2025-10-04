package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.command.Command;

/**
 * A command that exits the application.
 *
 * When executed, this command prints a farewell message and
 * terminates the program.
 */
public class ByeCommand implements Command {

    /**
     * Executes the Bye command.
     *
     * Prints a farewell message to the console and exits the application.
     *
     * @param args ignored; ByeCommand does not use any arguments
     */
    public void exec(String args) {
        System.out.println("Bye bye, Have a wonderful day ahead :)");
        System.exit(0);
    }
}
