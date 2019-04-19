Feature: Reordering the list management page

Scenario: Reorder the "Favorites" list
	  Given I am on the "Favorites" list page
	  When I click and drag the first item in the list below the second
	  Then first item in the list will be below the second item in the list