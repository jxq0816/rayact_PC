<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
	<title>教练明细</title>
	<meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
	<jsp:param name="action" value="tutorOrder"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
	<form:form id="searchForm" modelAttribute="reserveTutorOrder" action="${ctx}/reserve/reserveTutorOrder/orderDetail?tutorId=${param.tutorId}&tutorName=${param.tutorName}"
			   method="post" class="breadcrumb form-search hidden">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<div class="row">
		<div class="col-md-12">
			<div class="block-flat">
				<div class="header">
					<h3>${param.tutorName}教练授课明细</h3>
				</div>
				<sys:message content="${message}"/>
				<div class="content">
					<div class="table-responsive">
						<table>
							<thead>
							<tr>
								<th>客户姓名</th>
								<th>授课场地</th>
								<th>授课时长</th>
								<th>授课时间</th>
								<th>总价</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${page.list}" var="order">
								<tr>
									<td>
											${order.name}
									</td>
									<td>
											${order.fieldName}
									</td>
									<td>
											${order.minute}分钟
									</td>
									<td>
											${order.date}

									</td>
									<td>
											${order.price}元
									</td>
								</tr>
							</c:forEach>

							</tbody>
						</table>
						<div class="row">
							<div class="col-sm-12">

								<div class="pull-right">
									<div class="dataTables_paginate paging_bs_normal">
										<sys:javascript_page p="${page}" formId="searchForm"></sys:javascript_page>
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

</body>
</html>