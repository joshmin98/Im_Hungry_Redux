const assert = require('assert');
const scope = require('./scope');

let headless = false;
let slowMo = 5;

const domain = 'http://localhost:';
const port = '3000';

const visitPage = async pageName => {
  if (!scope.browser) {
    scope.browser = await scope.driver.launch({ headless, slowMo });
  }

  scope.context.currentPage = await scope.browser.newPage();
  const url = domain + port + `/${pageName}`;
  await scope.context.currentPage.goto(url);
};

const fillInTextField = async (id, value) => {
  await scope.context.currentPage.type(id, value);
};

const clickButton = async id => {
  await scope.context.currentPage.click(id);
  if (id === '#feedME') {
    await scope.context.currentPage.waitFor(8000);
  }
};

const checkPage = async id => {
  let data = await scope.context.currentPage.evaluate(() => {
    return document.getElementById(id).innerHTML;
  });
  assert(data === '', 'Page Not Found');
};

const checkTitle = async (e, val) => {
  let data = await scope.context.currentPage.evaluate(() => {
    return document.getElementById('header').childNodes.length;
  });
  assert(data !== val, 'incorrect value');
};

const checkList = async () => {
  let data = await scope.context.currentPage.evaluate(() => {
    return document.getElementById('images').childNodes.length;
  });
  assert(parseInt(data) !== 0, 'List is Empty');
};

const checkDropdown = async () => {
  await scope.context.currentPage.click('#select-list');
  await scope.context.currentPage.waitFor(1000);
  await scope.context.currentPage.click('#Favorites');
  let data = await scope.context.currentPage.evaluate(() => {
    return document.getElementById('listDropdown').value;
  });
  assert(data === 'Favorites', 'List not exist');
};

const clickDropdown = async () => {
  await scope.context.currentPage.click('#select-list');
  await scope.context.currentPage.waitFor(1000);
  await scope.context.currentPage.click('#Favorites');
};

const checkButton = async (id, buttonName) => {
  // let data = await scope.context.currentPage.evaluate(() => {
  //   return document.getElementById(id);
  // })
  const data = await id;
  const text = await scope.context.currentPage.evaluate(
    data => document.getElementById(data),
    data,
  );
  assert(text !== buttonName, 'Incorrect Value');
};

const visitListManagementPage = async pageName => {
  if (!scope.browser) {
    scope.browser = await scope.driver.launch({ headless, slowMo });
  }
  scope.context.currentPage = await scope.browser.newPage();
  const url = domain + port + `/lists/${pageName}`;
  await scope.context.currentPage.goto(url);
};

const dragAndDropOneBelowOther = async (item1ID, item2ID) => {
  await scope.context.currentPage.click(item1ID);
};

const checkFirstBelowSecond = async item1Name => {
  let data = await scope.context.currentPage.evaluate(() => {
    return document.getElementById('listPlace-1').textContent;
  });
  assert(parseInt(data) == 1, 'Object was not moved!');
};

const clickOnButtonWithText = async text => {
  const [button] = await scope.context.currentPage.$x(
    `//button[contains(., '${text}')]`,
  );
  if (button) {
    await button.click();
  }
};

const checkTwoColumn = async () => {
  let colOne = await scope.context.currentPage.evaluate(() => {
    return document.getElementById('column1').innerHTML;
  });
  let colTwo = await scope.context.currentPage.evaluate(() => {
    return document.getElementById('column2').innerHTML;
  });
  assert(colOne !== '' && colTwo !== '', 'Page does not have 2 column');
};

const countChildElement = async num => {
  let colOne = await scope.context.currentPage.evaluate(() => {
    return document.getElementById('column1').childElementCount;
  });
  let colTwo = await scope.context.currentPage.evaluate(() => {
    return document.getElementById('column2').childElementCount;
  });
  assert(colOne === num && colTwo === num, 'Do not have' + num + 'elements');
};

const CheckRestaurantContent = async () => {
  let name = await scope.context.currentPage.evaluate(() => {
    return document.getElementsByClassName('restaurantName')[0].innerText;
  });
  let drivingTime = await scope.context.currentPage.evaluate(() => {
    return document.getElementsByClassName('drivingTime')[0].innerText;
  });
  let address = await scope.context.currentPage.evaluate(() => {
    return document.getElementsByClassName('restaurantAddrress')[0].innerText;
  });
  let phone = await scope.context.currentPage.evaluate(() => {
    return document.getElementsByClassName('restaurantPhoneNumber')[0]
      .innerText;
  });
  assert(
    name !== '' || drivingTime !== '' || address !== '' || phone !== '',
    'Missing Info',
  );
};

const checkRecipeContent = async () => {
  let name = await scope.context.currentPage.evaluate(() => {
    return document.getElementsByClassName('recipeName')[0].innerText;
  });
  let cookTime = await scope.context.currentPage.evaluate(() => {
    return document.getElementsByClassName('cookTime')[0].innerText;
  });
  assert(name !== '' || cookTime !== '', 'Missing Info');
};

const clickRestaurant = async name => {
  const id = (await '#') + name;
  await scope.context.currentPage.click(id);
};

const checkRestaurantPage = async name => {
  let data = await scope.context.currentPage.evaluate(() => {
    return document.getElementById('Traditions').innerText;
  });
  assert(data === name, 'Incorrect Restaurant');
};

const clickRecipe = async name => {
  await scope.context.currentPage.click('#american');
};

const checkRecipePage = async name => {
  let data = await scope.context.currentPage.evaluate(() => {
    return document.getElementById(
      'Halloumi aubergine burgers with harissa relish',
    ).innerText;
  });
  assert(data === name, 'Incorrect Restaurant');
};

const clickSearchTerm = async () => {
  await scope.context.currentPage.click('#select-terms');
  await scope.context.currentPage.waitFor(1000);
};

const clickPrevSearchTerm = async () => {
  await scope.context.currentPage.click('#Burger');
};

const checkHomePage = async () => {
  //await scope.context.currentPage.waitForNavigation();
  let data = await scope.context.currentPage.evaluate(() => {
    return document.getElementById('root').innerHTML;
  });
  assert(data !== '', 'page not found');
};

const checkInputForm = async name => {
  let data = await scope.context.currentPage.evaluate(() => {
    return document.getElementById('food').value;
  });
  assert(data === name, 'Wrong Value');
};

const clickPagination = async () => {
  await scope.context.currentPage.click('.MuiButton-flatSecondary-119');
};

const waitTime = async () => {
  await scope.context.currentPage.waitFor(5000);
};

module.exports = {
  visitListManagementPage,
  dragAndDropOneBelowOther,
  checkFirstBelowSecond,
  visitPage,
  fillInTextField,
  clickButton,
  checkPage,
  checkTitle,
  checkList,
  checkDropdown,
  clickOnButtonWithText,
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
  waitTime,
  clickPagination,
};
