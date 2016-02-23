/**
 * Created by lenovo on 2016/1/11.
 */
function checkForm() {
    var id = $("#id").val();
    var cardno = $("#cardno").val();
    var mobile = $("#mobile").val();
    var sfz = $("#sfz").val();

    var rs=false;
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
                return false;
            }else if(result=="2"){
                errorLoding("手机号重复");
                return false;
            }else if(result=="3"){
                errorLoding("身份证号重复");
                return false;
            }else{
                rs= true;
            }
        }
    });
    return rs;
}