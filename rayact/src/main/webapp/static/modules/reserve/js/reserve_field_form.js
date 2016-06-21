$(document).ready(function () {
    //$("#reserveDialogModal").draggable({
    // handle: ".modal-header"
    //});
    $("#isMember").on('ifChecked', function () {
        $("#deposit").hide();
        $("#userName").attr("readonly", "true");
        $("#consMobile").attr("readonly", "true");
        $("#memberSelect").show();
        $("#userName").attr("value", "");
        $("#consMobile").attr("value", "");
    });

    $("#nMember").on('ifChecked', function () {
        $("#deposit").show();
        $("#userName").removeAttr("readonly");
        $("#consMobile").removeAttr("readonly");
        $("#memberSelect").hide();
        $("#memberId").val("");
        $("#s2id_memberId").find(".select2-chosen").html("--请选择--");
        $("#userName").attr("value", "");
        $("#consMobile").attr("value", "");
    });
     $("#memberId").on('change', function () {
         var text = $(this).find("option:selected").text();
         var username = text.split('-')[1];
         var mobile = text.split('-')[2];
         $("#userName").attr("value", username);
         $("#consMobile").attr("value", mobile);
     });

    $("#startTime").on('change', function () {
        var startTime = $("#startTime").attr("value");
        var endTime = $("#endTime").attr("value");
    });

    $("#endTime").on('change', function () {
        var startTime = $("#startTime").attr("value");
        var endTime = $("#endTime").attr("value");
    });

    //频率
    $("#frequency").on('change', function () {
        var frequency = $(this).val();
        if ('1' != frequency) {
            $("#date_div").show();
        } else {
            $("#date_div").hide();
        }
    });

    //教练
    $("#tutorId").on('change', function () {
        var price = $(this).find("option:selected").attr("data-price");
        if (price == undefined || price == '') {
            $("#tutor_price").val("0元/小时");
        } else {
            $("#tutor_price").val(price + "元/小时");
        }
    });
});