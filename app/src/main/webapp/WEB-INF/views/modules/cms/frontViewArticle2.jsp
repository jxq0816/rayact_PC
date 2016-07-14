<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${article.title}</title>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/remodal/remodal.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/remodal/remodal.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/remodal/remodal-default-theme.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/bootstrap/2.3.1/css_default/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<style>
		.reply{
			margin:2px 0px;
			padding: 4px 1px;
			border: 1px solid #dedede;
			background: #F6ECC6;
		}
		.row{
			margin-left: 0;
		}
		.round {
			width:60px;
			height:60px;
			-moz-border-radius: 30px;      /* Gecko browsers */
			-webkit-border-radius: 30px;   /* Webkit browsers */
			border-radius:30px;            /* W3C syntax */
		}
		.subject{
			font-size:25px;
			line-height:28px;
			width: 100%;
			word-wrap: break-word;
			font-weight:bold;
			text-align: left;
		}
		.sub_subject{
			border-bottom:1px solid #ddd;
			padding:10px;
			margin:15px 0;
			font-size:15px;
			line-height: 150%
		}
		.desc{
			color: #555555;
			font-size:14px;
			line-height: 150%;
			margin: 20px 0px
		}
		.a_content{
			width: 100%;
			line-height: 150%;
			font-size:15px;
			color:#555555;
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
			display: inline-block
		}
		.ptp_content{
			font-size: 15px;
			display: inline-block;
			color:#1e1e1e;
		}
		.c_item{
			margin-top: 20px;
			line-height: 50px;
			padding-bottom: 10px;
			border-bottom: 1px solid #C8C8C8;
		}
		.sub_item{
			display: inline-block;
			float:left;width: 80%;
			color: #1e1e1e;
			font-size: 30px;
			margin-left: 10px
		}
		.item_comment{
			color:#555555;display: inline-block;font-size: 40px;margin-left: 100px;
		}
	</style>
	<script>
		function jumpToInfo(dom){
			var id = $(dom).attr("name");
			iOSPhotoskipInfo(id);
		}
	</script>
</head>
<body style="font-family: Microsoft Yahei">
<div class="row">
	<div class="subject">${article.title}</div>
	<div class="sub_subject">
		<span style="color: #f0860c">${article.user.name}</span> &nbsp; <fmt:formatDate value="${article.createDate}" pattern="MM-dd HH:mm"/>
	</div>
	<c:if test="${not empty article.description}">
		<div class="desc">摘要：<span style="color: #8C8C8C;font-size: 14px">${article.description}</span>
		</div>
	</c:if>
	<div class="a_content">${article.articleData.content}</div>
</div>
<div class="row" style="border-top: 1px solid #c8c8c8;width: 100%;margin-top: 5px;padding-top: 5px">
	<div class="head postSubject">评论(${count})</div>
	<div id="wrapper" style="position: relative;top:5px;background: #ffffff">
		<ul style="margin-left: 0;margin-bottom: 0">
		</ul>
	</div>
</div>
<div style="width: 100%;text-align: center;display: none;height:35px" class="more">
	加载更多......
</div>
<div style="width: 100%;height:35px;background: #ffffff;">
</div>
<div style="position: fixed;bottom: 0;width: 100%;z-index: 100;height:35px;background: #ffffff;font-size: 17px ">
	<form name="post">
		<input type="text" style="width: 80%" name="content"/>
		<input type="hidden" name="remarks"/>
		<input type="hidden" name="replyId"/>
		<input type="hidden" name="contentId" value="${article.id}"/>
		<a href="#modal" style="display: none" class="alertBtn"></a>
		<div style="width: 17%;display: inline-block;border: 1px solid #cccccc;border-radius: 4px;text-align: center;margin-bottom: 13px;height: 23px;position: relative;bottom: 2px" onclick="postToMain();">发送</div>
	</form>
</div>
<div class="remodal" data-remodal-id="modal">
	<button data-remodal-action="close" class="remodal-close"></button>
	<p>提示</p>
	<p class="alterInfo">

	</p>
	<br>
	<button data-remodal-action="confirm" class="remodal-confirm">OK</button>
</div>
<script type="text/javascript">
	var f_flag = true;
	var pageNo = 0;
	var count = 5;
	var contentId = "${article.id}";
	$(function(){
		loadMore();
	});
	function loadMore(){
		$('.more').show();
		pageNo++;
		$.post("${ctx}/cms/comment/app/list;JSESSIONID=${sid}?contentId="+contentId,{pageNo:pageNo,pageSize:5},function(data){
			var rtn = eval(data);
			var length = rtn.length;
			if(length <= 0){
				$(".more").html("没有更多了");
				setTimeout("$('.more').hide();",2000);
			}else{
				$('.more').hide();
			}
			for(var i = 0;i<length;i++){
				count++;
				var tmp = rtn[i];
				var html="<li class='row postli' name='"+tmp.name+"' id='"+tmp.id+"' >"+
						"<div class='row' style='display: block'>"+
						"<div style='display: inline-block'>"+
						"<img src='"+tmp.photo+"' class='round' name='"+tmp.creatorId+"' onclick='jumpToInfo(this)'>"+
						"</div>"+
						"<div class='after_img'>"+
						"<span style='color: #f0860c;font-size: 15px'>"+tmp.name+"</span>"+
						"<br>"+
						"<span style='color:#7a7a7a; font-size: 15px'>"+tmp.createDate+"</span>"+
						"</div>"+
						"<img src='${ctxStatic}/images/btn-huifu-h@2x.png' height='25px' width='40px' style='float: right;display: inline-block;' onclick='postToForm(this);'/>"+
						"<div class='post_content'>"+tmp.content+"</div>"+
						"</div>"+
						"</li>";
				$("#wrapper ul").append(html);
			}
		});
	}
	function postToForm(dom){
		var name = $(dom).closest("li").attr("name");
		var cid = $(dom).closest("li").attr("id");
		var id = $(dom).attr("id");
		$("input[name='remarks']").val(id);
		$("input[name='replyId']").val(cid);
		$("input[name='content']").attr("placeholder","@"+name+":");
		$("input[name='content']").focus();
	}
	function postToMain(){
		if($.trim($("input[name='content']").val())==""){
			$(".alterInfo").html("请输入内容");
			$(".alertBtn")[0].click();
		}else{
			$.post("${ctx}/cms/comment/app/save;JSESSIONID=${sid}",$("form[name='post']").serialize(),function(data){
				$(".alterInfo").html(data.msg);
				$(".alertBtn")[0].click();
				f_flag = true;
				pageNo = 0;
				$("#wrapper ul").html("");
				$("input[name='remarks']").val("");
				$("input[name='replyId']").val("");
				$("input[name='content']").attr("placeholder","");
				$("input[name='content']").val("");
				//location.reload();
			});
		}
	}
	$(window).scroll(function(){
		console.log($("body").height());
		console.log($(window).scrollTop());
		console.log(screen.height);
		var scrollTop = $(window).scrollTop();
		var scrollHeight = screen.height;
		var windowHeight = $("body").height();
		if(scrollTop + scrollHeight == windowHeight){
			loadMore();
		}
	});
</script>
</body>
</html>