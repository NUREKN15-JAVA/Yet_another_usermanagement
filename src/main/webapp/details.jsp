<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<jsp:useBean id='user' class='ua.nure.baranov.User' scope='session' />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User management / Details</title>
</head>
<body>
	<table id='userTable' border='1'>
		<tr>
			<td>ID</td>
			<td>${sessionScope.user.id}</td>
		</tr>
		<tr>
			<td>First name</td>
			<td>${sessionScope.user.firstName}</td>
		</tr>
		<tr>
			<td>Last name</td>
			<td>${sessionScope.user.lastName}</td>
		</tr>
		<tr>
			<td>Date of birth</td>
			<td><fmt:formatDate value="${sessionScope.user.dateOfBirth}" pattern='dd MMM yyyy'/></td>
		</tr>
	</table>
	<br>
	<br>
	<form action='<%=request.getContextPath()%>/browse' method='post'>
	<input type="submit" name="okButton" value="OK"/>
	</form>
	
</body>
</html>