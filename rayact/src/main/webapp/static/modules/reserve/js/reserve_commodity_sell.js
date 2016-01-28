/**
 * Created by lenovo on 2016/1/11.
 */
function outStorage(id, name, price) {
    var index = $("#sellList tbody tr").length;

    var s = '<tr data-price="'+price+'" data-num="1" id="'+id+'tr"><td>'+name+'</td><td>\
        <a onclick="add(\''+id+'\')" class="glyphicon glyphicon-plus"></a>\
        <input name="reserveCommoditySellDetailList['+index+'].reserveCommodity.id" value=\''+id+'\' type="hidden">\
        <input name="reserveCommoditySellDetailList['+index+'].reserveCommodity.name" value=\''+name+'\' type="hidden" >\
        <input name="reserveCommoditySellDetailList['+index+'].num" id="'+id+'num" style="width:20px" value="1" onblur="changeNumber(\''+id+'\')">\
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
    $("#" + id + "num").val(num);
    $("#" + id+"tr").attr("data-num",num);
    sum();
}
function dele(id) {
    var num = Number($("#" + id + "num").val());
    num--;
    $("#" + id + "num").val(num);
    $("#" + id+"tr").attr("data-num",num);
    sum();
}
function changeNumber(id){
    var num = Number($("#" + id + "num").val());
    $("#" + id+"tr").attr("data-num",num);
    sum();
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
    jQuery.postItems({
        url: ctx+'/reserve/reserveCommoditySellDetail/paySubmit',
        data: data,
        success: function (result) {
            successLoding(result);
            location.reload(true);
        },
        error: function () {
            errorLoding("付款失败");
        }
    });
}