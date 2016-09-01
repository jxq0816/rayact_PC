<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<div class="row">
    <div class="content">
        <div class="table-responsive">
            <form id="paySubmitForm">
                <table>
                    <thead>
                    <tr>
                        <th>商品名称</th>
                        <th>数量</th>
                        <th>单价</th>
                        <th>总价</th>
                        <th>备注</th>
                    </tr>
                    </thead>
                    <tbody>
                    <input type="hidden" name="token" value="${token}"/>
                    <c:forEach items="${sellDetailList}" var="reserveCommoditySellDetail" varStatus="status">
                        <tr>
                            <input name="reserveCommoditySellDetailList[${status.index}].reserveCommodity.id"
                                   value="${reserveCommoditySellDetail.reserveCommodity.id}"
                                   type="hidden">
                            <td><input class="form-control" type="text"
                                       name="reserveCommoditySellDetailList[${status.index}].reserveCommodity.name"
                                       value="${reserveCommoditySellDetail.reserveCommodity.name}"
                                       placeholder="${reserveCommoditySellDetail.reserveCommodity.name}" readonly></td>
                            <td><input class="form-control" type="text"
                                       name="reserveCommoditySellDetailList[${status.index}].num"
                                       value="${reserveCommoditySellDetail.num}"
                                       placeholder="${reserveCommoditySellDetail.num}" readonly></td>
                            <td><input class="form-control" type="text"
                                       name="reserveCommoditySellDetailList[${status.index}].price"
                                       value="${reserveCommoditySellDetail.price}"
                                       placeholder="${reserveCommoditySellDetail.price}" readonly></td>
                            <td><input class="form-control" type="text"
                                       name="reserveCommoditySellDetailList[${status.index}].detailSum"
                                       value="${reserveCommoditySellDetail.num * reserveCommoditySellDetail.price}"
                                       placeholder="${reserveCommoditySellDetail.num * reserveCommoditySellDetail.price}"></td>
                            <td><input class="form-control" type="text"
                                       name="reserveCommoditySellDetailList[${status.index}].remarks"
                                       value="${reserveCommoditySellDetail.remarks}"
                                       placeholder="${reserveCommoditySellDetail.remarks}"></td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td>
                            支付方式:
                        </td>
                        <td colspan="4">
                            <div class="btn-group" id="payType">
                                <label class="radio-inline">
                                    <input type="radio" class="icheck" id="cash" value="2" checked="checked" name="payType"/>现金
                                </label>

                                <label class="radio-inline">
                                    <input type="radio" class="icheck" id="isMember"  value="1" name="payType"/>会员卡
                                </label>

                                <label class="radio-inline">
                                    <input type="radio" class="icheck" id="card" value="3" name="payType"/>银行卡
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" class="icheck" id="weixin" value="4" name="payType"/>微信
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" class="icheck" id="" value="9" name="payType"/>微信（个人）
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" class="icheck" id="alipay" value="5" name="payType"/>支付宝
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" class="icheck" value="10" name="payType"/>支付宝（个人）
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" class="icheck" value="11" name="payType"/>转账
                                </label>
                            </div>
                        </td>
                    </tr>
                    <div id="memberList" style="display:none">
                        <tr>
                            <td>姓名:</td>

                            <td>
                               <select style="width:100%"  id="reserveMemberSelect" class="select2" name="reserveStoredCardMember.id" disabled>
                                   <option value="">--请选择会员--</option>
                                   <c:forEach  items="${reserveMemberList}" var="m">
                                        <option value="${m.id}">${m.cardNo}-${m.mobile}-${m.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </div>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $("#isMember").on('ifChecked', function () {
            $("#reserveMemberSelect").removeAttr("disabled");
        });
        $("#isMember").on('ifUnchecked', function () {
            $("#reserveMemberSelect").attr("disabled","disabled");
        });
    })
</script>
