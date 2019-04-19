Feature: Searching

Scenario: Searching with all appropriate fields
  Given I am logged in and on the home page
  When I enter "burger" in the "#food" text box
  And I enter "2" in the "#distance" text box
  And I enter "2" in the "#limit" text box
  And I click the "#feedME" button
  Then I will transition to the Results Page
  And I will see the item with the ID "DXFhzx94myitMxmBhsdz8A" as the "first" item in "restaurants"
  And I will see the item with the ID "cgMqbKO7UGLfijRqfg9kjw" as the "second" item in "restaurants"
  And I will see the item with the ID "864633" as the "first" item in "recipes"
  And I will see the item with the ID "219957" as the "second" item in "recipes"

Scenario: Trying to search with missing distance
  Given I am logged in and on the home page
  When I enter "burger" in the "#food" text box
  And I enter "2" in the "#limit" text box
  And I click the "#feedME" button
  Then there will be a "Missing Distance" alert

Scenario: Trying to search with missing limit
  Given I am logged in and on the home page
  When I enter "burger" in the "#food" text box
  And I enter "2" in the "#distance" text box
  And I click the "#feedME" button
  Then there will be a "Missing Limit" alert

Scenario: Trying to search with missing term
  Given I am logged in and on the home page
  And I enter "2" in the "#distance" text box
  And I enter "2" in the "#limit" text box
  And I click the "#feedME" button
  Then there will be a "Missing Search Term" alert
