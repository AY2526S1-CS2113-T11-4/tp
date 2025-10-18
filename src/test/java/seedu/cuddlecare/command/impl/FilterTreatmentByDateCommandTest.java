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
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for {@link FilterTreatmentByDateCommand}
 */
class FilterTreatmentByDateCommandTest {

    private PetList petList;
    private ByteArrayOutputStream outContent;

    @BeforeAll
    static void muteLogs() {
        LogManager.getLogManager().reset();
        Logger root = Logger.getLogger("");
        root.setLevel(Level.OFF);
    }

    @BeforeEach
    void setUp() {
        Pet peanut = new Pet("Peanut", "Dog", 2);
        Pet whiskers = new Pet("Whiskers", "Cat", 3);
        Pet spot = new Pet("Spot", "Dog", 4);
        petList = new PetList();
        petList.add(peanut);
        petList.add(whiskers);
        petList.add(spot);

        Treatment dec1 = new Treatment("Rabies Vaccine", LocalDate.parse("2024-12-01"));
        Treatment dec10 = new Treatment("Annual Checkup", LocalDate.parse("2024-12-10"));
        Treatment dec15 = new Treatment("Grooming", LocalDate.parse("2024-12-15"));
        Treatment dec31 = new Treatment("Dental Cleaning", LocalDate.parse("2024-12-31"));
        Treatment jan5 = new Treatment("Nail Trim", LocalDate.parse("2025-01-05"));
        Treatment jan20 = new Treatment("Blood Test", LocalDate.parse("2025-01-20"));

        peanut.addTreatment(dec1);
        peanut.addTreatment(dec10);
        whiskers.addTreatment(dec15);
        whiskers.addTreatment(jan5);
        spot.addTreatment(dec31);
        spot.addTreatment(jan20);

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void exec_validDateRange_findsTreatmentsInRange() {
        FilterTreatmentByDateCommand command = new FilterTreatmentByDateCommand(petList);
        command.exec("from/2024-12-01 to/2024-12-31");

        String output = outContent.toString().trim();
        assertTrue(output.contains("Found 4 treatment(s) from 2024-12-01 to 2024-12-31:"));
        assertTrue(output.contains("Rabies Vaccine"));
        assertTrue(output.contains("Annual Checkup"));
        assertTrue(output.contains("Grooming"));
        assertTrue(output.contains("Dental Cleaning"));
    }

    @Test
    void exec_singleDayRange_findsTreatmentsOnThatDay() {
        FilterTreatmentByDateCommand command = new FilterTreatmentByDateCommand(petList);
        command.exec("from/2024-12-10 to/2024-12-10");

        String output = outContent.toString().trim();
        assertTrue(output.contains("Found 1 treatment(s) from 2024-12-10 to 2024-12-10:"));
        assertTrue(output.contains("Annual Checkup"));
    }

    @Test
    void exec_boundaryDates_findsInclusiveRange() {
        FilterTreatmentByDateCommand command = new FilterTreatmentByDateCommand(petList);
        command.exec("from/2024-12-01 to/2025-01-05");

        String output = outContent.toString().trim();
        assertTrue(output.contains("Found 5 treatment(s)"));
        assertTrue(output.contains("Rabies Vaccine"));
        assertTrue(output.contains("Nail Trim"));
    }

    @Test
    void exec_noTreatmentsInRange_printsNotFoundMessage() {
        FilterTreatmentByDateCommand command = new FilterTreatmentByDateCommand(petList);
        command.exec("from/2025-02-01 to/2025-02-28");

        String output = outContent.toString().trim();
        assertEquals("No treatments found from 2025-02-01 to 2025-02-28.", output);
    }

    @Test
    void exec_reversedDateRange_printsErrorMessage() {
        FilterTreatmentByDateCommand command = new FilterTreatmentByDateCommand(petList);
        command.exec("from/2024-12-31 to/2024-12-01");

        String output = outContent.toString().trim();
        assertEquals("Error: Start date cannot be after end date.", output);
    }

    @Test
    void exec_missingFromDate_printsUsageMessage() {
        FilterTreatmentByDateCommand command = new FilterTreatmentByDateCommand(petList);
        command.exec("to/2024-12-31");

        String output = outContent.toString().trim();
        assertTrue(output.contains("Invalid input. Usage: treatment-date from/DATE to/DATE"));
    }

    @Test
    void exec_missingToDate_printsUsageMessage() {
        FilterTreatmentByDateCommand command = new FilterTreatmentByDateCommand(petList);
        command.exec("from/2024-12-01");

        String output = outContent.toString().trim();
        assertTrue(output.contains("Invalid input. Usage: treatment-date from/DATE to/DATE"));
    }

    @Test
    void exec_emptyFromDate_printsErrorMessage() {
        FilterTreatmentByDateCommand command = new FilterTreatmentByDateCommand(petList);
        command.exec("from/ to/2024-12-31");

        String output = outContent.toString().trim();
        assertEquals("Error: Start date cannot be empty.", output);
    }

    @Test
    void exec_emptyToDate_printsErrorMessage() {
        FilterTreatmentByDateCommand command = new FilterTreatmentByDateCommand(petList);
        command.exec("from/2024-12-01 to/");

        String output = outContent.toString().trim();
        assertEquals("Error: End date cannot be empty.", output);
    }

    @Test
    void exec_invalidFromDateFormat_printsErrorMessage() {
        FilterTreatmentByDateCommand command = new FilterTreatmentByDateCommand(petList);
        command.exec("from/2024/12/01 to/2024-12-31");

        String output = outContent.toString().trim();
        assertEquals("Invalid start date format. Please use yyyy-MM-dd format.", output);
    }

    @Test
    void exec_invalidToDateFormat_printsErrorMessage() {
        FilterTreatmentByDateCommand command = new FilterTreatmentByDateCommand(petList);
        command.exec("from/2024-12-01 to/31-12-2024");

        String output = outContent.toString().trim();
        assertEquals("Invalid end date format. Please use yyyy-MM-dd format.", output);
    }

    @Test
    void exec_emptyArguments_printsUsageMessage() {
        FilterTreatmentByDateCommand command = new FilterTreatmentByDateCommand(petList);
        command.exec("");

        String output = outContent.toString().trim();
        assertTrue(output.contains("Invalid input. Usage: treatment-date from/DATE to/DATE"));
    }

    @Test
    void exec_withNoTreatments_printsNotFoundMessage() {
        PetList emptyPetList = new PetList();
        Pet lonelyPet = new Pet("Lonely", "Rabbit", 1);
        emptyPetList.add(lonelyPet);

        FilterTreatmentByDateCommand command = new FilterTreatmentByDateCommand(emptyPetList);
        command.exec("from/2024-12-01 to/2024-12-31");

        String output = outContent.toString().trim();
        assertEquals("No treatments found from 2024-12-01 to 2024-12-31.", output);
    }
}
