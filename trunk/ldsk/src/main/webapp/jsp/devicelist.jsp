<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>设备统计</title>
	<s:head />
</head>
<body>
	<s:form action="device">
		<s:select id="branchName" list="#session.BranchMap" listKey="key" listValue="value" headerKey="1" headerValue="--全部--"/>
		<s:submit />
	</s:form>
</body>
</html>
	