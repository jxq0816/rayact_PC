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
	.title{
		width: 100%;
		border: 1px solid #000000;
		color: #000000;
	}
</style>
<div style="width: 100%;text-align: center">
	<p style="color: #000000;font-size: 28px;margin: 50px 0;line-height:30px">2016青草足球夏季联赛报名</p>
</div>
<div id="index" class="step">
			<div style="font-size: 30px;text-align: center">
					<div class="s1" onclick="jumpToInfo1();">竞赛章程</div>
					<div class="s1" onclick="jumpToInfo2();">报名须知</div>
					<div class="s1" onclick="jumpToApply();">领队报名</div>
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
<div class="step" style="display: none" id="info1">
			<div style="text-align: center">
				<div class="s1" style="font-size: 30px;">竞赛章程</div>
				<div>
					<h3>日程安排</h3>
					<div>
					——2016/07/31——</br>
					五人制招募截止</br>
					——2016/08/03-2016/08/04——</br>
					五人制比赛日</br>
					八人制报名截止</br>
					——2016/08/07-2016/08/12——</br>
					八人制比赛日</br>
					</div>
					<h3>比赛赛制</h3>
					<div>
					——五人制——</br>
					U18（1997.9.1以后出生）</br>
					64支球队</br>
					U16（2000.1.1以后出生）</br>
					32支球队</br>
					小组4队交叉对阵</br>
					第二轮比赛为首场胜者对负者</br>
					第二轮所产生的负者被淘汰</br>
					——八人制——</br>
					U23（1993.1.1以后出生）</br>
					16-24支球队</br>
					U18（1998.1.1以后出生）</br>
					16-24支球队</br>
					小组赛为单循环</br>
					后两名淘汰</br>
					淘汰赛交叉淘汰</br>
					直至决出冠军</br>
					——P.S.——</br>
					为了让更多高三毕业生参与比赛</br>
					五人制U18年龄限制调整为97年</br>
					</div>
					<h3>比赛场地</h3>
					<div>
					东单体育中心（五人制）
					四得公园足球场（八人制）
					</div>
				</div>
				<div class="s1" style="font-size: 30px;" onclick="backToIndex();">返回主页</div>
			</div>
</div>
	<div class="step" style="display: none" id="info2">
		<div style="text-align: center">
				<div class="s1" style="font-size: 30px;">报名须知</div>
				<div>五人制比赛报名人数为最少5人最多10人</br>
					需缴纳500元保证金+每人50元报名费</br>
					八人制比赛报名人数最少为10人最多无上限</br>
					需缴纳500元保证金+每人100元报名费</br>
					队伍领队必须为18周岁以上成年人，可由队员兼任</br>
					报名队员不限性别（可混报）不限国籍</br>
					证件照为白底电子版，可拍照</br>
					身份证件电子版需要清晰无误，可拍照</br>
				</div>
				<div class="s1" style="font-size: 30px;" onclick="backToIndex();">返回主页</div>
		</div>
	</div>
<script>
	function jumpToApply(){
		window.open("${ctx}/cms/teamTmp/formToApp","_self");
	}
	function jumpToInfo(){
		$("#search").show();
		$("#index").hide();
		$("#info1").hide();
		$("#info2").hide();
	}
	function jumpToInfo1(){
		$("#search").hide();
		$("#index").hide();
		$("#info1").show();
		$("#info2").hide();
	}
	function jumpToInfo2(){
		$("#search").hide();
		$("#index").hide();
		$("#info1").hide();
		$("#info2").show();
	}
	function backToIndex(){
		$("#search").hide();
		$("#index").show();
		$("#info1").hide();
		$("#info2").hide();
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