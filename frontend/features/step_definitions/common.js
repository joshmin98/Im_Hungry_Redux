const { Given, When, Then } = require('cucumber');
const {
  visitPage,
  clickButton,
  enterToTextbox,
} = require('../support/actions.js');

Given('I am logged in and on the home page', async function() {
  visitPage('');
  return 'pending';
});

When('I enter {string} in the {string} text box', async function(
  string,
  string2,
) {
  return 'pending';
});

When('I click the {string} button', async function(string) {
  return 'pending';
});

Then(
  'I will transition to the Results Page',
  { timeout: 8000 },
  async function() {
    return 'pending';
  },
);
