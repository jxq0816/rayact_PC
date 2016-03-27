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
function timeCardRechargeAddTime(){
    var id=$("#id").val();
    var rechargeVolume=$("#rechargeVolume").val();
    var time=$("#time").val();
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
        data: {id: id,rechargeVolume:rechargeVolume,time:time,payType:payType},
        success: function () {
            successLoding("充值成功");
            location.reload(true);
        }
    });
}