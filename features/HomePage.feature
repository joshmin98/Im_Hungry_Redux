Feature: Search Page

Scenario:
  Given that I am on the homepage
  When I enter "food" into the text box labeled: Enter Food
  And I enter a number greater than or equal to 1 into the text box with a default value of 5
  And I click the button with the label: Feed Me!
  Then I will transition to the Results Page
