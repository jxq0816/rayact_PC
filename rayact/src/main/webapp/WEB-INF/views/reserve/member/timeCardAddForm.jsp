<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

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
            <div class="content">
                支付方式:
                <table class="no-border" id="payType">
                    <tbody class="no-border-y">
                    <tr>
                        <td>
                            <label class="radio-inline">
                                <input type="radio" class="icheck" value="2"
                                       <j:if test="${'1' eq cos.consType}">checked="checked"</j:if>
                                       name="payType"/>现金
                            </label>
                            <label class="radio-inline">
                                <input type="radio" class="icheck" value="3" name="payType"/>银行卡
                            </label>
                            <label class="radio-inline">
                                <input type="radio" class="icheck" value="4" name="payType"/>微信
                            </label>
                            <label class="radio-inline">
                                <input type="radio" class="icheck" value="5" name="payType"/>支付宝
                            </label>
                            <label class="radio-inline">
                                <input type="radio" class="icheck" value="6" name="payType"/>其它
                            </label>
                            <label class="radio-inline">
                                <input type="radio" class="icheck" value="7" name="payType"/>欠账
                            </label>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</form>