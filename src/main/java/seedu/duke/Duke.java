package seedu.duke;

import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    /** List of commands */
    private static final String ADD_COMMAND = "add";

    /** Horizontal line to be used for printing */
    private static final String HORIZONTAL_LINE = "\t_____________________________________________________";

    /** List of pets */
    private static ArrayList<Pet> pets = new ArrayList<>();

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        printWelcomeMessage();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            String lowerInput = input.toLowerCase();
            if (lowerInput.equals("bye")) {
                executeByeCommand();
                break;
            } else if (lowerInput.startsWith(ADD_COMMAND)) {
                executeAddPet(input);
            } else {
                System.out.println("Invalid command. Try again.");
            }
        }
    }

    /**
     * Prints the welcome message to the user.
     */
    private static void printWelcomeMessage() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Hello from\n" + logo);
        System.out.println("Hello! What can I do for you?");
        System.out.println(HORIZONTAL_LINE + "\n");
    }

    /**
     * Executes the add command to allow the user
     * to add a pet to their pet list.
     * @param input the user's input with name, species, and age
     */
    private static void executeAddPet(String input) {
        try {
            String[] parts = input.split(" ");

            String name = null;
            String species = null;
            int age = -1;

            for (String part : parts) {
                if (part.startsWith("n/")) {
                    name = part.substring(2);
                } else if (part.startsWith("s/")) {
                    species = part.substring(2);
                } else if (part.startsWith("a/")) {
                    age = Integer.parseInt(part.substring(2));
                }
            }

            if (name == null || species == null || age < 0) {
                System.out.println("Invalid input. Please try again.");
                return;
            }

            for (Pet pet : pets) {
                if (pet.getName().equalsIgnoreCase(name)) {
                    System.out.println("A pet with that name already exists.");
                    return;
                }
            }

            Pet newPet = new Pet(name, species, age);
            pets.add(newPet);
            System.out.println(name + " has been successfully added.");
        } catch (NumberFormatException e) {
            System.out.println("Age must be a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
    }

    /**
     * Executes the bye command that ends the program.
     */
    private static void executeByeCommand() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Goodbye!");
        System.out.println(HORIZONTAL_LINE);
    }
}
