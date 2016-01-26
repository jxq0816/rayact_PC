<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商品管理</title>
    <meta name="decorator" content="default"/>
    <link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>

    <script type="text/javascript">
        var ctx ='${ctx}';
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/reserve/commodity/">商品列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="reserveCommodity" action="${ctx}/reserve/commodity/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>名称：</label>
            <form:input path="name" htmlEscape="false" maxlength="30" class="input-medium"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<div class="container-fluid">
    <div class="row-fluid">
        <%-- left start--%>
        <div class="span6">
            <table id="contentTable" class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th>编号</th>
                    <th>名称</th>
                    <th>价格</th>
                    <th>库存量</th>
                    <th>类别</th>
                    <shiro:hasPermission name="reserve:commodity:edit">
                        <th>操作</th>
                    </shiro:hasPermission>
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

                        <shiro:hasPermission name="reserve:commodity:edit">
                            <td>
                                <button id="${commodity.id}" class="btn btn-primary btn-xs"
                                        onclick="outStorage('${commodity.id}','${commodity.name}',${commodity.price})">
                                    售卖
                                </button>
                            </td>
                        </shiro:hasPermission>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="pagination">${page}</div>
        </div>
        <%--end of left--%>
        <%-- right start--%>
        <div class="span6">
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
                <a class="btn btn-success" onclick="sellSubmit()">付款</a>
            </form>
        </div>

        <%--end of right--%>
    </div>
    <%-- end of row-fluid--%>
</div>
<%--end of container-fluid--%>
<script src="${ctxStatic}/modules/reserve/js/reserve_commodity_sell.js?id=66" type="text/javascript"></script>
</body>
</html>