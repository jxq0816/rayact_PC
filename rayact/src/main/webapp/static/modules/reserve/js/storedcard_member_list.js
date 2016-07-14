$(document).ready(function () {
    /*充值*/
    $(".rechargeBtn").on('click', function () {
        var id = $(this).attr("data-id");
        jQuery.postItems({
            url: ctx+'/reserve/reserveCardStatements/rechargeForm',
            data: {id: id},
            success: function (result) {
                if(result){
                    $("#rechargeForm").html(result);
                    $("#rechargeDialogBtn").click();
                    $("#rechargeForm .select2").select2({
                        width: '100%'
                    });
                    $('#rechargeForm .icheck').iCheck({
                        checkboxClass: 'icheckbox_square-blue checkbox',
                        radioClass: 'iradio_square-blue'
                    });
                }
            }
        });
    });
    /*销户*/
    $(".cancellationBtn").on('click', function () {
        var id = $(this).attr("data-id");
        jQuery.postItems({
            url: ctx+'/reserve/reserveCardStatements/cancellationForm',
            data: {id: id},
            success: function (result) {

                $("#cancellationForm").html(result);
                $("#cancellationDialogBtn").click();
                $("#cancellationForm .select2").select2({
                    width: '100%'
                });
                $('#cancellationForm .icheck').iCheck({
                    checkboxClass: 'icheckbox_square-blue checkbox',
                    radioClass: 'iradio_square-blue'
                });
            }

        });
    });
    /*销户保存*/
    $("#cancellationFormSaveBtn").on('click', function () {
        var realRefundVolume = $("#realRefundVolume").val();
        realRefundVolume= $.trim(realRefundVolume);
        var id = $("#id").val();
        var token = $("#token").val();

        jQuery.postItems({
            url: ctx+'/reserve/reserveCardStatements/cancellation',
            data: {
                id: id,
                token: token,
                realRefundVolume: realRefundVolume
            },
            success: function (result) {
                if (result == "success") {
                    location.reload("true");
                } else {
                    formLoding('销户失败!');
                }
            }
        });
    });
    /*大客户退费*/
    $(".refundBtnForVIP").on('click', function () {
        var id = $(this).attr("data-id");
        jQuery.postItems({
            url: ctx+'/reserve/reserveCardStatements/refundForVIPForm',
            data: {id: id},
            success: function (result) {

                $("#refundForVIPForm").html(result);
                $("#refundForVIPDialogBtn").click();
                $("#refundForVIPForm .select2").select2({
                    width: '100%'
                });
                $('#refundForVIPForm .icheck').iCheck({
                    checkboxClass: 'icheckbox_square-blue checkbox',
                    radioClass: 'iradio_square-blue'
                });
            }

        });
    });
    /*大客户退费保存*/
    $("#refundSaveForVIPBtn").on('click', function () {
        var refundVolume =$("#refundVolume").val().trim();
        var remarks =$("#remarks").val().trim();
        if (refundVolume ==null || refundVolume=='' || refundVolume==undefined ) {
            formLoding("请输入退费");
            return;
        }
        if(isNaN(refundVolume)){
            errorLoding(" 退费金额必须为数字！");
            return;
        }
        if(refundVolume<=0){
            errorLoding(" 退费金额必须为正数！");
            return;
        }
        var id = $("#id").val();
        var token = $("#token").val();

        jQuery.postItems({
            url: ctx+'/reserve/reserveCardStatements/refundForVIP',
            data: {
                id: id,
                token: token,
                refundVolume: refundVolume,
                remarks:remarks
            },
            success: function (result) {
                if (result == "success") {
                    location.reload("true");
                } else {
                    formLoding(result);
                }
            }
        });
    });
    //充值保存
    $("#rechargeSaveBtn").on('click', function () {
        var rechargeVolume = $("#rechargeVolume").val();
        rechargeVolume= $.trim(rechargeVolume);
        rechargeVolume=parseFloat(rechargeVolume);
        var remarks=$("#remarks").val().trim();
        if (rechargeVolume == '') {
            formLoding("请输入充值金额");
            return;
        }
        if(isNaN(rechargeVolume)){
            errorLoding(" 充值金额必须为合法数字！");
            return;
        }
        if(rechargeVolume<=0){
            errorLoding(" 充值金额必须为正数！");
            return;
        }

        var id = $("#id").val();
        var token = $("#token").val();
        var payType = $('#payType input:radio:checked').val();

        if (payType == ''|| payType== undefined ||payType==null) {
            formLoding("请选择支付方式");
            return;
        }

        jQuery.postItems({
            url: ctx+'/reserve/reserveCardStatements/recharge',
            data: {
                id: id,
                token: token,
                rechargeVolume: rechargeVolume,
                payType:payType,
                remarks:remarks
            },
            success: function (result) {
                if (result == "success") {
                    location.reload(true);
                } else {
                    formLoding('充值失败!');
                }
            }
        });
    });
});