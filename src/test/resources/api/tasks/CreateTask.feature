Feature: Create a task

  Scenario: User calls web service to create a task
    Given an empty task list
    When a user creates a task "New task"
    Then user sees tasks on the list
      | New task |

