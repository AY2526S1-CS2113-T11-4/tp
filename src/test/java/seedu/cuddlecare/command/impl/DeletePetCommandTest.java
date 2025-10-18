package seedu.cuddlecare.command.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeletePetCommandTest {

    private PetList pets;
    private DeletePetCommand deleteCommand;

    @BeforeAll
    static void muteLogs() {
        LogManager.getLogManager().reset();
        Logger root = Logger.getLogger("");
        root.setLevel(Level.OFF);
    }

    @BeforeEach
    void setUp() {
        pets = new PetList();
        pets.add(new Pet("Buddy", "Dog", 3));
        pets.add(new Pet("Milo", "Cat", 2));
        deleteCommand = new DeletePetCommand(pets);
    }

    @Test
    void exec_validName_deletesPet() {
        deleteCommand.exec("n/buddy");
        assertEquals(1, pets.size());
        assertEquals("Milo", pets.get(0).getName());
    }

    @Test
    void exec_invalidPetName_printsError() {
        deleteCommand.exec("n/what");
        assertEquals(2, pets.size());
    }

    @Test
    void exec_missingName_printsError() {
        deleteCommand.exec("");
        assertEquals(2, pets.size());
    }

    @Test
    void constructor_nullPetList_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new DeletePetCommand(null));
    }
}
