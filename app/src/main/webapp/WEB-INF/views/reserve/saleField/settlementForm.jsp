<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form id="settlementFormBean">
    <input type="hidden" name="id" value="${cos.id}"/>
    <input type="hidden" name="token" value="${token}"/>

    <div class="content">
        <table class="no-border">
            <tbody class="no-border-y">
            <tr>
                <td>预定人:${cos.userName}(<j:ifelse
                        test="${'1' eq cos.consType}"><j:then>散客</j:then><j:else>会员</j:else></j:ifelse>)
                </td>
                <td>
                    <input type="hidden" name="itemId" value="${item.id}"/>
                </td>
                <j:if test="${!empty tutorOrder}">
                    <input type="hidden" name="tutorOrder.id" value="${tutorOrder.id}"/>
                    <input type="hidden" name="tutorOrder.orderPrice" id="tutorPrice" value="${tutorOrder.orderPrice}"/>
                    <td>
                        <label>
                            <select class="select2" id="tutorId" name="tutorOrder.tutor.id">
                                <c:forEach items="${tutors}" var="tutor">
                                    <option <j:if test="${tutor.id eq tutorOrder.tutor.id}">selected="selected"</j:if> value="${tutor.id}">${tutor.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </td>
                    <td><label id="labelTutorPrice">每小时:${tutorOrder.orderPrice}元</label></td>
                    <td>时长(小时):</td>
                    <td>
                        <label>
                            <input type="text" id="tutorCount" name="tutorOrder.orderCount" class="form-control"
                                   value="${tutorOrder.orderCount}"/>
                        </label>
                    </td>
                </j:if>
            </tr>
            </tbody>
        </table>
    </div>

    <hr/>
    <div class="content">
        <table>
            <thead>
            <th>场地</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>频率</th>
            <th>是否半场</th>
            <th>价格</th>
            </thead>
            <tbody>
            <c:forEach items="${itemList}" var="item" varStatus="status">
                <input type="hidden" name="venueConsList[${status.index}].id" value="${item.id}"/>
                <input type="hidden" name="venueConsList[${status.index}].reserveField.id"
                       value="${item.reserveField.id}"/>
                <input type="hidden" name="venueConsList[${status.index}].reserveVenue.id"
                       value="${item.reserveVenue.id}"/>
                <input type="hidden" name="venueConsList[${status.index}].consPrice" value="${item.consPrice}"/>
                <tr>
                    <td>
                            ${item.reserveField.name}
                    </td>
                    <td>
                        <select readonly="" onfocus="this.defaultIndex=this.selectedIndex;"
                                onchange="this.selectedIndex=this.defaultIndex;" class="select2" id="startTime"
                                name="venueConsList[${status.index}].startTime">
                            <c:forEach items="${times}" var="t">
                                <option
                                        <j:if test="${t eq item.startTime}">selected="selected"</j:if>
                                        value="${t}">${t}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <select class="select2" onfocus="this.defaultIndex=this.selectedIndex;"
                                onchange="this.selectedIndex=this.defaultIndex;" id="endTime"
                                name="venueConsList[${status.index}].endTime">
                            <c:forEach items="${times}" var="t">
                                <option
                                        <j:if test="${t eq item.endTime}">selected="selected"</j:if>
                                        value="${t}">${t}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <j:if test="${'1' eq item.frequency}">单次</j:if>
                        <j:if test="${'2' eq item.frequency}">每天</j:if>
                        <j:if test="${'3' eq item.frequency}">每周(${item.consWeek})</j:if>
                        <input type="hidden" name="venueConsList[${status.index}].frequency"
                               value="${item.frequency}"/>
                        <input type="hidden" name="venueConsList[${status.index}].consWeek"
                               value="${item.consWeek}"/>
                    </td>
                    <td>
                        <j:ifelse test="${'1' eq item.halfCourt}"><j:then>是</j:then><j:else>否</j:else></j:ifelse>
                        <input type="hidden" name="venueConsList[${status.index}].halfCourt"
                               value="${item.halfCourt}"/>
                    </td>
                    <td>
                        <input type="text" style="width: 60px;" value="${item.consPrice}"
                               class="form-control orderPrice"
                               name="venueConsList[${status.index}].orderPrice"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <hr/>
    <div class="content">
        支付方式:
        <table class="no-border">
            <tbody class="no-border-y">
            <tr>
                <td>
                    <label class="radio-inline">
                        <input type="radio" class="icheck"
                               <j:if test="${'1' eq cos.consType}">disabled="disabled"</j:if> value="1"
                               <j:if test="${'2' eq cos.consType}">checked="checked"</j:if> name="payType"/>会员卡
                    </label>
                    <label class="radio-inline">
                        <input type="radio" class="icheck" value="2"
                               <j:if test="${'1' eq cos.consType}">checked="checked"</j:if> name="payType"/>现金
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
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <hr/>
    <div class="content">
        <label id="totalPrice">总计(元):<input type="text" id="orderPrice" class="form-control" name="orderPrice"/></label>
    </div>
</form>
<script type="text/javascript">
    $(document).ready(function () {
        function initData(){
            var tutorOrderPrice = 0;
            var tutorPrice = $("#tutorPrice").val();
            var tutorCount = $("#tutorCount").val();
            if (tutorPrice && tutorCount) {
                tutorOrderPrice = (tutorPrice * 1) * (tutorCount * 1);
            }
            var orderPrice = 0;
            $(".orderPrice").each(function () {
                var value = $(this).val();
                orderPrice += (value * 1);
            });
            $("#orderPrice").val(tutorOrderPrice + orderPrice);
        }
        initData();
        //选择教练
        $("#tutorId").on('change',function(){
            var tutorId = $(this).find("option:selected").val();
            jQuery.postItems({
                url: ctx + '/reserve/reserveTutor/getTutorById',
                data: {tutorId:tutorId},
                success: function (data) {
                    $("#tutorPrice").val(data.price);
                    $("#labelTutorPrice").text("每小时:"+data.price+"元");
                    initData();
                }
            });
        });
    });
</script>