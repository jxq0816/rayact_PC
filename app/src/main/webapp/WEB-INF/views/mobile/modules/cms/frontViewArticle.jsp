<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<html>
<head>
	<title>${article.title}</title>
	<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/jquery-mobile/list.css" type="text/css" rel="stylesheet" />
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
	</style>
</head>
<body>
<div class="row" style="margin-left: 10px;">
	<div style="color:#555555;font-size:30px;text-align:left;margin:5px 0;font-weight: 300">${article.title}</div>
	<div style="border-bottom:1px solid #ddd;padding:10px;margin:5px 0;"><span style="color: #f0860c">${article.user.name}</span> &nbsp; <fmt:formatDate value="${article.createDate}" pattern="MM-dd HH:mm"/></div>
	<c:if test="${not empty article.description}"><div style="color: #555555;font-size:20px ">摘要：<span style="color: #8C8C8C;font-size: 15px">${article.description}</span></div></c:if>
	<div style="width: 100%">${article.articleData.content}</div>
</div>
<div class="row">
	<div class="head">评论</div>
	<div class="list">
		<c:forEach items="${comments}" var="comment">
			<div class="item" style="margin-top: 20px">
				<div class="img" style="display: inline-block;"><img src="${comment.createBy.photo}" class="round" width="100px" height="100px" onclick="jumpToInfo(this)" name="${comment.createBy.id}"></div>
				<div style="display: inline-block;position:relative;top: 10px;width: 80%">
				<span class="name" name="${comment.createBy.id}" style="display: inline-block;color: #f0860c;font-size: 30px">${comment.createBy.name}:</span>
				</div>
				<div>
				<span class="content" style="color:#555555;display: inline-block;font-size: 25px;margin-left: 100px;">${comment.content}<br><fmt:formatDate value="${comment.createDate}" pattern="MM-dd HH:mm"/></span>
				</div>
			</div>
		</c:forEach>
	</div>
	<div class="row" style="padding: 10px;align:center;">
		<div onclick="jumpToApp(this)" style="margin:10px;height:50px;width:100%;background-image:url(${ctxStatic}/images/more2x.png);background-repeat:no-repeat;background-position:center;" ></div>
	</div>
</div>
<script>
	function jumpToApp(){
		iOSskipInfo();
	}
	function jumpToInfo(dom){
		var id = $(dom).attr("name");
		iOSPhotoskipInfo(id);
	}
</script>
</body>
</html>