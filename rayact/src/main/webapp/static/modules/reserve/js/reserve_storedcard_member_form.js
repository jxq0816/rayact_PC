/**
 * Created by lenovo on 2016/1/11.
 */
function checkForm() {
    var rs=true;
    var id = $("#id").val();
    var cardno = $("#cardno").val();
    var sfz = $("#sfz").val();
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
    if(cardno==null || cardno==''||cardno==undefined ){
        errorLoding("卡号为空");
        rs=false;
    }
    var mobile = $("#mobile").val();
    if(mobile==null || mobile==''||mobile==undefined ){
        errorLoding("手机号为空");
        rs=false;
    }

    jQuery.postItems({
        url: ctx + '/reserve/storedCardMember/check',
        data: {
            id:id,
            cardno:cardno,
            mobile:mobile,
            sfz:sfz
        },
        success: function (result) {
            if(result=="1") {
                errorLoding("卡号重复");
                rs=false;
            }else if(result=="2"){
                errorLoding("手机号重复");
                rs=false;
            }else if(result=="3"){
                errorLoding("身份证号重复");
                rs=false;
            }
        }
    });
    return rs;
}