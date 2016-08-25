<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<html>
<head>
    <title>支付成功</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="commoditySell"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>支付成功</h3>
                </div>
                <div class="content">
                    <div class="tab-container">
                        <div class="form-horizontal group-border-dashed">
                            <form:form id="inputForm" modelAttribute="reserveVenueBill"
                                       action="${ctx}/reserve/commodity/onShelfList" method="post"
                                       class="form-horizontal">
                            <table id="contentTable" class="table table-bordered">
                                <thead>
                                <tr>
                                    <th>商品名称</th>
                                    <th>单价</th>
                                    <th>数量</th>
                                    <th>合计</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${sellReport.sellDetailList}" var="sellDetail">
                                    <tr>
                                        <td>
                                                ${sellDetail.reserveCommodity.name}
                                        </td>
                                        <td>
                                                ${sellDetail.price}
                                        </td>
                                        <td>
                                                ${sellDetail.num}
                                        </td>
                                        <td>
                                                ${sellDetail.detailSum}
                                        </td>
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td>总计</td>
                                    <td colspan="3">${sellReport.totalSum}元</td>
                                </tr>
                                <tr>
                                    <td>购买人</td>
                                    <td colspan="3">${sellReport.sellDetailList[0].reserveMember.name}</td>
                                </tr>
                                <tr>
                                    <td>操作人</td>
                                    <td colspan="3">${sellReport.sellDetailList[0].updateBy.name}</td>
                                </tr>
                                <tr>
                                    <td>时间</td>
                                    <td colspan="3"><fmt:formatDate value="${sellReport.sellDetailList[0].updateDate}"
                                                                    type="both"/></td>
                                </tr>

                                </tbody>
                            </table>
                        </div>
                        <div>
                            <div>
                                <input id="btnSubmit"
                                       class="btn btn-primary"
                                       type="submit"
                                       value="确认"/>
                                <%@include file="printCommoditySettlementResult.jsp" %>
                            </div>
                        </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>

    </div>

</div>
</body>
</html>