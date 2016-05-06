$(document).ready(function () {
    $('.md-trigger').modalEffects();

    //-------取消预定---------
    function cancelReserve(t) {
        var itemId = t.attr("data-item");
        var field = t.attr("data-field");
        var time = t.attr("data-time");
        jQuery.postItems({
            url: ctx + '/reserve/field/cancelForm',
            data: {fieldId: field, time: time, itemId: itemId,venueId:venueId},
            success: function (result) {
                $("#cancelForm").html(result);
                $("#cancelBtn").click();
                $("#cancelForm .select2").select2({
                    width: '100%'
                });
                $('#cancelForm .icheck').iCheck({
                    checkboxClass: 'icheckbox_square-blue checkbox',
                    radioClass: 'iradio_square-blue'
                });
            }
        });
    }

    //结账
    function settlement(t) {
        var itemId = t.attr("data-item");
        jQuery.postItems({
            url: ctx + '/reserve/field/settlementForm',
            data: {itemId: itemId},
            success: function (result) {
                $("#settlementForm").html(result);
                $("#settlementBtn").click();
                $("#settlementForm .select2").select2({
                    width: '100%'
                });
                $('#settlementForm .icheck').iCheck({
                    checkboxClass: 'icheckbox_square-blue checkbox',
                    radioClass: 'iradio_square-blue'
                });
            }
        });
    }

    //查看详情
    function details(t) {
        var itemId = t.attr("data-item");
        jQuery.postItems({
            url: ctx + '/reserve/field/details',
            data: {itemId: itemId},
            success: function (result) {
                $("#detailsForm").html(result);
                $("#detailsBtn").click();
                $("#detailsForm .select2").select2({
                    width: '100%'
                });
                $('#detailsForm .icheck').iCheck({
                    checkboxClass: 'icheckbox_square-blue checkbox',
                    radioClass: 'iradio_square-blue'
                });
            }
        });
    }

    //退款
    function refund(t) {
        var itemId = t.attr("data-item");
        jQuery.postItems({
            url: ctx + '/reserve/field/refund',
            data: {itemId: itemId},
            success: function (result) {
                $("#detailsForm").html(result);
                $("#detailsBtn").click();
                $("#detailsForm .select2").select2({
                    width: '100%'
                });
                $('#detailsForm .icheck').iCheck({
                    checkboxClass: 'icheckbox_square-blue checkbox',
                    radioClass: 'iradio_square-blue'
                });
            }
        });
    }

    //结账
    $("#saveSettlementBtn").on('click', function () {
        var data = $("#settlementFormBean").serializeArray();
        $.postItems({
            url: ctx + '/reserve/field/saveSettlement',
            data: data,
            success: function (values) {
                if (values) {
                    formLoding('保存结账单据成功!');
                    location.reload();
                } else {
                    formLoding('保存结账单据出错!');
                }
            }
        });
    });

    //取消预定
    $("#saveCancelBtn").on('click', function () {
        var data = $("#cancelFormBean").serializeArray();
        $.postItems({
            url: ctx + '/reserve/field/cancelReservation',
            data: data,
            success: function (values) {
                if (values) {
                    formLoding('取消预定成功!');
                    location.reload();
                }
            }
        });
    });

    //-------预定---------
    $(".table-chang tbody td").on('dblclick', function () {
        if (!$(this).hasClass("access")) {
            return;
        }
        var field = $(this).attr("data-field");
        var time = $(this).attr("data-time");
        var date = consDate;//日期
        jQuery.postItems({
            url: ctx + '/reserve/field/reserveForm',
            data: {fieldId: field, time: time, date: date,venueId:venueId},
            success: function (result) {
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
        });
    });
    var accessMenuData = [[{
        text: "预定",
        func: function () {
            $(this).dblclick();
        }
    }]/*, [{
        text: "结账",
        func: function () {
            settlement($(this));
        }
    }]*/
    ];
    var reserveMenuData = [[{
        text: "取消订单",
        func: function () {
            cancelReserve($(this));
        }
    }], [{
        text: "预定详情",
        func: function () {
            details($(this));
        }
    }], [{
        text: "修改备注",
        func: function () {
            $(this).dblclick();
        }
    }], [{
        text: "结账",
        func: function () {
            settlement($(this));
        }
    }]
    ];
    var checkoutMenuData = [[{
        text: "查看详情",
        func: function () {
            details($(this));
        }
    }]/*, [{
        text: "退款",
        func: function () {
            refund($(this));
        }
    }]*/
    ];
    //mouserover
    $(".reserveTd").unbind("mouserover");
    $(".reserveTd").on('mousedown', function () {
        if ($(this).hasClass("access")) {//预定
            $(this).smartMenu(accessMenuData, {name: "access"});
        } else if ($(this).hasClass("red")) {//已经结账
            $(this).smartMenu(checkoutMenuData, {name: "checkout"});
        }else {//取消预定
            $(this).smartMenu(reserveMenuData, {name: "reserve"});
        }
    });

    //保存数据
    $("#saveBtn").on('click', function () {
        var consType = $('input:radio[name=consType]:checked').val();
        var userName = $('#userName').val();
        var consMobile = $("#consMobile").val();
        if (userName == '') {
            formLoding('请输入预定人姓名');
            return false;
        }
        if (consMobile == '') {
            formLoding('请输入预定人手机');
            return false;
        }
        var data = $("#reserveFormBean").serializeArray();
        $.postItems({
            url: ctx + '/reserve/field/reservation',
            data: data,
            success: function (values) {
                if (values) {
                    formLoding('订单预定成功!');
                    $(".table-chang tbody td").each(function (index) {
                        var $this = $(this);
                        var fieldId = $this.attr("data-field");
                        var time = $this.attr("data-time");
                        $.each(values, function (index, item) {
                            if (item.fieldId == fieldId && time == item.time) {
                                $this.removeClass("access");
                                $this.attr("status", "1");
                                $this.attr("data-item", item.itemId);
                            }
                        });
                    });
                }
            }
        });
        $("#closeBtn").click();
    });
});