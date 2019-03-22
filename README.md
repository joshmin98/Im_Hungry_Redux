# Frontend

## Using the Mock-API
1. Install the `json-server` npm package with `npm i -g json-server`
2. `cd` into the repo root directory and run the command `json-server -p 9999 -w testdb.json`

# Backend

## Spoonacular API
It works, but please do not overuse. We have 50 free calls per day, and any additional call costs money. The backend currently pulls data from the Spoonacular API depending on the search query (can pull name, picture, ingredients, time, and price) and needs at least two separate calls per recipe. This data is parsed into a RecipeDetails class.

## Yelp API
Currently working on integrating this with Maven - emily

## Web Servelet
Don't worry about this yet. We'll get this done together. (If you want an idea of what we're going to end up with: [this StackOverflow post- we're just going to serve the static "build" folder, and send out index.html file when the client connects to the servelet](https://stackoverflow.com/questions/132052/servlet-for-serving-static-content))

## Recipe/Restaurant Servelets
So what's going to happen here is we're going to have a servelet that accepts HTTP GET requests. Our frontend is going to hit this servlet with the following query: `?query=xxx` (where `xxx`) is the item that we are searching for on `HomePage`.

You'll extract this parameter (I forget exactly how you do this in Java. I think it's `request.getParameter(xxx)`), and then use this for your API call.

You'll then get data (JSON) back. What you'll need to do now is format this data (JSON) to the following spec:

### Recipe Response
```json
{
	"recipes": [
	{
		"id": "UNIQUE_ID" (string),
		"ingredients": ["item1", "item2","item3"] (array of strings),
		"price": 999.99 (float),
		"image": "http://image.com/image" (string)
		...
	},
	{
		"id": "UNIQUE_ID"
		...
	}
	...
	]
}
```

### Restaurant Response
```json
{
	"restaurants": [
	{
		"id": "UNIQUE_ID" (string),
		"drive_time": 20 (int),
		"price": 999.99 (float),
		"image": "http://image.com/image" (string)
		...
	},
	{
		"id": "UNIQUE_ID"
		...
	}
	...
	]
}
```

## Some Resources
[Here's some info on sending JSON](https://stackoverflow.com/questions/2010990/how-do-you-return-a-json-object-from-a-java-servlet)

[Here's some info on editing JSON Objects within Java](https://stackoverflow.com/questions/15334539/edit-json-fields)
