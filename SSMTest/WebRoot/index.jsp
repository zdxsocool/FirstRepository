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
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>验证码测试</title>
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
    //首先是页面,这里使用img来承载验证码,注意img的请求,为你生成验证码的那个请求,同时onclick="changeUrl()"是用来点击改变验证码的一个js方法,后面会叙述
<br>
<tr>
  <td class="text-right"><strong>验证码:</strong>&nbsp;&nbsp;</td>
  <td><input type="text" id="codes" required name="codevalidate" style="width:100;display: inline">
      <img id="codevalidate" src="code.do?time=<%=new Date().getTime()%>" width="90" height="30" style="margin-left: 10px" onclick="changeUrl()">
  </td>
</tr>
<p id="lab"></p>
<button id="btn">提交</button>
<script src="js/jquery.min.js"></script>
<script type="text/javascript">
function changeUrl() {
    var url = "code.do?time="+ (new Date()).valueOf();
    $("#codevalidate").prop('src',url);
}
$("#btn").click(function(){
	var code=$("#codes").val();
	$.post("login.do","codevalidate="+code,function(data){
		$("#lab").html("<label style='color:red;'>"+data+"</label>");
		changeUrl();
	});
});
</script>
  </body>
</html>
