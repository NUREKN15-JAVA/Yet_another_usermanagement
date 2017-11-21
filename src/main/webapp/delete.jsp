<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User management / Delete</title>
</head>
<body>
	<p>Are you sure you want to delete ${sessionScope.user.fullName}?</p>
	<form action='<%=request.getContextPath()%>/delete' method='post'>
		<input type='submit' name='okButton' value='OK' /> <input
			type='submit' name='cancelButton' value='Cancel' />
	</form>
</body>
</html>