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
		background-color: #000000;
		color: #ffffff;
		width: 100%;
		height: 30px;
		margin: 10px 0;
		padding: 20px 0;
	}
</style>
<div style="width: 100%;text-align: center">
	<p style="color: #000000;font-size: 28px;margin: 50px 0;line-height:30px">2016青草足球秋季联赛报名</p>
</div>
<div id="index" class="step">
	<div style="font-size: 30px;text-align: center">
		<div class="s1" onclick="jumpToApply();">创建球队</div>
		<div class="s1" onclick="jumpToJoin();">加入球队</div>
		<div class="s1" onclick="jumpToInfo();">已报名队伍查询</div>
	</div>
</div>
<div class="step" style="display: none" id="search">
	<div style="font-size: 30px;text-align: center">
		<div class="s1">输入队名或领队姓名：</div>
		<input type="text" name="searchIndex" style="width: 100%;height: 50px"/>
		<div class="s1" onclick="search()">查询</div>
		<div class="s1" onclick="backToIndex();">返回主页</div>
	</div>
</div>
<script>
	function jumpToApply(){
		window.open("${ctx}/cms/teamTmp/formToApp","_self");
	}
	function jumpToJoin(){
		window.open("${ctx}/cms/teamMemberTmp/join/team","_self");
	}
	function jumpToInfo(){
		$("#search").show();
		$("#index").hide();
	}
	function backToIndex(){
		$("#search").hide();
		$("#index").show();
	}
	function search(){
		var keyword = $("input[name='searchIndex']").val();
		if($.trim(keyword)==""){
			alert("请输入球队名称或领队姓名！");
		}else{
			window.open("${ctx}/cms/teamTmp/app/search?keyword="+keyword,"_self");
		}
	}
</script>
</body>
</html>