Feature: Order Functionality

  Scenario: Add an order
    Given an order is created with category "CARPET", name "Kareem`s Carpet", description "9*9 Pink Carpet", price 405.0 and no image
    When the order is added to the database
    Then the order should exist in the database with the same properties

  Scenario: Update an order
    Given an order is created with category "CARPET", name "Kareem`s Carpet", description "9*9 Pink Carpet", price 405.0 and no image
    When the order description is updated to "Testing description"
    Then the order description in the database should match "Testing description"

  Scenario: Delete an order
    Given an order is created with category "CARPET", name "Kareem`s Carpet", description "9*9 Pink Carpet", price 405.0 and no image
    When the order is deleted from the database
    Then the order should not exist in the database
