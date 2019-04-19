Feature: List Management

Scenario: Adding an item to a list, logging out, and checking that it's still there when we return
  Given I am logged in and on the home page
  When I enter "burger" in the "#food" text box
  And I enter "2" in the "#distance" text box
  And I enter "2" in the "#limit" text box
  And I click the "#feedME" button
  Then I will transition to the Results Page
  When I click the "#DXFhzx94myitMxmBhsdz8A" button
  And I click the "#menuBtn" button
  And I click the "#listDropdown" button
  And I click the "#Favorites" button

Scenario: Adding an item to a list
  Given I am logged in and on the home page
  When I enter "burger" in the "#food" text box
  And I enter "2" in the "#distance" text box
  And I enter "2" in the "#limit" text box
  And I click the "#feedME" button
  Then I will transition to the Results Page
  When I click the "#DXFhzx94myitMxmBhsdz8A" button
  And I click the "#menuBtn" button
  And I click the "#listDropdown" button
  And I click the "#Favorites" button

Scenario: Moving an item to the top of a list
  Given I am logged in and on the home page
  When I enter "burger" in the "#food" text box
  And I enter "2" in the "#distance" text box
  And I enter "2" in the "#limit" text box
  And I click the "#feedME" button
  Then I will transition to the Results Page
  When I click the "#DXFhzx94myitMxmBhsdz8A" button
  And I click the "#menuBtn" button
  And I click the "#listDropdown" button
  And I click the "#Favorites" button
 
