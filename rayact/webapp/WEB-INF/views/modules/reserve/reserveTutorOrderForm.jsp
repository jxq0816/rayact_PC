<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>教练订单管理</title>
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
		<li><a href="${ctx}/reserve/reserveTutorOrder/">教练订单列表</a></li>
		<li class="active"><a href="${ctx}/reserve/reserveTutorOrder/form?id=${reserveTutorOrder.id}">教练订单<shiro:hasPermission name="reserve:reserveTutorOrder:edit">${not empty reserveTutorOrder.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="reserve:reserveTutorOrder:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="reserveTutorOrder" action="${ctx}/reserve/reserveTutorOrder/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">所属教练：</label>
			<div class="controls">
				<form:input path="tutorId" htmlEscape="false" maxlength="19" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教练费用：</label>
			<div class="controls">
				<form:input path="orderPrice" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务编号(如:场地的教练费用的ID,人次售卖的教练费用的ID)：</label>
			<div class="controls">
				<form:input path="modelId" htmlEscape="false" maxlength="19" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务标识(如用field标识场地的教练费用,用visitor标识人次售卖的教练费用)：</label>
			<div class="controls">
				<form:input path="modelKey" htmlEscape="false" maxlength="19" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">remarks：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="reserve:reserveTutorOrder:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>