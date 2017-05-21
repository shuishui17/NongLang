<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1><p> 用户列表 </p></h1>

<table>
	
	<th>
		<td>编号</td>
		<td>用户名</td>
		<td>电话</td>
		<td>性别</td>
	</th>
	<c:forEach items="${varList}" var="var" varStatus="vs">
		<tr>
			<td>${vs.index+1 }</td>
			<td>${var.username }</td>
			<td>${var.phonenumber }</td>
			<td>${var.sex }</td>
		</tr>
	</c:forEach>
	
</table>

</body>
</html>