<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
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
<!---->
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/reserve/commodity/list">商品列表</a></li>
		<shiro:hasPermission name="reserve:commodity:edit"><li><a href="${ctx}/reserve/commodity/form">商品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="reserveCommodity" action="${ctx}/reserve/commodity/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
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
				<th>编号</th>
				<th>名称</th>
				<th>价格</th>
				<th>库存量</th>
				<th>类别</th>
				<th>状态</th>
				<shiro:hasPermission name="reserve:commodity:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="commodity">
			<tr>
				<td><a href="${ctx}/reserve/commodity/form?id=${commodity.id}">
					${commodity.commodityId}
				</a></td>
				<td>
					${commodity.name}
				</td>
				<td>
					${commodity.price}&nbsp;元
				</td>
				<td>
					${commodity.repertoryNum}
				</td>
				<td>
					${commodity.commodityType.name}
				</td>
				<td>
					<c:choose>
						<c:when test="${commodity.shelvesStatus== '0'}">
							下架
						</c:when>
						<c:otherwise>
							上架
						</c:otherwise>
					</c:choose>
				</td>
				<shiro:hasPermission name="reserve:commodity:edit"><td>
    				<a href="${ctx}/reserve/commodity/form?id=${commodity.id}">修改</a>
					<a href="${ctx}/reserve/commodity/delete?id=${commodity.id}" onclick="return confirmx('确认要删除该商品吗？', this.href)">删除</a>
					<a href="${ctx}/reserve/commodity/inStorageUrl?id=${commodity.id}">入库</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>