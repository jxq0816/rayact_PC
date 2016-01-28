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
                        </tr>
                    </c:forEach>
                    <tr>
                        <td>
                            支付方式:
                        </td>
                        <td colspan="3">
                            <div class="btn-group" id="payType">
                                <label class="radio-inline">
                                    <input type="radio" class="icheck" value="1" name="payType"/>会员卡
                                </label>
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
                                    <input type="radio" class="icheck" value="5" name="payType"/>支付宝
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" class="icheck" value="6" name="payType"/>其它
                                </label>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</div>

