const { Given, When, Then } = require('cucumber');
const {
  visitPage,
  clickButtonWithID,
  login,
  enterToTextbox,
  wait,
} = require('../support/actions.js');

Given('I am on the login page', async function() {
  await visitPage('');
  return 'success';
});

Given('I am logged in and on the home page', async function() {
  await visitPage('');
  await login('test@test.com', 'testing123');
  return 'success';
});

When('I enter {string} in the {string} text box', async function(
  string,
  string2,
) {
  await enterToTextbox(string2, string);
  return 'success';
});

When('I click the {string} button', { timeout: 1200 }, async function(string) {
  await clickButtonWithID(string);
  return 'success';
});

Then(
  'I will transition to the Results Page',
  { timeout: 9000 },
  async function() {
    await wait(8);
    return 'success';
  },
);

Then('there will be a {string} alert', async function(string) {
  return 'success';
});
