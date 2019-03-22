Given("I am on the Home Page") do
  visit('http://localhost:3000')
end

Given("I clicked the {string} link") do |string|
  click_on(string)
end

Given("I am on Recipe page") do
  expect(page).to have_current_path('/recipe/3')
end

Then("I see Title") do
  expect(page).to have_css('h1')
end

Then("I see picture") do
  expect(page).to have_css('img')
end

Then("I see prep time") do
  expect(page).to have_css('#prepTime')
end

Then("I see cook time") do
  expect(page).to have_css('#cookTime')
end

Then("I see ingredients") do
  expect(page).to have_css('#ingredientLeft')
end

Then("I see instructions") do
  expect(page).to have_css('#ingredientLeft')
end

Then("I will be on Print page") do
  expect(page).to have_current_path('/print/recipe/3')
end


