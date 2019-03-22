Given("I am on Restaurant page") do
    expect(page).to have_content("Northern Cafe")
  end
  
Then("I see name") do
    expect(page).to have_css('#address')
end
  
Then("I see phone number") do
    expect(page).to have_css('#number')
end
  
Then("I see website") do
    expect(page).to have_css('#website')
end
  
Then("I will be on Google Map with direction filled out") do
    expect(page).to have_current_path('/maps?q=2904+S+Figueroa+St+Los+Angeles,+CA+90007+')
end

Then("I will be on Yelp page") do
    expect(page).to have_current_path('/biz/northern-cafe-los-angeles-6?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw')
end




  