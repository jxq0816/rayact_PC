<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场馆管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="venue"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>场馆管理</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveVenue" action="${ctx}/reserve/reserveVenue/"
                           method="post" class="breadcrumb form-search">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

                    <div class="row">
                        <div class="col-sm-6 col-md-6 col-lg-6">
                        <table class="no-border">
                            <tbody class="no-border-y">
                                <tr>
                                    <td>场馆名称:</td>
                                    <td>
                                        <form:input path="name" htmlEscape="false" cssstyle="width:50px;" maxlength="30"
                                                    class="form-control"/>
                                    </td>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></td>
                                </tr>
                            </tbody>
                        </table>
                        </div>
                        <div class="pull-right">
                            <a class="btn btn-success" href="${ctx}/reserve/reserveVenue/form">
                                <i class="fa fa-plus"></i>添加
                            </a>
                        </div>
                    </div>
                </form:form>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>场馆名称</th>
                                <th>是否启用</th>

                                <th>经度</th>
                                <th>维度</th>
                                <th>标签</th>
                                <th>平均消费</th>
                                <th>评分</th>
                                <th>营业开始时间</th>
                                <th>营业结束时间</th>
                                <th>城市</th>
                                <th>区</th>
                                <th>详细地址</th>
                                    <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${page.list}" var="reserveVenue">
                                <tr>
                                    <td><a href="${ctx}/reserve/reserveVenue/form?id=${reserveVenue.id}">
                                            ${reserveVenue.name}
                                    </a></td>
                                    <td class="color-success">
                                            ${fns:getDictLabel(reserveVenue.available, 'yes_no', '')}
                                    </td>

                                    <td>
                                            ${reserveVenue.addressX}
                                    </td>
                                    <td>
                                            ${reserveVenue.addressY}
                                    </td>
                                    <td>
                                            ${reserveVenue.moreService}
                                    </td>
                                    <td>
                                            ${reserveVenue.avePrice}
                                    </td>
                                    <td>
                                            ${reserveVenue.evaluateScore}
                                    </td>
                                    <td>
                                            ${reserveVenue.startTime}
                                    </td>
                                    <td>
                                            ${reserveVenue.endTime}
                                    </td>
                                    <td>
                                            ${reserveVenue.cityName}
                                    </td>
                                    <td>
                                            ${reserveVenue.districtName}
                                    </td>
                                    <td>
                                            ${reserveVenue.address}
                                    </td>
                                        <td>
                                            <a class="btn btn-primary btn-xs"
                                               href="${ctx}/reserve/reserveVenue/form?id=${reserveVenue.id}"><i
                                                    class="fa fa-pencil"></i>修改</a>
                                            <a class="btn btn-danger btn-xs"
                                               href="${ctx}/reserve/reserveVenue/delete?id=${reserveVenue.id}"
                                               onclick="return confirmb('确认要删除该场馆吗？', this.href)"><i
                                                    class="fa fa-times"></i>删除</a>
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
            </div>
        </div>
    </div>
</div>
</body>
</html>
