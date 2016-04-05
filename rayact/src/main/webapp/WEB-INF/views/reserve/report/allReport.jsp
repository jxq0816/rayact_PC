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
    <title>场馆收入汇总</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="allReport"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>场馆收入统计</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveCardStatements"
                           action="${ctx}/reserve/reserveSellReport/list"
                           method="post" class="breadcrumb form-search form-horizontal form-inline">
                    <div class="row">
                        <div class="col-sm-12 col-md-12 col-lg-12">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>场馆:</td>
                                    <td>
                                        <sys:select cssClass="input-large" name="venue.id"
                                                    value="${reserveCardStatements.venue.id}"
                                                    items="${reserveVenueList}" itemLabel="name" itemValue="id"
                                                    defaultLabel="----请选择-----"
                                                    defaultValue=""></sys:select>
                                    </td>
                                    <td>
                                        <input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveCardStatements.startDate}"/>"
                                               name="startDate" id="startDate" type="text" readonly="readonly"
                                               maxlength="20"
                                               class="input-medium form-control Wdate "
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                    </td>
                                    <td>
                                        <input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveCardStatements.endDate}"/>"
                                               name="endDate" id="endDate" type="text" readonly="readonly"
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
                                <th>充值收入</th>
                                <th>储值卡消费</th>
                                <th>次卡消费</th>
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
                                    <td>
                                            ${allReport.commodity_in}
                                    </td>

                                    <td>
                                            ${allReport.field_in}
                                    </td>

                                    <td>
                                            ${allReport.store_in}
                                    </td>

                                    <td>
                                            ${allReport.store_cost}
                                    </td>

                                    <td>
                                            ${allReport.time_cost}
                                    </td>

                                    <td>
                                            ${allReport.commodity_in + allReport.field_in + allReport.store_in - allReport.store_cost - allReport.time_cost}
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
<script src="${ctxStatic}/modules/reserve/js/commodity_income_record.js" type="text/javascript"></script>
<script type="text/javascript">
    $("#btnExport").click(function(){
        $("#searchForm").attr("action","${ctx}/reserve/reserveSellReport/listExport");
        $("#searchForm").submit();
        $("#searchForm").attr("action","${ctx}/reserve/reserveSellReport/list");
    });
</script>
</body>
</html>
