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
		margin-top: 40px;
	}
	.s1{
		display: inline-block;
		width: 100px;
	}
	.s2{
		display: inline-block;
		width: 120px;
	}
	.s3{
		display: inline-block;
		width: 110px;
	}
	.item{
		border-bottom: 1px solid #000000;
		margin: 5px 0;
	}
	.jump{
		height: 30px;
		text-align:center;
		font-size: 30px;
		padding: 10px;
		background-color: #000000;
		width: 40%;
		display: inline-block;
		margin: 5px;
		color: #ffffff;
	}
	#delBtn{
		height: 20px;
		width: 20px;
		text-align: center;
		border-radius: 10px; /* 所有角都使用半径为5px的圆角，此属性为CSS3标准属性 */
		-moz-border-radius: 10px; /* Mozilla浏览器的私有属性 */
		-webkit-border-radius: 10px; /* Webkit浏览器的私有属性 */
		border-radius: 10px; /* 四个半径值分别是左上角、右上角、右下角和左下角 */
	}
</style>
<div style="width: 100%;text-align: center">
	<p style="color: #000000;font-size: 28px;margin: 50px 0;line-height:30px">2016青草足球夏季联赛报名</p>
</div>
<form id="teamForm" name="teamForm">
	<div id="step01" class="step">
		<div style="font-size: 15px;">
			<div>
				<div class="s1">队名：</div><input id="teamName" type="text" name="teamName" onchange="checkSame(this)">
			</div>
			<div>
				<div class="s1">参赛组别：</div><select id="zb" name="zb">
				<option value="U16">U16</option>
				<option value="U18">U18</option>
				<option value="U23">U23</option>
			</select>
			</div>
			<div>
				<div class="s1">参赛制式：</div><select id="rz" name="rz">
				<option value="5">五人制</option>
				<option value="8">八人制</option>
			</select>
			</div>
		</div>
		<div style="align:center;">
			<div onclick="step01next()" class="jump">下一步</div>
			<div onclick="backToIndex()" class="jump">返回主页</div>
		</div>
	</div>
	<div class="step" style="display: none" id="step02">
		<div style="font-size: 15px">
			<div class="item">
				<div >
					<div class="s2">领队姓名：</div><input type="text" name="leaderName" />
				</div>
				<div>
					<div class="s2">领队身份证号：</div><input type="text" name="leaderCardNo" />
				</div>
				<div>
					<div class="s2">领队电话：</div><input type="text" name="leaderPhone"/>
				</div>
				<div>
					<div class="s2">领队头像：</div><input id="leaderFiles" name="leaderFiles" type="file"  accept="image/png,image/gif,image/jpeg" onchange="previewPic(this,'.leaderfiles_preview')"/>
					<div class="leaderfiles_preview" style="background-color: #ffffff;margin: 2px">
					</div>
				</div>
				<div>
					<div class="s2">领队身份证正面：</div><input id="leaderCard" name="leaderCard" type="file"  accept="image/png,image/gif,image/jpeg" onchange="previewPic(this,'.leaderCard_preview')"/>
					<div class="leaderCard_preview" style="background-color: #ffffff;margin: 2px">
					</div>
				</div>
			</div>
			<div class="item">
				<div>
					<div class="s2">教练姓名：</div><input type="text" name="teacherName"/>
				</div>
				<div>
					<div class="s2">教练身份证：</div><input type="text" name="teacherCard" />
				</div>
				<div>
					<div class="s2">教练电话：</div><input type="text" name="teacherPhone"/>
				</div>
				<div>
					<div class="s2">教练头像：</div><input id="teacherFiles" name="teacherFiles" type="file"  accept="image/png,image/gif,image/jpeg" onchange="previewPic(this,'.teacherfiles_preview')"/>
					<div class="teacherfiles_preview" style="background-color: #ffffff;margin: 2px">
					</div>
				</div>
				<div>
					<div class="s2">教练身份证正面：</div><input id="teacherCard" name="teacherCard" type="file"  accept="image/png,image/gif,image/jpeg" onchange="previewPic(this,'.teacherCard_preview')"/>
					<div class="teacherCard_preview" style="background-color: #ffffff;margin: 2px">
					</div>
				</div>
			</div>
		</div>
		<div style="align:center;">
			<div onclick="step02prev()"  class="jump" >上一步</div>
			<div onclick="step02next()" class="jump"  >下一步</div>
		</div>
	</div>
	<div class="step" style="display: none" id="step03">
		<div id="dataWrap">
			<div class="item">
				<div>
					<div class="s3">姓名：</div><input type="text" name="name[0]" class=""/>
				</div>
				<div >
					<div class="s3">身份证：</div><input type="text" name="card[0]" class="" onchange="checkIdCard(this)">
				</div>
				<div>
					<div class="s3">电话：</div><input type="text" name="phone[0]" class=""/>
				</div>
				<div>
					<div class="s3">头像：</div><input id="files[0]" name="files[0]" type="file"  accept="image/png,image/gif,image/jpeg" onchange="previewPic(this,'.files_0_preview')"/>
					<div class="files_0_preview" style="background-color: #ffffff;margin: 2px">
					</div>
				</div>
				<div>
					<div class="s3">身份证正面：</div><input id="cardFile[0]" name="cardFile[0]" type="file"  accept="image/png,image/gif,image/jpeg" onchange="previewPic(this,'.card_0_preview')"/>
					<div class="card_0_preview" style="background-color: #ffffff;margin: 2px">
					</div>
				</div>
			</div>
		</div>
		<div >
			<div onclick="addMember()" style="height:25px;background-color: #000000;margin:20px 0;text-align:center;font-size: 20px;color: #ffffff;width: 100%">添加</div>
		</div>
		<div style="align:center">
			<div onclick="step03prev()" class="jump" >上一步</div>
			<div onclick="submitData(this)" class="jump" >提交</div>
		</div>
	</div>
