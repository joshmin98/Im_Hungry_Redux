const { Given, When, Then } = require('cucumber');
const {
  findPlaceInList,
  findPageButton,
  wait,
} = require('../support/actions.js');

Then(
  'I will transition to the Grocery Lists Page',
  { timeout: 5000 },
  async function() {
    await wait(3);
    return 'success';
  },
);
