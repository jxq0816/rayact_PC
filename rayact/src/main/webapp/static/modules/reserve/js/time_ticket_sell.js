$(document).ready(function () {
    $("#saveBtn").on('click', function () {
        var memberType = $("#memberType input:radio:checked").val();
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
        jQuery.postItems({
            url: ctx+'/reserve/reserveVenueOrder/save?random='+Math.random(),
            data: formJson,
            success: function (result) {
                if (result) {
                    if (result == "1") {
                        formLoding("保存成功");
                        $("#closeBtn").click();
                        return;
                    }else if (result == "2") {
                        errorLoding("该用户没有次卡");
                        return;
                    }else if (result == "3") {
                        errorLoding("该用户剩余次数不足");
                        return;
                    }else if (result == "4") {
                        errorLoding("该用户的次票不可在该场地使用,请使用非会员，现金结账");
                        return;
                    }else {
                        errorLoding("保存失败");
                    }
                }
            }
        });
    });

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