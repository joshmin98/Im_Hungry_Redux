Feature: Grocery List
  
# CHECKBOX is a span with id: checkbox-ID
# TRASH is a span with the id: trash

Scenario: Adding recipe ingredients to grocery list
  Given I am logged in and on the home page
  When I enter "burger" in the "#food" text box
  And I enter "2" in the "#distance" text box
  And I enter "2" in the "#limit" text box
  And I click the "#feedME" button
  Then I will transition to the Results Page
  When I click the "#grocery-0" button
  And I click the "#menuBtn" button
  And I click the "#Grocery-Lists" button
  Then I will transition to the Grocery Lists Page
  And I will see the item with the ID "#greens" as the "first" item in "Grocery List"
  And I will see the item with the ID "#cucumber" as the "second" item in "Grocery List"
  And I will see the item with the ID "#hamburger-bun" as the "third" item in "Grocery List"
  And I will see the item with the ID "#garlic-powder" as the "fourth" item in "Grocery List"
 
Scenario: Adding recipe ingredients to grocery list, logging out, and checking that they're there when we log in
  Given I am logged in and on the home page
  When I enter "burger" in the "#food" text box
  And I enter "2" in the "#distance" text box
  And I enter "2" in the "#limit" text box
  And I click the "#feedME" button
  Then I will transition to the Results Page
  When I click the "#grocery-0" button
  And I click the "#menuBtn" button
  And I click the "#Grocery-Lists" button
  Then I will transition to the Grocery Lists Page
  And I will see the item with the ID "#greens" as the "first" item in "Grocery List"
  And I will see the item with the ID "#cucumber" as the "second" item in "Grocery List"
  And I will see the item with the ID "#hamburger-bun" as the "third" item in "Grocery List"
  And I will see the item with the ID "#garlic-powder" as the "fourth" item in "Grocery List"
  When I click the "#menuBtn" button
  And I click the "#logout" button
  Then I login and am on the home page
  When I enter "burger" in the "#food" text box
  And I enter "2" in the "#distance" text box
  And I enter "2" in the "#limit" text box
  And I click the "#feedME" button
  Then I will transition to the Results Page
  When I click the "#menuBtn" button
  And I click the "#Grocery-Lists" button
  Then I will transition to the Grocery Lists Page
  And I will see the item with the ID "#greens" as the "first" item in "Grocery List"
  And I will see the item with the ID "#cucumber" as the "second" item in "Grocery List"
  And I will see the item with the ID "#hamburger-bun" as the "third" item in "Grocery List"
  And I will see the item with the ID "#garlic-powder" as the "fourth" item in "Grocery List"

Scenario: Removing ingredients from grocery list
  Given I am logged in and on the home page
  When I enter "burger" in the "#food" text box
  And I enter "2" in the "#distance" text box
  And I enter "2" in the "#limit" text box
  And I click the "#feedME" button
  Then I will transition to the Results Page
  When I click the "#grocery-0" button
  And I click the "#menuBtn" button
  And I click the "#Grocery-Lists" button
  Then I will transition to the Grocery Lists Page
  And I will see the item with the ID "#greens" as the "first" item in "Grocery List"
  And I will see the item with the ID "#cucumber" as the "second" item in "Grocery List"
  And I will see the item with the ID "#hamburger-bun" as the "third" item in "Grocery List"
  And I will see the item with the ID "#garlic-powder" as the "fourth" item in "Grocery List"
  When I click the "#checkbox-0" button
  And I click the "#trash" button
  Then I will not see the item with the ID "" as the "" item in ""
  
