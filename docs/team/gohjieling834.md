# Goh Jie Ling's Project Portfolio Page

## Project: CuddleCare

CuddleCare is a CLI-based pet care tracker that helps user tracks all medical treatments/appointments for their pets.

Given below are my contributions to the project.

## Summary of Contributions

Code
Contributed: [Code Dashboard](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=gohjieling834&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-09-19T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

### New Features

- **Added a list all treatments command**
    - What it does: displays all the treatments logged (completed and not completed) for all pets in ascending order
    - Justification: this feature allows users to conveniently view all treatments across pets without checking each one
      individually. It gives users a complete picture of all treatment activities and helps them ensure that no
      treatment is overlooked.
    - Highlights:
        - Sorts all treatment entries by date in ascending order
        - Shows both completed and pending treatments for comprehensive tracking.
        - Enhances usability by providing a single consolidated view of all treatment data.
    - _PR:_ [#27](https://github.com/AY2526S1-CS2113-T11-4/tp/pull/27)

- **Added a list treatments specific to pet command**
    - What it does: displays all the treatments logged (completed and not completed) for a pet specified by the user
    - Justification: users may want to focus on one pet’s treatment records at a time. This command allows them to do so
      easily, improving clarity and supporting pet-specific tracking.
    - Highlights:
        - Retrieves and prints all treatments for a given pet name provided by the user.
        - Displays results in a readable format with clear headers and numbered entries.
        - Provides helpful messages when a pet is not found or has no logged treatments.
    - _PR:_ [#26](https://github.com/AY2526S1-CS2113-T11-4/tp/pull/26)

- **Added a treatment summary command**
    - What it does: displays a summary of all completed treatments across all pets within a specified date range
      provided by the user.
    - Justification: users may wish to review treatments that have been completed within a certain time period. This
      feature provides a concise overview filtered by both date and completion status
    - Highlights:
      - Integrates with DateUtils to parse and validate user-provided date ranges. 
      - Uses the Stream API to efficiently filter completed treatments across all pets.
      - Handles invalid date inputs gracefully, providing helpful feedback to users. 
      - Enhances overall data visibility by focusing on completed records only.
    - _PR:_ [#81](https://github.com/AY2526S1-CS2113-T11-4/tp/pull/81)

### Enhancements to Existing Features

- Added a Ui class to centralize and format all user-facing outputs. 
[PR: #114](https://github.com/AY2526S1-CS2113-T11-4/tp/pull/114)
- Created a DateUtils class to handle date parsing and validation for commands using date inputs. 
[PR: #81](https://github.com/AY2526S1-CS2113-T11-4/tp/pull/81)

### Documentation

- **User Guide**:
    - Added documentation for the features `list all treatments`, `list pet treatments` and `summary`
- **Developer Guide**:
    - Added implementation details of the `list all treatments`, `list pet treatments` and `summary` features

### Team

- Reviewed teammates’ PRs 
  - Some PRs I've reviewed: [#38](https://github.com/AY2526S1-CS2113-T11-4/tp/pull/38), 
  [#41](https://github.com/AY2526S1-CS2113-T11-4/tp/pull/41), [#79](https://github.com/AY2526S1-CS2113-T11-4/tp/pull/79),
  [#100](https://github.com/AY2526S1-CS2113-T11-4/tp/pull/100)