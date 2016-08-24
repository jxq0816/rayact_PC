function addTime(id) {
    jQuery.postItems({
        url: ctx + '/reserve/timeCardMember/addTimeForm',
        data: {id: id},
        success: function (result) {
            $("#timeCardAddDialogButton").click();
            $("#timeCardAddForm").html(result);
        }
    });
}
function cancelAccountForm(id) {
    jQuery.postItems({
        url: ctx + '/reserve/timeCardMember/cancelAccountForm',
        data: {id: id},
        success: function (result) {
            $("#timeCardCancelAccountDialogButton").click();
            $("#timeCardCancelAccountForm").html(result);
        }
    });
}
function timeCardRechargeAddTime(){
    var id=$("#id").val();
    var rechargeVolume=$("#rechargeVolume").val().trim();
    var time=$("#time").val().trim();
    var remarks=$("#remarks").val().trim();
    if(rechargeVolume==null||rechargeVolume==""||rechargeVolume==undefined){
        errorLoding("请输入充值金额");
        return;
    }
    if(isNaN(rechargeVolume)){
        errorLoding("充值金额必须为小数");
        return;
    }
    if(time==null||time==""||time==undefined){
        errorLoding("请输入次数");
        return;
    }
    if(isNaN(time)){
        errorLoding("次数必须为数字");
        return;
    }
    var payType = $('#payType input:radio:checked').val();
    if(payType=="" || payType==null || payType==undefined){
        errorLoding("请选择支付类型");
        return;
    }
    jQuery.postItems({
        url: ctx + '/reserve/timeCardMember/addTime',
        data: {id: id,rechargeVolume:rechargeVolume,time:time,payType:payType,remarks:remarks},
        success: function () {
            successLoding("充值成功");
            location.reload(true);
        }
    });
}
function timeCardCancelAccount(){
    var id=$("#id").val();
    var rechargeVolume=$("#rechargeVolume").val().trim();
    var remarks=$("#remarks").val().trim();
    if(rechargeVolume==null||rechargeVolume==""||rechargeVolume==undefined){
        errorLoding("请输入退还金额");
        return;
    }
    if(isNaN(rechargeVolume)){
        errorLoding("充值金额必须为小数");
        return;
    }
    jQuery.postItems({
        url: ctx + '/reserve/timeCardMember/cancelAccount',
        data: {id: id,rechargeVolume:rechargeVolume,remarks:remarks},
        success: function () {
            successLoding("销卡成功");
            location.reload(true);
        }
    });
}