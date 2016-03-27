<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场次票设置</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="venueVisitorsSet"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>场次票设置</h3>
                </div>


                <form:form id="searchForm" modelAttribute="reserveVenueVisitorsSet"
                           action="${ctx}/reserve/reserveVenueVisitorsSet/list"
                           method="post" class="breadcrumb form-search">
                    <div class="row">
                        <div class="col-sm-6 col-md-6 col-lg-6">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>次票名称：</td>
                                    <td>
                                        <form:input path="name" htmlEscape="false" cssstyle="width:70px;" maxlength="30"
                                                    class="form-control"/>
                                    </td>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>


                        <div class="pull-right">
                            <a id="addBtn" class="btn btn-success">
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
                                <th>次票名称</th>
                                <th>所属项目</th>
                                <th>所属场馆</th>
                                <th>是否启用</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${page}" var="bean">
                                <tr>
                                    <td>
                                        <a href="${ctx}/reserve/reserveVenueVisitorsSet/form?id=${bean.id}">${bean.name}</a>
                                    </td>
                                    <td>${bean.project.name}</td>
                                    <td>${bean.reserveVenue.name}</td>
                                    <td>
                                            ${fns:getDictLabel(bean.available, 'yes_no', '')}
                                    </td>
                                    <td>
                                        <a class="btn btn-primary btn-xs editBtn" data-id="${bean.id}"><i
                                                class="fa fa-pencil"></i>修改</a>
                                        <a class="btn btn-danger btn-xs"
                                           href="${ctx}/reserve/reserveVenueVisitorsSet/delete?id=${bean.id}"
                                           onclick="return confirmb('确认要删除票次吗？', this.href)"><i
                                                class="fa fa-times"></i>删除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<button class="btn btn-primary btn-flat md-trigger" id="reserveBtn" style="display: none" data-modal="form-primary">
    Basic Form
</button>
<div class="md-modal colored-header custom-width md-effect-12" id="form-primary">
    <div class="md-content">
        <div class="modal-header">
            <h5>人次票设置</h5>
            <button type="button" class="close md-close" data-dismiss="modal"
                    aria-hidden="true">&times;</button>
        </div>
        <div class="modal-body form-horizontal" id="reserveForm">
            <!--人次票设置-->


            <!--end 人次票设置-->
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
        $("#addBtn").on('click', function () {
            jQuery.postItems({
                url: '${ctx}/reserve/reserveVenueVisitorsSet/form',
                data: {},
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

        $(".editBtn").on('click', function () {
            var id = $(this).attr("data-id");
            jQuery.postItems({
                url: '${ctx}/reserve/reserveVenueVisitorsSet/form',
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

        //保存
        $("#saveBtn").on('click', function () {
            var data = $("#pzname").val();
            if (data == '') {
                formLoding("请输入票种名称");
                return;
            }
            data = $("#pzprice").val();
            if (data == '') {
                formLoding("请输入价格");
                return;
            }
            var formJson = $("#formBean").serializeArray();
            jQuery.postItems({
                url: '${ctx}/reserve/reserveVenueVisitorsSet/save',
                data: formJson,
                success: function (result) {
                    if (result == 'success') {
                        location.reload();
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