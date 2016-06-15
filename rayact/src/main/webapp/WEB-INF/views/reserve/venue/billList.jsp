<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场馆损益管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="reserveVenueBill"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>场馆损益列表</h3>

                    <form:form id="searchForm" modelAttribute="reserveVenueBill"
                               action="${ctx}/reserve/reserveVenueBill/" method="post">
                    <div class="row col-lg-12 col-sm-12 breadcrumb form-search"
                         style="margin-left:0px; margin-right:0px;">
                        <div class="form-group col-lg-3 col-sm-5">
                            <label class="control-label" for="venue">场馆：</label>
                            <sys:select cssClass="input-xlarge" cssStyle="width:50%"
                                        id="venue"
                                        name="reserveVenue.id"
                                        items="${venueList}"
                                        value="${venue}" itemLabel="name"
                                        itemValue="id"></sys:select>
                        </div>
                        <div class="form-group col-lg-2 col-sm-2">
                            <input id="btnSubmit" class="btn btn-primary" type="submit"
                                   value="查询"/>
                        </div>
                        <div class="pull-right">
                            <a class="btn btn-success" href="${ctx}/reserve/reserveVenueBill/form">
                                <i class="fa fa-plus"></i>添加
                            </a>
                        </div>
                    </div>
                </div>
                </form:form>
                <sys:msg content="${message}"/>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>场馆</th>
                                <th>水费</th>
                                <th>电费</th>
                                <th>油费</th>
                                <th>体育用品维修</th>
                                <th>办公设施维修</th>
                                <th>场馆设备维修</th>
                                <th>其他</th>
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${page.list}" var="reserveVenueBill">
                                <tr>
                                    <td>
                                            ${reserveVenueBill.reserveVenue.name}
                                    </td>
                                    <td>
                                            ${reserveVenueBill.waterBill}
                                    </td>
                                    <td>
                                            ${reserveVenueBill.elecBill}
                                    </td>
                                    <td>
                                            ${reserveVenueBill.oilBill}
                                    </td>
                                    <td>
                                            ${reserveVenueBill.sportDeviceRepairBill}
                                    </td>
                                    <td>
                                            ${reserveVenueBill.officeDeviceRepairBill}
                                    </td>
                                    <td>
                                            ${reserveVenueBill.venueDeviceRepairBill}
                                    </td>
                                    <td>
                                            ${reserveVenueBill.otherBill}
                                    </td>
                                    <td><fmt:formatDate value="${reserveVenueBill.startDate}"
                                                        type="date"/></td>
                                    <td>
                                        <fmt:formatDate value="${reserveVenueBill.endDate}"
                                                        type="date"/>
                                    </td>
                                    <td>
                                        <a class="btn btn-primary btn-xs"
                                           href="${ctx}/reserve/reserveVenueBill/form?id=${reserveVenueBill.id}"><i
                                                class="fa fa-pencil"></i>修改</a>
                                        <a class="btn btn-danger btn-xs"
                                           href="${ctx}/reserve/reserveVenueBill/delete?id=${reserveVenueBill.id}"
                                           onclick="return confirmb('确认要删除该场馆损益吗？', this.href)"><i
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
