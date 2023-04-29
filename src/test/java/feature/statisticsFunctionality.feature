Feature: Statistics Functionality

  Scenario: Count workers
    Given no workers are in the database
    When a worker is added to the database
    Then the number of workers in the database should be 1 more than before

  Scenario: Count orders by status
    Given no orders are in the database
    When an order is added to the database with status "WAITING"
    And a worker is assigned to the order
    And the order status is changed to "IN_TREATMENT"
    And the order status is changed to "COMPLETE"
    Then the number of orders in the "WAITING" status should be 1
    And the number of orders in the "IN_TREATMENT" status should be 1
    And the number of orders in the "COMPLETE" status should be 1
