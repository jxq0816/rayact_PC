<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>${postMain.subject}</title>
	<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/jquery-mobile/list.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/bootstrap/2.3.1/css_default/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript">
		$(document).ready(function() {
			if ("${category.allowComment}"=="1" && "${article.articleData.allowComment}"=="1"){
				$("#comment").show();
				page(1);
			}
		});
		function page(n,s){
			$.get("${ctx}/mobile/comment",{theme: '${site.theme}', 'category.id': '${category.id}',
				contentId: '${article.id}', title: '${article.title}', pageNo: n, pageSize: s, date: new Date().getTime()
			},function(data){
				$("#comment").html(data);
			});
		}
	</script>
</head>
<body>
<div class="row">
	<style>
		.round {
			width:140px; height:140px;
			-moz-border-radius: 70px;      /* Gecko browsers */
			-webkit-border-radius: 70px;   /* Webkit browsers */
			border-radius:70px;            /* W3C syntax */
		}
		.row{
			margin-left: 5px;
		}
		.subject{
			font-size:60px;
			line-height:65px;
			width:90%;
			text-align:center;
			margin:10px 30px;
			display: inline-block;
			font-weight:bold;
		}
		.postSubject{
			line-height:150%;
			margin:20px 0px;
			border-left:20px solid #f0860c;
			color:#555555;
			font-size:50px;
			text-align:left;
			width: 18%;
			display:inline-block
		}
	</style>
	<div class="row">
		<div class="subject">${postMain.subject}</div>
		<div style="margin:25px 0px;display: inline-block">
			<img class="round" src="${postMain.createBy.photo}" name="${postMain.createBy.id}" onclick="jumpToInfo(this)">
		</div>
		<div style="display: inline-block;position: relative;bottom: -30px;line-height: 50px">
			<span style="color: #f0860c;font-size: 40px">${postMain.createBy.name}</span>
			<br>
			<span style="color:#8C8C8C"> 楼主 &nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${postMain.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
		</div>
		<div style="line-height: 150%;font-size: 50px;color:#555555;">${postMain.content}</div>
		<c:forEach items="${imgs}" var="url">
			<img src="${url}"/>
		</c:forEach>
	</div>
</div>
<div class="row">
	<div class="postSubject">回帖</div><div style="border-bottom:1px solid #C8C8C8;width: 75%;display:inline-block"></div>
	<c:forEach items="${ptm}" var="post" varStatus="status">
		<div class="row" style="margin:0 5px;padding-bottom: 10px;border-bottom: 1px solid #C8C8C8;">
			<div class="row" style="display: block;">
				<div style="display: inline-block"><img src="${post.createBy.photo}" class="round" name="${post.createBy.id}" onclick="jumpToInfo(this)"></div>
				<div style="display: inline-block;position: relative;bottom: -30px;line-height: 50px"><span style="color: #f0860c;font-size: 40px">${post.createBy.name}</span><br><span style="color:#8C8C8C"> 第${status.index + 1}楼 &nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${post.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
				<img src="${ctxStatic}/images/btn-huifu-h@2x.png" height="30px" width="60px" style="float: right;display: inline-block;"/>
				<div style="margin-left:110px;line-height: 150%;font-size: 45px">${post.content}</div>
			</div>
			<div  name="${post.id}" class="ptp"  style="margin-left:110px">

			</div>
		</div>
	</c:forEach>
	<div class="row" style="padding: 10px;align:center;">
		<div onclick="jumpToApp()" style="margin:10px;height:50px;width:100%;background-image:url(${ctxStatic}/images/more2x.png);background-repeat:no-repeat;background-position:center;" ></div>
	</div>
</div>
<script>
	function jumpToApp(){
		///intent();
		if(typeof iOSskip === 'function'){
			iOSskip();
		}else if(window.intent){
			intent.DLCallAndroid();
		}else{
			alert("无方法");
		}
	}
	function jumpToInfo(dom){
		var id = $(dom).attr("name");
		iOSPhotoskip(id);
	}
	$(function(){
		$(".ptp").each(function(){
			var postId = $(this).attr("name");
			var $dom = $(this);
			$.get("/app/a/cms/post/app/list?postId="+postId,function(data){
				var vo = eval(data);
				if (vo&&vo.length>0){
					for(var i = 0 ; i < vo.length ; i++){
						$dom.append("<div style='line-height:60px'><div style='color:#f0860c;margin-top:10px;font-size: 35px;display: inline-block'>"+vo[i].pname+":</div><div style='font-size: 40px;display: inline-block'>"+vo[i].content+"</div></div>");
					}
				}
			});
		});
	});
</script>
</body>
</html>