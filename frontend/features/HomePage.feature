Feature: Search Page

Scenario: Searching for food
  Given that I am on the homepage
  And I have entered a search query "burger"
  And "10" for distance
  And "10" for the number of results to be displayed
  And I clicked the "Feed Me!" button
  Then I will transition to the Results Page

Scenario: Logging in
  Given that I am on the homepage
  When I click the "Log In" button
  And I enter "testing123@test.com" in the "email" textbox
  And I enter "testing123" in the "password" textbox
  Then I will be logged in to the application
  
Scenario: Signing up
  Given that I am on the homepage
  When I click the "Sign Up" button
  And I enter "a username" in the "email" textbox
  And I enter "a password" in the "password" textbox
  And I enter "the same password" in the "password-again" textbox
  Then I will be logged in to the application
  
Scenario: Logging out
  Given that I am on the homepage
  When I click the "Log In" button
  And I enter "testing123@test.com" in the "email" textbox
  And I enter "testing123" in the "password" textbox
  Then I will be logged in to the application
  And when I click the "Log Out" button
  Then I will be logged out of the application

Scenario: Searching for food When radius is zero
  Given that I am on the homepage
  And I have entered a search query "burger"
  And "0" for distance
  And "10" for the number of results to be displayed
  And I clicked the "Feed Me!" button
  Then I will see an error message

Scenario: Searching for food When limit is zero
  Given that I am on the homepage
  And I have entered a search query "burger"
  And "10" for distance
  And "0" for the number of results to be displayed
  And I clicked the "Feed Me!" button
  Then I will see an error message

Scenario: Searching for invalid food
  Given that I am on the homepage
  And I have entered a search query "asdjfg"
  And "10" for distance
  And "0" for the number of results to be displayed
  And I clicked the "Feed Me!" button
  Then I will see an error message

 
