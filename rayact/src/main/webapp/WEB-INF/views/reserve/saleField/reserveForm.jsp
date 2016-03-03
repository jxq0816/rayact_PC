<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form id="reserveFormBean">
    <input type="hidden" name="token" value="${token}"/>
    <input type="hidden" name="project.id" value="${reserveField.reserveProject.id}"/>
    <div class="content">
        场地信息:
        <table class="no-border">
            <tbody class="no-border-y">
            <tr>
                <td>场地:</td>
                <td>${reserveField.name}
                    <input type="hidden" id="consDate" name="consDate"
                           value="${date}"/>
                    <input type="hidden" name="projectId" value="${reserveField.reserveProject.id}"/>
                    <input type="hidden" name="reserveVenue.id" value="${venueId}"/>
                    <input type="hidden" id="fieldId" name="venueConsList[0].reserveField.id"
                           value="${reserveField.id}"/>
                    <input type="hidden" name="venueConsList[0].reserveVenue.id"
                           value="${reserveField.reserveVenue.id}"/>
                    <input type="hidden" name="venueConsList[0].consPrice" value="${reserveField.actualPrice}"/>
                </td>
                <td>时间:</td>
                <td>
                    <div class="row">
                        <div class="col-lg-5">
                            <select class="select2" id="startTime" name="venueConsList[0].startTime">
                                <c:forEach items="${times}" var="t">
                                    <option
                                            <j:if test="${t eq startTime}">selected="selected"</j:if>
                                            value="${t}">${t}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-lg-1">
                            至
                        </div>
                        <div class="col-lg-5">
                            <select class="select2" id="endTime" name="venueConsList[0].endTime">
                                <c:forEach items="${times}" var="t">
                                    <option
                                            <j:if test="${t eq endTime}">selected="selected"</j:if>
                                            value="${t}">${t}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </td>
                <td>频率:</td>
                <td>
                    <select style="width: 80px;" id="frequency" name="frequency" class="select2">
                        <option value="1">单次</option>
                        <option value="2">每天</option>
                        <option value="3">每周</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>半场:</td>
                <td>
                    <input type="checkbox" name="halfCourt" value="1" class="icheck"/>
                </td>
                <td>
                    <select id="tutorId" name="tutor.id" class="select2">
                        <option value="">预定教练</option>
                        <c:forEach items="${tutors}" var="t">
                            <option data-price="${t.price}" value="${t.id}">${t.name}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <span id="tutor_price">

                    </span>
                </td>
                <td>
                    <div id="date_div" style="display: none;">
                        至
                        <input name="endDate" type="text" id="endDate" readonly="readonly" maxlength="20"
                               class="input-medium form-control Wdate "
                               value="${reserveVenue.endDate}"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <hr/>
    <div class="content">
        会员信息:
        <table class="no-border">
            <tbody class="no-border-y">
            <tr>
                <td colspan="4">
                    <label class="radio-inline">
                        <input type="radio" id="isMember" class="icheck" value="2" checked="checked" name="consType"/>会员
                    </label>
                </td>
                <td colspan="4"><label class="radio-inline">
                    <input type="radio" id="nMember" class="icheck" value="1" name="consType"/>非会员
                </label>
                </td>
            </tr>
            <tr>
                <td>手机号或姓名:</td>
                <td>
                    <select style="width: 80px;" id="memberId" class="select2" name="member.id">
                        <option value="">--请输入选择--</option>
                        <c:forEach items="${memberList}" var="m">
                            <option value="${m.id}">${m.mobile}-${m.name}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>押金:</td>
                <td><input type="text" readonly="readonly" id="consPrice" name="consPrice" class="form-control"/></td>

                <td>姓名:</td>
                <td class="text-right"><input readonly="readonly" id="userName" name="userName" type="text"
                                              class="form-control"/></td>

                <td>手机:</td>
                <td class="text-right"><input readonly="readonly" id="consMobile" name="consMobile" type="text"
                                              class="form-control"/></td>
            </tr>
            </tbody>
        </table>
    </div>
    <hr/>
    <div class="content">
        备注:
        <textarea name="remarks" rows="1" class="form-control"></textarea>
    </div>
</form>

<script type="text/javascript">
    $(document).ready(function () {

        $("#isMember").on('ifChecked', function () {
            $("#consPrice").attr("readonly", "readonly");
            $("#userName").attr("readonly", "readonly");
            $("#consMobile").attr("readonly", "readonly");
        });

        $("#nMember").on('ifChecked', function () {
            $("#consPrice").removeAttr("readonly");
            $("#userName").removeAttr("readonly");
            $("#consMobile").removeAttr("readonly");
        });

        $("#memberId").on('change', function () {
            var mid = $(this).attr("value");
            var text = $(this).find("option:selected").text();
            var mobile = text.split('-')[0];
            var username = text.split('-')[1];
            $("#userName").attr("value", username);
            $("#consMobile").attr("value", mobile);
        });

        $("#startTime").on('change', function () {
            var startTime = $("#startTime").attr("value");
            var endTime = $("#endTime").attr("value");
        });

        $("#endTime").on('change', function () {
            var startTime = $("#startTime").attr("value");
            var endTime = $("#endTime").attr("value");
        });

        //频率
        $("#frequency").on('change', function () {
            var frequency = $(this).val();
            if ('1' != frequency) {
                $("#date_div").show();
            } else {
                $("#date_div").hide();
            }
        });

        //教练
        $("#tutorId").on('change', function () {
            var price = $(this).find("option:selected").attr("data-price");
            if (price == undefined || price == '') {
                $("#tutor_price").html("")
            } else {
                $("#tutor_price").html("每小时:" + price + "<li class='fa fa-cny'></li>")
            }
        });
    });
</script>
