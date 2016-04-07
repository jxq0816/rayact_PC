<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场地管理</title>
    <meta name="decorator" content="main"/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/modules/reserve/css/field.css?id=7862256"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="vistorsSet"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="block-flat">
        <div class="tab-tit-first">
            <ul>
                <li <j:if test="${empty projectId}">class="on"</j:if>><a
                        href="${ctx}/reserve/reserveVenueOrder/list">所有</a>
                </li>
                <c:forEach items="${projects}" var="project" varStatus="status">
                    <li <j:if test="${project.id eq projectId}">class="on"</j:if>><a
                            href="${ctx}/reserve/reserveVenueOrder/list?project.id=${project.id}">${project.name}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <%-- <div class="cl-mcont">--%>

        <div class="row">
            <div class="col-md-12">
                <c:forEach items="${visitorsSets}" var="vs">
                <div class="col-sm-4 col-md-4 col-lg-2">

                    <div class="header">
                        <h3 class="visible-sm visible-md">${vs.name}</h3>

                        <h3 class="visible-lg">${vs.name}</h3>
                    </div>
                    <div class="content">
                        <p><b>${vs.project.name}</b><b>(${vs.price}<i class="fa fa-cny"></i>)</b></p>

                        <p>
                            <button type="button" data-id="${vs.id}"
                                    class="btn btn-default btn-cart btn-twitter bg"><i
                                    class="fa fa-shopping-cart"></i></button>
                        </p>
                    </div>
                </div>
            </div>
            </c:forEach>
        </div>
    </div>
    <%-- </div>--%>
</div>

<button class="btn btn-primary btn-flat md-trigger" id="reserveBtn" style="display: none" data-modal="form-primary">
    Basic Form
</button>
<div class="md-modal colored-header custom-width md-effect-12" id="form-primary">
    <div class="md-content">
        <div class="modal-header">
            <h5>确认付款</h5>
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
            <button type="button" id="saveBtn" class="btn btn-primary btn-flat">确认购买</button>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        //添加到购物车
        $(".btn-cart").on('click', function () {
            var vsId = $(this).attr("data-id");
            jQuery.postItems({
                url: '${ctx}/reserve/reserveVenueOrder/form',
                data: {vsId: vsId},
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

        $("#saveBtn").on('click', function () {
            var formJson = $("#formBean").serializeArray();
            jQuery.postItems({
                url: '${ctx}/reserve/reserveVenueOrder/save',
                data: formJson,
                success: function (result) {
                    if (result) {
                        if (result == "1") {
                            formLoding("保存成功");
                            $("#closeBtn").click();
                        }
                        if (result == "2") {
                            formLoding("该用户没有次卡");
                        }
                        if (result == "3") {
                            formLoding("该用户剩余次数不足");
                        }
                        if (result == "4") {
                            formLoding("该用户的次票不可在该场地使用,请使用非会员，现金结账");
                        }
                    } else {
                        formLoding("保存失败");
                    }
                }
            });
        });

    });
</script>
</body>
</html>