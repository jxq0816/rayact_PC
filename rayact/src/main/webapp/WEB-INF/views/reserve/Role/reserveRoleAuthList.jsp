<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色权限管理</title>
	<meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
	<jsp:param name="action" value="roleAuth"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
	<div class="row">
		<div class="col-md-12">
			<div class="block-flat">
				<div class="header">
					<h3>角色权限列表</h3>
				</div>
				<form:form id="searchForm" modelAttribute="reserveRoleAuth" action="${ctx}/reserve/reserveRoleAuth/list"
						   method="post">
					<div class="breadcrumb form-search">
						<div class="row">
							<div class="col-sm-12 col-md-12 col-lg-12">
								<div class="form-group col-lg-3">
									<form:input placeholder="" path="name" htmlEscape="false"
												cssstyle="width:70px;" maxlength="30"
												class="form-control"/>
								</div>
								<div class="form-group col-lg-2">
									<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
								</div>
								<div class="form-group pull-right">
									<a class="btn btn-success" href="${ctx}/reserve/reserveRoleAuth/form">
										<i class="fa fa-plus"></i>添加
									</a>
								</div>
							</div>
						</div>
					</div>

					<div class="content">
						<div class="table-responsive">
							角色编号（1:超级管理员;2:场馆管理员;3:高管;4:收银;5:财务;6:出纳）
							<table>
								<thead>
								<tr>
									<th>角色</th>
									<th>编号</th>
									<th>操作</th>
									<th>第二版本（开发中）</th>
								</tr>
								</thead>
								<tbody>
								<c:forEach items="${page.list}" var="reserveRoleAuth">
									<tr>
										<td><a href="${ctx}/reserve/reserveRoleAuth/form?id=${reserveRoleAuth.id}">
												${reserveRoleAuth.name}
										</a></td>
										<td>
												${reserveRoleAuth.userType}
										</td>
											<td>
												<a class="btn btn-primary btn-xs "
												   href="${ctx}/reserve/reserveRoleAuth/form?id=${reserveRoleAuth.id}">配置</a>

												<a class="btn btn-danger btn-xs"
												   href="${ctx}/reserve/reserveRoleAuth/delete?id=${reserveRoleAuth.id}"
												   onclick="return confirmb('确认要删除该角色权限吗？', this.href)">删除</a>
											</td>
											<td>
												<a class="btn btn-primary btn-xs "
												   href="${ctx}/reserve/reserveRoleAuth/menuForm?id=${reserveRoleAuth.id}">菜单</a>
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
				</form:form>
			</div>
		</div>
	</div>
</div>
</body>
</html>