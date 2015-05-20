<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    
	import="com.sample.ProcessTestSpring" 
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Invoking Test Process</title>
</head>
<body>
<%

out.println("<h1>Here in JSP</h1>");

ProcessTestSpring pt = new ProcessTestSpring();

System.out.println("2nd print in JSP...");


try {
	pt.testProcess();
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	System.out.println(String.format("Cause==>%s\n",e.getCause().toString()));
}




%>
</body>
</html>