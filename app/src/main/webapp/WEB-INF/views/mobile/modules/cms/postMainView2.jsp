<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-form/jquery.form.js" type="text/javascript"></script>
	<script src="${ctxStatic}/remodal/remodal.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/sinaEmotion/jquery.sinaEmotion.js" type="text/javascript"></script>
	<link href="${ctxStatic}/remodal/remodal.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/remodal/remodal-default-theme.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/bootstrap/2.3.1/css_default/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/sinaEmotion/jquery.sinaEmotion.css" />
</head>
<body style="width:98%;font-family: Microsoft Yahei;margin: 0 3px">
<%@include file="/WEB-INF/views/include/upload.jsp" %>
<style>
	.row{
		margin-left: 0;
	}
	.round {
		width:60px; height:60px;
		-moz-border-radius: 30px;      /* Gecko browsers */
		-webkit-border-radius: 30px;   /* Webkit browsers */
		border-radius:30px;            /* W3C syntax */
	}
	.subject{
		font-size:25px;
		line-height:28px;
		width: 79%;
		margin:2px 2px;
		display: inline-block;
		word-wrap: break-word;
		font-weight:bold;
		float: right;
	}
	.postli{
		list-style-type:none;
		padding-bottom: 2px;
		padding-top: 2px;
		border-bottom: 1px solid #C8C8C8;
	}
	.after_img{
		display: inline-block;position: relative;bottom: -10px;line-height: 20px
	}
	.post_content{
		margin-left:60px;
		line-height: 150%;
		font-size: 15px
	}
	.ptp_name{
		color:#7a7a7a;
		margin-top:5px;
		font-size: 10px;
	}
	.ptp_content{
		font-size: 15px;
		display: inline-block;
		color:#1e1e1e;
	}
	.btnsend{
		width: 17%;
		border: 1px solid #f0860c;
		border-radius: 4px;
		background-color:#ffffff;
		text-align: center;
		height: 25px;
		color:#f0860c;
		margin: 3px;
		float: right
	}
	.btnclear{
		width: 17%;
		border: 1px solid #f0860c;
		border-radius: 4px;
		text-align: center;
		height: 25px;
		color:#f0860c;
		margin: 3px;
		float: left
	}
