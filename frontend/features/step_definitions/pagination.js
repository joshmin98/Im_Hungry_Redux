const { Given, When, Then } = require('cucumber');
const {
  findPlaceInList,
  findPageButton,
  wait,
} = require('../support/actions.js');

When(
  'I click the {string} button on the pagination selector',
  { timeout: 8000 },
  async function(string) {
    await findPageButton(string);
    return 'success';
  },
);

Then(
  'I will not see the {string} button on the pagination selector',
  { timeout: 8000 },
  async function(string) {
    return 'success';
  },
);

Then(
  'I will transition to the Results Page and wait',
  { timeout: 16000 },
  async function() {
    await wait(15);
    return 'success';
  },
);
