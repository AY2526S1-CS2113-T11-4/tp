package seedu.cuddlecare.command.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;

// @@author HarshitSrivastavaHS
class OverdueTreatmentsCommandTest {

    private PetList pets;
    private OverdueTreatmentsCommand overdueTreatments;

    @BeforeAll
    static void muteLogs() {
        LogManager.getLogManager().reset();
        Logger root = Logger.getLogger("");
        root.setLevel(Level.OFF);
    }

    @BeforeEach
    void setUp() {
        pets = new PetList();
        pets.add(new Pet("Chiku", "Dog", 3));
        pets.add(new Pet("Bindi", "Cat", 2));
        overdueTreatments = new OverdueTreatmentsCommand(pets, LocalDate.parse("2025-10-24"));
    }

    @Test
    void exec_validPetWithTwoOverdueTreatments_showsBothOverdue() {
        Treatment treatment1 = new Treatment("Treatment1", null, LocalDate.parse("2025-11-11"));
        Treatment treatment2 = new Treatment("Treatment2", null, LocalDate.parse("2025-05-20"));
        Treatment treatment3 = new Treatment("Treatment3", null, LocalDate.parse("2025-10-09"));

        pets.getPetByName("Chiku").addTreatment(treatment1);
        pets.getPetByName("Chiku").addTreatment(treatment2);
        pets.getPetByName("Chiku").addTreatment(treatment3);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        overdueTreatments.exec("n/Chiku");

        System.setOut(originalOut);
        String output = outContent.toString();
        
        assertTrue(output.contains("\"Treatment2\" was due on 2025-05-20 (overdue for 157 days)") &&
                output.contains("\"Treatment3\" was due on 2025-10-09 (overdue for 15 days)") &&
                !output.contains("Treatment1"));
    }

    @Test
    void exec_validPetWithOneOverdueTreatment_showsSingleOverdue() {
        Treatment treatment1 = new Treatment("Treatment1", null, LocalDate.parse("2025-11-11"));
        Treatment treatment2 = new Treatment("Treatment2", null, LocalDate.parse("2025-05-20"));
        Treatment treatment3 = new Treatment("Treatment3", null, LocalDate.parse("2025-10-09"));
        treatment2.setCompleted(true);

        pets.getPetByName("Chiku").addTreatment(treatment1);
        pets.getPetByName("Chiku").addTreatment(treatment2);
        pets.getPetByName("Chiku").addTreatment(treatment3);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        overdueTreatments.exec("n/Chiku");

        System.setOut(originalOut);
        String output = outContent.toString();
        
        assertTrue(output.contains("\"Treatment3\" was due on 2025-10-09 (overdue for 15 days)") &&
                !output.contains("Treatment1") &&
                !output.contains("Treatment2"));
    }

    @Test
    void exec_validPetNameOnly_showsOverdueForThatPet() {
        Treatment treatment1 = new Treatment("Treatment1", null, LocalDate.parse("2025-11-11"));
        Treatment treatment2 = new Treatment("Treatment2", null, LocalDate.parse("2025-05-20"));
        Treatment treatment3 = new Treatment("Treatment3", null, LocalDate.parse("2025-10-09"));

        pets.getPetByName("Chiku").addTreatment(treatment1);
        pets.getPetByName("Bindi").addTreatment(treatment2);
        pets.getPetByName("Chiku").addTreatment(treatment3);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        overdueTreatments.exec("n/Chiku");

        System.setOut(originalOut);
        String output = outContent.toString();
        
        assertTrue(output.contains("\"Treatment3\" was due on 2025-10-09 (overdue for 15 days)") &&
                !output.contains("Treatment1") &&
                !output.contains("Treatment2"));
    }

    @Test
    void exec_noPetNameProvided_showsOverdueForAllPets() {
        Treatment treatment1 = new Treatment("Treatment1", null, LocalDate.parse("2025-11-11"));
        Treatment treatment2 = new Treatment("Treatment2", null, LocalDate.parse("2025-05-20"));
        Treatment treatment3 = new Treatment("Treatment3", null, LocalDate.parse("2025-10-09"));

        pets.getPetByName("Chiku").addTreatment(treatment1);
        pets.getPetByName("Bindi").addTreatment(treatment2);
        pets.getPetByName("Chiku").addTreatment(treatment3);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        overdueTreatments.exec("");

        System.setOut(originalOut);
        String output = outContent.toString();
        
        assertTrue(output.contains("Bindi: \"Treatment2\" was due on 2025-05-20 (overdue for 157 days)") &&
                output.contains("Chiku: \"Treatment3\" was due on 2025-10-09 (overdue for 15 days)") &&
                !output.contains("Treatment1"));
    }

    @Test
    void exec_noPetsAdded_printsNoPetsAddedMessage() {
        pets.deletePet(pets.getPetByName("Chiku"));
        pets.deletePet(pets.getPetByName("Bindi"));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        overdueTreatments.exec("");

        System.setOut(originalOut);
        String output = outContent.toString();
        assertTrue(output.contains("No pets added"));
    }

    @Test
    void exec_invalidArgsProvided_printsInvalidArgsMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        overdueTreatments.exec("some args without the correct tag");

        System.setOut(originalOut);
        String output = outContent.toString();
        assertTrue(output.contains("Invalid arguments provided"));
    }

    @Test
    void exec_invalidPetNameProvided_printsNoPetFoundMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        overdueTreatments.exec("n/I do not exist");

        System.setOut(originalOut);
        String output = outContent.toString();
        assertTrue(output.contains("No pet found with the name"));
    }

    @Test
    void exec_noOverdueTreatment_printsNoOverdueMessage() {
        Treatment treatment1 = new Treatment("Treatment1", null, LocalDate.parse("2025-11-11"));
        Treatment treatment2 = new Treatment("Treatment2", null, LocalDate.parse("2025-05-20"));
        Treatment treatment3 = new Treatment("Treatment3", null, LocalDate.parse("2025-10-09"));
        treatment1.setCompleted(true);
        treatment2.setCompleted(true);
        treatment3.setCompleted(true);

        pets.getPetByName("Chiku").addTreatment(treatment1);
        pets.getPetByName("Bindi").addTreatment(treatment2);
        pets.getPetByName("Chiku").addTreatment(treatment3);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        overdueTreatments.exec("");

        System.setOut(originalOut);
        String output = outContent.toString();
        assertTrue(output.contains("No overdue treatment"));
    }

    @Test
    void exec_treatmentDueToday_printsNoOverdueMessage() {
        Treatment today = new Treatment("Treatment1", null, LocalDate.parse("2025-10-24"));

        pets.getPetByName("Chiku").addTreatment(today);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        overdueTreatments.exec("");

        System.setOut(originalOut);
        String output = outContent.toString();
        assertTrue(output.contains("No overdue treatment"));
    }
}
