@PetStore
Feature: Automation Assignment

  @Type=UI @Data=AssignmentTestData.json
  Scenario: Dynamic Table
    When User clicks on "Dynamic Table" link
    And User reads the Dynamic Table Data
    Then Verify Dynamic Table Data
