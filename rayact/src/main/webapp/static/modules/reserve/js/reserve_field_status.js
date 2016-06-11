$(document).ready(function () {
    $('.md-trigger').modalEffects();
    //空场审核
    function checkEmpty(t) {
        var fieldId = $(t).attr("data-field");
        var time = $(t).attr("data-time");
        var isHalfCourt = $(t).attr("data-isHalfCourt");
        var date = consDate;//日期
        jQuery.postItems({
            url: ctx + '/reserve/field/checkEmpty',
            data: {fieldId: fieldId, time: time, date: date, venueId: venueId, isHalfCourt: isHalfCourt},
            success: function (result) {
                $("#checkEmptyForm").html(result);
                $("#checkEmptyDialog").click();
                $("#checkEmptyForm .select2").select2({
                    width: '100%'
                });
                $('#checkEmptyForm .icheck').iCheck({
                    checkboxClass: 'icheckbox_square-blue checkbox',
                    radioClass: 'iradio_square-blue'
                });
            }
        });
    }

    //空场审核
    function updateCheckEmpty(t) {
        var checkId = $(t).attr("data-check");
        jQuery.postItems({
            url: ctx + '/reserve/field/checkEmptyUpdate',
            data: {checkId: checkId},
            success: function (result) {
                $("#checkEmptyForm").html(result);
                $("#checkEmptyDialog").click();
                $("#checkEmptyForm .select2").select2({
                    width: '100%'
                });
                $('#checkEmptyForm .icheck').iCheck({
                    checkboxClass: 'icheckbox_square-blue checkbox',
                    radioClass: 'iradio_square-blue'
                });
            }
        });
    }

    //保存空场审核
    $("#saveCheckBtn").on('click', function () {
        var data = $("#checkEmptyBean").serializeArray();
        $.postItems({
            url: ctx + '/reserve/field/saveCheckEmpty',
            data: data,
            success: function (values) {
                if (values == "success") {
                    successLoding('保存成功!');
                    location.reload();
                }
                if (values == "fail") {
                    errorLoding("保存失败！")
                }
            }

        });
    });

    //查看详情
    function details(t) {
        var itemId = t.attr("data-item");
        var fieldId = t.attr("data-field");
        var time = t.attr("data-time");
        var date = t.attr("data-date");
        jQuery.postItems({
            url: ctx + '/reserve/field/details',
            data: {itemId: itemId,fieldId:fieldId,time:time,date:date},
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





    var accessMenuData = [
        [{
            text: "空场审核",
            func: function () {
                checkEmpty($(this));
            }
        }]];
    var reserveMenuData = [[{
        text: "预定详情",
        func: function () {
            details($(this));
        }
    }]
    ];
    var checkoutMenuData = [[{
        text: "查看详情",
        func: function () {
            details($(this));
        }
    }]];
    var checkEmptyMenuData = [[{
        text: "空场情况",
        func: function () {
            updateCheckEmpty($(this));
        }
    }]];
    //mouserover
    $(".reserveTd").unbind("mouserover");
    $(".reserveTd").on('mousedown', function () {
        if ($(this).hasClass("access")) {//预定
            $(this).smartMenu(accessMenuData, {name: "access"});
        } else if ($(this).hasClass("red")) {//已经结账
            $(this).smartMenu(checkoutMenuData, {name: "checkout"});
        }else if($(this).hasClass("normal")||$(this).hasClass("abnormal")){
            $(this).smartMenu(checkEmptyMenuData, {name: "checkEmpty"});
        }else {//取消预定
            $(this).smartMenu(reserveMenuData, {name: "reserve"});
        }
    });
});