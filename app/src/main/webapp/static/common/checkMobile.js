/**
 * Created by lenovo on 2016/9/29.
 */
function checkMobile(s){
    var length = s.length;
    if(length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(14[0-9]{1})|)+\d{8})$/.test(s) )
    {
        return true;
    }else{
        return false;
    }
}