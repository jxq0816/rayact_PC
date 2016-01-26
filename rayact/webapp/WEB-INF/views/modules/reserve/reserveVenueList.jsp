<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>场馆管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/reserve/reserveVenue/">场馆列表</a></li>
		<shiro:hasPermission name="reserve:reserveVenue:edit"><li><a href="${ctx}/reserve/reserveVenue/form">场馆添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="reserveVenue" action="${ctx}/reserve/reserveVenue/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>场馆名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>场馆名称</th>
				<th>是否启用</th>
				<th>地址</th>
				<th>负责人</th>
				<th>营业开始时间</th>
				<th>营业结束时间</th>
				<shiro:hasPermission name="reserve:reserveVenue:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="reserveVenue">
			<tr>
				<td><a href="${ctx}/reserve/reserveVenue/form?id=${reserveVenue.id}">
					${reserveVenue.name}
				</a></td>
				<td>
					${fns:getDictLabel(reserveVenue.available, 'yes_no', '')}
				</td>
				<td>
					${reserveVenue.address}
				</td>
				<td>
					${reserveVenue.chargeUser.id}
				</td>
				<td>
						${reserveVenue.startTime}
				</td>
				<td>
						${reserveVenue.endTime}
				</td>
				<shiro:hasPermission name="reserve:reserveVenue:edit"><td>
    				<a href="${ctx}/reserve/reserveVenue/form?id=${reserveVenue.id}">修改</a>
					<a href="${ctx}/reserve/reserveVenue/delete?id=${reserveVenue.id}" onclick="return confirmx('确认要删除该场馆吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>