</form>
<script>
	var index = 0;
	function step01next(){
		var rz = $("#rz").val();
		var zb = $("#zb").val();
		var teamName = $("#teamName").val();
		if((rz=="5"&&zb=="U23")||(rz=="8"&&zb=="U16")){
			alert("五人制只能报U16和U18,八人制只能报U18和U23！");
		}else if($.trim(teamName)==""){
			alert("请填写队名！");
		}else{
			$("#step01").hide();
			$("#step02").show();
			$("#step03").hide();
		}
	}
	function step02prev(){
		$("#step01").show();
		$("#step02").hide();
		$("#step03").hide();
	}
	function step02next(){
		var leaderFlag = true;
		$("input[name^='leader']").each(function(){
			if($(this).val()==""){
				leaderFlag = false
			}
		});
		if(!leaderFlag){
			alert("领队信息需要完善！");
			return;
		}
		var leader = $("input[name='leaderCardNo']").val();
		//var teacher = $("input[name='teacherCard']").val();
		if($.trim(leader)==""){
			alert("请填写领队身份证号码！");
			return;
		}
		if(checkIdcard(leader,'1')){
			$("#step01").hide();
			$("#step02").hide();
			$("#step03").show();
		}else{
			alert("身份证号码有误！");
		}
	}
	function step03prev(){
		$("#step01").hide();
		$("#step02").show();
		$("#step03").hide();
	}
	function submitData(dom){
		var allFlag = true;
		var rightFlag = true;
		$("#dataWrap input").each(function(){
			if($(this).val()==""){
				allFlag=false;
			}
		});
		if(!allFlag){
			alert("请完善队员信息。");
			return;
		}
		var zb = $("#zb").val();
		var rz = $("#rz").val();
		$("input[name^='card'][type='text']").each(function(){
			if(!checkIdcard($(this).val(),'3',zb,rz)){
				rightFlag = false;
			}
		});
		if(!rightFlag){
			alert("请检查身份证信息。");
			return;
		}
		if(allFlag&&rightFlag){
			$(dom).attr("onclick","");
			var options={
				type:"post",
				url:"${ctx}/cms/teamTmp/app/save?memberNum="+index,
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
			if(rz=="5"){
				if(confirm("报名需缴纳500元保证金+每人50元报名费</br>本次报名暂无修改入口，您确认信息填写无误吗？")){
					$('#teamForm').ajaxSubmit(options);
				}
			}else{
				if(confirm("报名需缴纳500元保证金+每人100元报名费</br>本次报名暂无修改入口，您确认信息填写无误吗？")){
					$('#teamForm').ajaxSubmit(options);
				}
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
	function addMember(){
		$("#delBtn").remove();
		index++;
		var html = "<div class='item'><div id='delBtn' style='float: right;background-color:#000000 ' onclick='deleteItem(this);'>X</div><div>"+
				"<div class='s3'>姓名：</div><input type='text' name='name["+index+"]' class=''/>"+
				"</div>"+
				"<div>"+
				"		<div class='s3'>身份证：</div><input type='text' name='card["+index+"]' class=''>"+
				"</div>"+
				"<div>"+
				"		<div class='s3'>电话：</div><input type='text' name='phone["+index+"]' class=''/>"+
				"</div>"+
				"<div>"+
				"		<div class='s3'>头像：</div><input id='files[0]' name='files["+index+"]' type='file'  accept='image/png,image/gif,image/jpeg' onchange='previewPic(this,\".files_"+index+"_preview\")'/>"+
				"<div class='files_"+index+"_preview' style='background-color: #ffffff;margin: 2px'>"+
				"	</div>"+
				"		</div>"+
				"		<div>"+
				"		<div class='s3'>身份证正面：</div><input id='cardFile["+index+"]' name='cardFile["+index+"]' type='file'  accept='image/png,image/gif,image/jpeg' onchange='previewPic(this,\".card_"+index+"_preview\")'/>"+
				"<div class='card_"+index+"_preview' style='background-color: #ffffff;margin: 2px'>"+
				"		</div>"+
				"		</div></div>";
		$("#dataWrap").append(html);
	}

	function checkIdcard(num,role,zb,rz)
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
			if('19'+arrSplit[2]>'1998'&&role=="1"){
				alert("领队或教练必须在18岁以上");
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
			if(arrSplit[2]>'1998'&&role=="1"){
				alert("领队或教练必须在18岁以上");
				return false;
			}
			if(zb=="U16"&&arrSplit[2]<'2000'&&role=="3"){
				alert("U16需要队员身份证的出生日期在2000.01.01以后");
				return false;
			}
			if(zb=="U23"&&arrSplit[2]<'1993'&&role=="3"){
				alert("U23需要队员身份证的出生日期在1993.01.01以后");
				return false;
			}
			if(rz=="5"&&zb=="U18"&&(arrSplit[2]<'1997'||arrSplit[2]=='1997'&&arrSplit[3]<'09')&&role=="3"){
				alert("U18五人制需要队员身份证的出生日期在1997.09.01以后");
				return false;
			}
			if(rz=="8"&&zb=="U18"&&arrSplit[2]<'1998'&&role=="3"){
				alert("U18八人制需要队员身份证的出生日期在1998.01.01以后");
				return false;
			}
			var bGoodDay;
			bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
			if (!bGoodDay)
			{
//alert(dtmBirth.getYear());
//alert(arrSplit[2]);
//alert('输入的身份证号里出生日期不对！');
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
//alert('18位身份证的校验码不正确！应该为：' + valnum);
					return false;
				}
				return true;
			}
		}
		return false;
	}

	function deleteItem(dom){
		$(dom).parent().prev().prepend("<div id='delBtn' style='float: right;background-color:#000000 ' onclick='deleteItem(this);'>X</div>");
		$(dom).parent().remove();
		index--;
	}

	function checkSame(dom){
		var name = $(dom).val();
		if($.trim(name)!=""){
			$.get("${ctx}/cms/teamTmp/app/checkSame?name="+name,function(data){
				if(data=="true"){
					$(dom).val("");
					alert("已有同名的队伍了！");
				}
			})
		}
	}
	function checkIdCard(dom){
		var card = $(dom).val();
		var rz = $("#rz").val();
		if($.trim(card)!=""){
			$.get("${ctx}/cms/teamMemberTmp/app/checkIdCard?card="+card+"&rz="+rz,function(data){
				if(data=="true"){
					$(dom).val("");
					alert("该身份证信息已经在同一组别中报过名了！");
				}
			})
		}
	}
	function backToIndex(){
		window.open("${ctx}/cms/teamTmp/teamIndex","_self");
	}
</script>
</body>
</html>