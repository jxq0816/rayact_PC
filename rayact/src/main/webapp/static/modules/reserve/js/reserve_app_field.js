//保存数据
$(document).ready(function () {
    //-------预定---------
    $(".reserveTd").on('mousedown', function () {
        var price = $(this).attr("data-price");
        if ($(this).hasClass("access")) {//预定
            $(this).removeClass("access");
            $(this).addClass("unpayed");
        }
    });
})