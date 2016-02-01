
function checkPassword(){
    var oldPassword=$("#oldPassword").val().trim();
    jQuery.postItems({
        url: ctx + '/reserve/user/checkPassword',
        data: {
            id:id,
            oldPassword:oldPassword
        },
        success: function (result) {
            if(result=="0") {
                errorLoding("原始密码不正确，请重新输入");
                return false;
            }else{
                return true;
            }
        }
    });


}