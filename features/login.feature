Feature: user login
  Description: the user logs in and out to the system
  Actor: user


  Scenario:
    Given the user is not logged in
    And the password is equal to <username> and the username is equal to <password>
      | username | password |
      | "aydi"   | "123"    |

    Then the user should log in to the system
    And the user type is determined
    And A page opens which is related to the user type
