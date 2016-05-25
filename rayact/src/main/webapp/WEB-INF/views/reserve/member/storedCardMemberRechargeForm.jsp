<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<td class="content">
    <form:form id="formBean" modelAttribute="reserveMember"
               action="${ctx}/reserve/storedCardMember/save" method="post"
               class="form-horizontal">
        <form:hidden id="id" path="id"/>
        <input id="token" type="hidden" name="token" value="${token}"/>
        <table id="contentTable" class="table table-bordered">
            <tr>
                <td>姓名:</td>
                <td>${reserveMember.name}</td>
                <td>手机号:</td>
                <td>${reserveMember.mobile}</td>
            </tr>
            <tr>
                <td>身份证:</td>
                <td>${reserveMember.sfz}</td>

                <td>地址:</td>
                <td>${reserveMember.address}</td>
            </tr>

            <tr>
                <td>性别:</td>
                <td>${reserveMember.sex}</td>

                <td>卡号:</td>
                <td>${reserveMember.cartno}</td>
            </tr>
            <tr>
                <td>余额:</td>
                <td>
                    <fmt:formatNumber value="${reserveMember.remainder}"/>
                </td>

                <td>储值卡名称:</td>
                <td>${reserveMember.storedcardSet.name}
                </td>
            </tr>

            <tr>
                <td>备注:</td>
                <td colspan="3">${reserveMember.remarks}
                </td>
            </tr>

            <tr>
                <td>
                    支付方式:
                </td>

                <td colspan="3">
                    <div class="btn-group" id="payType">
                        <label class="radio-inline">
                            <input type="radio" class="icheck" value="2" name="payType"/>现金
                        </label>
                        <label class="radio-inline">
                            <input type="radio" class="icheck" value="3" name="payType"/>银行卡
                        </label>
                        <label class="radio-inline">
                            <input type="radio" class="icheck" value="4" name="payType"/>微信
                        </label>
                        <label class="radio-inline">
                            <input type="radio" class="icheck" value="9" name="payType"/>微信（个人）
                        </label>
                        <label class="radio-inline">
                            <input type="radio" class="icheck" value="5" name="payType"/>支付宝
                        </label>
                        <label class="radio-inline">
                            <input type="radio" class="icheck" value="10" name="payType"/>支付宝（个人）
                        </label>
                        <label class="radio-inline">
                            <input type="radio" class="icheck" value="6" name="payType"/>其它
                        </label>
                    </div>
                </td>
            </tr>

            <tr>
                <td>充值:</td>
                <td colspan="3">
                    <input id="rechargeVolume" name="rechargeVolume" htmlEscape="false" maxlength="30"
                           class="form-control required number"/>
                </td>
            </tr>
        </table>
    </form:form>
</td>
<script type="text/javascript">
    $(document).ready(function () {
        $("#formBean").validate({
            submitHandler: function (form) {
                formLoding('正在提交，请稍等...');
                form.submit();
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
    });
</script>
