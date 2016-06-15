$(document).ready(function () {
    $("#isMember").on('ifChecked', function () {
        $("#memberId").removeAttr("disabled");
        $("#consPrice").attr("readonly", "readonly");
        $("#userName").attr("readonly", "readonly");
        $("#consMobile").attr("readonly", "readonly");
        $(".memberSelect").show();
        $("input[name='payType'][value='1']").iCheck('check');
        $("input[name='payType'][value='1']").iCheck('enable');
    });

    $("#nMember").on('ifChecked', function () {
        $("#memberId").attr("disabled","disabled");
        $("#consPrice").removeAttr("readonly");
        $("#userName").removeAttr("readonly");
        $("#consMobile").removeAttr("readonly");
        $("#memberId").val("");
        $("#s2id_memberId").find(".select2-chosen").html("--请选择--");
        $("#userName").attr("value", "");
        $("#consMobile").attr("value", "");
        $("input[name='payType'][value='2']").iCheck('check');
        $("input[name='payType'][value='1']").iCheck('disable');
    });

    $("#memberId").on('change', function () {
        var mid = $(this).attr("value");
        var text = $(this).find("option:selected").text();
        var mobile = text.split('-')[0];
        var username = text.split('-')[1];
        $("#userName").attr("value", username);
        $("#consMobile").attr("value", mobile);
    });

    $("#saveBtn").on('click', function () {
        var fieldId = $("#fieldId").val();
        var memberType = $("#memberType input:radio:checked").val();
        var startTime=$("#startTime").val();
        var endTime=$("#endTime").val();
        if (!fieldId) {
            formLoding('请选择场地');
            return false;
        }
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
        if(memberType=='2'){
            var memberId=$("#memberId").val();
            if(!memberId){
                errorLoding("请选择次卡会员");
                return;
            }
        }
        var collectPrice=$("#collectPrice").val();
        if(isNaN(collectPrice)){
            errorLoding("支付金额必须为数字");
            return;
        }
        var formJson = $("#formBean").serializeArray();
        var checkFlag=false;
        jQuery.postItems({
            url: ctx+'/reserve/reserveVenueOrder/checkSave?random='+Math.random(),
            data: formJson,
            success: function (result) {
                result = $.parseJSON(result);
                if (result.status==true) {
                    checkFlag=true;
                }else{
                    formLoding(result.msg);
                    return;
                }
            }
        });
        if(checkFlag==true){
            jQuery.postItems({
                url: ctx+'/reserve/reserveVenueOrder/save?random='+Math.random(),
                data: formJson,
                success: function (result) {
                    result = $.parseJSON(result);
                    if (result.status) {
                        $("#closeBtn").click();
                    }
                    formLoding(result.msg);
                }
            });
        }
    });



    jQuery.addPrice = function (price, orderPrice, count) {
        if (price == '' || price == undefined) {
            price = 0;
        }
        if (orderPrice == '' || orderPrice == undefined) {
            orderPrice = 0;
        }
        if (count == '' || count == undefined) {
            count = 1;
        }
        var orderTotal = price * count + orderPrice * count;
        $("#orderTotal").text(orderTotal);
        $("#collectPrice").attr("value", orderTotal);
    };

    //教练
    $("#tutorId").on('change', function () {
        var price = $(this).find("option:selected").attr("data-price");
        var orderPrice = $("#orderPrice").val();
        var count = $("#collectCount").val();
        $.addPrice(price, orderPrice, count);
    });

    //数量改变事件
    $("#collectCount").on('keyup', function () {
        var t = $(this);
        var r = /^[0-9]*[1-9][0-9]*$/
        var value = t.val();
        if (value == '') {
            value = 1;
        }
        if (r.test(value) == false) {
            $("#collectCount").val("1");
        } else {
            var price = $("#tutorId").find("option:selected").attr("data-price");
            var orderPrice = $("#orderPrice").val();
            $.addPrice(price, orderPrice, value);
        }
    });

});