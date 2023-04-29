Feature: Worker Functionality

  Scenario: Add a new worker
    Given a worker with name "Ahmad Rami", phone "059-555-5678", address "Nablus, Ragidya", email "worker@gmail.com", and type "EMPLOYEE"
    When the worker is added to the database with a password of "123123"
    Then the worker should be successfully added to the database
    And the worker's information should match the added information

  Scenario: Update an existing worker
    Given a worker with name "Ahmad Rami", phone "059-555-5678", address "Nablus, Ragidya", email "worker@gmail.com", and type "EMPLOYEE" exists in the database
    When the worker's name is updated to "Yaser Mostafa", phone is updated to "059-555-123", address is updated to "Nablus, BeitEihba", and type is updated to "EMPLOYEE"
    Then the worker's information should be successfully updated in the database

  Scenario: Delete an existing worker
    Given a worker with name "Ahmad Rami", phone "059-555-5678", address "Nablus, Ragidya", email "worker@gmail.com", and type "EMPLOYEE" exists in the database
    When the worker is deleted from the database
    Then the worker should be successfully deleted from the database
