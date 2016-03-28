<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <title>会员管理</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="member"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>用户管理</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveMember" action="${ctx}/reserve/reserveMember/"
                           method="post" class="breadcrumb form-search">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

                    <div class="row">
                        <div class="col-sm-10 col-md-10 col-lg-10">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>姓名：</td>
                                    <td><form:input path="name" cssStyle="width:100px;" htmlEscape="false" maxlength="30"
                                                    class="form-control"/></td>


                                    <td>手机号：</td>
                                    <td><form:input path="mobile" cssStyle="width:100px;" htmlEscape="false" class="form-control"/>
                                    </td>

                                    <td>卡号：</td>
                                    <td><form:input path="cartno" cssStyle="width:100px;" htmlEscape="false" class="form-control"/>
                                    </td>
                                    <td>
                                   卡号类型：
                                    <td>
                                        <form:radiobuttons class="icheck"  path="cartType" items="${fns:getDictList('cart_type')}"
                                                           itemLabel="label"
                                                           itemValue="value" htmlEscape="false"/>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                       <%-- <div class="pull-right">
                            <a class="btn btn-success" href="${ctx}/reserve/reserveMember/form">
                                <i class="fa fa-plus"></i>添加
                            </a>
                        </div>--%>
                    </div>
                </form:form>
                <sys:message content="${message}"/>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>姓名</th>
                                <th>手机号</th>
                                <th>性别</th>
                                <th>卡号</th>
                                <th>卡号类型</th>
                                <th>备注</th>
                                <shiro:hasPermission name="reserve:reserveMember:edit">
                                    <th>操作</th>
                                </shiro:hasPermission>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${memberList}" var="reserveMember">
                                <tr>
                                    <td><a href="${ctx}/reserve/reserveMember/form?id=${reserveMember.id}">
                                            ${reserveMember.name}
                                    </a></td>
                                    <td>
                                            ${fns:hidePhone(reserveMember.mobile)}

                                    </td>
                                    <td>
                                            ${fns:getDictLabel(reserveMember.sex, 'sex', '')}
                                    </td>
                                    <td>
                                            ${reserveMember.cartno}
                                    </td>
                                    <td>
                                            ${fns:getDictLabel(reserveMember.cartType, 'cart_type', '')}
                                    </td>
                                    <td>
                                            ${reserveMember.remarks}
                                    </td>
                                    <shiro:hasPermission name="reserve:reserveMember:edit">
                                        <td>
                                            <a class="btn btn-primary btn-xs"
                                               href="${ctx}/reserve/reserveMember/form?id=${reserveMember.id}"><i
                                                    class="fa fa-pencil"></i>修改</a>
                                            <a class="btn btn-danger btn-xs"
                                               href="${ctx}/reserve/reserveMember/delete?id=${reserveMember.id}"
                                               onclick="return confirmb('确认要删除该会员吗？', this.href)"><i
                                                    class="fa fa-times"></i>删除</a>
                                        </td>
                                    </shiro:hasPermission>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                       <%-- <div class="row">
                            <div class="col-sm-12">

                                <div class="pull-right">
                                    <div class="dataTables_paginate paging_bs_normal">
                                        <sys:javascript_page p="${page}"></sys:javascript_page>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                        </div>--%>

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
</body>
</html>
