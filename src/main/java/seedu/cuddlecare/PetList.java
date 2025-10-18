package seedu.cuddlecare;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * A list that contains all the user's pets.
 *
 */
public class PetList {

    /** Underlying data structure to hold all pets */
    private final ArrayList<Pet> pets;

    /**
     * Initializes the list of pets.
     */
    public PetList() {
        this.pets = new ArrayList<>();
    }

    /**
     * Adds the unique pet to the user's pet list.
     *
     * @param pet the newly to-be-added pet
     * @return success
     */
    public boolean add(Pet pet) {
        if (isDuplicateName(pet.getName())) {
            return false;
        }
        pets.add(pet);
        return true;
    }

    /**
     * Checks if pet already exists within the pet list.
     *
     * @param name the name of the pet
     * @return boolean whether pet exists or not
     */
    private boolean isDuplicateName(String name) {
        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a pet by its name in the list.
     *
     * @param name the index number of pet
     * @return pet if found, null if index is invalid
     */
    public Pet getPetByName(String name) {
        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(name)) {
                return pet;
            }
        }
        return null;
    }

    /**
     * gets list size
     */
    public int size() {
        return pets.size();
    }

    /**
     * returns a pet by its index in the list.
     *
     * @param index the index of pet in PetList
     * @return pet
     */
    public Pet get(int index) {
        return pets.get(index);
    }

    /**
     * returns a stream of pets in the list
     *
     * @return a stream of all pets in the list
     */
    public Stream<Pet> stream() {
        return pets.stream();
    }

    /**
     * Removes the specified pet from the list, if it exists.
     *
     * @param petToDelete the {@link Pet} object to remove
     * @return {@code true} if the pet was found and removed; {@code false} otherwise
     */
    public boolean deletePet(Pet petToDelete) {
        return pets.remove(petToDelete);
    }

}
