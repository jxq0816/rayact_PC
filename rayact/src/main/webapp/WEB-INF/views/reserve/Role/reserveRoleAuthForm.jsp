<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色权限管理</title>
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
		<li><a href="${ctx}/reserve/reserveRoleAuth/">角色权限列表</a></li>
		<li class="active"><a href="${ctx}/reserve/reserveRoleAuth/form?id=${reserveRoleAuth.id}">角色权限<shiro:hasPermission name="reserve:reserveRoleAuth:edit">${not empty reserveRoleAuth.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="reserve:reserveRoleAuth:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="reserveRoleAuth" action="${ctx}/reserve/reserveRoleAuth/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">角色ID：</label>
			<div class="controls">
				<form:input path="fkReserveRoleId" htmlEscape="false" maxlength="19" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">对应用户权限(json字符串)：</label>
			<div class="controls">
				<form:input path="authority" htmlEscape="false" maxlength="5000" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="reserve:reserveRoleAuth:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>