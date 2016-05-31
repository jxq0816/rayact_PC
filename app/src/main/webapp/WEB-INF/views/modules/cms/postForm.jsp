<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>回帖管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/post/">回帖列表</a></li>
		<li class="active"><a href="${ctx}/cms/post/form?id=${post.id}">回帖<shiro:hasPermission name="cms:post:edit">${not empty post.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:post:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="post" action="${ctx}/cms/post/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回复帖：</label>
			<div class="controls">
				<form:input path="postMain.id" htmlEscape="false" maxlength="19" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回复跟帖：</label>
			<div class="controls">
				<form:input path="postId" htmlEscape="false" maxlength="19" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cms:post:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>