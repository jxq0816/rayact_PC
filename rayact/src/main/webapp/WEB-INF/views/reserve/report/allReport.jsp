<%--
  Created by IntelliJ IDEA.
  User: DDT
  Date: 2016/4/5
  Time: 10:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/modules/reserve/css/field.css?id=7862256"/>
    <title>场馆收益统计</title>
</head>
<body>
<c:if test="${param.alone != 'true'}">
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="allReport"></jsp:param>
</jsp:include>
</c:if>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>场馆收益统计</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveCardStatements"
                           action="${ctx}/reserve/reserveSellReport/list?alone=${alone}"
                           method="post" class="breadcrumb form-search form-horizontal form-inline">
                    <div class="row">
                        <div class="col-sm-12 col-md-12 col-lg-12">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>场馆：</td>
                                    <td>
                                        <sys:select cssClass="input-large" name="venue.id"
                                                    cssStyle="width:80%"
                                                    value="${reserveCardStatements.venue.id}"
                                                    items="${reserveVenueList}" itemLabel="name" itemValue="id"
                                                    defaultLabel="----请选择-----"
                                                    defaultValue=""></sys:select>
                                    </td>
                                    <td>
                                        <input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveCardStatements.startDate}"/>"
                                               name="startDate" id="startDate" type="text"
                                               maxlength="20"
                                               class="input-medium form-control Wdate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                    </td>
                                    <td>

                                        <input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveCardStatements.endDate}"/>"
                                               name="endDate" id="endDate" type="text"
                                               maxlength="20"
                                               class="input-medium form-control Wdate "
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                    </td>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit"
                                               value="查询"/>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </form:form>
                <sys:msg content="${message}"/>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>场馆</th>
                                <th>商品收入</th>
                                <th>场地收入</th>
                                <th>违约金收入</th>
                                <%--<th>储值卡消费</th>--%>
                                <%--<th>次卡消费</th>--%>
                                <th>合计</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${rtn}" var="allReport">
                                <tr>
                                    <td>
                                            ${allReport.venue_name}
                                            <div style="display: none">${allReport.venue_id}</div>
                                    </td>
                                    <td class="commodity_in">
                                            ${allReport.commodity_in}
                                    </td>

                                    <td class="field_in">
                                            ${allReport.field_in}
                                    </td>

                                    <td class="break_in">
                                            ${allReport.break_in}
                                    </td>

                                    <%--<td class="store_in">--%>
                                            <%--${allReport.store_in}--%>
                                    <%--</td>--%>

                                    <%--<td class="store_cost">--%>
                                            <%--${allReport.store_cost}--%>
                                    <%--</td>--%>

                                    <%--<td class="time_cost">--%>
                                            <%--${allReport.time_cost}--%>
                                    <%--</td>--%>

                                    <td class="all">
                                            ${allReport.commodity_in + allReport.field_in + allReport.break_in}
                                    </td>
                                </tr>
                            </c:forEach>
                                <tr>
                                    <td>
                                       总计
                                    </td>
                                    <td class="commodity_in_all">
                                        ${allReport.commodity_in}
                                    </td>

                                    <td class="field_in_all">
                                        ${allReport.field_in}
                                    </td>

                                    <td class="break_in_all">
                                        ${allReport.break_in}
                                    </td>

                                    <%--<td class="store_in_all">--%>
                                        <%--${allReport.store_in}--%>
                                    <%--</td>--%>

                                    <%--<td class="store_cost_all">--%>
                                        <%---${allReport.store_cost}--%>
                                    <%--</td>--%>

                                    <%--<td class="time_cost_all">--%>
                                       <%--- ${allReport.time_cost}--%>
                                    <%--</td>--%>

                                    <td class="all_all">
                                        ${allReport.commodity_in + allReport.field_in + allReport.break_in}
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctxStatic}/modules/reserve/js/commodity_income_record.js" type="text/javascript"></script>
<script type="text/javascript">
    $("#btnExport").click(function(){
        $("#searchForm").attr("action","${ctx}/reserve/reserveSellReport/listExport");
        $("#searchForm").submit();
        $("#searchForm").attr("action","${ctx}/reserve/reserveSellReport/list");
    });
    $(function(){
        var commodity = 0;
        $(".commodity_in").each(function(){
            commodity += parseFloat($(this).html());
        });
        $(".commodity_in_all").html(commodity);
        var field = 0;
        $(".field_in").each(function(){
            field += parseFloat($(this).html());
        });
        $(".field_in_all").html(field);
        var breaks = 0;
        $(".break_in").each(function(){
            breaks += parseFloat($(this).html());
        });
        $(".break_in_all").html(breaks);
//        var store = 0;
//        $(".store_in").each(function(){
//            store += parseFloat($(this).html());
//        });
//        $(".store_in_all").html(store);
//        var store_cost = 0;
//        $(".store_cost").each(function(){
//            store_cost += parseFloat($(this).html());
//        });
//        $(".store_cost_all").html(store_cost);
//        var time_cost = 0;
//        $(".time_cost").each(function(){
//            time_cost += parseFloat($(this).html());
//        });
//        $(".time_cost_all").html(time_cost);
        var all = 0;
        $(".all").each(function(){
            all += parseFloat($(this).html());
        });
        $(".all_all").html(all);
    });
</script>
</body>
</html>
