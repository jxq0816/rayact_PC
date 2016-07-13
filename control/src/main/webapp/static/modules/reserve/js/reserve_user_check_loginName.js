function checkLoginName() {
    $.ajaxSetup({
        async : false
    });
    var rs = false;
    var loginName = $("#loginName").val().trim();
    if(loginName==null||loginName==''){
        $("#checkLoginNameResult").html("登陆名不能为空");
        return false;
    }
    var oldLoginName = $("#oldLoginName").val().trim();
    jQuery.postItems({
        url: ctx + '/reserve/reserveUser/checkLoginName',
        data: {
            loginName: loginName,
            oldLoginName: oldLoginName
        },
        success: function (result) {
            if (!result) {
                $("#checkLoginNameResult").html("该登陆名已被占用");
            }else{
                $("#checkLoginNameResult").html("");
            }
        }
    });
    return rs;
}