Feature: Search Page

Scenario: Searching for food
  Given that I am on the homepage
  When I enter "food" into the text box labeled: Enter Food
  And I enter a number greater than or equal to 1 into the text box with a default value of 5
  And I click the button with the label: Feed Me!
  Then I will transition to the Results Page

Scenario: Logging in
  Given that I am on the homepage
  When I click the "Log In" button
  And I enter "thisisatest@gmail.com" in the "email" textbox
  And I enter "testing123" in the "password" textbox
  Then I will be logged in to the application
  
Scenario: Signing up
  Given that I am on the homepage
  When I click the "Sign Up" button
  And I enter "a username" in the "email" textbox
  And I enter "a password" in the "password" textbox
  And I enter "the same password" in the "password again" textbox
  Then I will be logged in to the application
  
Scenario: Logging out
  Given that I am on the homepage
  When I click the "Log In" button
  And I enter "thisisatest@gmail.com" in the "email" textbox
  And I enter "testing123" in the "password" textbox
  Then I will be logged in to the application
  And when I click the "Log Out" button
  Then I will be loggout out of the application
 
