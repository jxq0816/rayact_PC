$(document).ready(function () {
    var childCheck = false;
    //选中主菜单
    $(".authCheck").on('ifChecked', function () {
        if (childCheck) {
            return;
        }
        var id = $(this).attr("value");
        $(".childAuthCheck[data-parent='" + id + "']").iCheck('check');
    });
    //主菜单取消
    $(".authCheck").on('ifUnchecked', function () {

        var id = $(this).attr("value");
        $(this).iCheck('uncheck');
        $(".childAuthCheck[data-parent='" + id + "']").iCheck('uncheck');
    });
    //二级菜单取消
    $(".childAuthCheck").on('ifChecked', function (event) {
        var id = $(this).attr("data-parent");
        childCheck = true;
        $(".authCheck[value='" + id + "']").iCheck('check');
        childCheck = false;
    });
});