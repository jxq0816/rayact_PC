<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商品销售明细管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="reserveCommoditySellDetail"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>商品销售记录</h3>
                </div>

                <form:form id="searchForm" modelAttribute="reserveCommoditySellDetail"
                           action="${ctx}/reserve/reserveCommoditySellDetail/findSellDetailList" method="post"
                           class="breadcrumb form-search">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

                    <div class="row">
                        <div class="col-sm-10 col-md-10 col-lg-10">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>商品名称:</td>
                                    <td>
                                        <form:input path="reserveCommodity.name" htmlEscape="false"
                                                    cssstyle="width:50px;"
                                                    maxlength="30"
                                                    class="form-control"/>
                                    </td>
                                    <td>时间:</td>
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
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                </form:form>
                <sys:message content="${message}"/>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>商品名称</th>
                                <th>商品类型</th>
                                <th>购买数量</th>
                                <th>单价</th>
                                <th>合计</th>
                                <th>售卖人</th>
                                <th>时间</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${page.list}" var="reserveCommoditySellDetail">
                                <tr>
                                    <td>${reserveCommoditySellDetail.reserveCommodity.name}</td>
                                    <td>${reserveCommoditySellDetail.reserveCommodity.commodityType.name}</td>
                                    <td>${reserveCommoditySellDetail.num}</td>
                                    <td>${reserveCommoditySellDetail.price}</td>
                                    <td>${reserveCommoditySellDetail.detailSum}</td>
                                    <td>${reserveCommoditySellDetail.updateBy.name}</td>
                                    <td><fmt:formatDate value="${reserveCommoditySellDetail.createDate}"
                                                        type="both"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="row">
                            <div class="col-sm-12">

                                <div class="pull-right">
                                    <div class="dataTables_paginate paging_bs_normal">
                                        <sys:javascript_page p="${page}"></sys:javascript_page>
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
    $("#btnExport").click(function(){
        $("#searchForm").attr("action","${ctx}/reserve/reserveCommoditySellDetail/findSellDetailListExport");
        $("#searchForm").submit();
        $("#searchForm").attr("action","${ctx}/reserve/reserveCommoditySellDetail/findSellDetailList");
    });
</script>
</body>
</html>
