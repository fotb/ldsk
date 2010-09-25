<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>设备统计</title>
	<script language="JavaScript" type="text/javascript" src="<s:url value='/js/jquery-1.4.2.js'/>"></script>
	<s:head />
</head>
<script>
$(document).ready(function() {
	$.getJSON("/ldsk/devicecount.action", function(data){
		alert(data);
		$("#test").html('test' + data[0].dellCount);
		alert(data[0].computerCount);
	});
});
</script>
<body>
	<s:form action="devicecount">
		<div id="test"></div>
		<s:submit />
	</s:form>
</body>
</html>
	