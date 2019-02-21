<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<img src="${postObj.imguri}" alt="Profile Page Image" height="250" width="250" > 
<audio autoplay>
<source src="${postObj.audiourl}" type="audio/webm"> <br>


</audio> <br> <br>
${postObj.text}
</body>
</html>