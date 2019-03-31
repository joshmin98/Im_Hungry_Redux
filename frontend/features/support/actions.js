const assert = require('assert');
const scope = require('./scope');

let headless = false;
let slowMo = 50;

const domain = 'http://localhost:';
const port = '3000';

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

module.exports = {
  visitListManagementPage,
  dragAndDropOneBelowOther,
  checkFirstBelowSecond,
};
