/**
 * Created by lenovo on 2016/1/11.
 */
function checkLoginName() {
    var loginName=$("#loginName").val().trim();
    var oldLoginName=$("#oldLoginName").val().trim();
    var rs=false;
    jQuery.postItems({
        url: ctx + '/reserve/reserveUser/checkLoginName',
        data: {
            loginName:loginName,
            oldLoginName:oldLoginName
        },
        success: function (result) {
            alert(result);
           if(result=="false") {
               errorLoding("该登陆名已被占用");
                rs=false;
            }else if(result=="true"){
                rs=true;
            }
        }
    });
    return rs;
}