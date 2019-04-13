const assert = require('assert');
const scope = require('./scope');

let headless = false;
let slowMo = 50;

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
};

const checkPage = async id => {
  let data = await scope.context.currentPage.evaluate(() => {
    return document.getElementById(id).innerHTML;
  });
  assert(data === '', 'Page Not Found');
};

const checkTitle = async (e, val) => {
  // let data = await scope.context.currentPage.evaluate(() => {
  //   return document.getElementById('headerTitle').innerText
  // });
  // assert(data !== val, 'Incorrect Vale');
  //await scope.context.currentPage.waitForSelector('h1',{timeout: 0});
  await scope.context.currentPage.waitFor(1000);
  const element = await scope.context.currentPage.$('#headerTitle');
  const text = await scope.context.currentPage.evaluateHandle(
    element => element.textContent,
    element,
  );
  assert(text !== val, 'incorrect value');
};

const checkList = async id => {
  let data = await scope.context.currentPage.evaluate(() => {
    return document.getElementById(id).childNodes.length;
  });

  assert(parseInt(data) === 0, 'List is Empty');
};

const checkDropdown = async () => {
  await scope.context.currentPage.click('#select-list');
  await scope.context.currentPage.click('#Favorites');
  let data = await scope.context.currentPage.evaluate(() => {
    return document.getElementById('dropdown').value;
  });
  assert(data !== 'Favorites', 'List not exist');
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
};
