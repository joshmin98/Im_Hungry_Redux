Given("that I am on the homepage") do
  visit('http://localhost:3000')
end

When("I enter {string} into the text box labeled: Enter Food") do |string|
  fill_in 'Enter Food', with: string
end

When("I enter a number greater than or equal to {int} into the text box with a default value of {int}") do |int, int2|
  fill_in 'numResults', with: int2
end

When("I click the button with the label: Feed Me!") do
  click_on('Feed Me!')
end

Then("I will transition to the Results Page") do
  sleep(2)
  expect(page).to have_current_path('/search')
end
