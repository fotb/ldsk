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
		var safeType = $("#safeType").val();
		if("null" == safeType) {
			alert("请选择安全活动类型!");
			return false;
		}
		var hipsActionType = $("#hipsActionType").val();
		var deviceControlType = $("#deviceControlType").val();
		if("1" == safeType && "null" == hipsActionType) {
			alert("请选择子类!");
			return false;
		} else if("2" == safeType && "null" == deviceControlType) {
			alert("请选择子类!");
			return false;
		}

		$(window).ajaxStart(function(){
			$("#loading").show();
			
		});
		$.getJSON("/ldsk/safeReportDetailJson.action?", {branchName:branchName, safeType:safeType, hipsActionType:hipsActionType, deviceControlType:deviceControlType, ran:Math.random()},function(data){

			//alert(data);
			var obj = $.parseJSON(data);
			//$("#test").html('test' + data[0].dellCount);
			//alert(data[0].computerCount);
			//alert(obj.details);
			
			if("1" == safeType) {
				$("#tableHips").html("");
				str = "<tr bgcolor='#F1F7F9'>";
				str += "<td align='center' nowrap>序号</td>";
				str += "<td align='center' nowrap>设备名称</td>";
				str += "<td align='center' nowrap>IP地址</td>";
				str += "<td align='center' nowrap>操作日期</td>";
				str += "<td align='center' nowrap>应用程序名称</td>";
				str += "</tr>";	
				$("#tableHips").append(str);	
				if(obj.details=="") {
					html = "<tr bgcolor='#FFFFFF'>";
					html += "<td align='center'>-</td>";
					html += "<td align='center'>-</td>";
					html += "<td align='center'>-</td>";
					html += "<td align='center'>-</td>";
					html += "<td align='center'>-</td>";
					html += "</tr>";
					$("#tableHips").append(html);
				} else {
					$.each(obj.details, function(i,item){
						html = "<tr bgcolor='#FFFFFF'>";
						html += "<td align='center'>" + item.index + "</td>";
						html += "<td>" + item.deviceName + "</td>";
						html += "<td>" + item.IP + "</td>";
						html += "<td nowrap>" + item.actionDate + "</td>";
						html += "<td>" + (item.appName).replace(/-/g, "\\") + "</td>";
						html += "</tr>";
						$("#tableHips").append(html);
					});	
				}
				$("#tableHips").show();
				$("#tableDc").hide();
			} else if("2" == safeType) {
				$("#tableDc").html("");
				str = "<tr bgcolor='#F1F7F9'>";
				str += "<td align='center' nowrap>序号</td>";
				str += "<td align='center' nowrap>设备名称</td>";
				str += "<td align='center' nowrap>IP地址</td>";
				str += "<td align='center' nowrap>操作日期</td>";
				str += "<td align='center' nowrap>说明</td>";
				str += "<td align='center' nowrap>硬件ID</td>";
				str += "</tr>";	
				$("#tableDc").append(str);	
				if(obj.details=="") {
					html = "<tr bgcolor='#FFFFFF'>";
					html += "<td align='center'>-</td>";
					html += "<td align='center'>-</td>";
					html += "<td align='center'>-</td>";
					html += "<td align='center'>-</td>";
					html += "<td align='center'>-</td>";
					html += "<td align='center'>-</td>";
					html += "</tr>";
					$("#tableDc").append(html);
				} else {
					$.each(obj.details, function(i,item){
						html = "<tr bgcolor='#FFFFFF'>";
						html += "<td align='center'>" + item.index + "</td>";
						html += "<td>" + item.deviceName + "</td>";
						html += "<td>" + item.IP + "</td>";
						html += "<td>" + item.actionDate + "</td>";
						html += "<td>" + item.desc + "</td>";
						html += "<td>" + item.deviceId + "</td>";
						html += "</tr>";
						$("#tableDc").append(html);
					});	
				}
				$("#tableHips").hide();
				$("#tableDc").show();
			}
		});
		$(window).ajaxStop(function(){
			$("#loading").hide();
		});
		return false;
	});

	changeSubBranchName();
	changeActionType();
	$("#branchName").change(function(){
		changeSubBranchName();
	});

	$("#safeType").change(function() {
		changeActionType();
	});
});
function changeActionType() {
	var safeTypeValue = $("#safeType :selected").val();

	if("1" == safeTypeValue) {
		$("#hipsActionType").show();
		$("#deviceControlType").hide();
	} else {
		$("#hipsActionType").hide();
		$("#deviceControlType").show();
	}
}

function changeSubBranchName() {
	var subBranchName = $('#branchName :selected').val();
	if(subBranchName == "null") {
		$("#subBranchName").html("<option value='null'>--请选择--</option>");
		return;
	}
	var branchNameAry = subBranchName.split(",");
	var htmlStr = "<option value='null'>--请选择--</option>";
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
				<td nowrap><s:select id="branchName" list="bList" listKey="branchValue" listValue="branchName" headerKey="null" headerValue="--请选择--"/></td>
				<td>分行</td>
				<td><s:select id="subBranchName"  list="#session.SubBranchList" headerKey="null" headerValue="--全部--"/></td>
				<td>安全活动类型</td>
				<td nowrap>
					<select id="safeType">
						<option value="1">主机侵入保护安全活动</option>
						<option value="2">设备控制安全活动</option>
					</select>
				</td>
				<td>子类</td>
				<td nowrap>
					<s:select id="hipsActionType" list="#session.hipsActionMap" listKey="key" listValue="value" headerKey="null" headerValue="--请选择--"/>
					<s:select cssStyle="display:none;" id="deviceControlType" list="#session.deviceControlMap" listKey="key" listValue="value" headerKey="null" headerValue="--请选择--"/>
				</td>
				<td nowrap><input type="button" value="查询" id="searchBt"/></td>
			</tr>
		</table>
		<br>
		<div id="loading" style="display:none;"><img src="/ldsk/images/ajax-loader2.gif"/></div>
		<table width="880" bgcolor="#E4E4E4" cellspacing="1" cellpadding="2" border="0" id="tableHips">
			<tr bgcolor="#F1F7F9">
				<td align="center" nowrap>序号</td>
				<td align="center" nowrap>设备名称</td>
				<td align="center" nowrap>IP地址</td>
				<td align="center" nowrap>操作日期</td>
				<td align="center" nowrap>应用程序名称</td>
			</tr>
			<tr bgcolor="#FFFFFF">
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
			</tr>
		</table>

		<table width="880" bgcolor="#E4E4E4" cellspacing="1" cellpadding="2" border="0" id="tableDc" style="display:none;">
			<tr bgcolor="#F1F7F9">
				<td align="center" nowrap>序号</td>
				<td align="center" nowrap>设备名称</td>
				<td align="center" nowrap>IP地址</td>
				<td align="center" nowrap>操作日期</td>
				<td align="center" nowrap>说明</td>
				<td align="center" nowrap>硬件ID</td>
			</tr>
			<tr bgcolor="#FFFFFF">
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center" nowrap>&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
			</tr>
		</table>
	</s:form>
</body>
</html>
	