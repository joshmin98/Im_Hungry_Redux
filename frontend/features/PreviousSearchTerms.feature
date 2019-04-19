Feature: Previous Search Terms

Scenario: Searching for a term and checking that it is in the history
  Given I am logged in and on the home page
  When I enter "burger" in the "#food" text box
  And I enter "2" in the "#distance" text box
  And I enter "2" in the "#limit" text box
  And I click the "#feedME" button
  Then I will transition to the Results Page
  When I click the "#menuBtn" button
  And I click the "#select-terms" button

Scenario: Searching via a previously entered term
  Given I am logged in and on the home page
  When I enter "burger" in the "#food" text box
  And I enter "2" in the "#distance" text box
  And I enter "2" in the "#limit" text box
  And I click the "#feedME" button
  Then I will transition to the Results Page
  When I click the "#menuBtn" button
  And I click the "#select-terms" button
  And I click the "#burger" button
  And I enter "2" in the "#distance" text box
  And I enter "2" in the "#limit" text box
