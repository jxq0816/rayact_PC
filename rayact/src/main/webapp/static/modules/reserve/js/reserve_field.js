$(document).ready(function () {
/*    $('.md-trigger').modalEffects();*/
    //-------预定---------
    $(".table-chang tbody td").on('dblclick', function () {
        if (!$(this).hasClass("access")) {
            return;
        }
        var fieldId = $(this).attr("data-field");
        var time = $(this).attr("data-time");
        var price = $(this).attr("data-price");
        var isHalfCourt = $(this).attr("data-isHalfCourt");
        if (price == null || price == "" || price == undefined) {
            errorLoding("抱歉，该时间段价格尚未设定");
            return;
        }
        var date = consDate;//日期
        jQuery.postItems({
            url: ctx + '/reserve/field/reserveForm?math='+Math.random(),
            data: {fieldId: fieldId, time: time, date: date, venueId: venueId, isHalfCourt: isHalfCourt},
            success: function (result) {
                if (result) {
                    $("#reserveForm").html(result);
                    $("#reserveDialog").click();
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
    //保存预订数据
    $("#saveBtn").on('click', function () {
        var userName = $('#userName').val();
        var consMobile = $("#consMobile").val();
        var frequency = $("#frequency").val();
        var endDate = $("#endDate").val();
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();

        if (startTime == endTime) {
            formLoding('开始时间应小于结束时间');
            return false;
        }

        $.postItems({
            url: ctx + '/reserve/field/checkTime',
            data: {startTime: startTime, endTime: endTime},
            success: function (values) {
                if (values == "0") {
                    formLoding('开始时间应小于结束时间');
                    return false;
                }
            }
        });

        if (userName == '') {
            formLoding('请输入预定人姓名');
            return false;
        }
        if (checkMobile(consMobile)==false) {
             formLoding('请输入正确的手机');
             return false;
         }
        if (frequency == '2' || frequency == '3') {
            if (endDate == '') {
                formLoding('结束时间不能为空');
                return false;
            }
        }
        var data = $("#reserveFormBean").serializeArray();
        $.postItems({
            url: ctx + '/reserve/field/reservation',
            data: data,
            success: function (values) {
                if (values) {
                    $.each(values, function (index, item) {
                        if (item.bool == "0") {
                            formLoding('该时间段不可使用!');
                        }
                        else {
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
                                        $this.text(userName);
                                        /* location.reload(true);*/
                                    }
                                });
                            });
                        }
                    });
                }
            }
        });
        $("#closeBtn").click();
    });
    //-------取消预定---------
    function cancelReserve(t) {
        var itemId = t.attr("data-item");
        var field = t.attr("data-field");
        var time = t.attr("data-time");
        jQuery.postItems({
            url: ctx + '/reserve/field/cancelForm',
            data: {fieldId: field, time: time, itemId: itemId, venueId: venueId},
            success: function (result) {
                $("#cancelForm").html(result);
                $("#cancelBtn").click();
            }
        });
    }

    //取消预定保存
    $("#saveCancelBtn").on('click', function () {
        var data = $("#cancelFormBean").serializeArray();
        $.postItems({
            url: ctx + '/reserve/field/cancelReservation',
            data: data,
            success: function (values) {
                if (values) {
                    $("#closeCancelBtn").click();
                    formLoding('取消预定成功!');
                    $(".table-chang tbody td").each(function (index) {
                        var $this = $(this);
                        var fieldId = $this.attr("data-field");
                        var time = $this.attr("data-time");
                        $.each(values, function (index, item) {
                            if (item.fieldId == fieldId && time == item.time) {
                                $this.addClass("access");
                                $this.attr("status", "0");
                                $this.text("");
                            }
                        });
                    });
                }
            }
        });
    });


    //赠品
    function gift(t) {
        var itemId = t.attr("data-item");
        jQuery.postItems({
            url: ctx + '/reserve/field/gift',
            data: {itemId: itemId},
            success: function (result) {
                $("#giftForm").html(result);
                $("#giftBtn").click();
                $("#giftForm .select2").select2({
                    width: '100%'
                });
                $('#giftForm .icheck').iCheck({
                    checkboxClass: 'icheckbox_square-blue checkbox',
                    radioClass: 'iradio_square-blue'
                });
            }
        });
    }

    //申请优惠
    function applycut(t) {
        var itemId = t.attr("data-item");
        jQuery.postItems({
            url: ctx + '/reserve/field/applyCut',
            data: {itemId: itemId},
            success: function (result) {
                $("#applyCutForm").html(result);
                $("#applyCutBtn").click();
                $("#applyCutForm .select2").select2({
                    width: '100%'
                });
                $('#applyCutForm .icheck').iCheck({
                    checkboxClass: 'icheckbox_square-blue checkbox',
                    radioClass: 'iradio_square-blue'
                });
            }
        });
    }

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


    //保存赠品
    $("#saveGiftBtn").on('click', function () {
        var data = $("#giftFormBean").serializeArray();
        var length = $("#giftTable tr").length;
        if (length < 1) {
            formLoding('请选择赠品!');
            return;
        }
        $.postItems({
            url: ctx + '/reserve/field/saveGift',
            data: data,
            success: function (values) {
                if (values == "success") {
                    successLoding('保存赠品成功!');
                    location.reload();
                }
                if (values == "fail") {
                    errorLoding("库存量不足！")
                }
            }
        });
    });

    //保存优惠申请
    $("#saveApplyCutBtn").on('click', function () {
        var data = $("#applyCutFormBean").serializeArray();
        var applyer = $("#userId").val();
        if (!applyer) {
            formLoding('请选择通知人!');
            return;
        }
        $.postItems({
            url: ctx + '/reserve/field/saveApplyCut',
            data: data,
            success: function (values) {
                if (values == "success") {
                    successLoding('发送通知成功!');
                    location.reload();
                }
                if (values == "fail") {
                    errorLoding("发送通知失败！")
                }
            }

        });
    });

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

    //右击 结账
    function settlement(t) {
        $("#settlementDetailForm").html("");
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

    //确认结账
    $("#saveSettlementBtn").on('click', function () {

        var shouldPrice = $("#shouldPrice").val();
        var discountPrice = $("#discountPrice").val();
        var remarks = $("#remarks").val();
        var consPrice = shouldPrice;
        if(discountPrice){
            if (isNaN(discountPrice)) {
                errorLoding("优惠金额必须为数字！");
                return;
            }
            if (eval(discountPrice) > eval(shouldPrice)) {
                errorLoding("优惠金额不能大于应收金额！");
                return;
            }
            consPrice=shouldPrice-discountPrice;
            if(eval(shouldPrice)-eval(discountPrice)!=eval(consPrice)){
                errorLoding("应收减去优惠不等于实收，请点击确认优惠！");
                return;
            }
        }
        /* 以上为数据验证*/

        if (isNaN(consPrice)) {
            errorLoding("结算金额必须为数字！");
            return;
        }
        if (eval(consPrice) < 0) {
            errorLoding("结账金额不能小于0！");
            return;
        }

        var authUserId = $("#authUser").val();
        var id = $("#id").val();
        var token = $("#token").val();
        var payType = $("#payTypeDIV input:radio:checked").val();
        if (payType == "" || payType == null || payType == undefined) {
            errorLoding("请选择支付类型！");
            return;
        }
        /*  var data = $("#settlementFormBean").serializeArray();*/
        var memberCardInput = 0;
        var cashInput = 0;
        var bankCardInput = 0;
        var weiXinInput = 0;
        var weiXinPersonalInput = 0;
        var aliPayInput = 0;
        var aliPayPersonalInput = 0;
        var couponInput = 0;
        /*   var owningInput=0;*/
        if (payType == '8') {
            memberCardInput = eval($("#memberCardInput").val());
            cashInput = eval($("#cashInput").val());
            bankCardInput = eval($("#bankCardInput").val());
            weiXinPersonalInput = eval($("#weiXinPersonalInput").val());
            weiXinInput = eval($("#weiXinInput").val());
            aliPayInput = eval($("#aliPayInput").val());
            aliPayPersonalInput = eval($("#aliPayPersonalInput").val());
            couponInput = eval($("#couponInput").val());
            /*   owningInput=eval($("#owningInput").val()) ;*/
            var sum = eval(memberCardInput + cashInput + bankCardInput + weiXinInput + weiXinPersonalInput + aliPayInput + aliPayPersonalInput + couponInput);
            if (sum != consPrice) {
                errorLoding("多方式付款的总和不等于实收");
                return;
            }
        }
        $.postItems({
            url: ctx + '/reserve/field/saveSettlement?random=' + Math.random(),
            data: {
                token: token,
                id: id,
                payType: payType,
                authUserId: authUserId,
                discountPrice: discountPrice,
                consPrice: consPrice,
                memberCardInput: memberCardInput,
                cashInput: cashInput,
                bankCardInput: bankCardInput,
                weiXinInput: weiXinInput,
                weiXinPersonalInput: weiXinPersonalInput,
                aliPayInput: aliPayInput,
                aliPayPersonalInput: aliPayPersonalInput,
                couponInput: couponInput,
                remarks:remarks
            },
            success: function (values) {
                if(values){
                    var fieldList=values.mapList;
                    var orderId=values.orderId;
                    if (fieldList) {
                        $(".table-chang tbody td").each(function (index) {
                            var $this = $(this);
                            var fieldId = $this.attr("data-field");
                            var time = $this.attr("data-time");
                            $.each(fieldList, function (index, item) {
                                if (item.fieldId == fieldId && time == item.time) {
                                    $this.addClass("red");
                                    $this.attr("status", "0");
                                }
                            });
                        });
                    }
                    settlementResult(orderId);
                }
                $("#closeSettlementBtn").click();
                formLoding('保存结账单据成功!');
            }
        });
    });


    var accessMenuData = [[{
        text: "预定",
        func: function () {
            $(this).dblclick();
        }
    }]];
    var reserveMenuData = [[{
        text: "取消订单",
        func: function () {
            cancelReserve($(this));
        }
    }], [{
        text: "申请优惠",
        func: function () {
            applycut($(this));
        }
    }], [{
        text: "预定详情",
        func: function () {
            details($(this));
        }
    }], [{
        text: "赠品",
        func: function () {
            gift($(this));
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
    }]];
    //mouserover
    $(".reserveTd").unbind("mouserover");
    $(".reserveTd").on('mousedown', function () {
        if ($(this).hasClass("access")) {//预定
            $(this).smartMenu(accessMenuData, {name: "access"});
        } else if ($(this).hasClass("red")) {//已经结账
            $(this).smartMenu(checkoutMenuData, {name: "checkout"});
        } else if ($(this).hasClass("normal") || $(this).hasClass("abnormal")) {

        } else if ($(this).hasClass("reserveTd")) {//取消预定
            $(this).smartMenu(reserveMenuData, {name: "reserve"});
        }
    });
});
//修改价格的连接
function changePrice() {
    if (document.getElementById('changePrice').style.display == "none") {
        $("#changePrice").show();
    } else {
        $("#changePrice").hide();
    }

}
//修改结算价格
function editPrice() {
    var discountPrice = $("#discountPrice").val();
    if (isNaN(discountPrice)) {
        errorLoding("优惠金额必须为数字！");
        return;
    }
    var shouldPrice = $("#shouldPrice").val();

    var consPrice = shouldPrice - discountPrice;
    if (consPrice < 0) {
        errorLoding("优惠金额不能大于应收金额！");
        return;
    }
    $("#consPrice").attr("value",consPrice);
}
function checkAuthorization() {
    var userId = $("#authUser").val();
    var authPassword = $("#authPassword").val();
    jQuery.postItems({
        url: ctx + '/reserve/field/checkUserAuth',
        data: {userId: userId, authPassword: authPassword},
        success: function (result) {
            if (result != null && result.id != null) {
                successLoding("授权码正确!");
                $("#discountPriceDiv").show();
            } else {
                errorLoding("授权码不正确!");
            }
        }
    });
}
function settlementResult(orderId) {
    jQuery.postItems({
        url: ctx + '/reserve/field/settlementResult',
        data: {orderId: orderId},
        success: function (result) {
            if (result) {
                $("#settlementResultForm").html(result);
                $("#settlementResultBtn").click();
            }
        }
    });
}
function checkMobile(s){
    var length = s.length;
    if(length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(14[0-9]{1})|)+\d{8})$/.test(s) )
    {
        return true;
    }else{
        return false;
    }
}