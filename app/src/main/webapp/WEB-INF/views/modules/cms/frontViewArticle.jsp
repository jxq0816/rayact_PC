<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<html>
<head>
	<title>${article.title}</title>
	<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/bootstrap/2.3.1/css_default/bootstrap.min.css" type="text/css" rel="stylesheet" />
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
		.a_content img{
			 width: 100%;
		 }
	</style>
	<script>
		function jumpToApp(){
			if(typeof iOSskipInfo === 'function'){
				iOSskipInfo();
			}else if(window.intent){
				intent.DLCallAndroid();
			}else{
				alert("无方法");
			}
		}
		function jumpToInfo(dom){
			var id = $(dom).attr("name");
			iOSPhotoskipInfo(id);
		}
	</script>
</head>
<body style="font-family: Microsoft Yahei">
<div class="row" style="margin-left: 10px;">
	<div style="font-size:60px;text-align:left;margin:20px 0;font-weight: bold;line-height: 200%;">${article.title}</div>
	<div style="border-bottom:1px solid #ddd;padding:10px;margin:15px 0;font-size:30px;line-height: 150%">
		<span style="color: #f0860c">${article.user.name}</span> &nbsp; <fmt:formatDate value="${article.createDate}" pattern="MM-dd HH:mm"/>
	</div>
	<c:if test="${not empty article.description}">
		<div style="color: #555555;font-size:30px;line-height: 150%;margin: 20px 0px">摘要：<span style="color: #8C8C8C;font-size: 30px">${article.description}</span>
		</div>
	</c:if>
	<div class="a_content" style="width: 100%;line-height: 150%;font-size:50px;color:#555555;">${article.articleData.content}</div>
</div>
<div class="row" style="border-top: 1px solid #c8c8c8;width: 100%;margin-top: 38px;padding-top: 28px">
	<div class="head postSubject">评论</div>
	<div class="list">
		<c:forEach items="${comments}" var="comment">
			<div class="item" style="margin-top: 20px;line-height: 50px;padding-bottom: 10px;border-bottom: 1px solid #C8C8C8;">
				<div class="img" style="display: inline-block;float:left;"><img src="${comment.createBy.photo}" class="round" width="100px" height="100px" onclick="jumpToInfo(this)" name="${comment.auditUser.id}"></div>
				<div style="display: inline-block;float:left;width: 80%;color: #1e1e1e;font-size: 30px;margin-left: 10px">
					<span class="name" name="${comment.createBy.id}" style="display: inline-block;color: #f0860c;font-size: 40px">${comment.createBy.name}:</span>
					<br><fmt:formatDate value="${comment.createDate}" pattern="MM-dd HH:mm"/>
				</div>
				<div style="margin-left: 10px">
					<span class="content" style="color:#555555;display: inline-block;font-size: 40px;margin-left: 100px;">${comment.content}</span>
				</div>
			</div>
		</c:forEach>
	</div>
	<c:if test="${comments.size() > 0}">
		<div class="row" style="padding: 10px;align:center;">
			<div onclick="jumpToApp(this)" style="margin:10px;height:100px;width:100%;background-image:url(${ctxStatic}/images/more.png);background-repeat:no-repeat;background-position:center;" ></div>
		</div>
	</c:if>
</div>
</body>
</html>