/**
 * Created by lenovo on 2016/1/11.
 */
function checkForm() {

    var id = $("#id").val();
    var cardno = $("#cardno").val();
    if(cardno==null || cardno==''||cardno==undefined ){
        errorLoding("卡号为空");
        return false;
    }
    var mobile = $("#mobile").val();
    if(mobile==null || mobile==''||mobile==undefined ){
        errorLoding("手机号为空");
        return false;
    }
    var sfz = $("#sfz").val();
    var flg=false;
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
            }else if(result=="2"){
                errorLoding("手机号重复");
            }else if(result=="3"){
                errorLoding("身份证号重复");
            }else{
                flg=true;
            }
        }
    });
    return flg;
}