package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * A command that filters treatments by date range across all pets.
 *
 * When executed, this command searches through all treatments of all pets
 * and returns those that fall within the specified date range (inclusive).
 */
public class FilterTreatmentByDateCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(FilterTreatmentByDateCommand.class.getName());

    /** A list of all pets. */
    private final PetList pets;

    /**
     * Initializes the FilterTreatmentByDateCommand with the list of pets.
     *
     * @param pets the list of all pets
     */
    public FilterTreatmentByDateCommand(PetList pets) {
        assert pets != null : "PetList cannot be null";
        this.pets = pets;
    }

    /**
     * Executes the filter treatment by date command.
     *
     * Filters treatments across all pets and displays those that fall
     * within the specified date range (inclusive of start and end dates).
     *
     * @param args the date range in format: from/DATE to/DATE
     */
    @Override
    public void exec(String args) {
        assert args != null : "Command arguments cannot be null";
        LOGGER.log(Level.INFO, "Starting filter treatment by date command execution");

        try {
            LocalDate fromDate = null;
            LocalDate toDate = null;

            String[] parts = args.split("(?=from/|to/)");
            for (String part : parts) {
                if (part.startsWith("from/")) {
                    String dateString = part.substring(5).trim();
                    if (dateString.isEmpty()) {
                        System.out.println("Error: Start date cannot be empty.");
                        return;
                    }
                    try {
                        fromDate = LocalDate.parse(dateString);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid start date format. Please use yyyy-MM-dd format.");
                        return;
                    }
                } else if (part.startsWith("to/")) {
                    String dateString = part.substring(3).trim();
                    if (dateString.isEmpty()) {
                        System.out.println("Error: End date cannot be empty.");
                        return;
                    }
                    try {
                        toDate = LocalDate.parse(dateString);
                    } catch (DateTimeParseException e) {
                        LOGGER.log(Level.WARNING, "Invalid to date format: {0}", dateString);
                        System.out.println("Invalid end date format. Please use yyyy-MM-dd format.");
                        return;
                    }
                }
            }

            if (fromDate == null || toDate == null) {
                LOGGER.log(Level.INFO, "Missing date range parameters - from: {0}, to: {1}",
                        new Object[]{fromDate, toDate});
                System.out.println("Invalid input. Usage: treatment-date from/DATE to/DATE");
                return;
            }

            if (fromDate.isAfter(toDate)) {
                LOGGER.log(Level.INFO, "Invalid date range - from {0} is after to {1}",
                        new Object[]{fromDate, toDate});
                System.out.println("Error: Start date cannot be after end date.");
                return;
            }

            ArrayList<String> filteredTreatments = new ArrayList<>();

            for (int i = 0; i < pets.size(); i++) {
                Pet pet = pets.get(i);
                for (Treatment treatment : pet.getTreatments()) {
                    LocalDate treatmentDate = treatment.getDate();
                    if (!treatmentDate.isBefore(fromDate) && !treatmentDate.isAfter(toDate)) {
                        filteredTreatments.add(pet.getName() + ": " + treatment);
                    }
                }
            }

            if (filteredTreatments.isEmpty()) {
                LOGGER.log(Level.INFO, "No treatments found in date range {0} to {1}",
                        new Object[]{fromDate, toDate});
                System.out.println("No treatments found from " + fromDate + " to " + toDate + ".");
                return;
            }

            System.out.println("Found " + filteredTreatments.size() +
                    " treatment(s) from " + fromDate + " to " + toDate + ":");
            filteredTreatments.forEach(System.out::println);

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Unexpected error during filter treatment by date execution", e);
            System.out.println("Unable to filter treatments by date. Please try again.");
        }
    }
}
