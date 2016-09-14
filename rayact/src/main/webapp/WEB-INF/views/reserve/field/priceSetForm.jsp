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


                        <div class="tab-container">
                            <ul class="nav nav-tabs" id="myTab">
                                <li class="active"><a href="#profile">常规价格设定</a></li>
                            </ul>
                            <div class="tab-content">
                                <!--常规价格设置-->
                                <div class="tab-pane active" id="profile">
                                    <h5>${reserveField.reserveVenue.name} / ${reserveField.name}</h5>
                                    <table class="table table-bordered">
                                        <tr>
                                            <td><span id="weekTd" style="color: red">周一至周日</span></td>
                                            <td>市场价:</td>
                                            <td>
                                                <input type="text" id="retail" class="number form-control"
                                                       style="width: 40px;height:30px;padding: 0px"/>
                                            </td>
                                            <td>会员价:</td>
                                            <td>
                                                <input type="text" id="member" class="number form-control"
                                                       style="width: 40px;height:30px;padding: 0px"/>
                                            </td>
                                            <td><input id="globalPrice" data="all" class="btn btn-primary"
                                                                    type="button"
                                                                    value="价格设定"/></td>
                                        </tr>
                                    </table>
                                    <table class="table table-bordered" style="padding:1px">
                                        <tr>
                                            <td colspan="2"><a style="color: red"
                                                               title="点击,设计全局数值" href="#" data="all"
                                                               class="weekPriceTable">时间</a></td>
                                            <c:forEach items="${times}" var="t">
                                                <th><span style="font-size: 10px">${t}</span></th>
                                            </c:forEach>
                                        </tr>
                                            <%-- 这层遍历主要是为了获得某个周次的价格--%>
                                        <c:forEach items="${priceSetList}" var="priceSet" varStatus="status">
                                            <tr>
                                                    <%--第0-1行：周一到周五 第2-3行：周六 第4-5行：周日--%>
                                                <j:if test="${status.index==0||status.index==2||status.index==4}">
                                                    <td rowspan="2">
                                                        ${priceSet.weekName}
                                                    </td>
                                                </j:if>
                                                    <%--第0-1行：周一到周五 第2-3行：周六 第4-5行：周日--%>
                                                <td valign="top">${priceSet.consTypeName}</td>
                                                    <%--顾客标准--%>
                                                <input type="hidden" name="fieldPriceSetList[${status.index}].id"
                                                       value="${priceSet.id}"/><%--价格设置的id--%>
                                                <input type="hidden" name="fieldPriceSetList[${status.index}].week"
                                                       value="${priceSet.week}"/><%--周次--%>
                                                <input type="hidden"
                                                       name="fieldPriceSetList[${status.index}].consType"
                                                       value="${priceSet.consType}"/>
                                                    <%-- 这层遍历主要是为了获得时间的index 从而遍历出每个时间的价格--%>
                                                        <c:forEach items="${times}" var="timeThread">
                                                                <c:forEach items="${priceSet.timePriceList}" var="t"
                                                                           varStatus="priceSetStatus">
                                                                    <c:if test="${timeThread==t.time}">
                                                                        <td style="padding:5px">
                                                                            <input type="hidden"
                                                                                   name="fieldPriceSetList[${status.index}].timePriceList[${priceSetStatus.index}].time"
                                                                                   value="${t.time}"/>
                                                                            <input value="<fmt:formatNumber value='${t.price}' pattern='0.00'/>"
                                                                                   type="text" data-time="${t.time}"
                                                                                   data="${priceSet.week}-${priceSet.consType}"
                                                                                   name="fieldPriceSetList[${status.index}].timePriceList[${priceSetStatus.index}].price"
                                                                                   class="number form-control"/>
                                                                        </td>
                                                                    </c:if>
                                                            </c:forEach>
                                                        </c:forEach>
                                                    <%-- 这层遍历主要是为了获得时间的index 从而遍历出每个时间的价格--%>
                                            </tr>
                                        </c:forEach>
                                            <%-- 这层遍历主要是为了获得某个周次的价格--%>
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
        //var start = new Date(startTime.replace(":", "/").replace(":", "/"));
        //var end = new Date(endTime.replace(":", "/").replace(":", "/"));
        //var local = new Date(localTime.replace(":", "/").replace(":", "/"));
        /* if (localTime >= startTime && localTime <= endTime) {
         return true;
         }*/
        return true;
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
