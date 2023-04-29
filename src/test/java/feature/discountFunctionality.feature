Feature: Discount Functionality

  Scenario: Calculate discount based on time served
    Given a price of 2000 and 0 time served
    When the discount is calculated
    Then the discount should be 0.0

    Given a price of 500 and 4 time served
    When the discount is calculated
    Then the discount should be 0.02

    Given a price of 300 and 11 time served
    When the discount is calculated
    Then the discount should be 0.05
