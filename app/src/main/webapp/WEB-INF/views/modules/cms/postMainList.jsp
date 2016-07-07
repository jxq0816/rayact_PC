<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>帖子管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function viewPost(href){
			top.$.jBox.open('iframe:'+href,'查看回帖',$(top.document).width()-220,$(top.document).height()-120,{
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
			$.post("${ctx}/cms/postMain/deleteAll",{ids:delIds},function(data){
				alert(data);
				location.reload();
			})
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cms/postMain/">帖子列表</a></li>
		<shiro:hasPermission name="cms:postMain:edit"><li><a href="${ctx}/cms/postMain/form">帖子添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="postMain" action="${ctx}/cms/postMain/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>帖子名称：</label>
				<form:input path="subject" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>所属圈子：</label>
				<sys:treeselect id="group" name="group.id" value="${postMain.group.id}" labelName="group.name" labelValue="${postMain.group.name}"
								title="所属圈子" url="/cms/category/treeData" module="group" notAllowSelectRoot="false" cssClass="input-small"/>
			</li>
			<li><label>作者：</label>
				<sys:treeselect id="createBy" name="createBy.id" value="${postMain.createBy.id}" labelName="createBy.name" labelValue="${postMain.createBy.name}"
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
				<th>主题</th>
				<th>所属圈子</th>
				<th>回复数</th>
				<th>权重</th>
				<th>最后回复时间</th>
				<th>创建人</th>
				<th>创建时间</th>
				<shiro:hasPermission name="cms:postMain:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="postMain">
			<tr>
				<td><input type="checkbox" name="ids" value="${postMain.id}"></td>
				<td><a href="${ctx}/cms/postMain/form?id=${postMain.id}">
					${postMain.subject}
				</a></td>
				<td>
					${postMain.group.name}
				</td>
				<td>
					${postMain.postNum}
				</td>
				<td>
				    ${postMain.orderNum}
				</td>
				<td>
					<fmt:formatDate value="${postMain.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${postMain.createBy.name}
				</td>
				<td>
					<fmt:formatDate value="${postMain.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cms:postMain:edit"><td>
    				<a href="${ctx}/cms/postMain/form?id=${postMain.id}">修改</a>
					<a href="${ctx}/cms/post/list?postMain.id=${postMain.id}" onclick="return viewPost(this.href)">回帖列表</a>
					<a href="${ctx}/cms/postMain/delete?id=${postMain.id}" onclick="return confirmx('确认要删除该帖子吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>