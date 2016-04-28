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
                      method="post">
                    <div class="row breadcrumb form-search col-lg-12 col-sm-12" style="margin-left:0px; margin-right:0px;">
                        <div class="form-group col-lg-3 col-sm-4">
                            <label class="control-label" for="venue">场馆：</label>
                            <select id="venue" name="venue.id" class="select2">
                                <option value="">---请选择---</option>
                                <c:forEach items="${venueList}" var="venue">
                                    <option
                                            <j:if test="${venue.id eq venueLog.venue.id}">selected="selected"</j:if>
                                            value="${venue.id}">${venue.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group col-lg-3 col-sm-4">
                            <label class="control-label" for="createBy">操作人：</label>
                            <select id="createBy" name="createBy.id" class="select2">
                                <option value="">请选择</option>
                                <c:forEach items="${userList}" var="createBy">
                                    <option
                                            <j:if test="${createBy.id eq venueLog.createBy.id}">selected="selected"</j:if>
                                            value="${createBy.id}">${createBy.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group col-lg-4 col-sm-3">
                            <div class="col-lg-6 col-sm-6">
                                <input name="startDate"
                                       value="<fmt:formatDate value="${venueLog.startDate}" pattern="yyyy-MM-dd"/>"
                                       type="text" id="startDate"
                                       maxlength="20"
                                       class="input-medium form-control Wdate "
                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
                                />
                            </div>
                            <div class="col-lg-6 col-sm-6">
                                <input name="endDate"
                                       value="<fmt:formatDate value="${venueLog.endDate}" pattern="yyyy-MM-dd"/>"
                                       type="text" id="endDate"
                                       maxlength="20"
                                       class="input-medium form-control Wdate "
                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
                                />
                            </div>
                        </div>
                        <div class="form-group col-lg-2 col-sm-3">
                            <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                            <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
                        </div>
                    </div>
                    <div class="content">
                        <div class="table-responsive">
                            <table>
                                <thead>
                                <tr>
                                    <th>订单编号</th>
                                    <th>所属场馆</th>
                                    <th>所属项目</th>
                                    <th>时间区间</th>
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
                                <c:forEach items="${page.list}" var="log">
                                    <tr style="height: 30px;">
                                        <td>${log.id}</td>
                                        <td>${log.venue.name}</td>
                                        <td>${log.projectName}</td>
                                        <td>${log.startTime}—${log.endTime}</td>
                                        <td>${log.orderPrice}</td>
                                        <td>${log.shouldPrice}</td>
                                        <td>${log.discountPrice}</td>
                                        <td>${log.consPrice}</td>
                                        <td>${fns:getPayType(log.payType)}
                                            <j:if test="${log.payType==8}">
                                                <a onclick="multiple_payments('${log.id}')">详情</a>
                                            </j:if>
                                        </td>
                                        <td>${log.member.name}</td>
                                        <td>${log.createBy.name}</td>
                                        <td>${log.checkoutName}</td>
                                        <td><fmt:formatDate value="${log.consDate}"
                                                            type="date"></fmt:formatDate></td>
                                        <td><fmt:formatDate value="${log.consDate}"
                                                            type="both"></fmt:formatDate></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
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
                </form>
            </div>

        </div>
    </div>
</div>
<%@include file="../include/modal.jsp" %>
<script type="text/javascript">
    $("#btnExport").click(function () {
        $("#searchForm").attr("action", "${ctx}/reserve/saleVenue/listExport");
        $("#searchForm").submit();
        $("#searchForm").attr("action", "${ctx}/reserve/saleVenue/list");
    });
</script>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/multiple_payments.js?t=" + Math.random()></script>
</body>
</html>
