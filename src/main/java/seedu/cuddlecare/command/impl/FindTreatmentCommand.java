package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * A command that searches for treatments by keyword across all pets.
 *
 * When executed, this command searches through all treatments of all pets
 * and returns those that contain the specified keyword in their name.
 */
public class FindTreatmentCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(FindTreatmentCommand.class.getName());

    /** A list of all pets. */
    private final PetList pets;

    /**
     * Initializes the FindTreatmentCommand with the list of pets.
     *
     * @param pets the list of all pets
     */
    public FindTreatmentCommand(PetList pets) {
        assert pets != null : "PetList cannot be null";
        this.pets = pets;
    }

    /**
     * Executes the find treatment command.
     *
     * Searches through all treatments of all pets and displays those
     * that contain the keyword in their treatment name. The search is
     * case-insensitive
     *
     * @param args the keyword to search for in treatment names
     */
    @Override
    public void exec(String args) {
        assert args != null : "Command arguments cannot be null";
        LOGGER.log(Level.INFO, "Starting find treatment command execution");

        try {
            String keyword = args.trim();

            if (keyword.isEmpty()) {
                LOGGER.log(Level.WARNING, "Empty keyword provided");
                System.out.println("Error: Please provide a keyword to search for.");
                return;
            }

            ArrayList<String> matchingTreatments = new ArrayList<>();

            for (int i = 0; i < pets.size(); i++) {
                Pet pet = pets.get(i);
                for (Treatment treatment : pet.getTreatments()) {
                    if (treatment.getName().toLowerCase().contains(keyword.toLowerCase())) {
                        matchingTreatments.add(pet.getName() + ": " + treatment);
                    }
                }
            }

            if (matchingTreatments.isEmpty()) {
                LOGGER.log(Level.INFO, "No treatments found containing keyword: {0}", keyword);
                System.out.println("No treatments found containing: \"" + keyword + "\"");
                return;
            }

            System.out.println("Found " + matchingTreatments.size() +
                    " treatment(s) containing: \"" + keyword + "\"");
            matchingTreatments.forEach(System.out::println);

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Unexpected error during find treatment execution", e);
            System.out.println("Unable to search for treatments. Please try again.");
        }
    }
}
