package seedu.cuddlecare.command.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;

/**
 * Tests for {@link AddPetCommand}.
 * 
 * These tests validate that pets are created and added properly to the list.
 */
public class AddPetCommandTest {
    
    private PetList petList;
    private ByteArrayOutputStream outContent;
    private final PrintStream originalOut = System.out;

    private final String syntax = "add-pet n/PET_NAME s/PET_SPECIES a/PET_AGE";

    @BeforeAll
    static void muteLogs() {
        LogManager.getLogManager().reset();
        Logger root = Logger.getLogger("");
        root.setLevel(Level.OFF);
    }

    @BeforeEach
    void setUp() {
        petList = new PetList();

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void exec_validInput_addsPet() {
        AddPetCommand command = new AddPetCommand(petList);

        String input = "n/Fluffy s/Cat a/3";
        command.exec(input);

        assertEquals(1, petList.size());
        Pet newPet = petList.get(0);
        assertEquals("Fluffy", newPet.getName());
        assertEquals("Cat", newPet.getSpecies());
        assertEquals(3, newPet.getAge());
        assertTrue(outContent.toString().contains("Fluffy has been successfully added."));
    }

    @Test
    void exec_inputWithMultipleWords_stillParses() {
        AddPetCommand command = new AddPetCommand(petList);

        String input = "n/James Bond s/Golden Retriever a/5  ";
        command.exec(input);

        assertEquals(1, petList.size());
        Pet newPet = petList.get(0);
        assertEquals("James Bond", newPet.getName());
        assertEquals("Golden Retriever", newPet.getSpecies());
        assertEquals(5, newPet.getAge());
    }

    @Test
    void exec_inputWithExtraSpaces_stillParses() {
        AddPetCommand command = new AddPetCommand(petList);

        String input = "   n/Buddy   s/Dog    a/5  ";
        command.exec(input);

        assertEquals(1, petList.size());
        Pet newPet = petList.get(0);
        assertEquals("Buddy", newPet.getName());
        assertEquals("Dog", newPet.getSpecies());
        assertEquals(5, newPet.getAge());
    }

    @Test
    void exec_missingArgument_showsError() {
        AddPetCommand command = new AddPetCommand(petList);

        command.exec("n/Fluffy");
        assertEquals(0, petList.size());
        assertTrue(outContent.toString().contains("Invalid input. Usage: " + syntax));
    }

    @Test
    void exec_invalidAge_showsError() {
        AddPetCommand command = new AddPetCommand(petList);

        command.exec("n/Fluffy s/Cat a/three");
        assertEquals(0, petList.size());
        assertTrue(outContent.toString().contains("Age must be a valid number."));
    }

    @Test
    void exec_duplicatePet_showsError() {
        AddPetCommand command = new AddPetCommand(petList);

        command.exec("n/Fluffy s/Cat a/3");
        command.exec("n/Fluffy s/Cat a/3");

        assertEquals(1, petList.size());
        assertTrue(outContent.toString().contains("A pet with that name already exists."));
    }
}
