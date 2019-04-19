const { Given, When, Then } = require('cucumber');
const { findPlaceInList } = require('../support/actions.js');

Then(
  'I will see the item with the ID {string} as the {string} item in {string}',
  async function(string, string2, string3) {
    await findPlaceInList(string, string2, string3);
    return 'success';
  },
);
