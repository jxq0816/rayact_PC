<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <title>商品入库记录管理</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="reserveCommodityStorageLog"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>商品入库记录列表</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveCommodityStorageLog"
                           action="${ctx}/reserve/reserveCommodityStorageLog/list"
                           method="post">
                    <div class="breadcrumb form-search">
                        <div class="row">
                            <div class="col-sm-10 col-md-10 col-lg-10">

                                <div class="form-group">
                                    <label class="col-sm-1 control-label"
                                           style="height: 36px;line-height:36px;overflow: hidden;">场馆:</label>
                                    <div class="col-sm-2 ">
                                        <sys:select cssClass="input-large" name="reserveVenue.id"
                                                    value="${query.reserveVenue.id}"
                                                    items="${venues}" itemLabel="name" itemValue="id"
                                                    defaultLabel="----请选择-----"
                                                    defaultValue=""></sys:select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label"
                                           style="height: 36px;line-height:36px;overflow: hidden;">时间:</label>
                                    <div class="col-sm-2 ">
                                        <input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${query.startDate}"/>"
                                               name="startDate" id="startDate" type="text"
                                               maxlength="20"
                                               class="input-medium form-control Wdate"
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                    </div>
                                    <div class="col-sm-2 ">
                                        <input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${query.endDate}"/>"
                                               name="endDate" id="endDate" type="text"
                                               maxlength="20"
                                               class="input-medium form-control Wdate "
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                    </div>
                                    <div class="col-sm-2 ">
                                        <input id="btnSubmit" class="btn btn-primary" type="submit"
                                               value="查询"/>
                                    </div>
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
                                    <th>场馆</th>
                                    <th>商品</th>
                                    <th>入库箱数</th>
                                    <th>入库量</th>
                                    <th>入库前库存量</th>
                                    <th>入库后库存量</th>
                                    <th>入库单箱价格</th>
                                    <th>入库单价</th>
                                    <th>操作人</th>
                                    <th>时间</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${page.list}" var="reserveCommodityStorageLog">
                                    <tr>
                                        <td>
                                                ${reserveCommodityStorageLog.reserveVenue.name}
                                        </td>
                                        <td>
                                                ${reserveCommodityStorageLog.reserveCommodity.name}
                                        </td>
                                        <td>
                                                ${reserveCommodityStorageLog.boxNum}
                                        </td>
                                        <td>
                                                ${reserveCommodityStorageLog.num}
                                        </td>
                                        <td>
                                                ${reserveCommodityStorageLog.beforeNum}
                                        </td>
                                        <td>
                                                ${reserveCommodityStorageLog.afterNum}
                                        </td>
                                        <td>
                                                ${reserveCommodityStorageLog.boxPrice}
                                        </td>
                                        <td>
                                                ${reserveCommodityStorageLog.price}
                                        </td>
                                        <td>
                                                ${reserveCommodityStorageLog.createBy.name}
                                        </td>

                                        <td>
                                            <fmt:formatDate value="${reserveCommodityStorageLog.createDate}"
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
        $("#searchForm").attr("action", "${ctx}/reserve/reserveCommodityStorageLog/listExport");
        $("#searchForm").submit();
        $("#searchForm").attr("action", "${ctx}/reserve/reserveCommodityStorageLog/list");
    });
</script>
</body>
</html>
