<html>
<body>
<h2>Hello World!</h2>

<script>
fetch("/Backend/ImHungryServlet/RecipeServlet?query=burgers&numResults=3")
	.then(function(resp) {
		resp.json().then(function(data) {
			console.log(data);
		});
	});
</script>
</body>
</html>
