Feature: Recipe Page Feature

Background:
    Given I am on the Home Page
    And I have entered a search query "burger"
    And 5 for the number of results to be displayed
    And I clicked the "Feed Me!" button
    And I am on the Results Page
    And I clicked the "Red Lentil Soup with Chicken and Turnips" link

Scenario: 
    Given I am on Recipe page
    Then I see Title
    And I see picture
    And I see prep time
    And I see cook time
    And I see ingredients
    And I see instructions

Scenario: Navigating to the Print Page
    Given I am on Recipe page
    When I click the "Printable View" button
    Then I will be on Print page

Scenario: Navigating to Result Page
    Given I am on Recipe page
    When I click the "Back to Result" button
    Then I will transition to the Results Page
    