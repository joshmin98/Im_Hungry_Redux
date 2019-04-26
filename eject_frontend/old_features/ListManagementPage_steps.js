const { Given, When, Then } = require('cucumber');
const {
  visitListManagementPage,
  dragAndDropOneBelowOther,
  checkFirstBelowSecond,
} = require('../support/actions');

Given('I am on the {string} list page', async function(string) {
  await visitListManagementPage(string);
  return 'success';
});

When(
  'I click and drag the first item in the list below the second',
  async function() {
    await dragAndDropOneBelowOther('#item-1-down', '#item-3');
    return 'success';
  },
);

Then(
  'first item in the list will be below the second item in the list',
  async function() {
    checkFirstBelowSecond(1);
    return `success`;
  },
);
