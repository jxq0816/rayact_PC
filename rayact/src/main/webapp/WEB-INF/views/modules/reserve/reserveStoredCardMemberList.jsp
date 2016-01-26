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
		<li class="active"><a href="${ctx}/reserve/storedCardMember/">储值卡会员列表</a></li>
		<shiro:hasPermission name="reserve:reserveMember:edit"><li><a href="${ctx}/reserve/storedCardMember/form">储值卡会员添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="reserveMember" action="${ctx}/reserve/storedCardMember/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>卡号：</label>
				<form:input path="cartno" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>手机号</th>
				<th>性别</th>
				<th>卡号</th>
				<th>身份证</th>
				<th>地址</th>
				<th>余额</th>
				<th>备注</th>
				<shiro:hasPermission name="reserve:reserveMember:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="reserveMember">
			<tr>
				<td><a href="${ctx}/reserve/storedCardMember/form?id=${reserveMember.id}">
					${reserveMember.name}
				</a></td>
				<td>
					${reserveMember.mobile}
				</td>
				<td>
					${fns:getDictLabel(reserveMember.sex, 'sex', '')}
				</td>
				<td>
					${reserveMember.cartno}
				</td>
				<td>
						${reserveMember.sfz}
				</td>
				<td>
						${reserveMember.address}
				</td>

				<td>
						${reserveMember.remainder}
				</td>
				<td>
					${reserveMember.remarks}
				</td>
				<shiro:hasPermission name="reserve:reserveMember:edit"><td>
    				<a href="${ctx}/reserve/storedCardMember/form?id=${reserveMember.id}">修改</a>
					<a href="${ctx}/reserve/storedCardMember/delete?id=${reserveMember.id}" onclick="return confirmx('确认要删除该会员吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>