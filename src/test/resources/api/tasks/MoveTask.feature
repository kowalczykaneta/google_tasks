Feature: Move a task

  Scenario: User calls web service to move a task
    Given a task list with three tasks
      | Task one   |
      | Task two   |
      | Task three |
    When a user moves "Task one" to the last position
    Then user sees tasks in order
      | Task two   |
      | Task three |
      | Task one   |