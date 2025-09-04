<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Home</title>
</head>
<body>

<h1>/param/page3</h1>


DTO: ${dto}<br/>
Name: ${dto.name}<br/>  // 이렇게 쓰기 위해서는 dto의 클래스에 @Data(setter, getter를 설정해주는) 있어야함
Age: ${dto.age}<br/>
Addr: ${dto.addr}<br/>



</body>
</html>