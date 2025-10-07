package seedu.cuddlecare.command.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

/**
 * Tests for {@link UnmarkTreatmentCommand} using syntax n/PET_NAME i/INDEX.
 */
public class UnmarkTreatmentCommandTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream out;

    private PetList pets;
    private Pet milo;

    @BeforeEach
    void setUp() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        pets = new PetList();

        milo = new Pet("Milo", "Dog", 2);
        milo.addTreatment(new Treatment("Surgery", LocalDate.parse("2025-10-01")));
        milo.addTreatment(new Treatment("Stitches Removal", LocalDate.parse("2025-10-10")));

        pets.add(milo);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    /**
     * Unmarks the first treatment of Milo and verifies the success message.
     */
    @Test
    void exec_validArgs_printsSuccess() {
        Command cmd = new UnmarkTreatmentCommand(pets);

        cmd.exec("n/Milo i/1");

        String s = out.toString();
        Assertions.assertTrue(s.contains("Unmarked"), "Expected success phrase missing.\n" + s);
        Assertions.assertTrue(s.contains("Pet: Milo"), "Expected pet name missing.\n" + s);
    }

    /**
     * Uses an unknown pet name and expects an informative error.
     */
    @Test
    void exec_unknownPet_printsNoSuchPet() {
        Command cmd = new UnmarkTreatmentCommand(pets);

        cmd.exec("n/Unknown i/1");

        String s = out.toString();
        Assertions.assertTrue(s.contains("No such pet"), "Expected 'No such pet' message.\n" + s);
    }

    /**
     * Uses an out-of-range index for an existing pet and expects an informative error.
     */
    @Test
    void exec_indexOutOfRange_printsNoSuchTreatment() {
        Command cmd = new UnmarkTreatmentCommand(pets);

        cmd.exec("n/Milo i/99");

        String s = out.toString();
        Assertions.assertTrue(s.contains("No such treatment"), "Expected 'No such treatment' message.\n" + s);
    }

    /**
     * Provides malformed arguments and expects the usage line.
     */
    @Test
    void exec_badArgs_printsUsage() {
        Command cmd = new UnmarkTreatmentCommand(pets);

        cmd.exec("i/1");
        String s1 = out.toString();
        Assertions.assertTrue(s1.contains("Usage: unmark n/PET_NAME i/INDEX"),
                "Expected usage line when missing name.\n" + s1);

        out.reset();
        cmd.exec("n/Milo");
        String s2 = out.toString();
        Assertions.assertTrue(s2.contains("Usage: unmark n/PET_NAME i/INDEX"),
                "Expected usage line when missing index.\n" + s2);
    }
}
