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
    var time=$("time").val();
    if(isNaN(rechargeVolume)){
        errorLoding("请输入充值金额");
        return;
    }
    if(isNaN(time)){
        errorLoding("请输入次数");
        return;
    }
    var payType=$("payType").val();
    if(payType=="" || payType==null || payType==undefined){
        errorLoding("请选择支付类型");
        return;
    }
    jQuery.postItems({
        url: ctx + '/reserve/timeCardMember/addTime',
        data: {id: id,rechargeVolume:rechargeVolume,time:time,payType:payType},
        success: function (result) {
            $("#timeCardAddDialogButton").click();
            $("#timeCardAddForm").html(result);

        }
    });
}