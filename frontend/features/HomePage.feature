Feature: Search Page

Scenario:
  Given that I am on the homepage
  When I enter "burger" into the text box labeled: Enter Food
  And I enter a number greater than or equal to "1000" into the text box labeled: Distance
  And I enter a number greater than or euqal to "5" into the text box labeled: Limit
  And I click the button with the label: Feed Me!
  Then I will transition to the Results Page