<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>储值卡管理</title>
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
		<li class="active"><a href="${ctx}/reserve/storedCardMemberSet/list">储值卡列表</a></li>
		<shiro:hasPermission name="reserve:reservestoredCardMemberSet:view"><li><a href="${ctx}/reserve/storedCardMemberSet/form">储值卡添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="reserveStoredcardMemberSet" action="${ctx}/reserve/storedCardMemberSet/list" method="post" class="breadcrumb form-search">
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
				<th>名称</th>
				<th>项目</th>
				<th>起止金额</th>
				<th>赠送金额</th>
				<th>截止日期</th>
				<th>折扣比率</th>
				<th>备注</th>
				<shiro:hasPermission name="reserve:reserveStoredcardMemberSet:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="reserveStoredcardMemberSet">
			<tr>
				<td><a href="${ctx}/reserve/storedCardMemberSet/form?id=${reserveStoredcardMemberSet.id}">
					${reserveStoredcardMemberSet.name}
				</a></td>

				<td>${reserveStoredcardMemberSet.reserveProject.name}</td>

				<td>${reserveStoredcardMemberSet.startPrice} ~ ${reserveStoredcardMemberSet.endPrice} 元</td>

				<td>${reserveStoredcardMemberSet.givePrice} 元</td>

				<td><fmt:formatDate value="${reserveStoredcardMemberSet.deadline}" type="date"/></td>


				<td>${reserveStoredcardMemberSet.discountRate}</td>

				<td>
					${reserveStoredcardMemberSet.remarks}
				</td>
				<shiro:hasPermission name="reserve:reserveStoredcardMemberSet:edit"><td>
    				<a href="${ctx}/reserve/storedCardMemberSet/form?id=${reserveStoredcardMemberSet.id}">修改</a>
					<a href="${ctx}/reserve/storedCardMemberSet/delete?id=${reserveStoredcardMemberSet.id}" onclick="return confirmx('确认要删除该储值卡吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>