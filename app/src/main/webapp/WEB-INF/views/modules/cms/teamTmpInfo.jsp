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
		height: 120px;
		width: 90px;
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
	<tr>
		<td colspan="4" align="center" style="font-size: 30px;">
			2016青草足球夏季联赛
		</td>
	</tr>
	<tr>
		<td>球队名称</td><td>${team.name}</td><td>组别</td><td>${team.zb}</td>
	</tr>
	<tr>
		<td>制式</td><td><c:if test="${team.rz == '5'}">五人制</c:if><c:if test="${team.rz == '8'}">八人制</c:if></td><td>联系方式</td><td>${leader.phone}</td>
	</tr>
	<tr>
		<td align="center">
			<div><script>
				var leaderImg = "${leader.remarks}";
				var img = leaderImg.split(";")[0];
				document.write("<img src='"+img+"' class='photo' onclick='jumpToInfo(\"${leader.id}\")'/>");
			</script></div>
			<div>领队：${leader.name}</div>
			<div>${leader.cardNo}</div>
		</td>
		<td align="center">
			<div>
				<script>
					var teacherImg = "${teacher.remarks}";
					var img = teacherImg.split(";")[0];
					document.write("<img src='" + img + "' class='photo' onclick='jumpToInfo(\"${teacher.id}\")'/>");
				</script>
			</div>
		<div>教练：${teacher.name}</div>
		<div>${teacher.cardNo}</div>
		</td>
		<td align="center">
			<div>
				<script>
					var teacherImg = "${captain.remarks}";
					var img = teacherImg.split(";")[0];
					document.write("<img src='" + img + "' class='photo' onclick='jumpToInfo(\"${captain.id}\")'/>");
				</script>
			</div>
			<div>队长：${captain.name}</div>
			<div>${captain.cardNo}</div>
		</td>
	</tr>
	<tr><td colspan="4" align="center">参赛成员基本信息</td></tr>
	<tr>
		<c:forEach items="${members}" var="member" varStatus="status">
			<td align="center">
				<div><script>
					var img = "${member.remarks}";
					var i = img.split(";")[0];
					document.write("<img src='"+i+"' class='photo' onclick='jumpToInfo(\"${member.id}\")'/>");
				</script></div>
				<%--<div>序号：${status.count}</div>--%>
				<div>${member.playerNum}-${member.name}</div>
				<div>身高：${member.height} 鞋码：${member.weight}手机号：${member.phone}</div>
				<div>${member.cardNo}</div>
			</td>
			<c:if test="${status.count%4==0}">
				</tr>
				<tr>
			</c:if>
		</c:forEach>
	</tr>
</table>
	<c:forEach items="${counts}" var="count">
		<c:if test="${count.role == '1'}">
			领队：${count.num}人；
		</c:if>
		<c:if test="${count.role == '2'}">
			教练：${count.num}人；
		</c:if>
		<c:if test="${count.role == '3'}">
			队员：${count.num}人；
		</c:if>
		<c:if test="${count.role == '9'}">
			总计：${count.num}人
		</c:if>
	</c:forEach>
</div>
<script>
	function jumpToInfo(id){
		window.open("${ctx}/cms/teamMemberTmp/info?id="+id,"_blank");
	}
</script>
</body>
</html>