Feature: Signing Up

Scenario: Signing up with valid credentials
  Given I am on the login page
  And I click the "#switch" button
  When I enter "newuser@test.com" in the "#email" text box
  And I enter "newuserpassword" in the "#password" text box
  And I enter "newuserpassword" in the "#password-again" text box
  And I click the "#signup" button
  Then I will be logged in and on the home page

Scenario: Signing up with an existing user's credentials
  Given I am on the login page
  And I click the "#switch" button
  When I enter "newuser@test.com" in the "#email" text box
  And I enter "newuserpassword" in the "#password" text box
  And I enter "newuserpassword" in the "#password-again" text box
  And I click the "#signup" button
  Then there will be a "Existing User" alert

Scenario: Signing up with mismatched passwords
  Given I am on the login page
  And I click the "#switch" button
  When I enter "newuser@test.com" in the "#email" text box
  And I enter "newuserpassword" in the "#password" text box
  And I enter "a" in the "#password-again" text box
  And I click the "#signup" button
  Then there will be a "Mismatched Passwords" alert

Scenario: Signing up with invalid or missing email
  Given I am on the login page
  And I click the "#switch" button
  When I enter "newom" in the "#email" text box
  And I enter "newuserpassword" in the "#password" text box
  And I enter "a" in the "#password-again" text box
  And I click the "#signup" button
  Then there will be a "Invalid or missing email" alert

Scenario: Signing up with missing password
  Given I am on the login page
  And I click the "#switch" button
  When I enter "newom" in the "#email" text box
  And I click the "#signup" button
  Then there will be a "Missing password" alert

Scenario: Signing up with missing confirmation password
  Given I am on the login page
  And I click the "#switch" button
  When I enter "newom" in the "#email" text box
  And I enter "newuserpassword" in the "#password" text box
  And I click the "#signup" button
  Then there will be a "Mismatched Passwords" alert
