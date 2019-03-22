Given("I am on the Search Page") do
  visit('http://localhost:3000')
end

Given("I have entered a search query {string}") do |string|
  fill_in 'Enter Food', with: string
end

Given("{int} for the number of results to be displayed") do |int|
  fill_in 'numResults', with: int
end

Given("I clicked the {string} button") do |string|
  click_on(string)
end

Given("I am on the Results Page") do
  sleep(2)
  expect(page).to have_current_path('/search')
end

Then("the page will have the title: Results for {string}") do |string|
  expect(page).to have_content(string)
end

Then("the page will have a collage of photos related to the search query") do
  expect(page).to have_css('img')
end

Then("the page will have a dropdown box with the predefined lists") do
  expect(page).to have_select 'lists',
                  with_options: ['Favorites', 'To Explore', 'Do Not Show']
end

Then("the page will have a button labeled {string}") do |string|
  expect(page).to have_button(string)
end

Then("the page will have two columns of results titled: Restaurants, Recipes") do
  expect(page).to have_css('#recipe-list')
  expect(page).to have_css('#restaurant-list')
end

Then("the page will have {int} restaurant and recipe results in each column") do |int|
  x = 0
  until x == int
    expect(page).to have_css('#restaurant-' + x.to_s)
    expect(page).to have_css('#recipe-' + x.to_s)
    x = x + 1
  end
end

Then("each restaurant item on the page will have an address, name, and minutes") do
  expect(page).to have_css('#restaurantName')
  expect(page).to have_css('#address')
  expect(page).to have_css('#drive')
end

Then("each recipe item on the page will have a name, prep time, and cook time") do
  expect(page).to have_css('#recipeName')
  expect(page).to have_css('#prep')
  expect(page).to have_css('#cook')
end

When("I select {string} from the dropdown") do |string|
  select string
end

When("I click the {string} button") do |string|
  click_on string
end

Then("I will be on the {string} list page") do |string|
  sleep(1)
  expect(page).to have_current_path('/lists/' + string)
end

Then("I will be on the Search Page") do
  sleep(1)
  expect(page).to have_current_path('/')
end

When("I click on a restaurant with name {string}") do |string|
  click_on string
end

Then("I will be on the Restaurant Page of restaurant {string}") do |string|
  # TODO
  expect(page).to have_content(string)
end

When("I click on a recipe with name {string}") do |string|
  click_on string
end

Then("I will be on the Recipe Page of recipe {string}") do |string|
  # TODO
  expect(page).to have_content(string)
end

