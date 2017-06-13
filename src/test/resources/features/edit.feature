@edit
  Feature: Edit computer details
    Once a computer has been added with valid details searching for the added computer
    and locating it and clicking it should take to a details page where an edit form is
    displayed with details of the computer prepopulated.Read feature is also covered.
    Test Setup:
    As the system allows duplicates a helper method deletes all matching entries before
    adding as part of test setup

  Scenario: Edit computer details
    Given I add a computer with details
      | name  | introducedDate | discontinuedDate | company |
      | skulx | 1980-1-1       | 1980-12-31       | Amstrad|
    When I edit the computer with
      | name  | introducedDate | discontinuedDate | company |
      | skulz | 1981-1-1       | 1981-12-31       | IBM     |

    Then success message should be displayed "Done! Computer skulz has been updated"
    And search for the computer to read the changes

