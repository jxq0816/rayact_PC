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
    /*退费*/
    $(".refundBtn").on('click', function () {
        var id = $(this).attr("data-id");
        jQuery.postItems({
            url: ctx+'/reserve/reserveCardStatements/refundForm',
            data: {id: id},
            success: function (result) {

                $("#reserveForm").html(result);
                $("#refundDialogBtn").click();
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
    /*退费保存*/
    $("#refundSaveBtn").on('click', function () {
        var realRefundVolume = $("#realRefundVolume").val();
        realRefundVolume= $.trim(realRefundVolume);
        var refundVolume =$("#refundVolume").val();
        refundVolume= $.trim(refundVolume);
        if (refundVolume == '') {
            formLoding("请输入退费");
            return;
        }

        if (realRefundVolume == '') {
            formLoding("请输入实际费用");
            return;
        }

        var id = $("#id").val();
        var token = $("#token").val();

        jQuery.postItems({
            url: ctx+'/reserve/reserveCardStatements/refund',
            data: {
                id: id,
                token: token,
                realRefundVolume: realRefundVolume
            },
            success: function (result) {
                if (result == "success") {
                    location.reload("true");
                } else {
                    formLoding('退费失败!');
                }
            }
        });
    });
    //充值保存
    $("#rechargeSaveBtn").on('click', function () {
        var rechargeVolume = $("#rechargeVolume").val();
        rechargeVolume= $.trim(rechargeVolume);

        if (rechargeVolume == '') {
            formLoding("请输入充值金额");
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
                payType:payType
            },
            success: function (result) {
                if (result == "success") {
                    location.reload("true");
                } else {
                    formLoding('充值失败!');
                }
            }
        });
    });
});
function culculate() {
    var refundVolume = $("#refundVolume").val();
    refundVolume= $.trim(refundVolume);
    var realRefundVolume = 0.9 * refundVolume;
    realRefundVolume = realRefundVolume.toFixed(2);
    $("#realRefundVolume").val(realRefundVolume);
}