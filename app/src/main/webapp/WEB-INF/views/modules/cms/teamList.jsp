<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>战队管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function viewMember(href){
			top.$.jBox.open('iframe:'+href,'查看队员',$(top.document).width()-220,$(top.document).height()-120,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
					$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
					$("body", h.find("iframe").contents()).css("margin","10px");
				}
			});
			return false;
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//全选、取消全选的事件
		function selectAll(){
			if ($("input[name='selectAll']").attr("checked")) {
				$(":checkbox").attr("checked", true);
			} else {
				$(":checkbox").attr("checked", false);
			}
		}
		function deleteAll(){
			if(confirm("确认删除？")){
				var delIds = [];
				$("input[name='ids']:checked").each(function(){
					delIds.push($(this).val());
				});
				if(delIds.length<=0){
					alert("未选中删除项");
					return;
				}
				$.post("${ctx}/cms/team/deleteAll",{ids:delIds},function(data){
					alert(data);
					location.reload();
				})
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cms/team/">战队列表</a></li>
		<shiro:hasPermission name="cms:team:edit"><li><a href="${ctx}/cms/team/form">战队添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="team" action="${ctx}/cms/team/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>队伍名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>所属圈子：</label>
				<sys:treeselect id="group" name="group.id" value="${team.group.id}" labelName="group.name" labelValue="${team.group.name}"
								title="所属圈子" url="/cms/category/treeData" module="group" notAllowSelectRoot="false" cssClass="input-small"/>
			</li>
			<li><label>创建者：</label>
				<sys:treeselect id="createBy" name="createBy.id" value="${team.createBy.id}" labelName="createBy.name" labelValue="${team.createBy.name}"
								title="用户" url="/sys/user/treeData" cssClass="input-small" allowClear="true"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="批量删除" onclick="deleteAll();"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input type="checkbox" name="selectAll" onclick="selectAll();"/></th>
				<th>队伍名称</th>
				<th>位置</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>备注</th>
				<shiro:hasPermission name="cms:team:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="team">
			<tr>
				<td><input type="checkbox" name="ids" value="${team.id}"></td>
				<td><a href="${ctx}/cms/team/form?id=${team.id}">
					${team.name}
				</a></td>
				<td>
						${team.position}
				</td>
				<td>
						${team.createBy.name}
				</td>
				<td>
					<fmt:formatDate value="${team.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${team.remarks}
				</td>
				<shiro:hasPermission name="cms:team:edit"><td>
    				<a href="${ctx}/cms/team/form?id=${team.id}">修改</a>
					<a href="${ctx}/cms/teamMember/list?team.id=${team.id}" onclick="return viewMember(this.href)">球员列表</a>
					<a href="${ctx}/cms/team/delete?id=${team.id}" onclick="return confirmx('确认要删除该战队吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>