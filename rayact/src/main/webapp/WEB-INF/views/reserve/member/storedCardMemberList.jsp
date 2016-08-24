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
                <form:form id="searchForm" modelAttribute="reserveMember" action="${ctx}/reserve/storedCardMember/list"
                           method="post">
                    <div class="breadcrumb form-search">
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
                                        <td><form:input path="cardNo" htmlEscape="false" maxlength="20"
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
                    </div>
                    <sys:msg content="${message}"/>
                    <div class="content">
                        <div class="table-responsive">
                            <table>
                                <thead>
                                <tr>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                    <th>卡号</th>
                                    <th>开户场馆</th>
                                    <th>余额</th>
                                    <th>操作</th>
                                    <th>交易</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${page.list}" var="reserveMember">
                                    <tr>
                                        <td><a href="${ctx}/reserve/storedCardMember/form?id=${reserveMember.id}">
                                                ${reserveMember.name}
                                        </a></td>
                                        <td>
                                                ${fns:hidePhone(reserveMember.mobile)}
                                        </td>
                                        <td>
                                                ${reserveMember.cardNo}
                                        </td>
                                        <td>
                                                ${reserveMember.reserveVenue.name}
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${reserveMember.remainder}"/>

                                        </td>
                                        <td>
                                            <a class="btn btn-primary btn-xs"
                                               href="${ctx}/reserve/storedCardMember/form?id=${reserveMember.id}"><i
                                                    class="fa fa-pencil"></i>修改</a>
                                            <a class="btn btn-danger btn-xs"
                                               href="${ctx}/reserve/storedCardMember/delete?id=${reserveMember.id}"
                                               onclick="return confirmb('确认要删除该会员吗？', this.href)"><i
                                                    class="fa fa-times"></i>删除</a>

                                        </td>
                                        <td>
                                            <a class="btn btn-primary btn-xs rechargeBtn" data-id="${reserveMember.id}"><i
                                                    class="fa fa-pencil"></i>充值</a>
                                            <a class="btn btn-primary btn-xs refundBtnForVIP"
                                               data-id="${reserveMember.id}"><i
                                                    class="fa fa-pencil"></i>退费</a>

                                            <a class="btn btn-primary btn-xs"
                                               href="${ctx}/reserve/reserveMember/statements?reserveMember.id=${reserveMember.id}">交易明细</a>
                                            <a class="btn btn-danger btn-xs cancellationBtn"
                                               data-id="${reserveMember.id}"><i
                                                    class="fa fa-pencil"></i>销卡</a>
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
<button id="rechargeDialogBtn" style="display: none" class="btn btn-primary btn-large"
        href="#rechargeDialog" data-toggle="modal">付款成功
</button>
<div class="modal fade" id="rechargeDialog" style="display: none;" aria-hidden="true">
    <div class="modal-dialog" style="width: 80%">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">充值</h4>
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <div class="reserve_top_line">

                </div>
                <div class="modal-body form-horizontal" id="rechargeForm">
                    <!--付款成功模态-->
                    <!--end 付款成功模态-->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button"  class="btn btn-default btn-flat md-close"
                        data-dismiss="modal">
                    取消
                </button>
                <button type="button" id="rechargeSaveBtn"
                        class="btn btn-primary btn-flat">
                    确定
                </button>
            </div>
        </div>
    </div>
</div>


<button class="btn btn-primary btn-flat md-trigger" id="cancellationDialogBtn" style="display: none"
        data-modal="cancellationDialog">
    销卡
</button>
<div class="md-modal colored-header custom-width md-effect-12 warning" id="cancellationDialog">
    <div class="md-content">
        <div class="modal-header">
            <h5>销卡</h5>
            <button type="button" class="close md-close" data-dismiss="modal"
                    aria-hidden="true">&times;</button>
        </div>
        <div class="modal-body form-horizontal" id="cancellationForm">
            <!--销户-->


            <!--销户-->
        </div>
        <div class="modal-footer">
            <button type="button" id="closeBtn" class="btn btn-default btn-flat md-close" data-dismiss="modal">
                取消
            </button>
            <button type="button" id="cancellationFormSaveBtn" class="btn btn-primary btn-flat">保存</button>
        </div>
    </div>
</div>
<jsp:include page="../include/refundModal.jsp"></jsp:include>
<script src="${ctxStatic}/modules/reserve/js/storedcard_member_list.js" type="text/javascript"></script>
</body>
</html>
