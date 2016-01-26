<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易记录管理</title>
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
		<li class="active"><a href="${ctx}/reserve/reserveCardStatements/">交易记录列表</a></li>
		<shiro:hasPermission name="reserve:reserveCardStatements:edit"><li><a href="${ctx}/reserve/reserveCardStatements/form">交易记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="reserveCardStatements" action="${ctx}/reserve/reserveCardStatements/" method="post" class="breadcrumb form-search">
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
				<th>姓名</th>
				<th>卡号</th>
				<th>金额</th>
				<th>时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="reserveCardStatements">
			<tr>
				<td>
					${reserveCardStatements.reserveMember.name}
				</td>
				<td>
					${reserveCardStatements.reserveMember.cardno}
				</td>
				<td>
					${reserveCardStatements.transactionVolume}
				</td>
				<td><a href="${ctx}/reserve/reserveCardStatements/form?id=${reserveCardStatements.id}">
					<fmt:formatDate value="${reserveCardStatements.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
    				<a href="${ctx}/reserve/reserveCardStatements/form?id=${reserveCardStatements.id}">修改</a>
					<a href="${ctx}/reserve/reserveCardStatements/delete?id=${reserveCardStatements.id}" onclick="return confirmx('确认要删除该交易记录吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>