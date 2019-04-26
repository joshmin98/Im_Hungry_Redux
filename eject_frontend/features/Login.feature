Feature: Login

Scenario: Logging in with valid username
  Given I am on the login page
  When I enter "test@test.com" in the "#email" text box
  And I enter "testing123" in the "#password" text box
  And I click the "#login" button
  Then I will be logged in and on the home page
  
Scenario: Logging in with a badly formatted or missing email
  Given I am on the login page
  When I enter "a" in the "#email" text box
  And I enter "testing123" in the "#password" text box
  And I click the "#login" button
  Then there will be a "Badly Formatted Email" alert

Scenario: Logging in with an incorrect or missing password
  Given I am on the login page
  When I enter "test@test.com" in the "#email" text box
  And I enter "a" in the "#password" text box
  And I click the "#login" button
  Then there will be a "Incorrect or Missing Password" alert
