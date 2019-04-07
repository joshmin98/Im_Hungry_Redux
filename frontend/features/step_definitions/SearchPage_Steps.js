const { Given, When, Then } = require('cucumber');
const {
  visitPage,
  fillInTextField,
  clickButton,
  checkPage,
  checkTitle,
  checkList,
  checkDropdown,
} = require('../support/actions');

Given('I have entered a search query {string}', async function(string) {
  await fillInTextField('#food', string);
  return 'success';
});

Given('{string} for distance', async function(string) {
  await fillInTextField('#distance', string);
  return 'success';
});

Given('{string} for the number of results to be displayed', async function(
  string,
) {
  await fillInTextField('#limit', string);
  return 'success';
});

Given('I clicked the {string} button', async function(string) {
  await clickButton('#feedME');
  return 'success';
});

Given('I am on the Results Page', async function() {
  await visitPage('search');
  return 'success';
});

Then(
  'the page will have the title: Results for {string}',
  {
    timeout: 6 * 1000,
  },
  async function(string) {
    await checkTitle('title', string);
    return 'success';
  },
);

Then(
  'the page will have a collage of photos related to the search query',
  async function() {
    await checkList('#images');
    return 'success';
  },
);

Then(
  'the page will have a dropdown box with the predefined lists',
  async function() {
    await clickButton('#menuBtn');
    await checkDropdown();
    return 'success';
  },
);

Then('the page will have a button labeled {string}', function(string) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

Then(
  'the page will have two columns of results titled: Restaurants, Recipes',
  function() {
    // Write code here that turns the phrase above into concrete actions
    return 'pending';
  },
);

Then(
  'the page will have {int} restaurant and recipe results in each column',
  function(int) {
    // Write code here that turns the phrase above into concrete actions
    return 'pending';
  },
);

Then(
  'each restaurant item on the page will have an address, name, and minutes',
  function() {
    // Write code here that turns the phrase above into concrete actions
    return 'pending';
  },
);

Then(
  'each recipe item on the page will have a name, prep time, and cook time',
  function() {
    // Write code here that turns the phrase above into concrete actions
    return 'pending';
  },
);

When('I select {string} from the dropdown', function(string) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

When('I click the {string} button', function(string) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

Then('I will be on the {string} list page', function(string) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

Then('I will be on the Search Page', function() {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

When('I click on a restaurant with name {string}', function(string) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

Then('I will be on the Restaurant Page of restaurant {string}', function(
  string,
) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

When('I click on a recipe with name {string}', function(string) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

Then('I will be on the Recipe Page of recipe {string}', function(string) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

When('I click on the {string} button', function(string) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

Then(
  'I will be logged out of the application if I am logged in already',
  function() {
    // Write code here that turns the phrase above into concrete actions
    return 'pending';
  },
);
