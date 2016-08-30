/**
 * Created by lenovo on 2016/1/11.
 */
function checkForm() {
    var rs=true;
    var id = $("#id").val().trim();
    var cardNo = $("#cardNo").val().trim();
    var mobile = $("#mobile").val().trim();
    var sfz = $("#sfz").val().trim();
    var name=$("#name").val().trim();
    var venue=$("#reserveVenue_id").val();
    if(mobile==''|| mobile==null || mobile==undefined){
        errorLoding("手机号不能为空");
        return false;
    }
    if(checkMobile(mobile)==false){
        errorLoding("请输入正确的手机号");
        return false;
    }
    if(name==''|| name==null || name==undefined){
        errorLoding("姓名不能为空");
        return false;
    }

    if(!venue){
        errorLoding("请选择开户场馆");
        return false;
    }
    jQuery.postItems({
        url: ctx + '/reserve/reserveMember/checkBeforeSave',
        data: {
            id:id,
            cardNo:cardNo,
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
function checkMobile(s){
    var length = s.length;
    if(length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(14[0-9]{1})|)+\d{8})$/.test(s) )
    {
        return true;
    }else{
        return false;
    }
}