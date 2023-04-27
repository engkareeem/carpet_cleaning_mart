Feature: Login

  Scenario: Login as a worker
    Given a worker with email "worker@test.com" and password "password"
    When they log in
    Then they should be logged in successfully as a worker with a role

  Scenario: Login as a customer
    Given a customer with email "customer@test.com" and password "password"
    When they log in
    Then they should be logged in successfully as a customer without a role

  Scenario: Invalid email
    Given an invalid email "invalid@test.com" and password "password"
    When they log in
    Then they should not be logged in

  Scenario: Invalid password
    Given a worker with email "worker@test.com" and an invalid password "invalid_password"
    When they log in
    Then they should not be logged in
