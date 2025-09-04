<%@ page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Home</title>
</head>
<body>

<h1>/ except/ Error PAGE </h1>
위에 페이지 지정자칸에 page isErrorPage="ture"해줘야지 이 페이지는 에러페이지다라는걸 알아먹음

EX: ${ex}
<hr/>

Exception: <%=exception %>   <br/>



<P>  The time on the server is 하이 </P>
</body>
</html>