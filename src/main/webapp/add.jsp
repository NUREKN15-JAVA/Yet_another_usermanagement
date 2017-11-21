<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<html>
<head>
<title>UserManagement / Add</title>
</head>
<body>
	<form action='<%=request.getContextPath()%>/add' method='post'>
		First name <input type='text' name='firstName' value=''>
		Last name <input type='text' name='lastName' value=''>
		Date of birth <input type='text' name='birthday' value=''>
		<input type='submit' name='okButton' value='OK'> 
		<input type='submit' name='cancelButton' value='Cancel'>
	</form>
	<c:if test="${requestScope.eror != null}">
		<script>
			alert('${requestScope.error}]')
		</script>
	</c:if>
</body>
</html>