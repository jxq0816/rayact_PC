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
	<div class="step">
		<div style="font-size: 15px">
			<div class="row" id="baseDataWrap">
				<div class="row col-xs-12">
					<div class="col-xs-4">球队：</div>
					<div class="col-xs-8">
						<sys:select cssClass="input-large" name="teamTmp.id" id="team"
									cssStyle="width:100%"
									value="${query.teamTmp.id}"
									items="${teamList}" itemLabel="name" itemValue="id"
									defaultLabel="----请选择-----"
									defaultValue=""></sys:select>
					</div>
				</div>
				<div class="row col-xs-12">
					<div class="col-xs-12">
						<label class="checkbox-inline">
							<input type="checkbox" name="role" value="1"> 领队
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
						<input type="text" class="form-control" name="name" value="${query.name}"/>
					</div>
				</div>
				<div class="row col-xs-12">
					<div class="col-xs-4">球衣号码：</div>
					<div class="col-xs-8">
						<input type="text" class="form-control" name="playerNum" value="${query.playerNum}"/>
					</div>
				</div>
				<div class="row col-xs-12">
					<div class="col-xs-4">身份证号：</div>
					<div class="col-xs-8">
						<input type="text" name="idNo" class="form-control" value="${query.cardNo}"/>
					</div>
				</div>
				<div class="row col-xs-12">
					<div class="col-xs-4">联系电话：</div>
					<div class="col-xs-8">
						<input type="text" name="phone" class="form-control" value="${query.phone}"/>
					</div>
				</div>
				<div class="row col-xs-12">
					<div class="col-xs-4">身高：</div>
					<div class="col-xs-8">
						<input type="text" name="height" class="form-control" value="${query.height}"/>
					</div>
				</div>
				<div class="row col-xs-12">
					<div class="col-xs-4">鞋码：</div>
					<div class="col-xs-8">
						<input type="text" name="weight" class="form-control" value="${query.weight}"/>
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
			<div onclick="submitData(this)" class="btn-primary col-xs-5">保存</div>
			<div class="col-xs-2"></div>
			<div onclick="backToIndex()" class="btn-primary col-xs-5">返回主页</div>
		</div>
	</div>
</form>
<script>
	var index = 0;
	function submitData(dom){
		var flag = true;
		$("#baseDataWrap input").each(function(){
			if($(this).val()==""){
				flag=false;
			}
		});
		if(!flag){
			alert("信息需要完善！");
			flag=false;
			return;
		}
		var idNo = $("input[name='idNo']").val();
		if($.trim(idNo)==""){
			alert("请填写身份证号码！");
			flag=false;
			return;
		}
		if(checkIdcard(idNo)==false){
			alert("身份证号码有误！");
			flag=false;
			return;
		}
		if(flag){
			$(dom).attr("onclick","");
			var options={
				type:"post",
				url:"${ctx}/cms/teamMemberTmp/app/save",
				async: false,
				enctype:"multipart/form-data",
				iframe:true,
				dataType:"json",
				error:function(data){
					alert(data);
				},
				success:function(data) {
					alert("申请成功！");
					window.open("${ctx}/cms/teamTmp/teamIndex","_self");
				}
			};
			if(confirm("您确认信息填写无误吗？")){
				$('#teamForm').ajaxSubmit(options);
			}
		}
	}

	var previewrHeight = 100;
	var previewrWidth = 100;
	function previewPic(dom,preClass){
		$(preClass).html("");
		for(var i = 0 ; i<dom.files.length ; i++){
			var reader = new FileReader();
			var img;
			reader.onload = function(e){
				img = document.createElement("img")
				img.src = e.target.result;
				$(img).load(function(){
					var size = autoSize(this.naturalWidth, this.naturalHeight);
					$(this).css({
						width: size.width,
						height: size.height,
						margin: 2,
						top: (previewrHeight - size.height) / 2,
						left: (previewrWidth - size.width) / 2
					}).show();
				});
				$(preClass).append(img);
			}
			reader.readAsDataURL(dom.files[i]);
		}
	}
	function autoSize(width, height) {
		var scale = width / height;
		width = previewrHeight * scale;
		height = previewrHeight;
		return {
			width: width,
			height: height
		}
	}

	function checkIdcard(num)
	{
		num = num.toUpperCase();
//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
		if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num)))
		{
//alert('输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。');
			return false;
		}
//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
//下面分别分析出生日期和校验位
		var len, re;
		len = num.length;
		if (len == 15)
		{
			re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
			var arrSplit = num.match(re);

//检查生日日期是否正确
			var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
			if('19'+arrSplit[2]<'1992'){
				alert("必须在24岁以下");
				return false;
			}
			var bGoodDay;
			bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
			if (!bGoodDay)
			{
//alert('输入的身份证号里出生日期不对！');
				return false;
			}
			else
			{
//将15位身份证转成18位
//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
				var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
				var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
				var nTemp = 0, i;
				num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
				for(i = 0; i < 17; i ++)
				{
					nTemp += num.substr(i, 1) * arrInt[i];
				}
				num += arrCh[nTemp % 11];
				return true;
			}
		}
		if (len == 18)
		{
			re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
			var arrSplit = num.match(re);

//检查生日日期是否正确
			var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
			if(arrSplit[2]>'1992'){
				alert("年龄必须在24岁以上");
				return false;
			}
			var bGoodDay;
			bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
			if (!bGoodDay)
			{
				return false;
			}
			else
			{
//检验18位身份证的校验码是否正确。
//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
				var valnum;
				var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
				var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
				var nTemp = 0, i;
				for(i = 0; i < 17; i ++)
				{
					nTemp += num.substr(i, 1) * arrInt[i];
				}
				valnum = arrCh[nTemp % 11];
				if (valnum != num.substr(17, 1))
				{
					return false;
				}
				return true;
			}
		}
		return false;
	}


	function checkSame(dom){
		var name = $(dom).val();
		var flag=false;
		if($.trim(name)!=""){
			$.get("${ctx}/cms/teamTmp/app/checkSame?name="+name,function(data){
				if(data=="true"){
					$(dom).val("");
					alert("已有同名的队伍了！");
					flag=true;
				}else{
					flag=false;
				}
			})
		}
		return flag;
	}
	function backToIndex(){
		window.open("${ctx}/cms/teamTmp/teamIndex","_self");
	}
</script>
</body>
</html>