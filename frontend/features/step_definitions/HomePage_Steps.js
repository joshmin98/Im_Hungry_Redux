const { Given, When, Then } = require('cucumber');
const {
  visitPage,
  fillInTextField,
  clickButton,
  checkPage,
  clickOnButtonWithText,
} = require('../support/actions');

Given(
  'that I am on the homepage',
  {
    setTimeout: 5000,
  },
  async function() {
    await visitPage('');
    return 'success';
  },
);

When('I enter {string} into the text box labeled: Enter Food', async function(
  string,
) {
  await fillInTextField('#food', string);
  return 'success';
});

When(
  'I enter a number greater than or equal to {string} into the text box labeled: Distance',
  async function(string) {
    await fillInTextField('#distance', string);
    return 'success';
  },
);

When(
  'I enter a number greater than or euqal to {string} into the text box labeled: Limit',
  async function(string) {
    await fillInTextField('#limit', string);
    return 'success';
  },
);

When('I click the button with the label: Feed Me!', async function() {
  await clickButton('#feedME');
  return 'success';
});

Then('I will transition to the Results Page', function() {
  checkPage('#searchPage');
  return 'success';
});

When('I enter {string} in the {string} textbox', async function(
  string,
  string2,
) {
  await fillInTextField(string2, string);
  return 'success';
});

When('I click the {string} button', async function(string) {
  await clickOnButtonWithText(string);
  return 'success';
});

Then('I will be logged in to the application', function() {
  return 'success';
});

Then('I will be logged out of the application', function() {
  return 'success';
});
