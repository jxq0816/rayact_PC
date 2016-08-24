<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <title>次卡会员管理</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="timeCardMember"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>次卡会员列表</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveMember" action="${ctx}/reserve/timeCardMember/list/"
                           method="post">
                    <div class="breadcrumb form-search">
                        <div class="row">
                            <div class="col-sm-9 col-md-9 col-lg-9">
                                <table class="no-border">
                                    <tbody class="no-border-y">
                                    <tr>
                                        <td>姓名：</td>
                                        <td><form:input path="name" htmlEscape="false" maxlength="30"
                                                        class="form-control"/></td>

                                        <td>手机号：</td>
                                        <td><form:input path="mobile" htmlEscape="false" maxlength="20"
                                                        class="form-control"/>
                                        </td>
                                        <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                                        </td>

                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="pull-right">
                                <a class="btn btn-success" href="${ctx}/reserve/timeCardMember/form">
                                    <i class="fa fa-plus"></i>添加
                                </a>
                            </div>
                        </div>
                    </div>

                    <sys:message content="${message}"/>
                    <div class="content">
                        <div class="table-responsive">
                            <table>
                                <thead>
                                <tr>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                    <th>性别</th>
                                    <th>场馆</th>
                                    <th>次卡名称</th>
                                    <th>余额</th>
                                    <th>剩余次数</th>
                                    <th>开始时间</th>
                                    <th>到期时间</th>
                                    <th>操作</th>
                                    <th>交易</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${page.list}" var="reserveMember">
                                    <tr>
                                        <td><a href="${ctx}/reserve/timeCardMember/form?id=${reserveMember.id}">
                                                ${reserveMember.name}
                                        </a></td>
                                        <td>
                                                ${fns:hidePhone(reserveMember.mobile)}
                                        </td>
                                        <td>
                                                ${fns:getDictLabel(reserveMember.sex, 'sex', '')}
                                        </td>
                                        <td>
                                                ${reserveMember.reserveVenue.name}
                                        </td>
                                        <td>
                                                ${reserveMember.timecardSet.name}
                                        </td>
                                        <td>
                                                ${reserveMember.remainder}
                                        </td>
                                        <td>
                                                ${reserveMember.residue}
                                        </td>

                                        <td>
                                            <fmt:formatDate value="${reserveMember.validitystart}" type="date"/>
                                        </td>

                                        <td>
                                            <fmt:formatDate value="${reserveMember.validityend}" type="date"/>
                                        </td>
                                        <td>
                                            <a class="btn btn-primary btn-xs"
                                               href="${ctx}/reserve/timeCardMember/form?id=${reserveMember.id}"><i
                                                    class="fa fa-pencil"></i>修改</a>
                                            <a class="btn btn-danger btn-xs"
                                               href="${ctx}/reserve/timeCardMember/delete?id=${reserveMember.id}"
                                               onclick="return confirmb('确认要删除该会员吗？', this.href)"> <i
                                                    class="fa fa-times"></i>删除</a>
                                        </td>
                                        <td>
                                            <a class="btn btn-primary btn-xs"
                                               onclick="addTime('${reserveMember.id}')"><i
                                                    class="fa fa-pencil"></i>充值</a>
                                            <a class="btn btn-primary btn-xs"
                                               href="${ctx}/reserve/reserveTimeCardPrepayment/list?memberId=${reserveMember.id}"
                                               class="fa fa-pencil">预付款记录</a>

                                            <a class="btn btn-primary btn-xs"
                                               href="${ctx}/reserve/reserveMember/statements?reserveMember.id=${reserveMember.id}">交易明细</a>

                                            <a class="btn btn-danger btn-xs"
                                               onclick="cancelAccountForm('${reserveMember.id}')"> <i
                                                    class="fa fa-times"></i>销卡</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="pull-right">
                                        <div class="dataTables_paginate paging_bs_normal">
                                            <sys:javascript_page p="${page}" formId="searchForm"></sys:javascript_page>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../include/timeCardCancelAccountDialog.jsp"></jsp:include>
<jsp:include page="../include/timeCardAddDialog.jsp"></jsp:include>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/time_card.js"></script>
</body>
</html>