</style>
<div class="row main_content">
	<div class="row">
		<div style="margin:2px 0px;display: inline-block">
			<img class="round" src="${postMain.createBy.photo}" name="${postMain.createBy.id}" onclick="jumpToInfo(this)">
		</div>
		<div class="subject">${postMain.subject}</div>
		<div style="display: inline-block;line-height: 35px;border-bottom: 1px solid #c8c8c8;width: 100%">
			<span style="color: #f0860c;font-size: 15px">${postMain.createBy.name}</span>
			<span style="color:#8C8C8C;font-size: 15px">&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${postMain.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
		</div>
		<div style="line-height: 150%;font-size: 16px;color:#555555;margin-top: 15px;word-wrap:break-word;word-break:break-all;">${postMain.content}</div>
		<c:forEach items="${imgs}" var="url">
			<img src="${url}" width="100%"/>
		</c:forEach>
	</div>
</div>
<div class="postSubject"><div style="border-left:5px solid #f0860c;border-right:2px solid #f0860c;display:inline-block;margin-right: 3px">&nbsp;</div>回帖<span id="count">(${count})</span></div>
<div style="width: 100%;font-size: 17px;height: 100px;margin-top: 5px">
	<form id="postData" name="post" enctype="multipart/form-data">
		<textarea  name="content" class="emotion" style="width: 100%;margin-bottom: 0;" placeholder="我来回个帖"></textarea>
		<input type="hidden" name="remarks"/>
		<input type="hidden" name="postId"/>
		<input type="hidden" name="ptpId"/>
		<input id="files" name="files" type="file" multiple="multiple" style="display: none" accept="image/png,image/gif,image/jpeg" onchange="previewPic(this)"/>
		<input type="hidden" name="postMain.id" value="${postMain.id}"/>
		<a href="#modal" style="display: none" class="alertBtn"></a>
		<div class="btnsend" onclick="postToMain();">发送</div>
		<div class="btnclear"  onclick="postClear();">清空</div>
		<div id="face" class="btnclear">表情</div>
		<div id="pic" class="btnclear" onclick="getFiles()">图片</div>
	</form>
</div>
<div class="pic_preview" style="background-color: #ffffff;margin: 2px">
</div>
<div id="wrapper" style="background: #ffffff">
	<ul style="margin-left: 0;margin-bottom: 0">
	</ul>
</div>
<div style="width: 100%;text-align: center;display: none;height:35px" class="more">
	加载更多......
</div>
<div class="remodal" data-remodal-id="modal">
	<p class="alterInfo">

	</p>
	<br>
	<button data-remodal-action="confirm" class="remodal-confirm" style="background-color: #f0860c;">确定</button>
</div>
<img src="${ctxStatic}/images/bianji@2x.png" style="position: fixed;bottom: 10px;right: 10px" onclick="postFocus()"/>
<script type="text/javascript">
	var sum = "${count}";
	// 绑定表情
	$('#face').SinaEmotion($('.emotion'));
	var client = "${param.client}";
	var f_flag = true;
	var x = 0;
	var pageNo = 0;
	var count = 10;
	var postMainId = "${postMain.id}";
	//选择文件
	function getFiles(){
		$("input[name='files']").click();
	}
	var previewrHeight = 100;
	var previewrWidth = 100;
	function previewPic(dom){
		$(".pic_preview").html("");
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
				$(".pic_preview").append(img);
			}
			reader.readAsDataURL(dom.files[i]);
		}
	}
	function autoSize(width, height) {
		var scale = width / height;
//		 if (scale >= previewrWidth / previewrHeight) {
//			 height = previewrWidth / scale;
//			 width = previewrWidth;
//			 } else {
		width = previewrHeight * scale;
		height = previewrHeight;
		// }
		return {
			width: width,
			height: height
		}
	}

	$(function(){
		var app_id = '1708665333';
		$.ajax( {
			dataType : 'jsonp',
			url : 'https://api.weibo.com/2/emotions.json?source=' + app_id ,
			success : function(response) {
				var data = response.data;
				for ( var i in data) {
					if (data[i].category == '') {
						data[i].category = '默认';
					}
					if (emotions[data[i].category] == undefined) {
						emotions[data[i].category] = new Array();
						categorys.push(data[i].category);
					}
					emotions[data[i].category].push( {
						name : data[i].phrase,
						icon : data[i].icon
					});
					uSinaEmotionsHt.put(data[i].phrase, data[i].icon);
				}
				loadMore();
			}
		});
	});
	function postClear(){
		$("textarea[name='content']").val('');
		$("input[name='postId']").val("");
		$("input[name='ptpId']").val("");
		$("input[name='remarks']").val("");
		$("textarea[name='content']").attr("placeholder","我来回个帖");
		$(".pic_preview").html("");
		var file = $("#files");
		file.after(file.clone().val(""));
		file.remove();
	}
	function postFocus(){
		$("textarea[name='content']").focus();
	}
	function loadMore(){
		$('.more').show();
		pageNo++;
		$.post("${ctx}/cms/post/app/list?postMain.id="+postMainId,{pageNo:pageNo,pageSize:10},function(data){
			var rtn = eval(data);
			var length = rtn.length;
			if(length <= 0){
				$(".more").html("没有更多了");
				setTimeout("$('.more').hide();",2000);
			}else{
				$('.more').hide();
			}
			for(var i = 0;i<length;i++){
				x++;
				var tmp = rtn[i];
				var tmpInner = tmp.pop;
				var htmlInner = "";
				if(tmpInner){
					for(var j=0;j<tmpInner.length;j++){
						htmlInner+="<div class='ptp_name' name='"+tmpInner[j].createByName+"' onclick='ptpToForm(this);' id='"+tmpInner[j].id+"'>"+tmpInner[j].createByName+":<span class='ptp_content'>"+AnalyticEmotion(tmpInner[j].content)+"</span></div>";
					}
				}
				var html="<li class='row postli' name='"+tmp.pname+"' id='"+tmp.id+"'>"+
						"<div class='row' style='display: block'>"+
						"<div style='display: inline-block'>"+
						"<img src='"+tmp.photo+"' class='round' name='"+tmp.pid+"' onclick='jumpToInfo(this)'>"+
						"</div>"+
						"<div class='after_img'>"+
						"<span style='color: #f0860c;font-size: 15px'>"+tmp.pname+"</span>"+
						"<br>"+
						"<span style='color:#7a7a7a; font-size: 15px'> 第"+x+"楼 &nbsp;&nbsp;&nbsp;&nbsp;"+tmp.time+"</span>"+
						"</div>"+
						"<img src='${ctxStatic}/images/btn-huifu-h@2x.png' height='25px' width='40px' style='float: right;display: inline-block;' onclick='postToForm(this);'/>"+
						"<div class='post_content'>"+AnalyticEmotion(tmp.content)+"</div>"+
						"</div>"+
						"<div  name='"+tmp.id+"' class='ptp'  style='margin-left:60px;line-height:30px'>"+htmlInner+
						"</div>"+
						"</li>";
				$("#wrapper ul").append(html);
			}
		});
	}
	function ptpToForm(dom){
		var name = $(dom).attr("name");
		var postId = $(dom).closest("li").attr("id");
		var ptpId = $(dom).attr("id");
		$("textarea[name='content']").val("@"+name+":");
		$("input[name='postId']").val(postId);
		$("input[name='ptpId']").val(ptpId);
		$("textarea[name='content']").focus();
	}
	function postToForm(dom){
		var name = $(dom).closest("li").attr("name");
		var postId = $(dom).closest("li").attr("id");
		$("textarea[name='content']").val("@"+name+":");
		$("input[name='postId']").val(postId);
		$("textarea[name='content']").focus();
	}
	function postToMain(){
		if($.trim($("textarea[name='content']").val())==""){
			$(".alterInfo").html("请输入内容");
			$(".alertBtn")[0].click();
		}else{
			var options={
				type:"post",
				url:"${ctx}/cms/post/app/save;JSESSIONID=${sid}",
				async: false,
				enctype:"multipart/form-data",
				iframe:true,
				dataType:"json",
				error:function(data){
					alert(data);
				},
				success:function(data) {
					$(".alterInfo").html(data.msg);
					$(".alertBtn")[0].click();
					f_flag = true;
					x = 0;
					pageNo = 0;
					sum++;
					$("#wrapper ul").html("");
					$("#count").html(sum);
					$("textarea[name='content']").attr("placeholder", "我来回个帖");
					$("textarea[name='content']").val('');
					$("input[name='postId']").val("");
					$("input[name='ptpId']").val("");
					$(".pic_preview").html("");
					var file = $("#files");
					file.after(file.clone().val(""));
					file.remove();
					loadMore();
				}
			};
			$('#postData').ajaxSubmit(options);
		}
	}
	$(window).scroll(function(){
		console.log($("body").height());
		console.log($(window).scrollTop());
		console.log(screen.height);
		var scrollTop = $(window).scrollTop();
		var scrollHeight =screen.height;
		var windowHeight = $("body").height();
//		if(scrollTop + scrollHeight - 110 == windowHeight){
//			loadMore();
//		}
		if(scrollTop + scrollHeight  == windowHeight){
			loadMore();
		}
	});
</script>
</body>
</html>