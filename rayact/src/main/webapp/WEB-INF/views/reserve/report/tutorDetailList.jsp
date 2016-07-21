<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>教练明细</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="tutorOrder"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">

    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>${param.tutorName}教练授课明细</h3>
                    <form:form id="searchForm" modelAttribute="reserveTutorOrder"
                               action="${ctx}/reserve/reserveTutorOrder/orderDetail"
                               method="post">
                    <input type="hidden" name="tutorId" value="${param.tutorId}">
                    <input type="hidden" name="tutorName" value="${param.tutorName}">
                    <div class="breadcrumb form-search">
                        <div class="row">
                            <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                            <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

                            <div class="col-lg-2">
                                <input
                                        value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${startDate}"/>"
                                        name="startDate" id="startDate" type="text"
                                        maxlength="20"
                                        class="input-medium form-control Wdate"
                                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                            </div>
                            <div class="col-lg-2">
                                <input
                                        value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${endDate}"/>"
                                        name="endDate" id="endDate" type="text"
                                        maxlength="20"
                                        class="input-medium form-control Wdate "
                                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                            </div>
                            <div class="col-lg-2">
                                <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                                <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
                            </div>
                            </form:form>
                            <div class="col-lg-1 pull-right">

                                <a href="${ctx}/reserve/reserveTutorOrder/orderAll"><img style="width:30px;height: 30px"
                                                                                         src="${ctxStatic}/modules/reserve/images/return.png"></a>
                            </div>
                        </div>
                    </div>
                </div>
                <sys:message content="${message}"/>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>编号</th>
                                <th>时期</th>
                                <th>客户姓名</th>
                                <th>授课时段</th>
                                <th>授课场地</th>
                                <th>授课时长/小时</th>
                                <c:set var="time" value="0"></c:set>
                                <th>教练费</th>
                                <c:set var="price" value="0"></c:set>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${page.list}" var="order">
                                <tr>
                                    <td>
                                            ${order.id}
                                    </td>
                                    <td>
                                            ${order.date}
                                    </td>
                                    <td>
                                            ${order.name}
                                    </td>
                                    <td>
                                            ${order.startTime}-${order.endTime}
                                    </td>
                                    <td>
                                            ${order.fieldName}
                                    </td>
                                    <td>
                                            ${order.hour}
                                        <c:set var="time" value="${time+order.hour}"></c:set>
                                    </td>
                                    <td>
                                            ${order.price}元
                                        <c:set var="price" value="${price+order.price}"></c:set>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td colspan="5">
                                    合计
                                </td>
                                <td>
                                    ${time}
                                </td>
                                <td>
                                    ${price}元
                                </td>
                            </tr>
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
<script type="text/javascript">
    $("#btnExport").click(function () {
        $("#searchForm").attr("action", "${ctx}/reserve/reserveTutorOrder/orderDetailExport");
        $("#searchForm").submit();
        $("#searchForm").attr("action", "${ctx}/reserve/reserveTutorOrder/orderDetail");
    });
</script>
</body>
</html>