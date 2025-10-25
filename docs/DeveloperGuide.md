# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
original source as well}

## Design & implementation

### Feature: Add Pet
The diagram below shows how the `AddPetCommand` class interacts with other
components in the system.

![AddPetCommand Class Diagram](diagrams/AddPetCommand_Class_Diagram.png)

This design follows a command-based architecture, where each command is
encapsulated in its own class implementing the `Command` interface.
`AddPetCommand` depends on the `PetList` object, which stores all registered
pets. Each `Pet` object maintains information about its name, species, and age.

When executed, the command performs the following steps:
1. Parses user input to extract the pet's name (`n/`), species (`s/`), and
age (`a/`)
2. Validates the input fields
3. Creates a new `Pet` object with the parsed parameters
4. Adds the new pet to the pet list using `addPet()`
5. Handles duplicates and disregards the addition of the
pet if the duplicate exists
6. Displays and logs activity

The `AddPetCommand` ensures that only valid and unique pets are added to the
pet list. It emphasizes input validation, duplicate prevention, and
error handling, while maintaining logging for debugging/monitoring execution.

### Feature: Delete Pet
This feature is built using the Command Pattern. This design decouples the invoker 
(main app logic) from the operation itself by using a Command interface. 
The DeletePetCommand is a concrete implementation of this interface that contains 
all the logic for parsing and execution.

![DeletePetCommand Class Diagram](diagrams/DeletePetCommand_Class_Diagram.png)

As shown in the class diagram above, the DeletePetCommand does not manage the list directly. 
Instead, it holds a reference to a PetList instance (a "has-a" relationship, 
or Aggregation) that is provided to its constructor. This use of Dependency 
Injection makes the command highly testable, as the PetList can be easily mocked.

The execution flow starts when the exec method is called with the user's input. 
The command parses the string to find the pet's name, then validates the input by 
querying the PetList. If the syntax is correct and the pet exists, the 
command instructs the PetList to perform the actual deletion. The DeletePetCommand 
is also responsible for printing all relevant success or error messages to the user.

The detailed interaction between the main app (CuddleCare), DeletePetCommand, and 
PetList during execution is illustrated in the sequence diagram below.

![DeletePetCommand Class Diagram](diagrams/DeletePetCommand_Sequence_Diagram.png)

### Feature: Edit Pet
![EditPetCommand Class Diagram](diagrams/EditPetCommand_Class_Diagram.png)
![EditPetCommand Sequence Diagram](diagrams/EditPetCommand_Sequence_Diagram.png)

**Purpose**: Edit an existing pet’s properties. Only provided fields are changed.

**Command word**: `edit-pet`

**Format**
```
edit-pet n/OLD_NAME [nn/NEW_NAME] [s/SPECIES] [a/AGE]
```

**Arguments**
- `n/OLD_NAME` *(required)* – name of the pet to edit.
- `nn/NEW_NAME` *(optional)* – new name to assign.
- `s/SPECIES` *(optional)* – new species.
- `a/AGE` *(optional)* – integer age (non-negative).

**Examples**
```
edit-pet n/Milo nn/Millie a/4
edit-pet n/Lucky s/Dog
edit-pet n/Tama a/12
```

**Success behaviour**
- Finds the pet by `OLD_NAME`. Updates only the tokens present. Prints a success line with the edited pet.

**Failure cases & messages**
- Missing `n/`: prints usage line.
- Pet not found: `Pet not found.`
- No editable tokens provided (`nn/`, `s/`, `a/` all absent): `Nothing to edit.`
- `a/` not an integer: `Age must be a valid number.`

**Logging**
- INFO: start/end; edited fields.
- WARNING: invalid tokens/number format; pet missing.

### Feature: List Pets
![ListPetsCommand Class Diagram](diagrams/ListPetsCommand_Class_Diagram.png)
![ListPetsCommand Sequence Diagram](diagrams/ListPetsCommand_Sequence_Diagram.png)

**Purpose**: Display all pets maintained in memory.

**Command word**: `list pets`

**Format**
```
list-pets
```

- Prints an enumerated list (1-based) of all pets, each rendered via `Pet#toString()`.
- If empty, prints `No pets found.`

**Behaviour**
- Iterates `PetList` from index `0..size-1` and prints `(i+1) + ". " + pet.toString()`.

**Failure cases & messages**
- None (arguments are ignored). If the list is empty, shows the friendly message above.

**Logging**
- INFO on command entry/exit; FINE for iteration count.

### Feature: Find Treatment
The FindCommand allows users to search for treatments across all pets by matching a keyword against treatment names. 
The search is case-insensitive and uses substring matching.

The sequence diagram below illustrates the execution flow of the find treatment command:

![FindTreatmentCommand_Sequence_Diagram.png](Diagrams/FindTreatmentCommand_Sequence_Diagram.png)

