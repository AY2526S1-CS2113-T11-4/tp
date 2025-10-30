# Pavithra Srinivasan - Project Portfolio Page

## Overview
CuddleCare is a CLI-based pet care tracker that allows users to track multiple pets, maintain detailed treatment 
histories, and quickly search and filter records.

My primary contribution was developing functionalities related to the treatment management system.

Code Contributed: [Code Dashboard](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-09-19T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=Pavithra6-Srinivasan&tabRepo=AY2526S1-CS2113-T11-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Summary of Contributions

#### New Features
#### 1. Add Treatment Feature 

What it does: 
Allows users to add treatment records to a pet with treatment name, date, and optional notes.

Justification: 
This feature is essential for pet owners to maintain treatments records for each pet. The optional note field allows 
them to record important details such as doctor names, medications, or costs.

Highlights:
* Implements robust input validation for dates using LocalDate.parse()
* Supports multi-line notes with special characters 
* Provides clear error messages for invalid inputs 

_PR_: [#23](https://github.com/AY2526S1-CS2113-T11-4/tp/pull/23)

#### 2. Delete Treatment Feature

What it does: 
Removes a treatment record from a pet's history by index.

Justification: 
Users need the ability to remove incorrect or duplicate treatment entries.

Highlights:
* Uses 1-based indexing for user-friendliness 
* Includes comprehensive error handling for invalid indices and non-existent pets 
* Provides confirmation messages with the deleted treatment name

_PR_: [#31](https://github.com/AY2526S1-CS2113-T11-4/tp/pull/31)

#### 3. Find Treatment by Phrase Feature
   
What it does: 
Searches for treatments across all pets using case-insensitive substring matching.

Justification:  
Pet owners often need to quickly locate all instances of a particular treatment type (e.g., all vaccinations) across 
multiple pets.

Highlights:
* Displays results with pet names and dates for easy reference 
* Handles edge cases such as empty keywords and no matches gracefully

_PR_: [#71](https://github.com/AY2526S1-CS2113-T11-4/tp/pull/71)

#### 4. Filter Treatments by Date Range Feature
   
What it does: 
Displays all treatments within a specified date range across all pets.

Justification: 
Essential for reviewing recent treatments, generating reports, or planning follow-up appointments.

Highlights:
* Inclusive date range (includes both start and end dates) matches user expectations 
* Validates that start date is not after end date 
* Provides clear error messages for invalid date formats or ranges

_PR_: [#74](https://github.com/AY2526S1-CS2113-T11-4/tp/pull/74)

#### 5. Testing
Added JUnit testing for following features
* `AddTreatmentCommandTest`
* `DeleteTreatmentCommandTest`
* `FilterTreatmentByDateCommandTest`
* `FindTreatmentCommandTest`

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
* Added sequence diagrams for find treatment and filter treatment by date features

#### Project Management
* Reviewed pull requests from team members (#116)
#### Contributions to Team-Based Tasks
* Reviewed pull requests from team members
