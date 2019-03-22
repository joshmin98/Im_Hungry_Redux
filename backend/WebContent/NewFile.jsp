<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
fetch('/RecipeServlet?query=test&numResults=10').then(function(resp) {
	resp.json().then(function(data) {
		console.log(data);l
	});
});
</script>
</head>
<body>

</body>
</html>