Implementation
The find treatment mechanism is facilitated by `FindTreatmentCommand`. It performs a case-insensitive keyword search 
across all treatments for all pets in the system. The search uses substring matching, allowing partial keyword matches.

Key operations:
* `PetList#getAllPets()` — Retrieves all pets in the system. 
* `Pet#getTreatments()` — Gets the treatment list for each pet. 
* String comparison using `toLowerCase()` and `contains()` for matching.

Command format: `find-treatment KEYWORD`

When the command is executed:
1. User input is parsed to extract the search keyword 
2. The keyword is validated (must not be empty)
3. Command retrieves all pets from `PetList` 
4. For each pet, the command iterates through its treatments 
5. Each treatment name is checked against the keyword (case-insensitive substring match)
6. Matching treatments are collected with their pet names and dates 
7. Results are displayed to the user, or a "No treatments found" message if no matches

### Feature: Filter Treatments By Date
The FilterTreatmentCommand enables users to view all treatments that fall within a specified date range across all pets 
in the system.

The sequence diagram below shows the execution flow when filtering treatments by date:

![FilterTreatmentByDateCommand_Sequence_Diagram.png](Diagrams/FilterTreatmentByDateCommand_Sequence_Diagram.png)

The filter treatments by date range feature is facilitated by `FilterTreatmentCommand`. It allows users to view all 
treatments that fall within a specified date range across all pets in the system.

The implementation involves the following operations:
* `PetList#getAllPets()` — Retrieves all pets. 
* `Pet#getTreatments()` — Gets treatments for filtering. 
* `Treatment#getDate()` — Retrieves the treatment date for comparison. 
* Date comparison using `LocalDate#isAfter()` and `LocalDate#isBefore()`.

Command format: `filter-treatment start/START_DATE end/END_DATE`

When the command is executed:
1. User input is parsed to extract start date and end date 
2. Both dates are validated using `LocalDate.parse()`
3. The command checks that start date is not after end date 
4. Command retrieves all pets from `PetList`
5. For each pet, the command iterates through its treatments 
6. Each treatment's date is checked against the date range (inclusive)
7. Matching treatments are collected and displayed to the user

### Feature: Add Treatment
The diagram below shows how the AddTreatmentCommand class interacts with other components in the system.

![AddTreatmentCommand-AddTreatmentCommand_Class_Diagram.png](Diagrams/AddTreatmentCommand-AddTreatmentCommand_Class_Diagram.png)

The design follows a command-based architecture, where each command is encapsulated in its own class implementing the 
Command interface. AddTreatmentCommand depends on the PetList object, which stores all registered pets. Each Pet object 
maintains a list of Treatment objects.

When executed, the command:

1. Parses user input to extract the pet name (`n/`), treatment name (`t/`), and date (`d/`)
2. Retrieves the corresponding Pet object from the PetList using `getPetByName()`
3. Validates the date format using `LocalDate.parse()`
4. Creates a new Treatment object with the validated parameters 
5. Adds the treatment to the pet's treatment list via `addTreatment()`
6. Displays a confirmation message or error if validation fails

The command validates all inputs before modifying the pet's treatment list. If the pet is not found or the date format 
is invalid, appropriate error messages are displayed.

### Feature: Delete Treatment
The figure below shows how the DeleteTreatmentCommand interacts with other key classes in the system.

![DeleteTreatmentCommand-DeleteTreatmentCommand_Class_Diagram.png](Diagrams/DeleteTreatmentCommand-DeleteTreatmentCommand_Class_Diagram.png)

The DeleteTreatmentCommand follows a command-based architecture, where each command is encapsulated in its own class 
implementing the Command interface.

When the command is executed:
1. User input is parsed to extract the pet's name (`n/`) and treatment index (`i/`)
2. Command retrieves the corresponding Pet object from the PetList using `getPetByName()`
3. Validates that the index is within valid bounds 
4. Converts the 1-based user index to 0-based array index 
5. Removes the treatment from the pet's list using `removeTreatment()`
6. Displays a confirmation message with the deleted treatment's name

If the pet is not found or the index is invalid, the command prints an appropriate error message.

#### Design Considerations

Alternative 1 (current choice): Use index-based deletion.
Pros: 
* Always deletes exactly one specific treatment. 
* Easier for user to type out.

Cons: 
* Requires users to know or look up the index.

Alternative 2: Use treatment name for deletion.

Pros: 
* More intuitive for users who remember treatment names.

Cons: 
* Ambiguous when multiple treatments have similar names. 
* Requires additional confirmation steps.

### Feature: Mark a treatment as done
![MarkTreatmentCommand Class Diagram](diagrams/MarkTreatmentCommand_Class_Diagram.png)
![MarkTreatmentCommand Sequence Diagram](diagrams/MarkTreatmentCommand_Sequence_Diagram.png)

**Purpose**: Mark a specific treatment (by per‑pet local index) as completed.

