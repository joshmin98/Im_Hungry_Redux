const { Given, When, Then } = require('cucumber');
const {
  visitPage,
  fillInTextField,
  clickButton,
  checkPage,
} = require('../support/actions');

Given('that I am on the homepage', async function() {
  await visitPage('');
  return 'success';
});

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

When('I enter {string} in the {string} textbox', function(string, string2) {
  fillInTextField(string2, string);
  return 'pending';
});

Then('when I click the {string} button', async function(string) {
  await clickOnButtonWithText(string);
  return 'pending';
});

Then(
  'I will be logged in to the application',
  {
    setTimeOut: 500,
  },
  async function() {
    await checkLoggedIn();
    return 'pending';
  },
);

Then(
  'I will be logged out of the application',
  {
    setTimeOut: 500,
  },
  async function() {
    await checkedLoggedOut();
    return 'pending';
  },
);

Given('that I am on the homepage', function () {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

When('I enter {string} into the text box labeled: Enter Food', function (string) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

When('I enter {string} into the text box labeled: Distance', function (string) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

When('I enter {string} into the text box labeled: Limit', function (string) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

When('I click the button with the label: Feed Me!', function () {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

Then('I will see an error message', function () {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

Given('that I am on the homepage', function () {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

When('I enter {string} into the text box labeled: Enter Food', function (string) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

When('I enter {string} into the text box labeled: Distance', function (string) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

When('I enter {string} into the text box labeled: Limit', function (string) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

When('I click the button with the label: Feed Me!', function () {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

Then('I will see an error message', function () {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

Given('that I am on the homepage', function () {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

When('I enter {string} into the text box labeled: Enter Food', function (string) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

When('I enter {string} into the text box labeled: Distance', function (string) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

When('I enter {string} into the text box labeled: Limit', function (string) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

When('I click the button with the label: Feed Me!', function () {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

Then('I will see an error message', function () {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

