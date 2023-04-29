Feature: Distribute waiting orders to free workers

  Scenario: Customer places a new order and there is a free worker
    Given a customer wants to make a new order
    When they place the order
    Then the order status should be "IN_TREATMENT"

  Scenario: Customer places a new order and it is waiting because there are no free workers
    Given a customer wants to make a new order
    When they place the order
    And there are no free workers available
    Then the order status should be "WAITING"
