<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP</title>
</head>
<body>
<jsp:useBean id="user" class="sample.beans.UserBeans"></jsp:useBean>
<jsp:setProperty property="username" name="user" value="ユーザー名"/>
<jsp:setProperty property="address" name="user" value="神奈川県"/>

username : <c:out value="${user.username }" /><br />
address : <c:out value="${user.address }" /><br />
phone : <c:out value="${user.phone }" /><br />
</body>
</html>