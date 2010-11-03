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
		//alert(data);
		var obj = $.parseJSON(data);
		//$("#test").html('test' + data[0].dellCount);
		//alert(data[0].computerCount);
		//alert(obj);
		
		//$("#totalCount").append(obj.totalCount);
		//$("#totalHipsCount").append(obj.totalHipsCount);
		//$("#totalDcCount").append(obj.totalDeviceControlCount);
		var total_hips = 0;
		var total_dc = 0;
		var total_109 = 0;
		var total_113 = 0;
		var total_106 = 0;
		var total_105 = 0;
		var total_104 = 0;
		var total_110 = 0;
		var total_hipsOther = 0;
		var total_115 = 0;
		var total_dcOther = 0;
		$.each(obj.Branchs, function(i,item){
			html = "<tr bgcolor='#FFFFFF'>";
			html += "<td align='center' bgcolor='#F1F7F9'>" + item.branchName + "</td>";
			html += "<td align='right'>"+ getTotal(item.hipsTotalCount, item.dcTotalCount) +"</td>";
			html += "<td align='right'>" + getValue(item.actionCode_109) + "</td>";
			html += "<td align='right'>" + getValue(item.actionCode_113) + "</td>";
			html += "<td align='right'>" + getValue(item.actionCode_106) + "</td>";
			html += "<td align='right'>" + getValue(item.actionCode_105) + "</td>";
			html += "<td align='right'>" + getValue(item.actionCode_104) + "</td>";
			html += "<td align='right'>" + getValue(item.actionCode_100) + "</td>";
			html += "<td align='right'>" + getValue(item.hipsOther) + "</td>";
			html += "<td align='right'>" + getValue(item.hipsTotalCount) + "</td>";
			//html += "<td>" + item.actionCode_116 + "</td>";
			//html += "<td>" + item.actionCode_117 + "</td>";
			html += "<td align='right'>" + getValue(item.actionCode_115) + "</td>";
			html += "<td align='right'>" + getValue(item.dcOther) + "</td>";
			html += "<td align='right'>" + getValue(item.dcTotalCount) + "</td>";
			html += "</tr>";
			$("#tableComputer").append(html);
			total_109 = getTotal(total_109, item.actionCode_109);
			total_113 = getTotal(total_113, item.actionCode_113);
			total_106 = getTotal(total_106, item.actionCode_106);
			total_105 = getTotal(total_106, item.actionCode_105);
			total_104 = getTotal(total_104, item.actionCode_104);
			total_110 = getTotal(total_110, item.actionCode_100);
			total_hipsOther = getTotal(total_hipsOther, item.hipsOther);
			total_115 = getTotal(total_115, item.actionCode_115);
			total_dcOther = getTotal(total_dcOther, item.dcOther);
			total_hips = getTotal(total_hips, item.hipsTotalCount);
			total_dc = getTotal(total_dc, item.dcTotalCount);
		});
		$("#total_109").append(total_109);
		$("#total_113").append(total_113);
		$("#total_106").append(total_106);
		$("#total_105").append(total_105);
		$("#total_104").append(total_104);
		$("#total_110").append(total_110);
		$("#total_hipsOther").append(total_hipsOther);
		$("#total_115").append(total_115);
		$("#total_dcOther").append(total_dcOther);	
		$("#totalCount").append(total_hips + total_dc);
		$("#totalHipsCount").append(total_hips);
		$("#totalDcCount").append(total_dc);
			
	});
});

function getValue(value) {
	if(""==value || null==value) {
		return "0";
	}else {
		return value;
	}
}
function getTotal(count1, count2) {
	return parseInt(getValue(count1)) + parseInt(getValue(count2));
}
</script>
<body>
	<s:form action="devicecount">
		<div id="test"></div>
		<table width="1000" bgcolor="#E4E4E4" cellspacing="1" cellpadding="2" border="0" id="tableComputer">
			<tr bgcolor="#F1F7F9"	>
			    <td width="80" rowspan="2" nowrap align='center'>辖区</td>
			    <td width="40" rowspan="2" align='center'>总计</td>
			    <td colspan="8" align='center'>主机侵入保护安全活动</td>
			    <td colspan="3" align='center'>设备控制安全活动</td>
		  	</tr>
			<tr bgcolor="#F1F7F9">
				<td width="80" align='center'>检测到尝试修改可执行文件</td>
				<td width="80" align='center'>未授权程序程序试图执行</td>
				<td width="80" align='center'>不允许注册表写尝试</td>
				<td width="80" align='center'>此软件已安装</td>
				<td width="80" align='center'>程序添加到系统启动项目</td>
				<td width="80" align='center'>检测到不允许的文件访问</td>
				<td width="80" align='center'>其他事件</td>
				<td width="80" align='center'>总计</td>
				<td width="80" align='center'>已禁用存储设备</td>
				<td width="80" align='center'>其他事件</td>
				<td width="80" align='center'>总计</td>
			</tr>
			<tr>
				<td nowrap  align='center'>全行总计</td>
				<td id="totalCount" align="right">&nbsp;</td>
				<td id="total_109" align="right">&nbsp;</td>
				<td id="total_113" align="right">&nbsp;</td>
				<td id="total_106" align="right">&nbsp;</td>
				<td id="total_105" align="right">&nbsp;</td>
				<td id="total_104" align="right">&nbsp;</td>
				<td id="total_110" align="right">&nbsp;</td>
				<td id="total_hipsOther" align="right">&nbsp;</td>
				<td id="totalHipsCount" align="right">&nbsp;</td>
				<td id="total_115" align="right">&nbsp;</td>
				<td id="total_dcOther" align="right">&nbsp;</td>
				<td id="totalDcCount" align="right">&nbsp;</td>
			</tr>

	</table>
	</s:form>
</body>
</html>
	