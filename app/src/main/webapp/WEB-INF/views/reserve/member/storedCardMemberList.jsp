<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <title>储值卡会员管理</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="storedcardMember"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>储值卡会员列表</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveMember" action="${ctx}/reserve/reserveMember/"
                           method="post" class="breadcrumb form-search ">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                    <div class="row">
                        <div class="col-sm-10 col-md-10 col-lg-10">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <ul class="ul-form">
                                    <td>姓名：</td>
                                    <td><form:input path="name" htmlEscape="false" maxlength="30"
                                                    class="form-control"/></td>

                                    <td>手机号：</td>
                                    <td><form:input path="mobile" htmlEscape="false" maxlength="20"
                                                    class="form-control"/>
                                    </td>

                                    <td>卡号：</td>
                                    <td><form:input path="cartno" htmlEscape="false" maxlength="20"
                                                    class="form-control"/>
                                    </td>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit"
                                               value="查询"/></td>

                                </ul>
                                </tbody>
                            </table>
                        </div>
                        <div class="pull-right">
                            <a class="btn btn-success" href="${ctx}/reserve/storedCardMember/form">
                                <i class="fa fa-plus"></i>添加
                            </a>
                        </div>
                    </div>
                </form:form>
                <sys:msg content="${message}"/>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>姓名</th>
                                <th>手机号</th>
                                <th>性别</th>
                                <th>卡号</th>
                                <th>储值卡名称</th>
                                <th>身份证</th>
                                <th>地址</th>
                                <th>余额</th>
                                <th>备注</th>
                                <shiro:hasPermission name="reserve:reserveMember:edit">
                                    <th>操作</th>
                                </shiro:hasPermission>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${page.list}" var="reserveMember">
                                <tr>
                                    <td><a href="${ctx}/reserve/storedCardMember/form?id=${reserveMember.id}">
                                            ${reserveMember.name}
                                    </a></td>
                                    <td>
                                            ${reserveMember.mobile}
                                    </td>
                                    <td>
                                            ${fns:getDictLabel(reserveMember.sex, 'sex', '')}
                                    </td>
                                    <td>
                                            ${reserveMember.cartno}
                                    </td>
                                    <td>
                                            ${reserveMember.storedcardSet.name}
                                    </td>
                                    <td>
                                            ${reserveMember.sfz}
                                    </td>
                                    <td>
                                            ${reserveMember.address}
                                    </td>

                                    <td>
                                            ${reserveMember.remainder}
                                    </td>
                                    <td>
                                            ${reserveMember.remarks}
                                    </td>
                                    <shiro:hasPermission name="reserve:reserveMember:edit">
                                        <td>
                                            <a class="btn btn-primary btn-xs"
                                               href="${ctx}/reserve/storedCardMember/form?id=${reserveMember.id}"><i
                                                    class="fa fa-pencil"></i>修改</a>
                                            <a class="btn btn-danger btn-xs"
                                               href="${ctx}/reserve/storedCardMember/delete?id=${reserveMember.id}"
                                               onclick="return confirmb('确认要删除该会员吗？', this.href)"><i
                                                    class="fa fa-times"></i>删除</a>
                                            <a class="btn btn-primary btn-xs rechargeBtn" data-id="${reserveMember.id}"><i
                                                    class="fa fa-pencil"></i>充值</a>
                                            <a class="btn btn-primary btn-xs refundBtn" data-id="${reserveMember.id}"><i
                                                    class="fa fa-pencil"></i>退费</a>
                                        </td>
                                    </shiro:hasPermission>
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

<button class="btn btn-primary btn-flat md-trigger" id="rechargeDialogBtn" style="display: none"
        data-modal="rechargeDialog">
    充值
</button>

<div class="md-modal colored-header custom-width md-effect-12 warning" id="rechargeDialog">
    <div class="md-content">
        <div class="modal-header">
            <h5>充值</h5>
            <button type="button" class="close md-close" data-dismiss="modal"
                    aria-hidden="true">&times;</button>
        </div>
        <div class="modal-body form-horizontal " id="rechargeForm">
            <!--充值-->


            <!--end 充值-->
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-default btn-flat md-close" data-dismiss="modal">
                取消
            </button>
            <button type="button" id="rechargeSaveBtn" class="btn btn-primary btn-flat">保存</button>
        </div>
    </div>
</div>

<button class="btn btn-primary btn-flat md-trigger" id="refundDialogBtn" style="display: none"
        data-modal="refundDialog">
    退费
</button>
<div class="md-modal colored-header custom-width md-effect-12 warning" id="refundDialog">
    <div class="md-content">
        <div class="modal-header">
            <h5>退费</h5>
            <button type="button" class="close md-close" data-dismiss="modal"
                    aria-hidden="true">&times;</button>
        </div>
        <div class="modal-body form-horizontal" id="reserveForm">
            <!--退费-->


            <!--end 退费-->
        </div>
        <div class="modal-footer">
            <button type="button" id="closeBtn" class="btn btn-default btn-flat md-close" data-dismiss="modal">
                取消
            </button>
            <button type="button" id="refundSaveBtn" class="btn btn-primary btn-flat">保存</button>
        </div>
    </div>
</div>
<script src="${ctxStatic}/modules/reserve/js/storedcard_member_list.js" type="text/javascript"></script>
</body>
</html>
