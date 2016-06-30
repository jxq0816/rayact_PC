<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场地关系管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="fieldRelation"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>场地关系列表</h3>
                </div>


                <form:form id="searchForm" modelAttribute="reserveFieldRelation" action="${ctx}/reserve/reserveFieldRelation/"
                           method="post" class="breadcrumb form-search">

                    <div class="row">
                        <div class="col-sm-6 col-md-6 col-lg-6">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>父场地：</td>
                                    <td><sys:select cssClass="input-medium" name="parentField.id"
                                                    cssStyle="width:100%"
                                                    value="${reserveFieldRelation.parentField.id}"
                                                    items="${fields}" itemLabel="name" itemValue="id"
                                                    defaultLabel="----请选择-----"
                                                    defaultValue=""></sys:select></td>
                                    <td>子场地：</td>
                                    <td><sys:select cssClass="input-medium" name="childField.id"
                                                    cssStyle="width:100%"
                                                    value="${reserveFieldRelation.childField.id}"
                                                    items="${fields}" itemLabel="name" itemValue="id"
                                                    defaultLabel="----请选择-----"
                                                    defaultValue=""></sys:select></td>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>


                        <div class="pull-right">
                            <a class="btn btn-success" href="${ctx}/reserve/reserveFieldRelation/form">
                                <i class="fa fa-plus"></i>添加
                            </a>
                        </div>
                    </div>
                </form:form>
                <sys:message content="${message}"/>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>父场地名称</th>
                                <th>子场地名称</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${reserveFieldRelationList}" var="reserveFieldRelation">
                                <tr>
                                    <td>${reserveFieldRelation.parentField.name}</td>
                                    <td>${reserveFieldRelation.childField.name}</td>
                                    <td>
                                        <a class="btn btn-primary btn-xs"
                                           href="${ctx}/reserve/reserveFieldRelation/form?id=${reserveFieldRelation.id}"><i
                                                class="fa fa-pencil"></i>修改</a>
                                        <a class="btn btn-danger btn-xs"
                                           href="${ctx}/reserve/reserveFieldRelation/delete?id=${reserveFieldRelation.id}"
                                           onclick="return confirmb('确认要删除该场地关系吗？', this.href)"><i
                                                class="fa fa-times"></i>删除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>