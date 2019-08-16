Feature: Login
  As user I want to login under different roles
  Background:
    Given user is on the landing page

  @storemanager
  Scenario: Login as a store manager
    Then user logs in as a store manager
    And user verifies that "Dashboard" page name is displayed
  @negative
  Scenario: Verify warning message for invalid credentials
    Then user logs in with "wrong" username and "wrong" password
    And user verifies that "Invalid user name or password." warning message is displayed



