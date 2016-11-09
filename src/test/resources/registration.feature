Feature: Registration

  Scenario: Registration Functionality
    Given I navigate to the registration page
    When I try to register with valid data
    And I press submit button
    Then I should see currency converter page with button get rate
