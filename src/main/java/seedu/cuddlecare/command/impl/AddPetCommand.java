package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.command.Command;
import seedu.cuddlecare.ui.Ui;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A command that adds a pet to the pet list.
 *
 * When executed, this command adds a pet with its
 * name, species, and age added to its attributes.
 */
public class AddPetCommand implements Command {
    
    /** Logger for this class. */
    private static final Logger LOGGER = Logger.getLogger(AddPetCommand.class.getName());

    // @@author HarshitSrivastavaHS
    private static final String SYNTAX = "add-pet n/PET_NAME s/PET_SPECIES a/PET_AGE";
    private static final String SHORT_DESCRIPTION = "Adds a new pet";
    private static final String LONG_DESCRIPTION = "Adds a new pet to the application " +
            "with the specified name, species, and age. " +
            "All fields are required, and the command ensures " +
            "the pet is recorded for future operations. " +
            "Pet name can be max 20characters long, pet species can be max " +
            "30characters long, and age of the pet can be max 200years";;
    private static final List<String> CATEGORIES = List.of("Pet");
    // @@author

    /** A list of all pets. */
    private final PetList pets;
    
    /**
     * Initializes the AddPetCommand with the
     * list of pets to be modified.
     * 
     * @param pets the list of all pets
     */
    public AddPetCommand(PetList pets) {
        assert pets != null : "Pets list cannot be null";
        this.pets = pets;
    }

    /**
     * Executes the Add Pet command.
     *
     * Parses user input, creates a new Pet object, and
     * adds it to the pet list.
     *
     * @param args arguments that contain name, species, and age
     */
    @Override
    public void exec(String args) {
        LOGGER.log(Level.INFO, "Executing add-pet command with args: " + args);
        assert args != null : "Command arguments cannot be null";

        try {
            String[] parts = args.trim().split("(?=\\bn/|\\bs/|\\ba/)");

            String name = null;
            String species = null;
            int age = -1;

            for (String part : parts) {
                if (part.startsWith("n/")) {
                    name = part.substring(2).trim();
                } else if (part.startsWith("s/")) {
                    species = part.substring(2).trim();
                } else if (part.startsWith("a/")) {
                    age = Integer.parseInt(part.substring(2).trim());
                }
            }

            String validation = validate(name, species, age);
            if (validation != null) {
                LOGGER.log(Level.WARNING, "Validation failed: " + validation);
                System.out.println(validation);
                Ui.printInvalidInputMessage(SYNTAX);
                return;
            }

            Pet newPet = new Pet(name, species, age);
            assert newPet != null : "New pet object should not be null.";

            if (!pets.add(newPet)) {
                LOGGER.log(Level.WARNING, "Duplicate pet created: " + name);
                System.out.println("A pet with that name already exists.");
                return;
            }

            LOGGER.log(Level.INFO, "Pet has been added: " + name);
            System.out.println(name + " has been successfully added.");
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid number format for age: " + args, e);
            System.out.println("Age must be a valid number.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error when executing add-pet command", e);
            System.out.println("An error occurred.");
        } finally {
            LOGGER.log(Level.INFO, "Add-pet command execution completed.");
        }
    }

    private String validate(String name, String species, int age) {
        String valid = "^[a-zA-Z\\- ]+";

        if (name == null || name.isEmpty()) {
            return "Pet name cannot be empty.";
        }

        if (name.length() > 20) {
            return "Pet name cannot exceed 20 characters.";
        }

        if (!name.matches(valid)) {
            return "Pet name can only contain letters, hyphen, and spaces.";
        }

        if (species == null || species.isEmpty()) {
            return "Pet species cannot be empty.";
        }

        if (species.length() > 30) {
            return "Pet species cannot exceed 30 characters.";
        }

        if (!species.matches(valid)) {
            return "Pet species can only contain letters, hyphen, and spaces.";
        }

        if (age < 0 || age > 200) {
            return "Pet age must be between 0 and 200.";
        }

        return null;
    }

    // @@author HarshitSrivastavaHS
    @Override
    public String getSyntax() {
        return SYNTAX;
    }

    @Override
    public String getLongDescription() {
        return LONG_DESCRIPTION;
    }

    @Override
    public String getShortDescription() {
        return SHORT_DESCRIPTION;
    }

    @Override
    public List<String> getCategory() {
        return CATEGORIES;
    }
    // @@author
}
