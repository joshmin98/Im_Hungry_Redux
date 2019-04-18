Feature: Searching

Scenario: Searching for burger with all appropriate fields
  Given I am logged in and on the home page
  When I enter "burger" in the "Enter Food" text box
  And I enter "10" in the "Distance" text box
  And I enter "10" in the "Limit" text box
  And I click the "Feed Me!" button
  Then I will transition to the Results Page
