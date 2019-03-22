Then("I will be on the {string} List Management Page") do |string|
  expect(page).to have_content(string)
end

Then("{string} will be in the list") do |string|
  expect(page).to have_content(string)
end

Given("I am on the {string} List Management Page") do |string|
  visit("http://localhost:3000/lists/" + string)
end
