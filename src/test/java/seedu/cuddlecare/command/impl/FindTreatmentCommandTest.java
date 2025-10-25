package seedu.cuddlecare.command.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for {@link FindTreatmentCommand}
 */
class FindTreatmentCommandTest {

    private PetList petList;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        // Create pets and add to list
        Pet peanut = new Pet("Peanut", "Dog", 2);
        Pet whiskers = new Pet("Whiskers", "Cat", 3);
        petList = new PetList();
        petList.add(peanut);
        petList.add(whiskers);

        // Add treatments with various names for testing
        Treatment vaccine1 = new Treatment("Rabies Vaccine", null, LocalDate.parse("2025-10-10"));
        Treatment vaccine2 = new Treatment("Annual Vaccine", null, LocalDate.parse("2025-11-15"));
        Treatment grooming = new Treatment("Full Grooming", null, LocalDate.parse("2025-10-20"));
        Treatment checkup = new Treatment("Regular Checkup", null, LocalDate.parse("2025-09-15"));
        Treatment dental = new Treatment("Dental Cleaning", null, LocalDate.parse("2025-12-05"));

        peanut.addTreatment(vaccine1);
        peanut.addTreatment(grooming);
        peanut.addTreatment(dental);
        whiskers.addTreatment(vaccine2);
        whiskers.addTreatment(checkup);

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void exec_findVaccine_findsAllVaccineTreatments() {
        FindTreatmentCommand command = new FindTreatmentCommand(petList);
        command.exec("vaccine");

        String output = outContent.toString().trim();
        assertTrue(output.contains("Found 2 treatment(s) containing: \"vaccine\""));
        assertTrue(output.contains("Rabies Vaccine"));
        assertTrue(output.contains("Annual Vaccine"));
    }

    @Test
    void exec_findGrooming_findsGroomingTreatment() {
        FindTreatmentCommand command = new FindTreatmentCommand(petList);
        command.exec("grooming");

        String output = outContent.toString().trim();
        assertTrue(output.contains("Found 1 treatment(s) containing: \"grooming\""));
        assertTrue(output.contains("Full Grooming"));
        assertTrue(output.contains("Peanut:"));
    }

    @Test
    void exec_findCaseInsensitive_findsTreatments() {
        FindTreatmentCommand command = new FindTreatmentCommand(petList);
        command.exec("VACCINE");

        String output = outContent.toString().trim();
        assertTrue(output.contains("Found 2 treatment(s) containing: \"VACCINE\""));
        assertTrue(output.contains("Rabies Vaccine"));
        assertTrue(output.contains("Annual Vaccine"));
    }

    @Test
    void exec_findPartialWord_findsTreatments() {
        FindTreatmentCommand command = new FindTreatmentCommand(petList);
        command.exec("clean");

        String output = outContent.toString().trim();
        assertTrue(output.contains("Found 1 treatment(s) containing: \"clean\""));
        assertTrue(output.contains("Dental Cleaning"));
    }

    @Test
    void exec_findNonExistentKeyword_printsNotFoundMessage() {
        FindTreatmentCommand command = new FindTreatmentCommand(petList);
        command.exec("surgery");

        String output = outContent.toString().trim();
        assertEquals("No treatments found containing: \"surgery\"", output);
    }

    @Test
    void exec_emptyKeyword_printsErrorMessage() {
        FindTreatmentCommand command = new FindTreatmentCommand(petList);
        command.exec("");

        String output = outContent.toString().trim();
        assertEquals("Error: Please provide a keyword to search for.", output);
    }

    @Test
    void exec_whitespaceKeyword_printsErrorMessage() {
        FindTreatmentCommand command = new FindTreatmentCommand(petList);
        command.exec("   ");

        String output = outContent.toString().trim();
        assertEquals("Error: Please provide a keyword to search for.", output);
    }

    @Test
    void exec_findWithNoTreatments_printsNotFoundMessage() {
        // Create empty pet list
        PetList emptyPetList = new PetList();
        Pet lonelyPet = new Pet("Lonely", "Rabbit", 1);
        emptyPetList.add(lonelyPet);
        // No treatments added

        FindTreatmentCommand command = new FindTreatmentCommand(emptyPetList);
        command.exec("vaccine");

        String output = outContent.toString().trim();
        assertEquals("No treatments found containing: \"vaccine\"", output);
    }
}
