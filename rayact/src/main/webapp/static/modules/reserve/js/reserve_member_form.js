/**
 * Created by lenovo on 2016/1/11.
 */
function checkForm() {
    var rs=true;
    var id = $("#id").val().trim();
    var mobile = $("#mobile").val().trim();
    var sfz = $("#sfz").val().trim();
    var name=$("#name").val().trim();
    if(mobile==''|| mobile==null || mobile==undefined){
        errorLoding("手机号不能为空");
        return false;
    }
    if(name==''|| name==null || name==undefined){
        errorLoding("姓名不能为空");
        return false;
    }
    jQuery.postItems({
        url: ctx + '/reserve/storedCardMember/check',
        data: {
            id:id,
        /*    cardno:cardno,*/
            mobile:mobile,
            sfz:sfz
        },
        success: function (result) {
           /* if(result=="1") {
                errorLoding("卡号重复");
                rs=false;
            }else */
            if(result=="2"){
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