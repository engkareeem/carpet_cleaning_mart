Feature: User authentication feature

  Scenario: Customer sign up and login

    Given the user is not signed up
    When the user sign up with email "customer@gmail.com", name "Ahmad Moneer", address "Nablus, Rafidya", phone "059-872-8291", and password "123123"
    Then the user should be signed up successfully
    And the user should have email "customer@gmail.com", name "Ahmad Moneer", address "Nablus, Rafidya", and phone "059-872-8291"
    And the user should not be a worker

  Scenario: Customer log in and log out

    Given the user is signed up with email "customer@gmail.com" and password "123123"
    When the user log in with email "customer@gmail.com" and password "123123"
    Then the user should be logged in successfully
    And the user should not be a worker
    And the user should not have a role
    And the user should be able to log out

  Scenario: Check if the user is logged in

    Given the user is signed up with email "customer@gmail.com" and password "123123"
    When the user log in with email "customer@gmail.com" and password "123123"
    Then the user should be logged in successfully
    And the user should be able to check if they are logged in
    And the user should be able to log out
    And the user should not be logged in after they log out
