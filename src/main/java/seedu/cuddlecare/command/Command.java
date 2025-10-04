package seedu.cuddlecare.command;

/**
 * Represents a command that can be executed with optional arguments.
 *
 * All commands in the application implements this interface
 * to define their execution behavior.
 */
public interface Command {

    /**
     * Executes the command with the given arguments.
     *
     * @param args the arguments passed to the command, may be empty
     */
    void exec(String args);
}
