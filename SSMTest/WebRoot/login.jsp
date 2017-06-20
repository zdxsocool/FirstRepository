<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
     <meta charset="utf-8">
    <title>测试短信接口</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   请您输入手机号：<input type="text" id="phone">
   <p id="lab"></p><br>
 	<button id="send">发送验证码</button>
 	<script src="js/jquery.min.js"></script>
<script type="text/javascript">
	$("#send").click(function(){
		var phone=$("#phone").val();
		$.post("sendSms.do","phone="+phone,function(data){
			$("#lab").html(data);
		});
	});
</script>
  </body>
</html>
