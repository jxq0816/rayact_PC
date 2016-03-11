<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
	<title>教练添加</title>
	<meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
	<jsp:param name="action" value="tutor"></jsp:param>
</jsp:include>
<div class="cl-mcont" id="pcont">
	<div class="row">
		<div class="col-md-12">
			<div class="block-flat">
				<div class="header">
					<h3>教练添加</h3>
				</div>
				<div class="content">
					<div class="tab-container">
						<div class="form-horizontal group-border-dashed">
							<form:form id="inputForm" modelAttribute="reserveTutor"
									   action="${ctx}/reserve/reserveTutor/save" method="post" class="form-horizontal">
								<form:hidden path="id"/>
								<input type="hidden" name="token" value="${token}"/>
								<sys:message content="${message}"/>
								<table id="contentTable" class="table table-bordered">
									<tr>
										<td>姓名:</td>
										<td><form:input path="name" htmlEscape="false" maxlength="30"
														class="form-control required "/>
											<span class="help-inline"><font color="red">*</font> </span></td>
										<td>项目:</td>
										<td>
											<sys:select cssClass="input-large" name="project.id"
														items="${reserveProjectList}"
														value="${reserveTutor.project.id}" itemLabel="name" itemValue="id"
														defaultLabel="请选择"
														defaultValue="">
											</sys:select>
										</td>
									</tr>
									<tr>
										<td>价格:</td>
										<td>
											<form:input path="price" htmlEscape="false" maxlength="16"
														class="form-control"/>
										</td>

										<td>级别:</td>
										<td>
											<form:input path="level" htmlEscape="false" maxlength="18"
														class="form-control "/>
										</td>
									</tr>

									<tr>
										<td>是否启用:</td>
										<td colspan="3">
											<sys:radio name="startUsing" value="${reserveTutor.startUsing}"
													   items="${fns:getDictList('yes_no')}" itemLabel="label"
													   itemValue="value" cssClass="icheck">
											</sys:radio>
										</td>
									</tr>
									<tr>
										<td>备注:</td>
										<td colspan="3">
											<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255"
														   class="form-control "/>
										</td>
									</tr>
								</table>
								<div>
									<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
									<input id="btnCancel" class="btn" type="button" value="返 回"
										   onclick="history.go(-1)"/>
								</div>
							</form:form>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/validate.js"></script>
</body>
</html>