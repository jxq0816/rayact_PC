<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <title>次卡销户记录</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="timeCardCancellationStatements"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>次卡销户记录</h3>
                </div>

                <form:form id="searchForm" modelAttribute="reserveCardStatements"
                           action="${ctx}/reserve/reserveCardStatements/list"
                           method="post">
                    <div class="breadcrumb form-search">
                        <input id="transactionType" name="transactionType" type="hidden" value="11"/>
                        <div class="row">
                            <div class="col-sm-10 col-md-10 col-lg-10">
                                <table class="no-border">
                                    <tbody class="no-border-y">
                                    <tr>
                                        <td>
                                            <label class="control-label" for="venue">场馆：</label>
                                        </td>
                                        <td>
                                            <sys:select cssClass="input-large" name="venue.id" id="venue"
                                                        cssStyle="width:100%"
                                                        value="${query.venue.id}"
                                                        items="${reserveVenueList}" itemLabel="name" itemValue="id"
                                                        defaultLabel="----请选择-----"
                                                        defaultValue=""></sys:select>
                                        </td>
                                        <td><form:input placeholder="请输入姓名、卡号或手机号" path="reserveMember.name"
                                                        htmlEscape="false" maxlength="30"
                                                        class="form-control"/></td>
                                        <td>时间:</td>
                                        <td>
                                            <input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveCardStatements.startDate}"/>"
                                                   name="startDate" id="startDate" type="text"
                                                   maxlength="20"
                                                   class="input-medium form-control Wdate"
                                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
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
                                        <td>
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
                                    <th>场馆</th>
                                    <th>姓名</th>
                                    <th>卡内余额</th>
                                    <th>退还金额</th>
                                    <th>手机号</th>
                                    <th>操作人</th>
                                    <th>时间</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${page.list}" var="reserveCardStatements">
                                    <tr>
                                        <td>
                                                ${reserveCardStatements.venue.name}
                                        </td>
                                        <td>
                                                ${reserveCardStatements.reserveMember.name}
                                        </td>
                                        <td>
                                                ${reserveCardStatements.reserveMember.remainder}
                                        </td>
                                        <td>
                                                ${reserveCardStatements.transactionVolume}
                                        </td>
                                        <td>
                                                ${fns:hidePhone(reserveCardStatements.reserveMember.mobile)}
                                        </td>
                                        <td>
                                                ${reserveCardStatements.createBy.name}
                                        </td>

                                        <td>
                                            <fmt:formatDate value="${reserveCardStatements.createDate}"
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
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $("#btnExport").click(function () {
        $("#searchForm").attr("action", "${ctx}/reserve/reserveCardStatements/listExport");
        $("#searchForm").submit();
        $("#searchForm").attr("action", "${ctx}/reserve/reserveCardStatements/list");
    });
</script>
</body>
</html>
