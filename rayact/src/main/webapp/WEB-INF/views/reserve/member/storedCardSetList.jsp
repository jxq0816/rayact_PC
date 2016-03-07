<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <title>储值卡设置</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="storedcardSet"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>储值卡列表</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveStoredcardMemberSet"
                           action="${ctx}/reserve/storedCardMemberSet/list" method="post"
                           class="breadcrumb form-search">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                    <div class="row">
                        <div class="col-sm-6 col-md-6 col-lg-6">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>名称：</td>
                                    <td><form:input path="name" htmlEscape="false" maxlength="30"
                                                    class="form-control"/></td>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></td>
                                </tr>
                                </tbody>

                            </table>
                        </div>
                        <div class="pull-right" style="margin: auto">
                            <a class="btn btn-success" href="${ctx}/reserve/storedCardMemberSet/form">
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
                                <th>名称</th>
                                <th>项目</th>
                                <th>起止金额</th>
                                <th>赠送金额</th>
                                <th>折扣比率</th>
                                <th>备注</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${page.list}" var="reserveStoredcardMemberSet">
                                <tr>
                                    <td>
                                        <a href="${ctx}/reserve/storedCardMemberSet/form?id=${reserveStoredcardMemberSet.id}">
                                                ${reserveStoredcardMemberSet.name}
                                        </a></td>

                                    <td>${reserveStoredcardMemberSet.reserveProject.name}</td>

                                    <td>${reserveStoredcardMemberSet.startPrice}
                                        ~ ${reserveStoredcardMemberSet.endPrice} 元
                                    </td>

                                    <td>${reserveStoredcardMemberSet.givePrice} 元</td>

                                    <td>${reserveStoredcardMemberSet.discountRate}%</td>

                                    <td>
                                            ${reserveStoredcardMemberSet.remarks}
                                    </td>
                                    <td>
                                        <a class="btn btn-primary btn-xs"
                                           href="${ctx}/reserve/storedCardMemberSet/form?id=${reserveStoredcardMemberSet.id}">
                                            <i class="fa fa-pencil"></i>修改</a>
                                        <a class="btn btn-danger btn-xs"
                                           href="${ctx}/reserve/storedCardMemberSet/delete?id=${reserveStoredcardMemberSet.id}"
                                           onclick="return confirmb('确认要删除该储值卡吗？', this.href)">
                                            <i class="fa fa-times"></i>删除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <div class="row">
                            <div class="col-sm-12">

                                <div class="pull-right">
                                    <div class="dataTables_paginate paging_bs_normal">
                                        <sys:javascript_page p="${page}"></sys:javascript_page>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
