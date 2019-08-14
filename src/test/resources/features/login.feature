Feature: As a user I want to login under different roles
  Scenario: Login as store manager
    Given User is on the landing page
    Then user logs in as a store manager
    And User verifies that "Dashboard" page name is displayed
    Then user quits
    @negative
    Scenario: Verify warning message for invalid credentials
      Given User is on the landing page
      Then user logs in with "wrong" username and "wrong" password
      And user verifies that "" is displayed



