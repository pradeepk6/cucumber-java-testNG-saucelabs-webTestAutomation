
Feature: Create computer
  Add a new computer entry with valid details.
  Validation:
  'Name' of the computer is the only mandatory field the rest being optional.
  There is a specified list of options for company-name while the field
  itself is optional.Dates must be in the format (yy)yy-(m)m-(d)d.
  This Feature also covers 'Read'.
  Test Setup:
  As the system allows duplicates a helper method deletes all matching entries before
  adding as part of test setup

  Background:
    Given I am on Home page

  @add
  Scenario Outline: Add a new computer successfully
    When I fill in <name> for name,<introducedDate> for introducedDate,<discontinuedDate> for discontinuedDate,<company> for company and submit
    Then I should see a success message
    """
    Done! Computer <name> has been created
    """
    And search for the computer should match details
    Examples:
      | name          | introducedDate | discontinuedDate | company |
      | Alpha_Go2.5#  | 1980-1-1       | 1981-1-1         | IBM     |
      | deep_mind2.5# |                |                  |         |

  #using 'Scenario Outline' here means less coding if spec changes in future

  Scenario Outline: 'Add new computer' fails as name field is Required
    When I fill in empty sapce <name> for name and submit
    Then I should see Required validation failure
    Examples:
      | name |
      |      |

  Scenario: 'Add new computer' fails as name field is Required
    When I do not fill in name and submit
    Then I should see Required validation failure

  Scenario Outline: 'Add new computer' form-validation fails due to invalid introduced_date
    When I fill in <name> for name and <introduced_date> for introduced_date and submit
    Then I should see introduced_date validation failure
    #covering only logical errors, exhaustive tests are covered at unit test level
    Examples:
      | name | introduced_date |
      | test | 1987/12/1       |
      | test | 1987-jan-01     |
      | test | 1-12-87         |
      | test | 1987-13-1       |

  Scenario Outline: Add new computer form-validation fails due to invalid discontinued_date
    When I fill in <name> for name and <discontinued_date> for discontinued_date and submit
    Then I should see discontinued_date validation failure
    Examples:
      | name | discontinued_date |
      | test | 1987/12/1         |





