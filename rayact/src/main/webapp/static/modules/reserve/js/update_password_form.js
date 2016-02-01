function checkForm(){
    var id=$("#id").val();
    var oldPassword=$("#oldPassword").val();
    oldPassword= $.trim(oldPassword);

    var password=$("#password").val();
    password=$.trim(password);

    var password2=$("#password2").val();
    password2= $.trim(password2);

    $("#inputForm").validate({
        submitHandler: function (form) {

        },
        errorContainer: "#messageBox",
        errorPlacement: function (error, element) {
            formLoding('输入有误，请先更正。');
            if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        }
    });

    if(password!=password2){
        errorLoding("两次密码输入不一致，请重新输入");
        return false;
    }

    var rs=false;
    jQuery.postItems({
        url: ctx + '/reserve/user/checkPassword',
        data: {
            id:id,
            oldPassword:oldPassword
        },
        success: function (result) {
            if(result=="0") {
                errorLoding("原始密码不正确，请重新输入");
                rs=false;
            }else{
                rs=true;
            }
        }
    });
    return rs;

}