<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <title>交易明细</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="member"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3>交易明细</h3>
                        <a href="${ctx}/reserve/reserveMember/list"><img
                                style="width:30px;height: 30px;position: absolute;top:30px;right:40px"
                                src="${ctxStatic}/modules/reserve/images/return.png"></a>
                    </div>
                    <table class="table">
                        <tr>
                            <th>姓名：${member.name}</th>
                            <th>场馆：${member.reserveVenue.name}</th>
                            <th>卡号：${member.cartno}</th>
                            <th>会员卡类型：
                                <j:if test="${member.cartType==1}">
                                    储值卡
                                </j:if>
                                <j:if test="${member.cartType==2}">
                                    次卡
                                </j:if>
                            </th>
                            <th>手机号：${member.mobile}</th>
                        </tr>
                    </table>

                    <table>
                        <thead>
                        <tr>
                            <th>充值金额</th>
                            <th>消费类型</th>
                            <th>半小时</th>
                            <th>支付方式</th>
                            <th>消费金额</th>
                            <th>会员余额</th>
                            <th>操作员</th>
                            <th>备注</th>
                            <th>操作时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="statement">
                            <tr>
                                <td>
                                    <j:if test="${statement.transactionType==1 or statement.transactionType==7}">
                                        ${statement.transactionVolume}
                                    </j:if>
                                </td>
                                <td>
                                        ${fns:getTransactionType(statement.transactionType)}
                                </td>
                                <td>
                                        ${statement.transactionNum}
                                </td>
                                <td>
                                        ${fns:getPayType(statement.payType)}
                                </td>
                                <td>
                                    <j:if test="${statement.transactionType!=1 and statement.transactionType!=7}">
                                        ${statement.transactionVolume}
                                    </j:if>
                                </td>
                                <td>
                                        ${statement.reserveMember.remainder}
                                </td>

                                <td>
                                        ${statement.updateBy.name}
                                </td>
                                <td>
                                        ${statement.remarks}
                                </td>
                                <td>
                                    <fmt:formatDate value="${statement.updateDate}"
                                                    pattern="yyyy-MM-dd HH:mm:ss"/>

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
        </div>
    </div>
</div>
</body>
</html>
