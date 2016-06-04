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
			width:100px; height:100px;
			-moz-border-radius: 50px;      /* Gecko browsers */
			-webkit-border-radius: 50px;   /* Webkit browsers */
			border-radius:50px;            /* W3C syntax */
		}
		.row{
			margin-left: 5px;
		}
	</style>
	<div class="row">
		<div style="margin:5px 0;display: inline-block"><img class="round" src="${postMain.createBy.photo}" name="${postMain.createBy.id}" onclick="jumpToInfo(this)"></div>
		<div style="color:#555555;font-size:40px;text-align:center;margin:10px 30px;display: inline-block">${postMain.subject}</div>
		<div style="margin-bottom:20px"><span style="color:#f0860c">${postMain.createBy.name}</span><span style="color: #8C8C8C;margin-left: 20px"><fmt:formatDate value="${postMain.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span> </div>
		<div>${postMain.content}</div>
		<c:forEach items="${imgs}" var="url">
			<img src="${url}"/>
		</c:forEach>
	</div>
</div>
<div class="row">
	<div style="border-left:5px solid #f0860c;color:#555555;font-size:20px;text-align:left;width: 8%;display:inline-block">回帖</div><div style="border-bottom:1px solid #C8C8C8;width: 90%;display:inline-block"></div>
	<c:forEach items="${ptm}" var="post" varStatus="status">
		<div class="row" style="margin:0 5px;padding-bottom: 10px;border-bottom: 1px solid #C8C8C8;">
			<div class="row" style="display: block;">
				<div style="display: inline-block"><img src="${post.createBy.photo}" class="round" name="${post.createBy.id}" onclick="jumpToInfo(this)"></div>
				<div style="display: inline-block;position: relative;bottom: -10px"><span style="color: #f0860c">${post.createBy.name}</span><br><span style="color:#8C8C8C"> 第${status.index + 1}楼 &nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${post.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
				<img src="${ctxStatic}/images/btn-huifu-h@2x.png" height="30px" width="60px" style="float: right;display: inline-block;"/>
				<div style="margin-left:110px">${post.content}</div>
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
		iOSskip();
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
						$dom.append("<div><div style='color:#f0860c;margin-top:10px;font-size: 20px;display: inline-block'>"+vo[i].pname+":</div><div style='font-size: 20px;display: inline-block'>"+vo[i].content+"</div></div>");
					}
				}
			});
		});
	});
</script>
</body>
</html>