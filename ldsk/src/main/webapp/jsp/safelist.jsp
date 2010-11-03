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
	$("#searchBt").click(function() {
		var branchName = $("#subBranchName").val();
		if("null" == branchName) {
			alert("请选择支行!");
			return false;
		}
		$.getJSON("/ldsk/branchDevices.action?", {branchName:branchName, ran:Math.random()},function(data){
			var obj = $.parseJSON(data);
			
			//$("#test").html('test' + data[0].dellCount);
			//alert(data[0].computerCount);
			//alert(obj);

			$("#tdTotalCount").html(obj.totalCount);
			$("#tdDellCount").html(obj.dCount);
			$("#tdLenovoCount").html(obj.lCount);
			$("#tdHPCount").html(obj.hCount);
			$("#tdOtherCount").html(obj.oCount);

			$("#tableComputer").html("");
			str = "<tr bgcolor='#F1F7F9'>";
			str += "<td align='center'>序号</td>";
			str += "<td align='center'>机器名称</td>";
			str += "<td align='center'>IP地址</td>";
			str += "<td align='center'>Mac地址</td>";
			str += "<td align='center'>机器类型</td>";
			str += "<td align='center'>位置</td>";
			str += "</tr>";	
			$("#tableComputer").append(str);	
			if(obj.devices=="") {
				html = "<tr bgcolor='#FFFFFF'>";
				html += "<td align='center'>-</td>";
				html += "<td align='center'>-</td>";
				html += "<td align='center'>-</td>";
				html += "<td align='center'>-</td>";
				html += "<td align='center'>-</td>";
				html += "<td align='center'>-</td>";
				html += "</tr>";
				$("#tableComputer").append(html);
			} else {
				$.each(obj.devices, function(i,item){
					html = "<tr bgcolor='#FFFFFF'>";
					html += "<td align='center'>" + (i+1) + "</td>";
					html += "<td>" + item.deviceName + "</td>";
					html += "<td>" + item.tcpAddress + "</td>";
					html += "<td>" + item.macAddress + "</td>";
					html += "<td>" + item.model + "</td>";
					html += "<td>" + item.position + "</td>";
					html += "</tr>";
					$("#tableComputer").append(html);
				});	
			}
		});
		  return false;
	});

	$("#branchName").change(function(){
		changeSubBranchName();
	});
	changeSubBranchName();
	
});
function changeSubBranchName() {
	var subBranchName = $('#branchName :selected').val();
	if(subBranchName == "null") {
		$("#subBranchName").html("<option value='null'>--请选择--</option>");
		return;
	}
	var branchNameAry = subBranchName.split(",");
	var htmlStr = "<option value='"+subBranchName+"'>--全部--</option>";
	for(i = 0; i < branchNameAry.length; i++) {
		htmlStr = htmlStr + "<option value='"+branchNameAry[i]+"'>"+branchNameAry[i]+"</option>";
	}
	$("#subBranchName").html(htmlStr);
}

</script>
<body>
	<s:form action="#" id="devicesList">
		<table border="0">
			<tr>
				<td>辖区</td>
				<td nowrap><s:select id="branchName" list="#session.BranchList" listKey="value" listValue="key" headerKey="null" headerValue="--请选择--"/></td>
				<td>分行</td>
				<td>
				<s:select id="subBranchName"  list="#session.SubBranchList" headerKey="null" headerValue="--全部--"/>
				<!--<select id="sunBranchName"><option value="all">--全部--</option></select>-->
				</td>
				<td nowrap><input type="button" value="查询" id="searchBt"/></td>
			</tr>
		</table>
		<table width="500" bgcolor="#E4E4E4" cellspacing="1" cellpadding="2" border="0" id="tableTotalComputer">
			<tr bgcolor="#F1F7F9">
				<td align="center" colspan="2">总计</td>
				<td align="center">DELL机</td>
				<td align="center">Lenovo机</td>
				<td align="center">HP机</td>
				<td align="center">其它品牌</td>
			</tr>
			<tr bgcolor="#FFFFFF">
				<td align="center" colspan="2" id="tdTotalCount">&nbsp;</td>
				<td align="center" id="tdDellCount">&nbsp;</td>
				<td align="center" id="tdLenovoCount">&nbsp;</td>
				<td align="center" id="tdHPCount">&nbsp;</td>
				<td align="center" id="tdOtherCount">&nbsp;</td>
			</tr>
</table>
<table width="500" bgcolor="#E4E4E4" cellspacing="1" cellpadding="2" border="0" id="tableComputer">
			<tr bgcolor="#F1F7F9">
				<td align="center">序号</td>
				<td align="center">机器名称</td>
				<td align="center">IP地址</td>
				<td align="center">Mac地址</td>
				<td align="center">机器类型</td>
				<td align="center">位置</td>
			</tr>
			<tr bgcolor="#FFFFFF">
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
			</tr>
		</table>
		<div id="pageDiv"></div>
	</s:form>
</body>
</html>
	