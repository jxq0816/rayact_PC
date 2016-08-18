<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <title>次卡会员预付款列表</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="timeCardMember"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="content">
                <div class="block-flat">
                    <div class="header">
                        <div class="row">
                            <div class="col-lg-2">
                                <h3>会员预付款列表</h3>
                            </div>
                            <div class="pull-right col-lg-1">
                                <a onclick="history.go(-1)"><img
                                        style="width:30px;height: 30px"
                                        src="${ctxStatic}/modules/reserve/images/return.png"></a>
                            </div>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>编号</th>
                                <th>姓名</th>
                                <th>项目</th>
                                <th>剩余次数</th>
                                <th>余额</th>
                                <th>单次价格</th>
                                <th>充值时间</th>
                                <th>备注</th>
                                <j:if test="${userType==1 || userType==5}">
                                    <th>操作</th>
                                </j:if>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${page.list}" var="prepayment">
                                <tr>
                                    <td>
                                            ${prepayment.id}
                                    </td>
                                    <td>
                                            ${prepayment.reserveMember.name}
                                    </td>
                                    <td>
                                            ${prepayment.reserveProject.name}
                                    </td>
                                    <td>
                                            ${prepayment.remainTime}
                                    </td>
                                    <td>
                                            ${prepayment.remainder}
                                    </td>
                                    <td>
                                            ${prepayment.singleTimePrice}
                                    </td>
                                    <td>

                                        <fmt:formatDate value="${prepayment.createDate}" type="both"/>
                                    </td>
                                    <td>
                                            ${prepayment.remarks}
                                    </td>
                                    <j:if test="${userType==1 || userType==5}">
                                        <td>
                                            <a class="btn btn-danger btn-xs"
                                               href="${ctx}/reserve/reserveTimeCardPrepayment/delete?id=${prepayment.id}"
                                               onclick="return confirmb('确认要删除该记录吗？', this.href)"> <i
                                                    class="fa fa-times"></i>删除</a>
                                        </td>
                                    </j:if>
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
            </div>
        </div>
    </div>
</div>