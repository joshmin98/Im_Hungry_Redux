Feature: Restaurant Page Feature

Background:
    Given I am on the Home Page
    And I have entered a search query "burger"
    And 5 for the number of results to be displayed
    And I clicked the "Feed Me!" button
    And I am on the Results Page
    And I clicked the "Northern Cafe" link

Scenario: 
    Given I am on Restaurant page
    Then I see Title
    And I see name
    And I see phone number
    And I see website

Scenario: Navigating to the Google Map
    Given I am on Restaurant page
    When I click the "2904 S Figueroa St Los Angeles, CA 90007" button
    Then I will be on Google Map with direction filled out

Scenario: Navigating to the Restaurant Page
    Given I am on Restaurant page
    When I click the "https://www.yelp.com/biz/northern-cafe-los-angeles-6?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw" button
    Then I will be on Yelp page