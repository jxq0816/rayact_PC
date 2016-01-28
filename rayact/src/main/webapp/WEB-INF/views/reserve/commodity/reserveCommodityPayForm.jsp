<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div class="row">
    <div class="content">
        <div class="table-responsive">
            <table>
                <thead>
                <tr>
                    <th>商品名称</th>
                    <th>数量</th>
                    <th>单价</th>
                </tr>
                </thead>
                <tbody>
                <form id="paySubmit">
                    <input type="hidden" id="token" value="${token}"/>
                    <c:forEach items="${sellDetailList}" var="reserveCommoditySellDetail" varStatus="status">
                        <tr>
                            <input name="reserveCommodity.id" value="${reserveCommoditySellDetail.reserveCommodity.id}" type="hidden">
                            <td><input name="reserveCommodity.name" readonly="${reserveCommoditySellDetail.reserveCommodity.name}"> </td>
                            <td><input name="num" value="${reserveCommoditySellDetail.num}" > </td>
                            <td><input name="price" value="${reserveCommoditySellDetail.price}"> </td>
                        </tr>
                    </c:forEach>
                </form>
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
        </div>
    </div>
</div>
