function showError(id, nullmsg) {
    removeTip(id);
    $("#" + id).addClass("er");

    $("#" + id).parent().find(".err").text(nullmsg);
    $("#" + id).parent().find(".tip").hide();
}

function removeTip(id) {
    $("#" + id).removeClass("er");
    $("#" + id).parent().find(".err").text("");
    $("#" + id).parent().find(".tip").show();
}

//正则表达式校验
function validateByReg(id, errormsg, reg) {
    var num = $.trim($("#" + id).val());
    if (!reg.test(num)) {
        showError(id, errormsg);
        return false;
    }
    removeTip(id);
    return true;
}
function validate(id) {
    var obj = $("#" + id);
    var dataType = $("#" + id).attr("dataType");
    var nullmsg = $("#" + id).attr("nullmsg");
    var errormsg = $("#" + id).attr("errormsg");
    var reg = "";
    //非空校验
    if (obj.val() == "undefined" || obj.val() == null || obj.val() == "" || dataType == "c") {
        showError(id, nullmsg);
        $("#" + id).focus();
        return false;
    }
    //根据dataType利用正则表达式对数据格式进行校验
    if (dataType == "m") {
        reg = /^(13[0-9]{9}|15[012356789][0-9]{8}|18[012356789][0-9]{8}|147[0-9]{8})$/;
        return validateByReg(id, errormsg, reg);
    } else if (dataType == "e") {
        reg = /^((([A-Za-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([A-Za-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/;
        return validateByReg(id, errormsg, reg);
    }
    else if (dataType == "p") {//密码（数字、字母、下划线）
        reg = /^[\w]{6,18}$/;
        return validateByReg(id, errormsg, reg, dataType);
    } else if (dataType.indexOf("-") != -1) {
        var start = dataType.split('-')[0].substring(1);
        var end = dataType.split('-')[1];
        if ($.trim(obj.val()).length < start || obj.val().length > end) {
            showError(id, errormsg);
            return false;
        } else {
            removeTip(id);
            return true;
        }
    } else if (dataType == "r") {//确认密码校验
        var password = $("#newPassword").val();
        var rePassword = $("#" + id).val();
        if (rePassword != password) {
            showError(id, errormsg);
            return false;
        } else {
            removeTip(id);
            return true;
        }
    } else {
        removeTip(id);
        return true;
    }
}
var checkMobileCode = true;

$(document).ready(function () {
    $("#mobile").focus();

    $("#btnSubmit").click(function () {
        var registerType = $("#registerType").attr("value");
        if (registerType == '0') {
            if (!validate("mobile")) {
                return false;
            }
            if (!validate("mobileCode")) {
                return false;
            }
        } else {
            if (!validate("email")) {
                return false;
            }
        }

        if (!validate("newPassword")) {
            return false;
        }
        if (!validate("confirmNewPassword")) {
            return false;
        }

        var checkAjax = true;
        var checkValue = $("#mobile").val();
        var checkMsg = "手机号不存在";
        if (registerType == '1') {
            checkValue = $("#email").val();
            checkMsg = "邮箱不存在"
        }

        $.postItems({
            url: front + '/f/user/register/checkMobile', data: {mobile: checkValue},
            success: function (values) {
                if (values == 'true') {
                    //showError('mobile', checkMsg);
                    alert(checkMsg);
                    checkAjax = false;
                    return false;
                } else {
                    removeTip("mobile");
                    removeTip("email");
                }
            }
        });
        if (registerType == '0') {
            if (checkMobileCode) {
                $.postItems({
                    url: front + '/f/user/register/checkCode',
                    data: {mobile: $("#mobile").val(), mobileCode: $("#mobileCode").val()},
                    success: function (values) {
                        if (values == 'false') {
                            showError('mobileCode', '验证码不正确');
                            checkAjax = false;
                            return false;
                        } else {
                            removeTip("mobileCode");
                        }
                    }
                });
            }
        }
        if (!checkAjax) {
            return false;
        }
        return true;
    });


    var interval;

    function run() {
        $("#sendMobile").attr("disabled", true);
        interval = setInterval(disableBtn, 1000);
    }

    var time = 30;

    function disableBtn() {
        time--;
        $("#sendMobile").attr("value", time + "秒后重新发送")
        if (time == 0) {
            $("#sendMobile").attr("disabled", false);
            $("#sendMobile").attr("value", "获取短信验证码");
            clearTimeout(interval);
        }
    }

    $("#sendMobile").click(function () {
        var checkAjax = true;
        $.postItems({
            url: front + '/f/user/register/checkMobile', data: {mobile: $("#mobile").val()},
            success: function (values) {
                if (values == 'false') {
                    removeTip("mobile");
                } else {
                    showError('mobile', '手机号不存在');
                    checkAjax = false;
                    return false;
                }
            }
        });
        if (!checkAjax) {
            return false;
        }
        $.postItems({
            url: front + '/f/user/register/sendSms', data: {mobile: $("#mobile").val()},
            error: function (l, m, k) {
                alert('失败');
            }, success: function (values) {
                time = 30;
                run();
                if (values == "0") {
                    //alert("验证码发送成功！");
                }
                else {
                    alert("验证码发送失败！");
                }
            }
        });
    });

});