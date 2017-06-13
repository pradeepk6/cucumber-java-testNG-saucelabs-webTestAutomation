@delete
Feature: Delete Computer
  I should be able to delete computer entry
  Test Setup:
  As the system allows duplicates a helper method deletes all matching entries before
  adding as part of test setup

    Scenario: Delete computer
      Given I create a new computer named "opticloopz"
      And search for the newly added computer and navigate to the computer page
      When I delete the computer
      Then I should see successful delete message
      """
      Done! Computer has been deleted
      """
      And search for the computer should find zero results

