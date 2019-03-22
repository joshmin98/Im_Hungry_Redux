Feature: List Management Page

Background:
  Given I am on the Search Page
  And I have entered a search query "burger"
  And 3 for the number of results to be displayed
  And I clicked the "Feed Me!" button

Scenario: Adding a restaurant to the "Favorites" list
  Given I am on the Results Page
  When I click on a restaurant with name "x"
  And I select "Favorites" from the dropdown
  And I click the "Add to List" button
  And I select "Favorites" from the dropdown
  And I click the "Back to Result" button
  And I click the "Manage List" button
  Then I will be on the "Favorites" List Management Page
  And "x" will be in the list

Scenario: Adding a recipe to the "Favorites" list
  Given I am on the Results Page
  When I click on a recipe with name "x"
  And I select "Favorites" from the dropdown
  And I click the "Add to List" button
  And I select "Favorites" from the dropdown
  And I click the "Back to Result" button
  And I click the "Manage List" button
  Then I will be on the "Favorites" List Management Page
  And "x" will be in the list

Scenario: Navigating to the Search Page using the return to search button
  Given I am on the "Favorites" List Management Page
  When I click the "Return to Search" button
  Then I will be on the Search Page

Scenario: Navigting to another list from within the List Management Page
  Given I am on the "Favorites" List Management Page
  When I select "To Explore" from the dropdown
  And I click the "Manage List" button
  Then I will be on the "To Explore" List Management Page