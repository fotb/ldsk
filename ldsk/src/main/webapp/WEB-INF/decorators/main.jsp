<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><decorator:title default="Struts Starter"/></title>
    <link href="<s:url value='/styles/main.css'/>" rel="stylesheet" type="text/css" media="all"/>
    <link href="<s:url value='/struts/niftycorners/niftyCorners.css'/>" rel="stylesheet" type="text/css"/>
    <link href="<s:url value='/struts/niftycorners/niftyPrint.css'/>" rel="stylesheet" type="text/css" media="print"/>
    <script language="JavaScript" type="text/javascript" src="<s:url value='/struts/niftycorners/nifty.js'/>"></script>
	<script language="JavaScript" type="text/javascript" src="<s:url value='/js/jquery-1.4.2.js'/>"></script>
	<script language="JavaScript" type="text/javascript">
        window.onload = function(){
            if(!NiftyCheck()) {
                return;
            }
            // perform niftycorners rounding
            // eg.
            // Rounded("blockquote","tr bl","#ECF1F9","#CDFFAA","smooth border #88D84F");
        }

        $(document).ready( function() {
    		$("#nav-one li").hover( function() {
    			$("ul", this).fadeIn("fast");
    		}, function() {
    		});
    		if (document.all) {
    			$("#nav-one li").hoverClass("sfHover");
    		}
    	});

    	$.fn.hoverClass = function(c) {
    		return this.each( function() {
    			$(this).hover( function() {
    				$(this).addClass(c);
    			}, function() {
    				$(this).removeClass(c);
    			});
    		});
    	};
    </script>
    <decorator:head/>
</head>
<body id="page-home">
    <div id="page">
        <div id="header" class="clearfix">
        	HEADER
            <hr />
        </div>
        
        <div id="content" class="clearfix">
            <div id="main">
            	<decorator:body/>
                <hr />
            </div>
            
            <div id="sub">
            	<h3>Sub Content</h3>
            </div>
            
<!-- 
            <div id="local">
                <ul id="deviceSubMenu">
                    <li><a href="devicetotal.action">设备汇总</a></li>
                    <li><a href="device.action">分区统计</a></li>
                </ul>

				
            </div>
 -->
            <div id="nav">
                <div class="wrapper">
					<ul class="navMenu" id="nav-one">
						<li><a href="devicetotal.action">设备统计</a>
						<ul style="opacity: 0.9999; display: block;">
							<li><a href="devicetotal.action">设备汇总</a></li>
							<li><a href="device.action">分区统计</a></li>
						</ul>
						</li>
						<li><a href="safeReportCount.action">安全信息统计</a>
						<ul style="opacity: 0.9999; display: block;">
							<li><a href="safeReportCount.action">统计汇总</a></li>
							<li><a href="#item2.2">分区统计</a></li>
						</ul>
						</li>
					</ul>
<!-- <h3>Nav. bar</h3> -->
<!-- 
	                <ul class="clearfix">
	                     <li><a href="devicetotal.action">设备统计</a></li>
	                     <li class="last"><a href="safeReportCount.action">安全信息统计</a></li>
	                </ul>
 -->
                </div>
                <hr />
            </div>
        </div>
        
        <div id="footer" class="clearfix">
            Footer
        </div>
        
    </div>
    
    <div id="extra1">&nbsp;</div>
    <div id="extra2">&nbsp;</div>
</body>
</html>
