<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场地售卖报表</title>
    <meta name="decorator" content="main"/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/modules/reserve/css/field.css?id=7862256"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="saleVenueLog"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>场地售卖列表</h3>
                </div>
                <form id="searchForm" action="${ctx}/reserve/saleVenue/list"
                      method="post" class="breadcrumb form-search">
                    <div class="row">
                        <div class="col-sm-12">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>
                                        场馆:
                                    </td>
                                    <td>
                                        <sys:select cssClass="input-xlarge" name="reserveVenue.id"
                                                    items="${venueList}"
                                                    value="${reserveMember.reserveVenue.id}"
                                                    itemLabel="name"
                                                    itemValue="id"
                                                    defaultValue=""
                                                    defaultLabel="请选择场馆"
                                        ></sys:select>
                                        <select name="venue.id" class="select2">
                                            <option value="">---请选择---</option>
                                            <c:forEach items="${venueList}" var="venue">
                                                <option <j:if test="${venue.id eq venueLog.venue.id}">selected="selected"</j:if> value="${venue.id}">${venue.name}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>操作人</td>
                                    <td>
                                        <select name="user.id" class="select2">
                                            <option value="">---请选择---</option>
                                            <c:forEach items="${userList}" var="user">
                                                <option <j:if test="${user.id eq venueLog.user.id}">selected="selected"</j:if> value="${user.id}">${user.name}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>时间:</td>
                                    <td><input name="startDate" value="<fmt:formatDate value="${venueLog.startDate}" pattern="yyyy-MM-dd"/>"
                                               type="text" id="startDate"
                                               maxlength="20"
                                               class="input-medium form-control Wdate "
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
                                               /></td>
                                    <td>
                                        <input name="endDate" value="<fmt:formatDate value="${venueLog.endDate}" pattern="yyyy-MM-dd"/>"  type="text" id="endDate"
                                               maxlength="20"
                                               class="input-medium form-control Wdate "
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
                                               />
                                    </td>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                                        <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
                                    </td>
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
                                <th>所属场馆</th>
                                <th>所属项目</th>
                                <th>订单金额</th>
                                <th>应收金额</th>
                                <th>优惠金额</th>
                                <th>实收金额</th>
                                <th>支付类型</th>
                                <th>预定人</th>
                                <th>操作人</th>
                                <th>授权人</th>
                                <th>订单时间</th>
                                <th>操作时间</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${venueLogList}" var="order">
                                <tr style="height: 30px;">
                                    <td>${order.name}</td>
                                    <td>${order.projectName}
                                        <j:if test="${'1' eq order.reserve_type}">(场次)</j:if>
                                        <j:if test="${'2' eq order.reserve_type}">(人次)</j:if>
                                    </td>
                                    <td>${order.order_price}</td>
                                    <td>${order.should_price}</td>
                                    <td>${order.discount_price}</td>
                                    <td>${order.cons_price}</td>
                                    <td>${fns:getPayType(order.pay_type)}</td>
                                    <td>${order.user_name}</td>
                                    <td>${order.create_user}</td>
                                    <td>${order.checkout_name}</td>
                                    <td><fmt:formatDate value="${order.cons_date}" type="date"></fmt:formatDate></td>
                                    <td><fmt:formatDate value="${order.update_date}" type="both"></fmt:formatDate></td>
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
    $(document).ready(function () {
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
    });
    $("#btnExport").click(function(){
        $("#searchForm").attr("action","${ctx}/reserve/saleVenue/listExport");
        $("#searchForm").submit();
        $("#searchForm").attr("action","${ctx}/reserve/saleVenue/list");
    });
</script>
</body>
</html>
