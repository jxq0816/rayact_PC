<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商品管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="commoditySell"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row col-lg-12">
        <div class="block-flat">
            <div class="header">
                <h3>商品列表</h3>
            </div>
            <form:form id="searchForm" modelAttribute="reserveCommodity"
                       action="${ctx}/reserve/commodity/list"
                       method="post">

                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <div class="breadcrumb form-search">
                    <div class="row">
                        <div class="col-sm-6 col-md-6 col-lg-6">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>名称：</td>
                                    <td><form:input path="name" htmlEscape="false" maxlength="30"
                                                    class="form-control"/></td>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

            <div class="row">
                <%-- left start--%>
                <div class="col-sm-6 col-md-6 col-lg-6">
                    <table id="contentTable">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>名称</th>
                            <th>价格</th>
                            <th>库存量</th>
                            <th>类别</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="commodity">
                            <tr>
                                <td><a href="${ctx}/reserve/commodity/form?id=${commodity.id}">
                                        ${commodity.commodityId}
                                </a></td>
                                <td>
                                        ${commodity.name}
                                </td>
                                <td>
                                        ${commodity.price}&nbsp;元
                                </td>
                                <td>
                                        ${commodity.repertoryNum}
                                </td>
                                <td>
                                        ${commodity.commodityType.name}
                                </td>

                                <td>
                                    <input type="button" id="${commodity.id}" class="btn btn-primary btn-xs" value="售卖"
                                           <j:if test="${commodity.repertoryNum==0}">disabled="true"</j:if>
                                           onclick="outStorage('${commodity.id}','${commodity.name}',${commodity.price},${commodity.repertoryNum})">

                                    </input>
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
                <%--end of left--%>
                <%-- right start--%>
                <div class="col-sm-6 col-md-6 col-lg-6">
                    <form id="paySell">
                        商品销售表

                        <table id="sellList" class="table table-striped table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th align="center">商品名称</th>
                                <th align="center">数量</th>
                            </tr>
                            </thead>
                        </table>

                        合计：<span id="sum">0</span>元
                        <a class="btn btn-success" onclick="settlement()">结算</a>
                    </form>
                </div>
                <%--end of right--%>
            </div>
            </form:form>
        </div>
    </div>
    <%-- end of row-fluid--%>
</div>
<button class="btn btn-primary btn-flat md-trigger" id="settlementDialogBtn" style="display: none"
        data-modal="settlementDialog">
    结算
</button>

<div class="md-modal colored-header custom-width md-effect-12 warning" id="settlementDialog">
    <div class="md-content">
        <div class="modal-header">
            <h5>结算</h5>
            <button type="button" class="close md-close" data-dismiss="modal"
                    aria-hidden="true">&times;</button>
        </div>
        <div class="modal-body form-horizontal " id="settlementForm">
            <!--结算-->


            <!--end 结算-->
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-default btn-flat md-close" data-dismiss="modal">
                取消
            </button>
            <button type="button" id="settlementSaveBtn" class="btn btn-primary btn-flat" onclick="paySubmit()">结算
            </button>
        </div>
    </div>
</div>
<%--end of container-fluid--%>
<script src="${ctxStatic}/modules/reserve/js/reserve_commodity_sell.js"
        type="text/javascript"></script>
</body>
</html>