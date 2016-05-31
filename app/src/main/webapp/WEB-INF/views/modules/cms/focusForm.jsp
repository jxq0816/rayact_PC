<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关注管理</title>
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
		<li><a href="${ctx}/cms/focus/">关注列表</a></li>
		<li class="active"><a href="${ctx}/cms/focus/form?id=${focus.id}">关注<shiro:hasPermission name="cms:focus:edit">${not empty focus.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:focus:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="focus" action="${ctx}/cms/focus/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">关注模块：</label>
			<div class="controls">
				<form:input path="modelName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关注实例ID：</label>
			<div class="controls">
				<form:input path="modelId" htmlEscape="false" maxlength="19" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关注人：</label>
			<div class="controls">
				<sys:treeselect id="fan" name="fan.id" value="${focus.fan.id}" labelName="fan.name" labelValue="${focus.fan.name}"
								title="人员" url="/sys/office/treeData?type=3" cssClass="required"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cms:focus:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>