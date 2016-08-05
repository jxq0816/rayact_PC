<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="reserveFormBean" class="form-horizontal">
    <input id="id" type="hidden" value="${reserveMember.id}">
    <div class="content text-justify" style="text-align: center;vertical-align: middle;">
        <div class="row">
            <div class="col-lg-6  reserve_mid_line">
                <div class="row">
                    <div class="form-group">
                        <label for="memberName" class="col-sm-2 control-label">姓名:</label>
                        <div class="col-lg-4">
                            <input readonly="readonly" id="memberName" class="form-control"
                                   value="${reserveMember.name}"/>
                        </div>
                        <label for="mobile" class="col-sm-2 control-label">手机号:</label>
                        <div class="col-sm-4">
                            <input readonly="readonly" id="mobile" class="form-control"
                                   value="${reserveMember.mobile}"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label for="reserveVenue" class="col-sm-2 control-label">场馆:</label>
                        <div class="col-sm-4">
                            <input readonly="readonly" id="reserveVenue" class="form-control"
                                   value="${reserveMember.reserveVenue.name}"/>
                        </div>
                        <label for="reserveFieldTicket" class="col-sm-2 control-label">场次票:</label>
                        <div class="col-sm-4">
                            <input readonly="readonly" id="reserveFieldTicket" class="form-control"
                                   value="${reserveMember.timecardSet.name}"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label for="remarks" class="col-sm-2 control-label">充值备注:</label>
                        <div class="col-sm-10">
                            <input id="remarks" name="remarks" htmlEscape="false" maxlength="30" class="form-control"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-6">
                <div class="row">
                    <div class="form-group">
                        <label for="residue" class="control-label col-lg-3">剩余次数:</label>
                        <div class="col-lg-4">
                            <input type="text" id="residue" readonly="readonly" value="${reserveMember.residue}"
                                   class="form-control"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label for="rechargeVolume" class="control-label col-lg-3">充值金额:</label>
                        <div class="col-lg-6"><input id="rechargeVolume" name="userName"
                                                     type="text"
                                                     class="form-control input-sm"/></div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label for="time" class="control-label col-lg-3">次数:</label>
                        <div class="col-lg-6">
                            <input type="text" id="time" name="time"
                                   class="form-control"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <label for="time" class="control-label col-lg-2">支付方式:</label>
            <div class="col-lg-10">
                <div class="btn-group" id="payType">
                    <label class="radio-inline">
                        <input type="radio" class="icheck" id="cash" value="2" checked="checked" name="payType"/>现金
                    </label>
                    <label class="radio-inline">
                        <input type="radio" class="icheck" id="card" value="3" name="payType"/>银行卡
                    </label>
                    <label class="radio-inline">
                        <input type="radio" class="icheck" id="weixin" value="4" name="payType"/>微信
                    </label>
                    <label class="radio-inline">
                        <input type="radio" class="icheck" id="personalWeixin" value="9" name="payType"/>微信（个人）
                    </label>
                    <label class="radio-inline">
                        <input type="radio" class="icheck" id="alipay" value="5" name="payType"/>支付宝
                    </label>
                    <label class="radio-inline">
                        <input type="radio" class="icheck" id="personalAlipay" value="10" name="payType"/>支付宝（个人）
                    </label>
                    <label class="radio-inline">
                        <input type="radio" class="icheck" id="other" value="6" name="payType"/>优惠券
                    </label>
                    <label class="radio-inline">
                        <input type="radio" class="icheck"  value="11" name="payType"/>转账
                    </label>
                </div>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript">
    $(document).ready(function () {
        $('.icheck').iCheck({
            checkboxClass: 'icheckbox_square-blue checkbox',
            radioClass: 'iradio_square-blue'
        });
    });
</script>