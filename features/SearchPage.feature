Feature: Results Page

Background:
  Given I am on the Search Page
  And I have entered a search query "burger"
  And 2 for the number of results to be displayed
  And I clicked the "Feed Me!" button

Scenario: Viewing the Results Page
  Given I am on the Results Page
  Then the page will have the title: Results for "burger"
  And the page will have a collage of photos related to the search query
  And the page will have a dropdown box with the predefined lists
  And the page will have a button labeled "Manage Lists"
  And the page will have a button labeled "Return to Search"
  And the page will have two columns of results titled: Restaurants, Recipes
  And the page will have 2 restaurant and recipe results in each column
  And each restaurant item on the page will have an address, name, and minutes
  And each recipe item on the page will have a name, prep time, and cook time

Scenario: Navigating to a list by using the dropdown
  Given I am on the Results Page
  When I select "Favorites" from the dropdown
  And I click the "Manage List" button
  Then I will be on the "Favorites" list page

Scenario: Navigating back to the Search Page by using the "Return to Search" button
  Given I am on the Results Page
  When I click the "Return to Search" button
  Then I will be on the Search Page

Scenario: Navigating to a Restaurant Page by clicking on a restaurant item
  Given I am on the Results Page
  When I click on a restaurant with name "x"
  Then I will be on the Restaurant Page of restaurant "x"

Scenario: Navigating to a Recipe Page by clicking on a recipe item
  Given I am on the Results Page
  When I click on a recipe with name "x"
  Then I will be on the Recipe Page of recipe "x"

Scenario: Trying to navigate to the list management page with nothing selected
  Given I am on the Results Page
  When I click the "Manage List" button
  Then I will transition to the Results Page