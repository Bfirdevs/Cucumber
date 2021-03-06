@Vehicles
Feature: Vehicles
  As a user I want to see list of vehicles
  Scenario: Login as a driver  and navigate to the Vehicles
    Given user is on the landing page
    When user logs in as a "driver"
    Then user navigates to "Fleet" and "Vehicles"
    And user verifies that "All Cars" page name is displayed
    And user verifies that default page number is 1