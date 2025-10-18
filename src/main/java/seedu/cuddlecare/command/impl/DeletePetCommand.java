package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.command.Command;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command to delete a pet from the {@link PetList} based on its name.
 * <p>
 * Usage: <code>delete-pet n/&lt;pet name&gt;</code>
 * <ul>
 *     <li>n/&lt;pet name&gt; : the name of the pet to delete from the list</li>
 * </ul>
 * <p>
 * Example: <code>delete-pet n/Fluffy</code> will remove the pet named "Fluffy" from the list.
 */
public class DeletePetCommand implements Command {

    /**
     * Logger instance for the {@code DeletePetCommand} class.
     */
    private static final Logger LOGGER = Logger.getLogger(DeletePetCommand.class.getName());

    /**
     * Syntax help message displayed on incorrect usage.
     */
    private static final String SYNTAX = "delete-pet n/<pet name>";

    /**
     * The list of pets to operate on.
     */
    private final PetList pets;

    /**
     * Constructs a new DeletePetCommand for the specified pet list.
     *
     * @param pets the PetList from which a pet will be deleted
     */
    public DeletePetCommand(PetList pets) {
        assert pets != null : "PetList cannot be null";
        this.pets = pets;
    }

    /**
     * Executes the delete-pet command.
     * <p>
     * Steps performed by this method:
     * <ol>
     *     <li>Parses the pet name from the input arguments using {@link #getPetName(String)}.</li>
     *     <li>Checks whether a pet with the given name exists in the {@link PetList}.</li>
     *     <li>
     *         If the pet exists, deletes it using {@link PetList#deletePet(Pet)} and
     *         prints a confirmation message.
     *     </li>
     *     <li>If the pet does not exist or the syntax is invalid, prints an error message.</li>
     * </ol>
     *
     * @param args the command arguments in the form <code>n/&lt;pet name&gt;</code>
     */
    public void exec(String args) {
        LOGGER.log(Level.INFO, "Executing DeletePetCommand with args: " + args);

        String petName = getPetName(args);

        if (petName == null || petName.isEmpty()) {
            System.out.printf("Invalid Syntax: %s%n", SYNTAX);
            LOGGER.log(Level.WARNING, "Invalid Command Syntax");
            return;
        }

        Pet pet = pets.getPetByName(petName);

        if (pet == null) {
            System.out.printf("No Pet named \"%s\" exists%n", petName);
            LOGGER.log(Level.WARNING, "Invalid Pet Name: "+petName);
            return;
        }

        boolean isDeleted = pets.deletePet(pet);

        if (!isDeleted) {
            System.out.printf("Something went wrong. Could not delete the pet");
            LOGGER.log(Level.WARNING, "Could not delete a pet that exists");
            return;
        }

        System.out.printf("Successfully removed %s (%s, %d) from the list.%n",
                pet.getName(), pet.getSpecies(), pet.getAge());

        LOGGER.log(Level.INFO, String.format("Deleted pet: %s (%s, %d)",
                pet.getName(), pet.getSpecies(), pet.getAge()));
    }

    /**
     * Parses the pet name from the command input string.
     *
     * @param input the raw command input, expected to contain "n/&lt;pet name&gt;"
     * @return the extracted pet name, or {@code null} if no valid name is found
     */
    private String getPetName(String input) {
        String[] parts = input.split(" (?=[w+]/)");

        for (String part: parts) {
            if (part.startsWith("n/")) {
                return part.substring(2).trim();
            }
        }

        return null;
    }
}
