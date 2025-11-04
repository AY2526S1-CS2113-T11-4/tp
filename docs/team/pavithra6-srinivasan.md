# Pavithra Srinivasan - Project Portfolio Page

## Overview
CuddleCare is a CLI-based pet care tracker that allows users to track multiple pets, maintain detailed treatment 
histories, and quickly search and filter records. My primary contribution was developing functionalities related to the 
treatment management system.

Code Contributed: [Code Dashboard](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-09-19T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=Pavithra6-Srinivasan&tabRepo=AY2526S1-CS2113-T11-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Summary of Contributions
#### 1. Add Treatment Feature
What it does: 
Allows users to add treatment records to a pet with treatment name, date, and optional notes. This feature is essential 
for pet owners to maintain treatments records for each pet.

Highlights:
* Treatment names limited to 50 characters with regex pattern validation (`[a-zA-Z\\- ]+`)
* Date validation ensures entries are within 10 years past to 100 years future (110-year window)
* Duplicate Prevention: Prevents adding identical treatment name and date for the same pet 

_PR_: [#23](https://github.com/AY2526S1-CS2113-T11-4/tp/pull/23)

#### 2. Delete Treatment Feature
What it does: 
Removes a treatment record from a pet's history by index.

Users need the ability to remove incorrect or unwanted treatment entries to maintain data accuracy.

Highlights:
* Uses 1-based indexing for user-friendliness 
* Includes comprehensive error handling for invalid indices and non-existent pets 
* Provides confirmation messages with the deleted treatment name

_PR_: [#31](https://github.com/AY2526S1-CS2113-T11-4/tp/pull/31)

#### 3. Find Treatments by Phrase Feature
What it does: 
Searches for treatments across all pets using case-insensitive substring matching. Pet owners often need to quickly 
locate all instances of a particular treatment type across pets.

Highlights:
* Displays results with pet names and dates for easy reference 
* Edge Case Handling: Empty keyword, No matches found, pets with no treatments

_PR_: [#71](https://github.com/AY2526S1-CS2113-T11-4/tp/pull/71)

#### 4. Filter Treatments by Date Range Feature
What it does: 
Displays all treatments within a specified date range across all pets. Essential for reviewing recent treatments.

Highlights:
* Inclusive date range (includes both start and end dates) matches user expectations 
* Validates that start date is not after end date 

_PR_: [#74](https://github.com/AY2526S1-CS2113-T11-4/tp/pull/74)

#### 5. Testing
Added JUnit testing for following features : `AddTreatmentCommandTest`, `DeleteTreatmentCommandTest`,
`FilterTreatmentByDateCommandTest`, `FindTreatmentCommandTest`

#### Project Management
* Reviewed pull requests from team members [#116](https://github.com/AY2526S1-CS2113-T11-4/tp/pull/116)
* Ensured code quality standards and UML diagram consistency

#### Contributions to Team-Based Tasks
* Reviewed pull requests from team members

#### Documentation
#### User Guide
Sections Added:
* Add Treatment command documentation with format, examples, and constraints 
* Delete Treatment command documentation 
* Find Treatment command documentation 
* Filter Treatments by Date command documentation

#### Developer Guide
Sections Added:
* Implementation details for Add Treatment, Delete Treatment, Find Treatment and Filter Treatments by Date features.
* Design considerations and alternative approaches
* Added class diagrams for add and delete treatment features
* Added sequence diagrams for find treatments and filter treatments by date features
