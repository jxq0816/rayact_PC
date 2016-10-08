<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-form/jquery.form.js" type="text/javascript"></script>
	<link href="${ctxStatic}/jquery-mobile/list.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/bootstrap/2.3.1/css_default/bootstrap.min.css" type="text/css" rel="stylesheet" />
</head>
<body style="font-family: Microsoft Yahei">
<style>
	.step{
		margin: 2px;
		margin-top: 100px;
	}
	.s1{
		background-color: #f0860c;
		color: #ffffff;
		width: 100%;
		height: 30px;
		margin: 10px 0;
		padding: 20px 0;
	}
	.title{
		width: 100%;
		border: 1px solid #f0860c;
		color: #f0860c;
	}
	.photo{
		height: 160px;
		width: 120px;
		border-radius: 5px; /* 所有角都使用半径为5px的圆角，此属性为CSS3标准属性 */
		-moz-border-radius: 5px; /* Mozilla浏览器的私有属性 */
		-webkit-border-radius: 5px; /* Webkit浏览器的私有属性 */
		border-radius: 5px; /* 四个半径值分别是左上角、右上角、右下角和左下角 */
	}
	.photo2{
		height: 300px;
		width: 400px;
		border-radius: 5px; /* 所有角都使用半径为5px的圆角，此属性为CSS3标准属性 */
		-moz-border-radius: 5px; /* Mozilla浏览器的私有属性 */
		-webkit-border-radius: 5px; /* Webkit浏览器的私有属性 */
		border-radius: 5px; /* 四个半径值分别是左上角、右上角、右下角和左下角 */
	}
	.item{
		display: inline-block;
		margin: 5px;
	}
</style>
		<div align="center">
		<table style="width: 80%;margin: 10px" border="1px">
			<tr><td>姓名</td><td>${teamMemberTmp.name}</td></tr>
			<tr><td>电话</td><td>${teamMemberTmp.phone}</td></tr>
			<tr><td>角色</td><td><c:if test="${teamMemberTmp.role == '1'}">领队</c:if>
				<c:if test="${teamMemberTmp.role == '2'}">教练</c:if>
				<c:if test="${teamMemberTmp.role == '3'}">队员</c:if></td></tr>
			<tr><td>身份证号</td><td>${teamMemberTmp.cardNo}</td></tr>
			<tr><td>球衣号</td><td>${teamMemberTmp.playerNum}</td></tr>
			<tr><td>身高</td><td>${teamMemberTmp.height}</td></tr>
			<tr><td>鞋码</td><td>${teamMemberTmp.weight}</td></tr>
			<tr><td>头像</td><td><script>
				var teacherImg = "${teamMemberTmp.remarks}";
				var img1 = teacherImg.split(";")[0];
				document.write("<div><img src='"+img1+"' class='photo'/></div>");
			</script></td></tr>
			<tr><td>身份证</td><td><script>
				var teacherImg = "${teamMemberTmp.remarks}";
				var img2 = teacherImg.split(";")[1];
				document.write("<div><img src='"+img2+"' class='photo2'/></div>");
			</script></td></tr>
		</table>
			</div>
</body>
</html>

