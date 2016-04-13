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
                           method="post" class="breadcrumb form-search">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                    <div class="row">
                        <div class="col-sm-6 col-md-6 col-lg-6">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>
                                        <form:input placeholder="请输入商品名称或者简写" path="quickSearch" htmlEscape="false"
                                                    cssstyle="width:70px;" maxlength="30"
                                                    class="form-control"/>
                                    </td>
                                    <td>
                                        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                                    </td>

                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="pull-right">
                            <a class="btn btn-success" href="${ctx}/reserve/commodity/form">
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
                                <th>编号</th>
                                <th>名称</th>
                                <th>价格</th>
                                <th>库存量</th>
                                <th>规格</th>
                                <th>类别</th>
                                <th>场馆</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${list}" var="commodity">
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
                                            ${commodity.repertoryNum}瓶
                                    </td>
                                    <td>
                                            1*${commodity.unit}
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
                                    <td>
                                        <a class="btn btn-primary btn-xs "
                                           href="${ctx}/reserve/commodity/form?id=${commodity.id}">修改</a>
                                        <a class="btn btn-danger btn-xs"
                                           href="${ctx}/reserve/commodity/delete?id=${commodity.id}"
                                           onclick="return confirmb('确认要删除该商品吗？', this.href)">删除</a>
                                        <a class="btn btn-primary btn-xs instorageBtn" data-id="${commodity.id}">入库</a>
                                    </td>
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
<button class="btn btn-primary btn-flat md-trigger" id="reserveBtn" style="display: none" data-modal="form-primary">
    Basic Form
</button>
<div class="md-modal colored-header custom-width md-effect-12 warning" id="form-primary">
    <div class="md-content">
        <div class="modal-header">
            <h5>商品入库</h5>
            <button type="button" class="close md-close" data-dismiss="modal"
                    aria-hidden="true">&times;</button>
        </div>
        <div class="modal-body form-horizontal" id="reserveForm">
            <!--商品入库-->


            <!--end 商品入库-->
        </div>
        <div class="modal-footer">
            <button type="button" id="closeBtn" class="btn btn-default btn-flat md-close" data-dismiss="modal">
                取消
            </button>
            <button type="button" id="saveBtn" class="btn btn-primary btn-flat">保存</button>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $(".editBtn").on('click', function () {
            var id = $(this).attr("data-id");
            jQuery.postItems({
                url: '${ctx}/reserve/commodity/inStorageUrl',
                data: {id: id},
                success: function (result) {
                    if (result) {
                        $("#reserveForm").html(result);
                        $("#reserveBtn").click();
                        $("#reserveForm .select2").select2({
                            width: '100%'
                        });
                        $('#reserveForm .icheck').iCheck({
                            checkboxClass: 'icheckbox_square-blue checkbox',
                            radioClass: 'iradio_square-blue'
                        });
                    }
                }
            });
        });

        $(".instorageBtn").on('click', function () {
            var id = $(this).attr("data-id");

            jQuery.postItems({
                url: '${ctx}/reserve/commodity/inStorageUrl',
                data: {
                    id: id
                },
                success: function (result) {
                    if (result) {
                        $("#reserveForm").html(result);
                        $("#reserveBtn").click();
                        $("#reserveForm .select2").select2({
                            width: '100%'
                        });
                        $('#reserveForm .icheck').iCheck({
                            checkboxClass: 'icheckbox_square-blue checkbox',
                            radioClass: 'iradio_square-blue'
                        });
                    }
                }
            });
        });
        //保存
        $("#saveBtn").on('click', function () {
            var id = $("#id").val();
            var token=$("#token").val();
            var inRepertoryBoxNum=$("#inRepertoryBoxNum").val();
            var boxPrice=$("#boxPrice").val();
            if(inRepertoryBoxNum==null || inRepertoryBoxNum==undefined || inRepertoryBoxNum=='') {
                errorLoding('请输入箱数!');
                return;
            }else if(isNaN(inRepertoryBoxNum)){
                errorLoding('入库量必须为数字!');
                return;
            }
            if(boxPrice==null || boxPrice==undefined || boxPrice=='') {
                errorLoding('请输入单箱价格!');
                return;
            }else if(isNaN(boxPrice)){
                errorLoding('单箱价格必须为数字!');
                return;
            }
            jQuery.postItems({
                url: '${ctx}/reserve/commodity/inStorage',
                data: {
                    id: id,
                    token: token,
                    inRepertoryBoxNum:inRepertoryBoxNum,
                    boxPrice:boxPrice
                },
                success: function (result) {
                    if (result == "success") {
                        formLoding('保存成功!');
                        location.reload("true");
                    } else {
                        formLoding('保存出错!');
                    }
                }
            });
        });
    });
</script>
</body>
</html>