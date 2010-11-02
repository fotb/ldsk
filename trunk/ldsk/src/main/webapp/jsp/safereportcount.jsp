<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>安全信息统计</title>
	<script language="JavaScript" type="text/javascript" src="<s:url value='/js/jquery-1.4.2.js'/>"></script>
	<s:head />
</head>
<script>
$(document).ready(function() {
	$.getJSON("/ldsk/safeReportCountJson.action", function(data){
		alert(data);
		var obj = $.parseJSON(data);
		//$("#test").html('test' + data[0].dellCount);
		//alert(data[0].computerCount);
		alert(obj);
		
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
			html += "</td>";
			$("#tableComputer").append(html);
		});

					
	});
});
</script>
<body>
	<s:form action="devicecount">
		<div id="test"></div>
		<table width="500" bgcolor="#E4E4E4" cellspacing="1" cellpadding="2" border="0" id="tableComputer">
			<tr bgcolor="#F1F7F9"	>
			    <td width="80" rowspan="2">辖区</td>
			    <td width="40" rowspan="2">总计</td>
			    <td colspan="8">主机侵入保护安全活动</td>
			    <td colspan="3">设备控制安全活动</td>
		  	</tr>
			<tr bgcolor="#F1F7F9">
				<td width="80">检测到尝试修改可执行文件</td>
				<td width="80">未授权程序程序试图执行</td>
				<td width="80">不允许注册表写尝试</td>
				<td width="80">此软件已安装</td>
				<td width="80">程序添加到系统启动项目</td>
				<td width="80">检测到不允许的文件访问</td>
				<td width="80">其他事件</td>
				<td width="80">总计</td>
				<td width="80">已禁用存储设备</td>
				<td width="80">其他事件</td>
				<td width="80">总计</td>
			</tr>
			<tr>
				<td>西湖辖区</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>

	</table>
	</s:form>
</body>
</html>
	