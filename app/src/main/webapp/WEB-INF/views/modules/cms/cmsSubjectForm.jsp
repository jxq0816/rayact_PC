<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>话题管理</title>
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
		<li><a href="${ctx}/cms/cmsSubject/">话题列表</a></li>
		<li class="active"><a href="${ctx}/cms/cmsSubject/form?id=${cmsSubject.id}">话题<shiro:hasPermission name="cms:cmsSubject:edit">${not empty cmsSubject.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:cmsSubject:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cmsSubject" action="${ctx}/cms/cmsSubject/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">所属圈子：</label>
			<div class="controls">
				<sys:treeselect id="group" name="group.id" value="${cmsSubject.group.id}" labelName="group.name" labelValue="${cmsSubject.group.name}"
								title="栏目" url="/cms/category/treeData" module="group" selectScopeModule="true" notAllowSelectRoot="false" notAllowSelectParent="true" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">话题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片：</label>
			<div class="controls">
				<mechanism:upload id="image" name="attMains1" imgWidth="50" imgHeight="50" exts="" showImg="true" modelId="${cmsSubject.id}" fdKey="subject" modelName="com.bra.modules.cms.entity.CmsSubject" multi="false"></mechanism:upload>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cms:cmsSubject:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>