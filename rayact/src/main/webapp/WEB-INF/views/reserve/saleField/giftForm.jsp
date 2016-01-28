<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form id="giftFormBean">
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
                <j:if test="${!empty tutorOrderList}">
                    <c:forEach items="${tutorOrderList}" var="tutorOrder">
                        <td>${tutorOrder.tutor.name}(教练):</td>
                        <td>${tutorOrder.totalPrice}</td>
                    </c:forEach>
                </j:if>
            </tr>
            </tbody>
        </table>
    </div>
    <hr/>
    <div class="content">
        选择赠品:<select class="select2" id="gift">
        <option value="">请选择</option>
        <c:forEach items="${giftList}" var="gift">
            <option data-name="${gift.name}" data-unit="${gift.unit}" value="${gift.id}">
                    ${gift.quickSearch}-${gift.name}-单位:${gift.unit}
            </option>
        </c:forEach>
    </select>
    </div>
    <hr/>
    <div class="content">
        <table id="giftTable"></table>
    </div>
</form>
<script type="text/javascript">
    $(document).ready(function () {
        var length = $("#giftTable tr").length;
        $("#gift").on('change', function () {
            var id = $(this).val();
            var name = $(this).find("option:selected").attr("data-name");
            var unit = $(this).find("option:selected").attr("data-unit");
            var html = '<tr><td>名称:<input type="hidden" name="giftList[' + length + '].modelId" value="${cos.id}"/>' + name + '</td><td>单位:' + unit + '</td>\
                        <td>数量:<input type="text" name="giftList[' + length + '].num" value="1" class="form-control"></td>\
                    <td> <input type="hidden" name="giftList[' + length + '].gift.id" value="' + id + '"/>\
                    <a class="btn btn-danger delGifTr btn-xs" <i class="fa fa-times"></i>删除</a></td></tr>';
            $("#giftTable").append(html);
            length++;
        });
        //删除赠品
        $("#giftTable").on('click', '.delGifTr', function (event) {
            $(this).parents("tr").remove();
            event.stopPropagation();
        });
    });
</script>