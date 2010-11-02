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
		//alert(data);
		var obj = $.parseJSON(data);
		//$("#test").html('test' + data[0].dellCount);
		//alert(data[0].computerCount);
		//alert(obj);
		
		$("#tdComputerCount").append(obj.computerCount);
		$("#tdDellCount").append(obj.dellCount);
		$("#tdLenovoCount").append(obj.lenovoCount);
		$("#tdHPCount").append(obj.hpCount);
		$("#tdOtherCount").append(obj.otherCount);

		$.each(obj.Branchs, function(i,item){
			html = "<tr bgcolor='#FFFFFF'>";
			html += "<td align='center' bgcolor='#F1F7F9'>" + item.BranchName + "</td>";
			html += "<td>" + item.dCount + "</td>";
			html += "<td>" + item.lCount + "</td>";
			html += "<td>" + item.hCount + "</td>";
			html += "<td>" + item.oCount + "</td>";
			html += "</tr>";
			$("#tableComputer").append(html);
		});

					
	});
});
</script>
<body>
	<s:form action="devicecount">
		<div id="test"></div>
		<table width="500" bgcolor="#E4E4E4" cellspacing="1" cellpadding="2" border="0" id="tableComputer">
		<tr bgcolor="#F1F7F9">
			<td rowspan="2" align="center" width="100">名称</td>
			<td align="center">PC终端</td>
			<td colspan="3" bgcolor="#FFFFFF" id="tdComputerCount"></td>
		</tr>
		<tr bgcolor="#F1F7F9">
			<td align="center">DELL机</td>
			<td align="center">Lenovo机</td>
			<td align="center">HP机</td>
			<td align="center">其它品牌</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align="center" width="100" bgcolor="#F1F7F9">全行设备总计</td>
			<td id="tdDellCount"></td>
			<td id="tdLenovoCount"></td>
			<td id="tdHPCount"></td>
			<td id="tdOtherCount"></td>
		</tr>

	</table>
	</s:form>
</body>
</html>
	