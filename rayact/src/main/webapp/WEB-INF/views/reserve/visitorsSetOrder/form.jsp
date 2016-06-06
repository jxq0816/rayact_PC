<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form id="formBean">
    <input type="hidden" name="token" value="${token}"/>
    <input type="hidden" name="visitorsSet.id" value="${visitorsSet.id}"/>
    <input type="hidden" name="reserveVenue.id" value="${visitorsSet.reserveVenue.id}"/>
    <div class="content">
        <div class="row">
            <!--用户信息-->
            <div class="col-lg-12 pull-left">
                <div class="col-lg-6 reserve_mid_line">
                    <!--用户信息;商品信息及数量-->
                    <div class="row">
                        <div class="form-group">
                            <label for="memberType" class="col-lg-2 control-label">顾客类型：</label>
                            <div class="radio-inline"  id="memberType">
                                <input type="radio" id="isMember" class="icheck" value="2" checked="checked" name="orderType"/>会员
                            </div>
                            <div class="radio-inline">
                                <input type="radio" id="nMember" class="icheck" value="1" name="orderType"/>散客
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <label for="fieldId" class="col-lg-2 control-label">场地：</label>
                            <div class="col-lg-10">
                                <select style="width: 100%" id="fieldId" class="select2" name="fieldId">
                                    <option value="">--请输入选择--</option>
                                    <c:forEach items="${fieldList}" var="field">
                                        <option value="${field.id}">${field.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <label class="memberSelect col-lg-2 control-label" for="memberId">会员列表：</label>
                            <div class="col-lg-10 memberSelect">
                                <select style="width: 100%" id="memberId" class="select2" name="member.id">
                                    <option value="">--请输入选择--</option>
                                    <c:forEach items="${memberList}" var="m">
                                        <option value="${m.id}">${m.mobile}-${m.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <label class="memberSelect col-lg-2 control-label">姓名：</label>
                            <div class="col-lg-10"><input readonly="readonly" id="userName" name="userName"
                                                          type="text"
                                                          class="form-control"/></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">手机：</label>
                            <div class="col-lg-10"><input readonly="readonly" id="consMobile" name="consMobile"
                                                          type="text"
                                                          class="form-control"/></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">人数：</label>
                            <div class="col-lg-10">
                                <input type="text" class="form-control number" name="collectCount"
                                       id="collectCount"
                                       value="1"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6">
                    <!--应收金额-->
                    <div class="row col-sm-12 col-md-12 col-lg-12" style="margin-left:10px ">
                        <div class="row">
                            <div class="form-group">
                                <label class="col-lg-2 control-label">票价：</label>
                                <div class="col-lg-10">
                                    <input type="text" id="orderPrice" readonly="readonly"
                                           value="${visitorsSet.price}"
                                           class="form-control" name="orderPrice"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="col-lg-2 control-label">教练：</label>
                                <div class="col-lg-10">
                                    <select id="tutorId" name="tutor.id" class="select2">
                                        <option value="">如果使用了教练,请选择</option>
                                        <c:forEach items="${tutors}" var="t">
                                            <option data-price="${t.price}"
                                                    value="${t.id}">${t.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label for="orderTotal" class="col-lg-2 control-label">应付：</label>
                                <div class="col-lg-10">
                                    <span id="orderTotal">${visitorsSet.price}</span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="col-lg-2 control-label">实付：</label>
                                <div class="col-lg-10">
                                    <input type="text" id="collectPrice" class="form-control"
                                           value="${visitorsSet.price}" name="collectPrice"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="col-lg-2 control-label">备注：</label>
                                <div class="col-lg-10">
                                    <textarea id="remarks" class="form-control" name="remarks"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <!--支付方式-->
            <div class="row col-sm-12 col-md-12 col-lg-12">
                <div class="pull-left col-lg-2">
                    <label class="control-label">支付方式：</label>
                </div>
                <div class="col-sm-10 col-md-10 col-lg-10">
                    <label class="radio-inline">
                        <input type="radio" class="icheck"
                               checked="checked" value="1"
                               name="payType"/>储值卡
                    </label>
                    <label class="radio-inline">
                        <input type="radio" class="icheck" value="2"
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
                        <input type="radio" class="icheck" value="9" name="payType"/>（个人）微信
                    </label>
                    <label class="radio-inline">
                        <input type="radio" class="icheck" value="10" name="payType"/>（个人）支付宝
                    </label>
                </div>
            </div>
        </div>
    </div>
</form>
<script src="${ctxStatic}/cleanzone/js/jquery.select2/select2.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/modules/reserve/js/time_ticket_sell.js" type="text/javascript"></script>