package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// @@author HarshitSrivastavaHS
/**
 * Command that lists all overdue treatments for pets.
 * <p>
 * This command can display overdue treatments for either a specific pet
 * (when the pet name is provided) or all pets if no name is given.
 * Overdue treatments are determined based on the current date and whether
 * the treatment has been completed.
 * </p>
 */
public class OverdueTreatmentsCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(OverdueTreatmentsCommand.class.getName());

    private static final String SYNTAX = "overdue-treatments [n/PET_NAME]";

    private final PetList pets;

    private LocalDate testDate;

    /**
     * Constructs an OverdueTreatmentsCommand with the given pet list.
     *
     * @param pets the PetList containing all pets in the system
     * @throws AssertionError if pets is null
     */
    public OverdueTreatmentsCommand(PetList pets) {
        assert pets != null : "Pets cannot be null";
        this.pets = pets;
    }

    /**
     * Constructs an OverdueTreatmentsCommand with a given pet list and custom date.
     * <p>
     * This constructor is primarily used for testing purposes to allow specifying
     * a fixed current date instead of using {@link LocalDate#now()}.
     * </p>
     *
     * @param pets     the PetList containing all pets in the system
     * @param testDate the LocalDate to be used as the current date (for testing)
     * @throws AssertionError if pets or testDate is null
     */
    protected OverdueTreatmentsCommand(PetList pets, LocalDate testDate) {
        assert pets != null : "Pets cannot be null";
        assert testDate != null : "Date cannot be null";
        this.pets = pets;
        this.testDate = testDate;
    }


    /**
     * Executes the overdue treatments command.
     * <p>
     * Prints all overdue treatments for pets. If a pet name is provided in the format
     * "n/PET_NAME", only that pet's overdue treatments are shown; otherwise, all pets are included.
     * A treatment is considered overdue if it is not completed and its date is before today.
     *
     * @param args the command arguments
     */
    public void exec(String args) {
        assert args != null : "args cannot be null";

        LOGGER.log(Level.INFO, "Executing Overdue treatment command with args: " + args);

        if (pets.size() == 0) {
            System.out.println("No pets added");
            LOGGER.log(Level.INFO, "No pets added yet.");
            return;
        }

        Pet pet = getPetByName(args);

        if (!args.isEmpty() && pet == null) {
            return;
        }

        Stream<Pet> targetPet = getPetStream(pet);

        LocalDate presentDate = testDate == null ? LocalDate.now() : testDate;
        Map<Pet, ArrayList<Treatment>> overdueTreatments = getOverdueTreatments(targetPet, presentDate);

        printOverdueTreatments(overdueTreatments, pet, presentDate);
    }

    private Map<Pet, ArrayList<Treatment>> getOverdueTreatments(Stream<Pet> targetPet, LocalDate presentDate) {
        assert targetPet != null : "Target pet cannot be null. It's either a stream of all pets or just the input pet.";
        assert presentDate != null : "Present date cannot be null";
        return targetPet
                .filter(p -> p.getTreatments().stream()
                        .anyMatch(t -> !t.isCompleted() && t.getDate().isBefore(presentDate)))
                .collect(Collectors.toMap(
                        p -> p,
                        p -> p.getTreatments().stream()
                                .filter(t -> !t.isCompleted() && t.getDate().isBefore(presentDate))
                                .collect(Collectors.toCollection(ArrayList::new))
                ));
    }

    private Stream<Pet> getPetStream(Pet pet) {
        return pet == null ? pets.stream() : Stream.of(pet);
    }

    private Pet getPetByName(String args) {
        if (args.isEmpty()) {
            return null;
        }

        String[] tags = args.split(" (?=[\\w+]/)");
        String petName = null;
        for (String tag : tags) {
            if (tag.startsWith("n/")) {
                petName = tag.substring(2).trim();
                break;
            }
        }
        Pet pet = pets.getPetByName(petName);

        if (petName == null) {
            LOGGER.log(Level.INFO, "Invalid args provided");
            System.out.printf("Invalid arguments provided.%nSyntax: %s", SYNTAX);
        } else if (pet == null) {
            LOGGER.log(Level.INFO, "No pet found");
            System.out.printf("No pet found with the name: %s%n", petName);
        }

        return pet;
    }

    private void printOverdueTreatments(Map<Pet, ArrayList<Treatment>> treatments,
                                        Pet inputPet, LocalDate presentDate) {
        assert presentDate != null : "Present date cannot be null";
        assert treatments != null : "Treatments cannot be null";
        if (treatments.isEmpty()) {
            LOGGER.log(Level.INFO, "No overdue Treatment");
            System.out.printf("No overdue treatment%s. Way to go!%n",
                    (inputPet == null ? "" : " for " + inputPet.getName()));
            return;
        }
        System.out.printf("Overdue Treatments%s:%n", (inputPet == null ? "" : " for " + inputPet.getName()));
        for (Map.Entry<Pet, ArrayList<Treatment>> entry : treatments.entrySet()) {

            for (Treatment treatment : entry.getValue()) {

                long overdueSince = ChronoUnit.DAYS.between(treatment.getDate(), presentDate);

                if (inputPet == null) {
                    System.out.printf("%s: \"%s\" was due on %s (overdue for %d days)%n",
                            entry.getKey().getName(), treatment.getName(), treatment.getDate(), overdueSince);
                } else {
                    System.out.printf("\"%s\" was due on %s (overdue for %d days)%n",
                            treatment.getName(), treatment.getDate(), overdueSince);
                }
            }
        }
        LOGGER.log(Level.INFO, "Successfully executed the command");
    }
}
