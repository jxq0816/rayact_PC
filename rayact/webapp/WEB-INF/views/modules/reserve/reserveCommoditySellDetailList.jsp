<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品销售明细管理</title>
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
		<li class="active"><a href="${ctx}/reserve/reserveCommoditySellDetail/">商品销售明细列表</a></li>
		<shiro:hasPermission name="reserve:reserveCommoditySellDetail:edit"><li><a href="${ctx}/reserve/reserveCommoditySellDetail/form">商品销售明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="reserveCommoditySellDetail" action="${ctx}/reserve/reserveCommoditySellDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>update_date</th>
				<th>remarks</th>
				<shiro:hasPermission name="reserve:reserveCommoditySellDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="reserveCommoditySellDetail">
			<tr>
				<td><a href="${ctx}/reserve/reserveCommoditySellDetail/form?id=${reserveCommoditySellDetail.id}">
					<fmt:formatDate value="${reserveCommoditySellDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${reserveCommoditySellDetail.remarks}
				</td>
				<shiro:hasPermission name="reserve:reserveCommoditySellDetail:edit"><td>
    				<a href="${ctx}/reserve/reserveCommoditySellDetail/form?id=${reserveCommoditySellDetail.id}">修改</a>
					<a href="${ctx}/reserve/reserveCommoditySellDetail/delete?id=${reserveCommoditySellDetail.id}" onclick="return confirmx('确认要删除该商品销售明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>