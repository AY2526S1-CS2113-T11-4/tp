# Harshit Srivastava — Project Portfolio Page

## Overview

**CuddleCare** is a desktop-based pet management application for pet owners and caregivers.  
It helps users record and track pets, treatments, and schedules through a simple command-line interface.  
The app aims to make pet care organized and stress-free, combining structured data handling with intuitive command execution.

---

## Summary of Contributions

[View my code contributions on RepoSense](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=HarshitSrivastavaHS&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByAuthors&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

### 1. Designed Core Command System & Architecture

* Designed and implemented the **core command architecture** (`Command`, `CommandWithArguments`, `Parser`, `LoggingConfigurator`).
* Created the **parser logic** to map user input to commands with argument handling, validation, and logging.
* Implemented key user-facing commands:
  * `HelpCommand` — lists all commands with syntax, descriptions, and categories.
  * `DeletePetCommand` — deletes pets safely with confirmation and logging.
  * `ByeCommand` — exits cleanly with a farewell message.
  * `OverdueTreatmentsCommand` — lists overdue treatments globally or per pet, supports testing with custom dates.
* Fixed bugs in the `Storage` class, adding validation and robust data handling.
* Added extensive JUnit test cases for `parser`, above-mentioned commands, and core functionality.
* Created **UML class and sequence diagrams** for above-mentioned command classes as well as `Storage`.
* Designed all commands with assertions and logging to ensure robustness and traceability.
* `OverdueTreatmentsCommand` supports flexible date testing and dynamic overdue day calculation.
* `HelpCommand` dynamically reflects all available commands for maintainability.


---

### 2. Documentation Contributions

* **User Guide (UG):** Authored detailed command documentation, including syntax and examples for the 
features implemented by me, as well as most of the FAQs.
* **Developer Guide (DG):** Documented command execution flow, design decisions, logging setup, assertions, and manual testing instructions.

---

### 3. Team Contributions & Collaboration
* Established the `base command framework` adopted by all teammates.
* Standardized logging, assertion, and coding conventions across the codebase.
* Reviewed teammates’ PRs for modularity, readability, and correctness.
* Assisted in integrating commands into the main application loop.

---

### 4. Review / Mentoring Contributions

* Regularly reviewed teammates’ PRs for code readability, modularity, and adherence to coding standards.
* Guided others on:
    * Proper Javadoc and logging format.
    * Using assertions effectively for defensive programming.
* Provided detailed feedback on UML diagram formatting and documentation tone.

---

### **Summary**

My main contribution was in **architecting and implementing the command system** that powers CuddleCare’s interaction layer.  
This included parser logic, logging setup, command encapsulation, and core user-facing commands.  
These form the foundation that enables smooth execution, extensibility, and maintainability for all future features.

Through these efforts, I ensured that **CuddleCare** adheres to strong **Software Engineering principles** such as modularity, testability, defensive coding, and clarity of documentation.

---

