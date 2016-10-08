<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>青草球队队员管理</title>
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
		<li><a href="${ctx}/cms/teamMemberTmp/">青草球队队员列表</a></li>
		<li class="active"><a href="${ctx}/cms/teamMemberTmp/form?id=${teamMemberTmp.id}">青草球队队员<shiro:hasPermission name="cms:teamMemberTmp:edit">${not empty teamMemberTmp.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:teamMemberTmp:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="teamMemberTmp" action="${ctx}/cms/teamMemberTmp/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">球衣号：</label>
			<div class="controls">
				<form:input path="playerNum" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身高：</label>
			<div class="controls">
				<form:input path="height" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">鞋码：</label>
			<div class="controls">
				<form:input path="weight" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">角色：</label>
			<div class="controls">
				<form:select path="role" htmlEscape="false" maxlength="255" class="input-xlarge ">
					<form:option value="1">领队</form:option>
					<form:option value="2">教练</form:option>
					<form:option value="3">球员</form:option>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="cardNo" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片链接：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片：</label>
			<div class="controls">
				<mechanism:upload id="image" name="attMains1" imgWidth="50" imgHeight="50" exts="" showImg="true" modelId="${teamMemberTmp.id}" fdKey="pic" modelName="com.bra.modules.cms.entity.TeamMemberTmp" multi="true"></mechanism:upload>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属队伍：</label>
			<div class="controls">
				<form:select path="teamTmp.id">
					<c:forEach items="${teams}" var="team">
						<form:option value="${team.id}">${team.name}</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cms:teamMemberTmp:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>