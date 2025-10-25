
package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * A command that adds a treatment record for a specified pet.
 * <p>
 * When executed, this command adds a treatment to specified pet with
 * name of treatment, date of treatment and type of treatment.
 */
public class AddTreatmentCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(AddTreatmentCommand.class.getName());
    private static final String USAGE = "Invalid input. Usage: add-treatment n/PET_NAME t/TREATMENT_NAME d/DATE " +
            "note/{NOTE}";

    /**
     * A list of all pets.
     */
    private final PetList pets;

    /**
     * Initializes the AddTreatmentCommand with the list of pets.
     *
     * @param pets the list of all pets
     */
    public AddTreatmentCommand(PetList pets) {
        assert pets != null : "PetList cannot be null";
        this.pets = pets;
    }

    /**
     * Executes the Add Treatment command.
     * Parses user input, finds the corresponding pet by index,
     * creates a new Treatment object, and adds it to the pet.
     *
     * @param args arguments that contain name of pet, treatment name, date, and note (if any).
     */
    @Override
    public void exec(String args) {
        assert args != null : "Command arguments cannot be null";
        LOGGER.log(Level.INFO, "Executing add-treatment: {0}", args);

        try {

            String petName = null;
            String treatmentName = null;
            String note = null;
            LocalDate date = null;

            // Parse note first
            int noteIndex = args.indexOf("note/");
            if (noteIndex != -1) {
                note = args.substring(noteIndex + 5).trim();
                args = args.substring(0, noteIndex).trim();
            }

            String[] parts = args.split("(?=n/|t/|d/)");

            for (String part : parts) {
                if (part.startsWith("n/")) {
                    petName = extractValue(part, "n/", "Pet name");
                } else if (part.startsWith("t/")) {
                    treatmentName = extractValue(part, "t/", "Treatment name");
                } else if (part.startsWith("d/")) {
                    String dateString = extractValue(part, "d/", "Date");
                    date = LocalDate.parse(dateString);
                }
            }
            validateRequiredParameters(petName, treatmentName, date);
            Pet pet = findPet(petName);
            addTreatmentToPet(pet, treatmentName, note, date);
            printSuccessMessage(petName, treatmentName, date, note);

        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid arguments: {0}", e.getMessage());
            System.out.println(e.getMessage());
        } catch (DateTimeParseException e) {
            LOGGER.log(Level.WARNING, "Invalid date format", e);
            System.out.println("Invalid date format. Please use yyyy-MM-dd format (e.g., 2024-12-25).");
        }
    }

    /**
     * Extracts and validates a parameter value from a command part.
     *
     * @param part the command part containing the parameter
     * @param prefix the parameter prefix (e.g., "n/", "t/")
     * @param fieldName the name of the field for error messages
     * @return the extracted value
     * @throws IllegalArgumentException if the value is empty
     */
    private String extractValue(String part, String prefix, String fieldName) {
        String value = part.substring(prefix.length()).trim();

        if (value.isEmpty()) {
            throw new IllegalArgumentException("Error: " + fieldName + " cannot be empty.");
        }

        return value;
    }

    /**
     * Validates that all required parameters are present.
     *
     * @param petName the pet name
     * @param treatmentName the treatment name
     * @param date the treatment date
     * @throws IllegalArgumentException if any required parameter is missing
     */
    private void validateRequiredParameters(String petName, String treatmentName, LocalDate date) {
        if (petName == null || treatmentName == null || date == null) {
            LOGGER.log(Level.INFO, "Missing required parameters - petName: {0}, treatmentName: {1}, date: {2}",
                    new Object[]{petName, treatmentName, date});
            throw new IllegalArgumentException(USAGE);
        }
    }

    /**
     * Finds a pet by name.
     *
     * @param petName the name of the pet to find
     * @return the Pet object
     * @throws IllegalArgumentException if the pet is not found
     */
    private Pet findPet(String petName) {
        Pet pet = pets.getPetByName(petName);

        if (pet == null) {
            throw new IllegalArgumentException("Pet not found: " + petName);
        }

        return pet;
    }

    /**
     * Adds a treatment to the specified pet.
     *
     * @param pet the pet to add the treatment to
     * @param treatmentName the treatment name
     * @param date the treatment date
     */
    private void addTreatmentToPet(Pet pet, String treatmentName, String note, LocalDate date) {
        Treatment newTreatment = new Treatment(treatmentName, note, date);
        pet.addTreatment(newTreatment);
        LOGGER.log(Level.INFO, "Added treatment '{0}' for {1} on {2}",
                new Object[]{treatmentName, pet.getName(), date});
    }

    /**
     * Prints the success message after adding a treatment.
     * @param petName the pet name
     * @param treatmentName the treatment name
     * @param date the treatment date
     * @param note note attached to treatment
     */
    private void printSuccessMessage(String petName, String treatmentName, LocalDate date, String note) {
        boolean hasNote = note != null && !note.isEmpty();
        String noteFormat = hasNote ? ("\n  Note: " + note) : "";
        System.out.println("Added treatment \"" + treatmentName + "\" on " + date +
                " for " + petName + "." + noteFormat);
    }
}
