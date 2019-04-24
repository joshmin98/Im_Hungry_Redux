Feature: Pagination
  
Scenario: Navigating to page 2 by clicking the page 2 button
  Given I am logged in and on the home page
  When I enter "burger" in the "#food" text box
  And I enter "10" in the "#distance" text box
  And I enter "10" in the "#limit" text box
  And I click the "#feedME" button
  Then I will transition to the Results Page
  When I click the "2" button on the pagination selector
  Then I will see the item with the ID "DXFhzx94myitMxmBhsdz8A" as the "first" item in "restaurants"
  And I will see the item with the ID "864633" as the "first" item in "recipes"
  
Scenario: Navigating to page 2 and back to page 1 by using previous and next
  Given I am logged in and on the home page
  When I enter "burger" in the "#food" text box
  And I enter "10" in the "#distance" text box
  And I enter "10" in the "#limit" text box
  And I click the "#feedME" button
  Then I will transition to the Results Page
  And I will see the item with the ID "DXFhzx94myitMxmBhsdz8A" as the "first" item in "restaurants"
  And I will see the item with the ID "864633" as the "first" item in "recipes"
  When I click the "next" button on the pagination selector
  Then I will see the item with the ID "DXFhzx94myitMxmBhsdz8A" as the "first" item in "restaurants"
  And I will see the item with the ID "864633" as the "first" item in "recipes"
  When I click the "1" button on the pagination selector
  And I will see the item with the ID "DXFhzx94myitMxmBhsdz8A" as the "first" item in "restaurants"
  And I will see the item with the ID "864633" as the "first" item in "recipes"

Scenario: Navigating to page 11 (demonstrating the sliding window)
  Given I am logged in and on the home page
  When I enter "burger" in the "#food" text box
  And I enter "10" in the "#distance" text box
  And I enter "55" in the "#limit" text box
  And I click the "#feedME" button
  Then I will transition to the Results Page
  When I click the "11" button on the pagination selector
  Then I will see the item with the ID "DXFhzx94myitMxmBhsdz8A" as the "first" item in "restaurants"
  And I will see the item with the ID "864633" as the "first" item in "recipes"
  And I will not see the "1" button on the pagination selector
