require 'capybara'
require 'capybara/cucumber'
require 'rspec'

Capybara.register_driver :chrome do |app|
  Capybara::Selenium::Driver.new(app, :browser => :chrome)
end

Capybara.default_driver = :chrome
