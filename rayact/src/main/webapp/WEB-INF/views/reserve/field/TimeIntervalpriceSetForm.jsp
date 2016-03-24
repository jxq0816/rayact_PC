<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场地管理</title>
    <meta name="decorator" content="main"/>
    <%@include file="/WEB-INF/views/include/upload.jsp" %>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="field"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12" style="padding-right: 0px">
            <div class="block-flat">
                <div class="header">
                    <h3>场地管理</h3>
                </div>
                <div class="content">
                    <form:form id="inputForm" modelAttribute="reserveField"
                               action="${ctx}/reserve/reserveField/savePrice"
                               method="post"
                               class="form-horizontal">
                        <form:hidden path="id"/>
                        <input type="hidden" name="token" value="${token}"/>
                        <input type="hidden" name="reserveTimeInterval.id" value="${reserveTimeInterval.id}"/>

                        <div class="tab-container">
                            <ul class="nav nav-tabs" id="myTab">
                                <c:forEach items="${reserveTimeIntervalList}" var="t">
                                    <c:choose>
                                        <c:when test="${reserveTimeInterval.id eq t.id}">
                                            <c:set var="timeClass" value="active"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="timeClass" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <li class="${timeClass}"><a href="${ctx}/reserve/reserveField/priceSetForm?id=${reserveField.id}&timeIntervalId=${t.id}">${t.name}</a></li>
                                </c:forEach>
                            </ul>

                            <div class="tab-content">
                                <!--常规价格设置-->
                                <div class="tab-pane active" id="profile">
                                    <h5>${reserveField.reserveVenue.name} / ${reserveField.name}</h5>
                                    <table class="table table-bordered">
                                        <tr>
                                            <td><span id="weekTd" style="color: red">周一至周日</span></td>
                                            <td>时间段:</td>
                                            <td>
                                                <select id="startTime">
                                                    <c:forEach items="${times}" var="t">
                                                        <option value="${t}">${t}</option>
                                                    </c:forEach>
                                                </select>
                                                至
                                                <select id="endTime">
                                                    <c:forEach items="${times}" var="t">
                                                        <option value="${t}">${t}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                          <%-- <td>时令</td>
                                            <td>
                                                <sys:select cssClass="input-medium " name=""
                                                            cssStyle="readonly:readonly"
                                                            value="${reserveTimeInterval.id}"
                                                            items="${reserveTimeIntervalList}" itemLabel="name"
                                                            itemValue="id"
                                                ></sys:select>
                                            </td>--%>
                                            <td>市场价:</td>
                                            <td>
                                                <input type="text" id="retail" class="number form-control"
                                                       style="width: 40px;height:30px;padding: 0px"/>
                                            </td>
                                            <td>会员价:</td>
                                            <td>
                                                <input type="text" id="member" class="number form-control"
                                                       style="width: 40px;height:30px;padding:0px"/>
                                            </td>
                                            <td valign="top"><input id="globalPrice" data="all" class="btn btn-primary"
                                                                    type="button"
                                                                    value="价格设定"/></td>
                                        </tr>
                                    </table>
                                    <table class="table table-bordered">
                                        <tr>
                                            <td colspan="2"><a style="color: red"
                                                               title="点击,设计全局数值" href="#" data="all"
                                                               class="weekPriceTable">时间</a></td>
                                            <c:forEach items="${times}" var="t">
                                                <th><span>${t}</span></th>
                                            </c:forEach>
                                        </tr>
                                        <!--周一至周五-->
                                        <c:forEach items="${priceSetList}" var="priceSet" varStatus="status">
                                            <tr>
                                                <j:if test="${status.index==0||status.index==2||status.index==4}">
                                                    <td rowspan="2" valign="top"><a data="${priceSet.week}"
                                                                                    style="color: red"
                                                                                    style="width: 25px;"
                                                                                    title="点击,设计全局数值" href="#"
                                                                                    class="weekPriceTable">${priceSet.weekName}</a>
                                                    </td>
                                                </j:if>
                                                <td valign="top">${priceSet.consTypeName}</td>
                                                <input type="hidden" name="fieldPriceSetList[${status.index}].id"
                                                       value="${priceSet.id}"/>
                                                <input type="hidden" name="fieldPriceSetList[${status.index}].week"
                                                       value="${priceSet.week}"/>
                                                <input type="hidden" name="fieldPriceSetList[${status.index}].consType"
                                                       value="${priceSet.consType}"/>
                                                <c:forEach items="${priceSet.timePriceList}" var="t"
                                                           varStatus="priceSetStatus">
                                                    <td>
                                                        <input type="hidden"
                                                               name="fieldPriceSetList[${status.index}].timePriceList[${priceSetStatus.index}].time"
                                                               value="${t.time}"/>
                                                        <input value="<fmt:formatNumber value='${t.price}' pattern='0'/>"
                                                               type="text" data-time="${t.time}"
                                                               data="${priceSet.week}-${priceSet.consType}"
                                                               name="fieldPriceSetList[${status.index}].timePriceList[${priceSetStatus.index}].price"
                                                               class="number form-control"
                                                               style="width: 40px;height:30px"/>
                                                    </td>
                                                </c:forEach>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="form-actions">
                            <input id="btnSubmit"
                                   class="btn btn-primary"
                                   type="submit"
                                   value="保 存"/>&nbsp;
                            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function checkEndTime(startTime, endTime, localTime) {
        if (localTime >= startTime && localTime <= endTime) {
            return true;
        }
        return false;
    }

    $(document).ready(function () {
        $('.icheck').iCheck({
            checkboxClass: 'icheckbox_square-blue checkbox',
            radioClass: 'iradio_square-blue'
        });
        jQuery.addWorkPrice = function (type, retail, member, group) {
            var startTime = $("#startTime").val();
            var endTime = $("#endTime").val();
            $.each($("input[data='" + type + "-1']"), function () {
                var t = $(this);
                var localTime = t.attr("data-time");
                console.log("------startTime-" + startTime + "---endTime===" + endTime);
                if (checkEndTime(startTime, endTime, localTime)) {
                    console.log("-------" + retail);
                    t.val(retail);
                }
            });
            $.each($("input[data='" + type + "-2']"), function () {
                var t = $(this);
                var localTime = t.attr("data-time");
                if (checkEndTime(startTime, endTime, localTime)) {
                    t.val(member);
                }
            });
            $.each($("input[data='" + type + "-3']"), function () {
                var t = $(this);
                var localTime = t.attr("data-time");
                if (checkEndTime(startTime, endTime, localTime)) {
                    t.val(group);
                }
            });
        };


        $(".weekPriceTable").on('click', function () {
            var t = $(this);
            $("#globalPrice").attr("data", t.attr("data"));
            $("#weekTd").text(t.text());
        });

        //常规价格设定
        $("#globalPrice").on('click', function () {
            //retail member group
            var startTime = $("#startTime").val();
            var endTime = $("#endTime").val();
            if (startTime > endTime) {
                alert('开始时间不能大于结束时间');
            }
            var type = $(this).attr("data");
            var weekTd = $("#weekTd").text();
            var retail = $("#retail").val();
            var member = $("#member").val();
            var group = $('#group').val();
            if (weekTd == '周一至周五') {
                $.addWorkPrice(type, retail, member, group);
            } else if (weekTd == '周六') {
                $.addWorkPrice(type, retail, member, group);
            } else if (weekTd == '周日') {
                $.addWorkPrice(type, retail, member, group);
            } else {
                $.addWorkPrice("1", retail, member, group);
                $.addWorkPrice("2", retail, member, group);
                $.addWorkPrice("3", retail, member, group);
            }
        });
    });
</script>
</body>
</html>
