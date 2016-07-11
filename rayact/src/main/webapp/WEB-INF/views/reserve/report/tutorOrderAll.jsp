<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <title>教练收入统计</title>
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
                    <h3>教练收入统计</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveTutorOrder"
                           action="${ctx}/reserve/reserveTutorOrder/orderAll"
                           method="post">
                <div class="breadcrumb form-search">
                    <div class="row">
                        <div class="col-sm-10 col-md-10 col-lg-10">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>姓名：</td>
                                    <td><form:input path="tutor.name" cssStyle="width:100px;" htmlEscape="false"
                                                    maxlength="30"
                                                    class="form-control"/></td>
                                    <td>项目：</td>
                                    <td>
                                        <sys:select cssClass="input-large" name="tutor.project.id" id="project"
                                                    cssStyle="width:100%"
                                                    value="${query.tutor.project.id}"
                                                    items="${reserveProjectList}" itemLabel="name" itemValue="id"
                                                    defaultLabel="请选择"
                                                    defaultValue=""></sys:select>
                                    </td>
                                    <td>开始日期：</td>
                                    <td><input
                                            value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveTutorOrder.startDate}"/>"
                                            name="startDate" id="startDate" type="text"
                                            maxlength="20"
                                            class="input-medium form-control Wdate"
                                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/></td>
                                    <td>结束日期：</td>
                                    <td><input
                                            value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveTutorOrder.endDate}"/>"
                                            name="endDate" id="endDate" type="text"
                                            maxlength="20"
                                            class="input-medium form-control Wdate "
                                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/></td>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                                        <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <sys:message content="${message}"/>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>教练姓名</th>
                                <th>授课时长/小时</th>
                                <th>教练费/元</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:set var="time" value="0"></c:set>
                            <c:set var="price" value="0"></c:set>
                            <c:forEach items="${page.list}" var="reserveTutorOrder">
                                <tr>
                                    <td>
                                        <a href="${ctx}/reserve/reserveTutorOrder/orderDetail?tutorId=${reserveTutorOrder.tutorId}&tutorName=${reserveTutorOrder.tutorName}&startDate=${startDate}&endDate=${endDate}">
                                                ${reserveTutorOrder.tutorName}
                                        </a></td>
                                    <td>
                                        <fmt:formatNumber value="${reserveTutorOrder.time}" pattern="0.0"
                                                          maxFractionDigits="2" type="number"/>
                                        <c:set var="time" value="${time+reserveTutorOrder.time}"></c:set>
                                    </td>
                                    <td>
                                            ${reserveTutorOrder.price}
                                        <c:set var="price" value="${price+reserveTutorOrder.price}"></c:set>
                                    </td>
                                    <td>
                                        <a class="btn btn-primary btn-xs"
                                           href="${ctx}/reserve/reserveTutorOrder/orderDetail?tutorId=${reserveTutorOrder.tutorId}&tutorName=${reserveTutorOrder.tutorName}&startDate=${startDate}&endDate=${endDate}"><i
                                                class="fa fa-pencil"></i>明细</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td>合计</td>
                                <td>
                                    <fmt:formatNumber value="${time}" pattern="0.0"
                                                      maxFractionDigits="2" type="number"/>
                                </td>
                                <td>${price}</td>
                                <td></td>
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
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $('.icheck').iCheck({
            checkboxClass: 'icheckbox_square-blue checkbox',
            radioClass: 'iradio_square-blue'
        });
    });
</script>
<script type="text/javascript">
    $("#btnExport").click(function () {
        $("#searchForm").attr("action", "${ctx}/reserve/reserveTutorOrder/orderAllExport");
        $("#searchForm").submit();
        $("#searchForm").attr("action", "${ctx}/reserve/reserveTutorOrder/orderAll");
    });
</script>
</body>
</html>
