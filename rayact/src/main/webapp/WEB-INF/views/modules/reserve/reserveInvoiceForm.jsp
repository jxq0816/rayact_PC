<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
	<title>发票管理</title>
	<meta name="decorator" content="main"/>
	<%@include file="/WEB-INF/views/include/upload.jsp" %>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
	<jsp:param name="action" value="invoiceList"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
	<div class="row">
		<div class="col-md-12" style="padding-right: 0px">
			<div class="block-flat">
				<div class="header">
					<h3>发票管理</h3>
				</div>
				<div class="content">
					<form:form id="inputForm" modelAttribute="reserveInvoice" action="${ctx}/reserve/reserveInvoice/save"
							   method="post"
							   class="form-horizontal">
					<form:hidden path="id"/>
					<input type="hidden" name="token" value="${token}"/>
					<div class="tab-container">
						<ul class="nav nav-tabs" id="myTab">
							<li class="active"><a href="#home" data-toggle="tab">基本信息</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="home">
								<div class="form-horizontal group-border-dashed">
									<div class="form-group">
										<label  class="col-sm-3 control-label">客户姓名</label>
										<div class="col-sm-6">
											<form:input path="userName" htmlEscape="false" maxlength="30"
														class="form-control"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">发票头</label>
										<div class="col-sm-6">
											<form:input path="invoiceHead" htmlEscape="false" maxlength="30"
														class="form-control"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">发票项目</label>
										<div class="col-sm-6">
											<form:input path="invoiceProject" htmlEscape="false" maxlength="30"
														class="form-control"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">发票金额</label>
										<div class="col-sm-6">
											<form:input path="invoicePrice" htmlEscape="false" maxlength="30"
														class="form-control"/>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label">消费时间</label>
										<div class="col-sm-6">
											<input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveInvoice.consumerTime}"/>"
												   name="consumerTime" id="consumerTime" type="text"
												   maxlength="20"
												   class="input-medium form-control Wdate "
												   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">申请时间</label>
										<div class="col-sm-6">
											<input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveInvoice.applyTime}"/>"
												   name="applyTime" id="applyTime" type="text"
												   maxlength="20"
												   class="input-medium form-control Wdate "
												   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">是否快递</label>
										<div class="col-sm-6">
											<form:radiobuttons path="isExpress"
															   items="${fns:getDictList('yes_no')}"
															   itemLabel="label"
															   itemValue="value"
															   htmlEscape="false" class="icheck required"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">快递地址</label>
										<div class="col-sm-6">
											<form:textarea path="address" htmlEscape="false" rows="4"
														   maxlength="255" class="form-control"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">是否已开票</label>
										<div class="col-sm-6">
											<form:radiobuttons path="isDone"
															   items="${fns:getDictList('yes_no')}"
															   itemLabel="label"
															   itemValue="value"
															   htmlEscape="false" class="icheck required"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">备注</label>
										<div class="col-sm-6">
											<form:textarea path="remarks" htmlEscape="false" rows="4"
														   maxlength="255" class="form-control"/>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="form-actions">
					<input id="btnSubmit"
						   class="btn btn-primary"
						   type="submit"
						   value="保 存"/>&nbsp;
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/validate.js"></script>
</body>
</html>
