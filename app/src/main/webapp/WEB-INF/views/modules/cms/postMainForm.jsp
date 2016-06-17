<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>帖子管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/upload.jsp" %>
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
		<li><a href="${ctx}/cms/postMain/">帖子列表</a></li>
		<li class="active"><a href="${ctx}/cms/postMain/form?id=${postMain.id}">帖子<shiro:hasPermission name="cms:postMain:edit">${not empty postMain.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:postMain:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="postMain" action="${ctx}/cms/postMain/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">主题：</label>
			<div class="controls">
				<form:input path="subject" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片：</label>
			<div class="controls">
				<mechanism:upload id="image" name="attMains1" imgWidth="50" imgHeight="50" exts="" showImg="true" modelId="${postMain.id}" fdKey="pic" modelName="com.bra.modules.cms.entity.PostMain" multi="true"></mechanism:upload>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="true" path="content" rows="4" maxlength="1000" class="input-xxlarge" cssStyle="height: 600px"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属圈子：</label>
			<div class="controls">
				<sys:treeselect id="group" name="group.id" value="${postMain.group.id}" labelName="group.name" labelValue="${postMain.group.name}"
								title="栏目" url="/cms/category/treeData" module="group" selectScopeModule="true" notAllowSelectRoot="false" notAllowSelectParent="false" cssClass="required"/>&nbsp;
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cms:postMain:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>