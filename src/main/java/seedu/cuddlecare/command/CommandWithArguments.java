package seedu.cuddlecare.command;

/**
 * Wraps a {@link Command} along with its arguments.
 *
 * This class allows the parser to separate command execution
 * from parsing input, storing the arguments for execution later.
 */
public class CommandWithArguments implements Command{

    /** The underlying command to execute. */
    private final Command command;

    /** The arguments associated with the command. */
    private final String args;

    /**
     * Constructs a new CommandWithArguments.
     *
     * @param command the underlying {@link Command} to execute
     * @param args    the arguments to pass to the command when executed
     */
    public CommandWithArguments(Command command, String args) {
        this.command = command;
        this.args = args;
    }

    /**
     * Executes the underlying command using the stored arguments.
     * 
     * The {@code exec} parameter is ignored because arguments
     * are already stored in this object.
     *
     * @param ignored ignored input (not used)
     */
    @Override
    public void exec(String ignored) {
        command.exec(args);
    }
}
