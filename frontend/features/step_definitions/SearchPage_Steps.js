const { Given, When, Then } = require('cucumber');
const {
  visitPage,
  fillInTextField,
  clickButton,
  checkPage,
  checkTitle,
  checkList,
  checkDropdown,
  checkButton,
  checkTwoColumn,
  countChildElement,
  CheckRestaurantContent,
  checkRecipeContent,
  clickDropdown,
  clickRestaurant,
  checkRestaurantPage,
  clickRecipe,
  checkRecipePage,
  clickSearchTerm,
  clickPrevSearchTerm,
  checkHomePage,
  checkInputForm,
  clickOnButtonWithText,
  waitTime,
  clickPagination,
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

Given('I clicked the {string} button', { timeout: 9 * 1000 }, async function(
  string,
) {
  await clickButton('#feedME');
  return 'success';
});

Given('I am on the Results Page', async function() {
  return 'success';
});

Then(
  'the page will have the title: Results for {string}',
  {
    timeout: 9 * 1000,
  },
  async function(string) {
    await checkTitle('title', string);
    return 'success';
  },
);

Then(
  'the page will have a collage of photos related to the search query',
  async function() {
    await checkList();
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

Then('the page will have a button labeled {string}', async function(string) {
  await checkButton(string, string);
  return 'success';
});

Then(
  'the page will have two columns of results titled: Restaurants, Recipes',
  async function() {
    await checkTwoColumn();
    return 'success';
  },
);

Then(
  'the page will have {int} restaurant and recipe results in each column',
  async function(int) {
    await countChildElement(int);
    return 'success';
  },
);

Then(
  'each restaurant item on the page will have an address, name, and minutes',
  async function() {
    await CheckRestaurantContent();
    return 'success';
  },
);

Then(
  'each recipe item on the page will have a name, prep time, and cook time',
  async function() {
    await checkRecipeContent();
    return 'success';
  },
);

When('I select {string} from the dropdown', async function(string) {
  await clickButton('#menuBtn');
  await clickDropdown();
  return 'success';
});

Then('I will be on the {string} list page', async function(string) {
  return 'success';
});

Then('I will be on the Search Page', function() {
  return 'success';
});

When('I click on a restaurant with name {string}', async function(string) {
  await clickRestaurant(string);
  return 'success';
});

Then('I will be on the Restaurant Page of restaurant {string}', async function(
  string,
) {
  await checkRestaurantPage(string);
  return 'success';
});

When('I click on a recipe with name {string}', async function(string) {
  await clickRecipe(string);
  return 'success';
});

Then('I will be on the Recipe Page of recipe {string}', async function(string) {
  await checkRecipePage(string);
  return 'success';
});

Then(
  'I will be logged out of the application if I am logged in already',
  function() {
    return 'success';
  },
);

When('I click on the dropdown labeled {string}', async function(string) {
  await clickButton('#menuBtn');
  await clickSearchTerm();
  return 'success';
});

When('I click on {string}', async function(string) {
  await clickPrevSearchTerm();
  return 'success';
});

Then('I am on the homepage', async function() {
  await checkHomePage();
  return 'success';
});

Then('{string} is appear in the search bar', async function(string) {
  await checkInputForm(string);
  return 'success';
});

When('I click on {string} button', async function(string) {
  await clickPagination();
  return 'success';
});

Then('I see all items on second page of result', async function() {
  //await waitTime();
  await countChildElement(5);
  return 'success';
});
