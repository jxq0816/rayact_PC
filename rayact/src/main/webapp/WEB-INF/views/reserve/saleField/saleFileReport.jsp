<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>项目收入统计</title>
    <meta name="decorator" content="main"/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/modules/reserve/css/field.css?id=7862256"/>
</head>
<body>
<c:if test="${param.alone != 'true'}">
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="projectIncomeReport"></jsp:param>
</jsp:include>
</c:if>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>项目收入统计</h3>
                </div>
                <form id="searchForm" action="${ctx}/reserve/saleVenueReport/list?alone=${alone}"
                      method="post" class="content">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

                    <div class="row">
                        <div class="col-sm-10">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td colspan="3">
                                        <div class="tab-tit-first">
                                            <ul>
                                                <li <j:if test="${'1' eq search}">class="on"</j:if>><a
                                                        href="${ctx}/reserve/saleVenueReport/list?search=1&alone=${alone}">当天</a></li>
                                                <li <j:if test="${'2' eq search}">class="on"</j:if>><a
                                                        href="${ctx}/reserve/saleVenueReport/list?search=2&alone=${alone}">当月</a></li>
                                                <li <j:if test="${'3' eq search}">class="on"</j:if>><a
                                                        href="${ctx}/reserve/saleVenueReport/list?search=3&alone=${alone}">当年</a></li>
                                             <%--   <li id="userDesign" style="cursor: hand;"
                                                    <j:if test="${'4' eq search}">class="on"</j:if>><a>自定义</a></li>--%>
                                            </ul>
                                        </div>
                                    </td>
                                    <td><input name="startDate" value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>" type="text" id="startDate"
                                               maxlength="20"
                                               class="input-medium form-control Wdate "
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
                                               value="${reserveVenue.endDate}"/></td>
                                    <td>
                                        <input name="endDate" value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>"  type="text" id="endDate"
                                               maxlength="20"
                                               class="input-medium form-control Wdate " onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
                                               value="${reserveVenue.endDate}"/>
                                    </td>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </form>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>项目类型</th>
                                <th>应收金额</th>
                                <th>实收金额</th>
                                <th>会员优惠</th>
                                <th>订单数量</th>
                                <th>储值卡</th>
                                <th>现金</th>
                                <th>银行卡</th>
                                <th>转账</th>
                                <th>微信</th>
                                <th>微信（个人）</th>
                                <th>支付宝</th>
                                <th>支付宝（个人）</th>
                                <th>优惠券</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${orderList}" var="report">
                                <tr style="height: 30px;">
                                    <td>${report.projectName}(${report.saleType})</td>
                                    <td>${report.shouldPrice}</td>
                                    <td>${report.orderPrice}</td>
                                    <td>${report.discountPrice}</td>
                                    <td>${report.total}</td>
                                    <td>${report.storedCardBill}</td>
                                    <td>${report.cashBill}</td>
                                    <td>${report.bankCardBill}</td>
                                    <td>${report.transferBill}</td>
                                    <td>${report.weiXinBill}</td>
                                    <td>${report.personalWeiXinBill}</td>
                                    <td>${report.aliPayBill}</td>
                                    <td>${report.personalAliPayBill}</td>
                                    <td>${report.otherPrice}</td>
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
<script type="text/javascript">
   /* $(document).ready(function () {
        $("#userDesign").on('click',function(){
            $(".tab-tit-first ul li").removeClass("on");
            $(this).addClass("on");
            $("#startDate").unbind('click').bind('click',function(){
                WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});
            });
            $("#endDate").unbind('click').bind('click',function(){
                WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});
            });
        });
    });*/
    $("#btnExport").click(function(){
        $("#searchForm").attr("action","${ctx}/reserve/saleVenueReport/export");
        $("#searchForm").submit();
        $("#searchForm").attr("action","${ctx}/reserve/saleVenueReport/list");
    });
</script>
</body>
</html>