**Command word**: `mark`

**Format**
```
mark n/PET_NAME i/INDEX
```
- `n/PET_NAME` *(required)* – the pet to operate on.
- `i/INDEX` *(required)* – 1‑based index into that pet’s treatment list.

**Examples**
```
mark n/Milo i/2
```

**Success behaviour**
- Sets `Treatment#setCompleted(true)` for the selected treatment. Prints a confirmation containing pet name and treatment info.

**Failure cases & messages**
- Missing `n/` or `i/`: prints usage line.
- Pet not found: `No such pet.`
- `i/` not an integer: `Invalid index.`
- Index out of bounds: `Invalid treatment index.`

**Logging**
- INFO on success with (petName, index).
- WARNING for invalid input or lookups.

### Feature: Mark a treatment as not done
![UnmarkTreatmentCommand Class Diagram](diagrams/UnmarkTreatmentCommand_Class_Diagram.png)
![UnmarkTreatmentCommand Sequence Diagram](diagrams/UnmarkTreatmentCommand_Sequence_Diagram.png)

**Purpose**: Unmark a specific treatment (by per‑pet local index) as not completed.

**Command word**: `unmark`

**Format**
```
unmark n/PET_NAME i/INDEX
```

**Examples**
```
unmark n/Milo i/2
```

**Success behaviour**
- Sets `Treatment#setCompleted(false)` for the selected treatment.

**Failure cases & messages**
- Same validation and messages as `mark`.

**Logging**
- Same as `mark`.

### Feature: Filter Treatment by Date
{add details here}

### Feature: List All Treatments across all pets
#### Design

The diagram below shows how the `ListAllTreatmentsCommand` class interacts with other components in the system.

![ListALlTreatmentsCommand class diagram](diagrams/ListAllTreatmentsCommand_Class_Diagram.png)

The design follows a command-based architecture, where each command is encapsulated in its own class
implementing the `Command` interface.\
`ListAllTreatmentsCommand` depends on the PetList object, which stores all registered pets. Each `Pet`
object maintains a list of `Treatment` objects.

When executed, the command iterates through each `Pet` in the `PetList`, retrieves its treatments,
and formats them into displayable strings. The command then sorts all treatments by their dates in
ascending order before printing them to the console.

### Feature: List All Treatments of a pet
#### Design

The figure below shows how the `ListPetTreatmentsCommand` interacts with other key classes in the system.
![ListPetTreatmentsCommand class diagram](diagrams/ListPetTreatmentsCommand_Class_Diagram.png)

The `ListPetTreatmentsCommand` follows a command-based architecture, where each command is encapsulated
in its own class implementing the `Command` interface.

When the command is executed:

1. user input is parsed to extract the pet’s name (n/PET_NAME)
2. command retrieves the corresponding Pet object from the PetList using getPetByName().
3. then obtains the list of treatments from the Pet object via getTreatments().
4. displays the list of treatments in a numbered format. If the pet has no logged treatments,
   or if the pet is not found, the command prints a message.

### Feature: Add Treatment Note
{add details here}

### Feature: Group Treatments by type
![GroupTreatmentsByTypeCommand Class Diagram](diagrams/GroupTreatmentsByTypeCommand_Class_Diagram.png)
![GroupTreatmentsByTypeCommand Sequence Diagram](diagrams/GroupTreatmentsByTypeCommand_Sequence_Diagram.png)

**Purpose**: Group treatments by **type** across the dataset, then print each group with its members.

**Command word**: `group-treatments-by-type`

**Format**
```
group-treatments-by-type
```
*(If your parser supports `n/PET_NAME`, the command may accept `group-treatments-by-type n/NAME` to scope by a single pet; otherwise it groups across all pets.)*

**Examples**
```
group-treatments-by-type
```

**Success behaviour**
- Flattens `(pet, treatment)` rows across `PetList`, then buckets by type using `extractType(treatment)`.
- Prints each type header followed by entries: `(petName, treatmentName, date, completed)`.

**Failure cases & messages**
- No treatments anywhere: `No treatments logged.`

**Notes**
- A simple default for `extractType` is to use `Treatment#getName()` as the key, or the first token before a colon/space if you want coarse types.
- Sorting of buckets/rows is cosmetic and optional.

**Logging**
- INFO summarising bucket counts; FINE for per‑bucket sizes.

### Feature: View overdue treatments for all pets
{add details here}

### Feature: Summary
{add details here}

### Feature: Help to view all commands

### Feature: Exit
{add details here}


## Product scope

### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

| Version | As a ... | I want to ...             | So that I can ...                                           |
|---------|----------|---------------------------|-------------------------------------------------------------|
| v1.0    | new user | see usage instructions    | refer to them when I forget how to use the application      |
| v2.0    | user     | find a to-do item by name | locate a to-do without having to go through the entire list |

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
