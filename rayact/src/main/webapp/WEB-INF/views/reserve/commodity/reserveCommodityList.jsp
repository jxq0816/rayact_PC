<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商品管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="commodity"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>商品列表</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveCommodity" action="${ctx}/reserve/commodity/list"
                           method="post">
                    <div class="breadcrumb form-search">
                        <div class="row">
                            <div class="col-sm-12 col-md-12 col-lg-12">
                                <div class="form-group col-lg-3 col-sm-5">
                                    <label class="control-label" for="venue">场馆：</label>
                                    <sys:select cssClass="input-large" name="reserveVenue.id" id="venue"
                                                cssStyle="width:50%"
                                                value="${query.reserveVenue.id}"
                                                items="${venues}" itemLabel="name" itemValue="id"
                                                defaultLabel="----请选择-----"
                                                defaultValue=""></sys:select>
                                </div>
                                <div class="form-group col-lg-3">
                                    <form:input placeholder="请输入商品名称或者简写" path="quickSearch" htmlEscape="false"
                                                cssstyle="width:70px;" maxlength="30"
                                                class="form-control"/>
                                </div>
                                <div class="form-group col-lg-2">
                                    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                                    <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
                                </div>
                                <div class="form-group pull-right">
                                    <a class="btn btn-success" href="${ctx}/reserve/commodity/form">
                                        <i class="fa fa-plus"></i>添加
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="content">
                        <div class="table-responsive">
                            <table>
                                <thead>
                                <tr>
                                    <th>编号</th>
                                    <th>名称</th>
                                    <th>价格</th>
                                    <th>库存量</th>
                                    <th>规格</th>
                                    <th>类别</th>
                                    <th>场馆</th>
                                    <th>状态</th>
                                    <j:if test="${userType==5 or userType==1 or userType==6}">
                                        <th>操作</th>
                                    </j:if>
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
                                            1*${commodity.unit} ${commodity.unitName}
                                        </td>
                                        <td>
                                                ${commodity.commodityType.name}
                                        </td>
                                        <td>
                                                ${commodity.reserveVenue.name}
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${commodity.shelvesStatus== '0'}">
                                                    下架
                                                </c:when>
                                                <c:otherwise>
                                                    上架
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <j:if test="${userType==5 or userType==1 or userType==6}">
                                            <td>
                                                <a class="btn btn-primary btn-xs "
                                                   href="${ctx}/reserve/commodity/form?id=${commodity.id}">修改</a>
                                                <a class="btn btn-danger btn-xs"
                                                   href="${ctx}/reserve/commodity/delete?id=${commodity.id}"
                                                   onclick="return confirmb('确认要删除该商品吗？', this.href)">删除</a>

                                                <a class="btn btn-primary btn-xs instorageBtn"
                                                   data-id="${commodity.id}">入库</a>
                                            </td>
                                        </j:if>
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
<%@include file="../include/modal.jsp" %>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/reserve_commodity_instorage.js"></script>
<script type="text/javascript">
    $("#btnExport").click(function () {
        $("#searchForm").attr("action", "${ctx}/reserve/commodity/listExport");
        $("#searchForm").submit();
        $("#searchForm").attr("action", "${ctx}/reserve/commodity/list");
    });
</script>
</body>
</html>