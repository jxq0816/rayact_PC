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
	<script src="${ctxStatic}/common/checkMobile.js" type="text/javascript"></script>
	<script src="${ctxStatic}/common/checkIdCard.js" type="text/javascript"></script>
	<script src="${ctxStatic}/common/previewPic.js" type="text/javascript"></script>
	<link href="${ctxStatic}/jquery-mobile/list.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/bootstrap/bootstrap-3.3.5-dist/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
</head>
<body style="font-family: Microsoft Yahei">
<style>
	.row{
		margin-bottom: 20px;
		margin-right: 0px;
		margin-left: 0px;
	}
	.btn-primary{
		height: 40px;
		line-height: 40px;
		text-align: center;
	}
</style>
<div style="width: 100%;text-align: center">
	<p style="color: #000000;font-size: 28px;margin: 50px 0;line-height:30px">2016青草足球秋季联赛报名</p>
</div>
<form id="teamForm" name="teamForm">
	<div id="step01">
		<div style="font-size: 15px;">
			<div class="row col-xs-12">
				<div class="col-xs-4">队名：</div>
				<div class="col-xs-8">
					<input id="teamName" class="form-control" type="text" name="teamName" onchange="checkSame(this)">
				</div>
			</div>
			<div class="row col-xs-12">
				<div class="col-xs-4">参赛组别：</div>
				<div class="col-xs-8">
					<select id="zb" name="zb" style="width:100%">
						<option value="U23">U23</option>
					</select>
				</div>
			</div>
			<div class="row col-xs-12">
				<div class="col-xs-4">参赛制式：</div>
				<div class="col-xs-8">
					<select id="rz" name="rz" style="width:100%">
						<option value="8">八人制</option>
					</select>
				</div>
			</div>
			<div id="teamDataWrap">
				<div class="row col-xs-12">
					<div class="col-xs-4">队徽：</div>
					<div class="col-xs-8">
						<input id="logoFiles" name="logoFiles" type="file"  accept="image/png,image/gif,image/jpeg" onchange="previewPic(this,'.logoFiles_preview')"/>
					</div>
				</div>
				<div class="row logoFiles_preview" style="background-color: #ffffff;">
				</div>
			</div>
		</div>
		<div class="row col-xs-12" style="align:center;">
			<div onclick="step01next()" class="btn-primary col-xs-5">下一步</div>
			<div class="col-xs-2"></div>
			<div onclick="backToIndex()" class="btn-primary col-xs-5">返回主页</div>
		</div>
	</div>
	<div class="step" style="display: none" id="step02">
		<div style="font-size: 15px">
			<div class="row" id="baseDataWrap">
				<div class="row col-xs-12">
					<div class="col-xs-12">
						<label class="checkbox-inline">
							<input type="checkbox" checked="true" name="role" value="1"> 领队
						</label>
						<label class="checkbox-inline">
							<input type="checkbox" name="role" value="2"> 教练
						</label>
						<label class="checkbox-inline">
							<input type="checkbox"  name="role" value="3"> 普通队员
						</label>
						<label class="checkbox-inline">
							<input type="checkbox"  name="role" value="4"> 队长
						</label>
					</div>
				</div>
				<div class="row col-xs-12">
					<div class="col-xs-4">姓名：</div>
					<div class="col-xs-8">
						<input type="text" class="form-control" name="name" />
					</div>
				</div>
				<div class="row col-xs-12">
					<div class="col-xs-4">球衣号码：</div>
					<div class="col-xs-8">
						<input type="text" class="form-control" name="playerNum"/>
					</div>
				</div>
				<div class="row col-xs-12">
					<div class="col-xs-4">身份证号：</div>
					<div class="col-xs-8">
						<input type="text" id="idNo" name="idNo" class="form-control"/>
					</div>
				</div>
				<div class="row col-xs-12">
					<div class="col-xs-4">手机号：</div>
					<div class="col-xs-8">
						<input type="text" id="phone" name="phone" class="form-control"/>
					</div>
				</div>
				<div class="row col-xs-12">
					<div class="col-xs-4">身高：</div>
					<div class="col-xs-8">
						<input type="text" name="height" class="form-control"/>
					</div>
				</div>
				<div class="row col-xs-12">
					<div class="col-xs-4">鞋码：</div>
					<div class="col-xs-8">
						<input type="text" name="weight" class="form-control"/>
					</div>
				</div>
				<div class="row col-xs-12">
					<div class="col-xs-4">白底一寸免冠照片：</div>
					<div class="col-xs-2">
						<input id="leaderFiles" name="picFiles" type="file"  accept="image/png,image/gif,image/jpeg" onchange="previewPic(this,'.picFiles_preview')"/>
					</div>
				</div>
				<div class="picFiles_preview row" style="background-color: #ffffff;margin: 2px">

				</div>

				<div class="row col-xs-12">
					<div class="col-xs-4">白底一寸免冠照片：</div>
					<div class="col-xs-2">
						<input id="idCardFiles" name="idCardFiles" type="file"  accept="image/png,image/gif,image/jpeg" onchange="previewPic(this,'.idCardFiles_preview')"/>
					</div>
				</div>
				<div class="idCardFiles_preview row" style="background-color: #ffffff;margin: 2px">
				</div>

			</div>
		</div>
		<div class="row col-xs-12" style="align:center;">
			<div onclick="step02prev()" class="btn-primary col-xs-5">上一步</div>
			<div class="col-xs-2"></div>
			<div onclick="submitData(this)" class="btn-primary col-xs-5">保存</div>
		</div>
	</div>
</form>
<script>

</script>
</body>
</html>