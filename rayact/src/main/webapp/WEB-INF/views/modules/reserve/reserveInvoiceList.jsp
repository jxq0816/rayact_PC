<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
	<title>发票管理</title>
	<meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
	<jsp:param name="action" value="invoiceList"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
	<div class="row">
		<div class="col-md-12">
			<div class="block-flat">
				<div class="header">
					<h3>发票列表</h3>
				</div>
				<form:form id="searchForm" modelAttribute="reserveInvoice" action="${ctx}/reserve/reserveInvoice/list"
						   method="post" class="breadcrumb form-search">
					<div class="row">
						<div class="col-sm-6 col-md-6 col-lg-6">
							<table class="no-border">
								<tbody class="no-border-y">
								<tr>
									<td>客户姓名：</td>
									<td><form:input path="userName" htmlEscape="false" cssstyle="width:70px;" maxlength="30"
													class="form-control"/></td>
									<td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
										<input id="btnExport" class="btn btn-primary" type="button" value="导出"/></td>
								</tr>
								</tbody>
							</table>
						</div>
						<div class="pull-right">
							<a class="btn btn-success" href="${ctx}/reserve/reserveInvoice/form">
								<i class="fa fa-plus"></i>添加
							</a>
						</div>
					</div>
				</form:form>
				<sys:message content="${message}"/>
				<div class="content">
					<div class="table-responsive">
						<table>
							<thead>
							<tr>
								<th>客户姓名</th>
								<th>发票抬头</th>
								<th>发票项目</th>
								<th>发票金额</th>
								<th>消费日期</th>
								<th>申请日期</th>
								<th>是否快递</th>
								<th>地址信息</th>
								<th>备注</th>
								<th>是否已开票</th>
								<th>操作</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${page.list}" var="reserveInvoice">
								<tr>
									<td>
										<a href="${ctx}/reserve/reserveInvoice/form?id=${reserveInvoice.id}">${reserveInvoice.userName}</a>
									</td>
									<td>${reserveInvoice.invoiceHead}</td>
									<td>${reserveInvoice.invoiceProject}</td>
									<td>${reserveInvoice.invoicePrice}</td>
									<td>
										<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveInvoice.consumerTime}"/>
									</td>
									<td>
										<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveInvoice.applyTime}"/>
									</td>
									<td>
											${fns:getDictLabel(reserveInvoice.isExpress, 'yes_no', '')}
									</td>
									<td>
										${reserveInvoice.address}
									</td>
									<td>
										${reserveInvoice.remarks}
									</td>
									<td>
											${fns:getDictLabel(reserveInvoice.isExpress, 'yes_no', '')}
									</td>
									<td>
										<a class="btn btn-primary btn-xs"
										   href="${ctx}/reserve/reserveInvoice/form?id=${reserveInvoice.id}"><i
												class="fa fa-pencil"></i>修改</a>
										<a class="btn btn-danger btn-xs"
										   href="${ctx}/reserve/reserveInvoice/delete?id=${reserveInvoice.id}"
										   onclick="return confirmb('确认要删除该发票吗？', this.href)"><i
												class="fa fa-times"></i>删除</a>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						<div class="row">
							<div class="col-sm-12">

								<div class="pull-right">
									<div class="dataTables_paginate paging_bs_normal">
										<sys:javascript_page p="${page}"></sys:javascript_page>
									</div>
								</div>
								<div class="clearfix"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$("#btnExport").click(function(){
		$("#searchForm").attr("action","${ctx}/reserve/reserveInvoice/listExport");
		$("#searchForm").submit();
		$("#searchForm").attr("action","${ctx}/reserve/reserveInvoice/list");
	});
</script>
</body>
</html>