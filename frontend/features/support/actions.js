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

const clickButton = async text => {};

const enterToTextbox = async (textbox, param) => {};

module.exports = {
  visitPage,
  clickButton,
  enterToTextbox,
};
