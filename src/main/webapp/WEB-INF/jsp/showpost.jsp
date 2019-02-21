<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<img src="${post.imguri}"  height="250" width="250"  > <br> <br>
<audio autoplay>
<source src="${post.audiourl}">
</audio> 
${post.text} <br> <br>
 <ul class="list-group">
<c:forEach items="${comment}" var ="i">
<li class="list-group-item">
<c:out value="${i.comment}"></c:out> by ${i.name} </li> <br> <br >
</c:forEach> 
</ul>
<form action="/savecomment" method="post">
<input type="text" placeholder="comment here" name="comment"/>
<input type="hidden" name="postid" value="${post.postid}"/>
<button type="submit" >Save </button>
</form>
</body>
</html>