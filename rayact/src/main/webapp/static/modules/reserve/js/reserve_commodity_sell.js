/**
 * Created by lenovo on 2016/1/11.
 */
$(document).ready(function () {
    $("#contentTable tbody tr button").each(function () {
        $(this).removeAttr("disabled");//页面刷新将所有的button重置
    });
})
function outStorage(id, name, price,repertoryNum) {
    var index = $("#sellList tbody tr").length;
    var s = '<tr data-price="'+price+'" data-num="1" commodityName="'+name+'" repertoryNum="'+repertoryNum+'" id="'+id+'tr"><td>'+name+'</td><td>\
        <a onclick="add(\''+id+'\')" class="glyphicon glyphicon-plus"></a>\
        <input name="reserveCommoditySellDetailList['+index+'].reserveCommodity.id" value=\''+id+'\' type="hidden">\
        <input name="reserveCommoditySellDetailList['+index+'].reserveCommodity.name" value=\''+name+'\' type="hidden" >\
        <input name="reserveCommoditySellDetailList['+index+'].num" id="'+id+'num" style="width:20px" value="1" onkeyup="changeNumber(\''+id+'\')">\
        <input name="reserveCommoditySellDetailList['+index+'].price" value=\''+price+'\' type="hidden">\
        <a onclick="dele(\''+id+'\')" class="glyphicon glyphicon-minus"></a></td></tr>';

    $("#" + id).attr("disabled", "true");

    /*求和*/
    var sum = Number($("#sum").text());
    sum += price;
    $("#sum").text(sum);

    $('#sellList').append(s);
}
function add(id) {
    var num = Number($("#" + id + "num").val());
    num++;
    var repertoryNum=$("#" + id+"tr").attr("repertoryNum");//库存
    var commodityName = $("#" + id+"tr").attr("commodityName");
    if(repertoryNum<num){
        errorLoding("抱歉，"+commodityName+"库存量不足");
        return;
    }
    $("#" + id + "num").val(num);
    $("#" + id+"tr").attr("data-num",num);
    sum();
}
function dele(id) {
    var num = Number($("#" + id + "num").val());
    num--;
    if(num<0){
        errorLoding("抱歉，数量不能为负");
        return;
    }
    $("#" + id + "num").val(num);
    $("#" + id+"tr").attr("data-num",num);
    sum();
}
function changeNumber(id){
    var num = Number($("#" + id + "num").val());
    $("#" + id+"tr").attr("data-num",num);
    var repertoryNum=$("#" + id+"tr").attr("repertoryNum");//库存
    sum();
    var commodityName = $("#" + id+"tr").attr("commodityName");
    if(repertoryNum<num){
        errorLoding("抱歉，"+commodityName+"库存量不足");
        return;
    }
}
function sum() {
    var sum = 0;
    $("#sellList tbody tr").each(function () {
        var num = $(this).attr("data-num");
        var price = $(this).attr("data-price");
        var t = price*1 * num*1;
        sum += t;
    });
    $("#sum").text(sum);
    return sum;
}

function settlement() {
    var flag=1;
    $("#sellList tbody tr").each(function () {
        var num = $(this).attr("data-num");
        var commodityName = $(this).attr("commodityName");
        var repertoryNum=$(this).attr("repertoryNum");//库存
        if(Number(num)>Number(repertoryNum)){
            errorLoding("抱歉，"+commodityName+"库存不足");
            flag=0;
            return false;
        }
    });
    if(flag==0){
        return;
    }
    var sellDetailList = $("#paySell").serializeArray();
    jQuery.postItems({
        url: ctx+'/reserve/reserveCommoditySellDetail/settlement',
        data: sellDetailList,
        success: function (result) {
            $("#settlementForm").html(result);
            $("#settlementDialogBtn").click();
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
function paySubmit() {
    var data = $("#paySubmitForm").serializeArray();
    var payType = $('#payType input:radio:checked').val();
    var bool=true;
    $("[name$=detailSum]").each(function () {
        var price=$(this).val();
        if(isNaN(price)){
            errorLoding(price+"不是数字，请修改之后再提交！");
            bool=false;
            return;
        }
    });
    if(bool==false){
        return;
    }
    if(payType=='1'){
        var memberId=jQuery("#reserveMemberSelect").val();
        if(memberId=="" || memberId==null || memberId== undefined){
            errorLoding("请选择会员");
            return;
        }
    }

    jQuery.postItems({
        url: ctx+'/reserve/reserveCommoditySellDetail/paySubmit',
        data: data,
        success: function (result) {
            var sellId=result;
            var url=ctx+'/reserve/reserveCommoditySell/sellReport?id='+sellId;
            window.location.replace(url);
        },
        error: function () {
            errorLoding("付款失败");
        }
    });
}