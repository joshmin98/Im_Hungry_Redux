const assert = require('assert');
const scope = require('./scope');

let headless = false;
let slowMo = 1;

const domain = 'http://localhost:';
const port = '3000';

const visitPage = async pageName => {
  if (!scope.browser) {
    scope.browser = await scope.driver.launch({ headless, slowMo });
  }
  scope.context.page = await scope.browser.newPage();
  const url = domain + port + `/${pageName}`;
  await scope.context.page.goto(url);
};

const login = async (email, password) => {
  await enterToTextbox('#email', email);
  await enterToTextbox('#password', password);
  await clickButtonWithID('#login');
  await scope.context.page.waitFor(1200);
};

const clickButtonWithID = async id => {
  await scope.context.page.click(id);
  scope.context.page.on('dialog', async dialog => {
    await dialog.accept();
  });
  await scope.context.page.waitFor(200);
};

const enterToTextbox = async (id, param) => {
  await scope.context.page.type(id, param);
};

const wait = async seconds => {
  await scope.context.page.waitFor(seconds * 1000);
};

const findPlaceInList = async (itemID, position, listID) => {
  let texts = await scope.context.page.evaluate(() => {
    let data = [];
    let elements = document.getElementById('#' + 'itemID');
  });
};

module.exports = {
  visitPage,
  login,
  wait,
  clickButtonWithID,
  enterToTextbox,
  findPlaceInList,
};
