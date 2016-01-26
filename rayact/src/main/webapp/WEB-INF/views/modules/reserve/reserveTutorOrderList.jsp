<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>教练订单管理</title>
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
		<li class="active"><a href="${ctx}/reserve/reserveTutorOrder/">教练订单列表</a></li>
		<shiro:hasPermission name="reserve:reserveTutorOrder:edit"><li><a href="${ctx}/reserve/reserveTutorOrder/form">教练订单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="reserveTutorOrder" action="${ctx}/reserve/reserveTutorOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>所属教练：</label>
				<form:input path="tutorId" htmlEscape="false" maxlength="19" class="input-medium"/>
			</li>
			<li><label>业务编号(如:场地的教练费用的ID,人次售卖的教练费用的ID)：</label>
				<form:input path="modelId" htmlEscape="false" maxlength="19" class="input-medium"/>
			</li>
			<li><label>业务标识(如用field标识场地的教练费用,用visitor标识人次售卖的教练费用)：</label>
				<form:input path="modelKey" htmlEscape="false" maxlength="19" class="input-medium"/>
			</li>
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
				<shiro:hasPermission name="reserve:reserveTutorOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="reserveTutorOrder">
			<tr>
				<td><a href="${ctx}/reserve/reserveTutorOrder/form?id=${reserveTutorOrder.id}">
					<fmt:formatDate value="${reserveTutorOrder.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${reserveTutorOrder.remarks}
				</td>
				<shiro:hasPermission name="reserve:reserveTutorOrder:edit"><td>
    				<a href="${ctx}/reserve/reserveTutorOrder/form?id=${reserveTutorOrder.id}">修改</a>
					<a href="${ctx}/reserve/reserveTutorOrder/delete?id=${reserveTutorOrder.id}" onclick="return confirmx('确认要删除该教练订单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>