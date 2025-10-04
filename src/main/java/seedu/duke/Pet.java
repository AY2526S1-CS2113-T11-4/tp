package seedu.duke;

public class Pet {

    private String name;
    private String species;
    private int age;

    /**
     * Initializes a pet with a specified name, species, and age.
     *
     * @param name name of the pet
     * @param species species of the pet
     * @param age age of the pet
     */
    public Pet(String name, String species, int age) {
        this.name = name;
        this.species = species;
        this.age = age;
    }

    /**
     * Returns the name of the pet.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a String composition that contains the pet's
     * name, species, and age in an organized format.
     */
    @Override
    public String toString() {
        return name + " (Species: " + species + ", Age: " + age + " years old)";
    }
}
