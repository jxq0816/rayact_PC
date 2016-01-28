<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场地管理</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/upload.jsp" %>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/modules/reserve/css/field.css?id=36"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>
    <style>
        .modal {
            width: 100%;
            position: fixed;
            text-align: center;
            margin: 0px auto;
            top: 0px;
            left: 0px;
            bottom: 150px;
            right: 0px;
            z-index: 1050;
        }

        .modal_wrapper {
            display: table;
            overflow: auto;
            overflow-y: scroll;
            height: 100%;
            -webkit-overflow-scrolling: touch;
            outline: 0;
            text-align: center;
            margin: 0px auto;
        }

        .modal-dialog {
            margin-top: 0px;
            display: table-cell;
            vertical-align: middle;
            margin: 0px 20px;
        }
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/reserve/reserveField/">场地列表</a></li>
    <li class="active"><a href="${ctx}/reserve/reserveField/form?id=${reserveField.id}">场地<shiro:hasPermission
            name="reserve:reserveField:edit">${not empty reserveField.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="reserve:reserveField:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="reserveField" action="${ctx}/reserve/reserveField/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <ul class="nav nav-tabs" id="myTab">
        <li class="active"><a href="#home">基本信息</a></li>
        <li><a href="#profile">常规价格设定</a></li>
        <li><a href="#datePrice">按日期价格设定</a></li>
    </ul>

    <div class="tab-content">
        <div class="tab-pane active" id="home">
            <table id="contentTable" class="table table-bordered">
                <tr>
                    <td>场地名称：</td>
                    <td><form:input path="name" htmlEscape="false" maxlength="30" class="input-xlarge "/></td>
                    <td>所属场馆：</td>
                    <td><sys:select cssClass="input-medium" name="reserveVenue.id"
                                    value="${reserveField.reserveVenue.id}"
                                    items="${venues}" itemLabel="name" itemValue="id" defaultLabel="请选择"
                                    defaultValue=""></sys:select></td>
                </tr>
                <tr>
                    <td>是否启用：</td>
                    <td>
                        <form:radiobuttons path="available" items="${fns:getDictList('yes_no')}" itemLabel="label"
                                           itemValue="value"
                                           htmlEscape="false" class=""/>
                    </td>
                    <td>所属项目：</td>
                    <td>
                        <sys:select cssClass="input-medium" name="reserveProject.id"
                                    value="${reserveField.reserveProject.id}"
                                    items="${projects}" itemLabel="name" itemValue="id" defaultLabel="请选择"
                                    defaultValue=""></sys:select>
                    </td>
                </tr>
                <tr>
                    <td>备注:</td>
                    <td colspan="3">
                        <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255"
                                       class="input-xxlarge "/>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">场地图片:</td>
                </tr>
                <tr>
                    <td></td>
                    <td colspan="3">
                        <mechanism:upload id="financeSchoolPic" fdKey="fieldPic" name="attMains1" exts=".jpg"
                                          btnText="添加"
                                          modelId="${reserveField.id}"
                                          showImg="true" resizeImg="true" resizeWidth="454" resizeHeight="247"
                                          imgWidth="120" imgHeight="80"
                                          modelName="reserveField" multi="true"/>
                    </td>
                </tr>
            </table>
        </div>
        <!--常规价格设置-->
        <div class="tab-pane" id="profile">
            <table class="table table-bordered">
                <tr>
                    <td><span id="weekTd" style="color: red">周一至周日</span></td>
                    <td>  时间段:</td>
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
                    <td>散客:</td>
                    <td>
                        <input type="text" id="retail" class="number" style="width: 25px;"/>
                    </td>
                    <td>会员:</td>
                    <td>
                        <input type="text" id="member" class="number" style="width: 25px;"/>
                    </td>
                    <td>团体:</td>
                    <td><input type="text" id="group" class="number" style="width: 25px;"/></td>
                    <td valign="top"><input id="globalPrice" data="all" class="btn btn-primary" type="button"
                                            value="价格设定"/></td>
                </tr>
            </table>
            <table class="table table-bordered">
                <tbody>
                <tr>
                    <td><a style="color: red" style="width: 25px;"
                           title="点击,设计全局数值" href="#" data="all" class="weekPriceTable">周一至周日</a></td>
                    <td></td>
                    <c:forEach items="${times}" var="t">
                        <th><span>${t}</span></th>
                    </c:forEach>
                </tr>

                <!--周一至周五-->

                <c:forEach items="${priceSetList}" var="priceSet" varStatus="status">
                    <tr>
                        <j:if test="${status.index==0||status.index==3||status.index==6}">
                            <td rowspan="3" valign="top"><a data="${priceSet.week}" style="color: red"
                                                            style="width: 25px;"
                                                            title="点击,设计全局数值" href="#"
                                                            class="weekPriceTable">${priceSet.weekName}</a>
                            </td>
                        </j:if>
                        <td valign="top">${priceSet.consTypeName}</td>
                        <input type="hidden" name="fieldPriceSetList[${status.index}].id" value="${priceSet.id}"/>
                        <input type="hidden" name="fieldPriceSetList[${status.index}].week" value="${priceSet.week}"/>
                        <input type="hidden" name="fieldPriceSetList[${status.index}].consType"
                               value="${priceSet.consType}"/>
                        <c:forEach items="${priceSet.timePriceList}" var="t" varStatus="priceSetStatus">
                            <td>
                                <input type="hidden"
                                       name="fieldPriceSetList[${status.index}].timePriceList[${priceSetStatus.index}].time"
                                       value="${t.time}"/>
                                <input value="${t.price}" type="text" data-time="${t.time}"
                                       data="${priceSet.week}-${priceSet.consType}"
                                       name="fieldPriceSetList[${status.index}].timePriceList[${priceSetStatus.index}].price"
                                       class="number"
                                       style="width: 25px;"/>
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>

                </tbody>
            </table>

            <ul class="nav nav-list">
                <li class="divider"></li>
            </ul>
        </div>

        <!--节假日价格设置-->
        <div class="tab-pane" id="datePrice">

            <table class="table table-bordered">
                <tr>
                    <td>日期:</td>
                    <td>
                        <input name="startDate" id="startDate" type="text" readonly="readonly" maxlength="20"
                               style="width: 90px;"
                               class="input-medium Wdate "
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>至
                        <input name="endDate" id="endDate" type="text" readonly="readonly" maxlength="20"
                               style="width: 90px;"
                               class="input-medium Wdate "
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                    </td>
                    <td>时间段:</td>
                    <td>
                        <select id="dateStartTime">
                            <c:forEach items="${times}" var="t">
                                <option value="${t}">${t}</option>
                            </c:forEach>
                        </select>
                        至
                        <select id="dataEndTime">
                            <c:forEach items="${times}" var="t">
                                <option value="${t}">${t}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>散客:</td>
                    <td>
                        <input type="text" id="dataRetail" class="number" style="width: 25px;"/>
                    </td>
                    <td>会员:</td>
                    <td>
                        <input type="text" id="dataMember" class="number" style="width: 25px;"/>
                    </td>
                    <td>团体:</td>
                    <td><input type="text" id="dataGroup" class="number" style="width: 25px;"/></td>
                    <td valign="top"><input id="addGlobalPrice" data="all" class="btn btn-primary" type="button"
                                            value="添加"/></td>
                </tr>
            </table>
            <!---->
            <table class="table table-bordered">
                <thead>
                <th>日期</th>
                <th>时间</th>
                <th>散客</th>
                <th>会员</th>
                <th>团体</th>
                <th>操作</th>
                </thead>
                <tbody id="holidayPriceSet">
                <c:forEach items="${holidayPriceSetList}" var="holiday" varStatus="status">
                <tr><input type="hidden" name="fieldHolidayPriceSetList[${status.index}].startDate" value="${holiday.startDate}"/>
                    <input type="hidden" name="fieldHolidayPriceSetList[${status.index}].endDate" value="${holiday.endDate}"/>
                    <input type="hidden" name="fieldHolidayPriceSetList[${status.index}].fieldStartTime" value="${holiday.fieldStartTime}"/>
                    <input type="hidden" name="fieldHolidayPriceSetList[${status.index}].fieldEndTime" value="${holiday.fieldEndTime}"/>
                    <c:forEach items="${holiday.userTypePriceList}" var="userTypePrice" varStatus="typeStatus">
                        <input type="hidden" name="fieldHolidayPriceSetList[${status.index}].userTypePriceList[${typeStatus.index}].userType" value="${userTypePrice.userType}"/>
                        <input type="hidden" name="fieldHolidayPriceSetList[${status.index}].userTypePriceList[${typeStatus.index}].price" value="${userTypePrice.price}"/>
                    </c:forEach>
                    <td>${holiday.startDate}至${holiday.endDate}</td><td>${holiday.fieldStartTime}至${holiday.fieldEndTime}</td>
                    <c:forEach items="${holiday.userTypePriceList}" var="userTypePrice" varStatus="typeStatus">
                        <td>${userTypePrice.price}</td>
                    </c:forEach>
                    <td><a href="#" onclick="deleteHolidayRow(this)">删除</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div class="form-actions">
        <shiro:hasPermission name="reserve:reserveField:edit"><input id="btnSubmit" class="btn btn-primary"
                                                                     type="submit"
                                                                     value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
<script type="text/javascript">
    function checkEndTime(startTime, endTime, localTime) {
        //var start = new Date(startTime.replace(":", "/").replace(":", "/"));
        //var end = new Date(endTime.replace(":", "/").replace(":", "/"));
        //var local = new Date(localTime.replace(":", "/").replace(":", "/"));
        if (localTime >= startTime && localTime <= endTime) {
            return true;
        }
        return false;
    }

    function deleteHolidayRow(t) {
        $(t).parents("tr").remove();
    }

    alert(checkEndTime('09:00', '10:00', '09:00'));

    $(document).ready(function () {
        jQuery.addWorkPrice = function (type, retail, member, group) {
            var startTime = $("#startTime").attr("value");
            var endTime = $("#endTime").attr("value");
            $.each($("input[data='" + type + "-1']"), function () {
                var t = $(this);
                var localTime = t.attr("data-time");
                if (checkEndTime(startTime, endTime, localTime)) {
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
        var index = 0;
        //按日期设定价格
        $("#addGlobalPrice").on('click', function () {
            var length = $('#holidayPriceSet tr').length;
            if (length > 0) {
                index = length;
            }
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            var dateStartTime = $("#dateStartTime").attr("value");
            var dataEndTime = $("#dataEndTime").attr("value");
            var dataRetail = $("#dataRetail").val();//散客
            var dataMember = $("#dataMember").val();//会员
            var dataGroup = $("#dataGroup").val();//团体
            if (startDate > endDate) {
                alert('起始日期不能大于结束日期');
                return;
            }
            if (dataEndTime > dataEndTime) {
                alert('起始时间不能大于结束时间');
                return;
            }
            //<th>日期</th><th>时间</th><th>散客</th><th>会员</th><th>团体</th>
            //holidayPriceSet
            var tr = '<tr><input type="hidden" name="fieldHolidayPriceSetList['+index+'].startDate" value="'+startDate+'"/>' +
                    '<input type="hidden" name="fieldHolidayPriceSetList['+index+'].endDate" value="'+endDate+'"/>' +
                    '<input type="hidden" name="fieldHolidayPriceSetList['+index+'].fieldStartTime" value="'+dateStartTime+'"/>' +
                    '<input type="hidden" name="fieldHolidayPriceSetList['+index+'].fieldEndTime" value="'+dataEndTime+'"/>' +
                    '<input type="hidden" name="fieldHolidayPriceSetList['+index+'].userTypePriceList[0].userType" value="1"/>' +
                    '<input type="hidden" name="fieldHolidayPriceSetList['+index+'].userTypePriceList[0].price" value="'+dataRetail+'"/>' +
                    '<input type="hidden" name="fieldHolidayPriceSetList['+index+'].userTypePriceList[1].userType" value="2"/>' +
                    '<input type="hidden" name="fieldHolidayPriceSetList['+index+'].userTypePriceList[1].price" value="'+dataMember+'"/>' +
                    '<input type="hidden" name="fieldHolidayPriceSetList['+index+'].userTypePriceList[2].userType" value="3"/>' +
                    '<input type="hidden" name="fieldHolidayPriceSetList['+index+'].userTypePriceList[2].price" value="'+dataGroup+'"/>' +
                    '<td>' + startDate + '至' + endDate + '</td><td>' + dateStartTime + '至' + dataEndTime + '</td>' +
                    '<td>' + dataRetail + '</td><td>' + dataMember + '</td><td>' + dataGroup + '</td><td><a href="#" onclick="deleteHolidayRow(this)">删除</a></td>' +
                    '</tr>';
            $("#holidayPriceSet").prepend(tr);
        });

        $('#myTab a:first').tab('show');//初始化显示哪个tab

        $('#myTab a').click(function (e) {
            e.preventDefault();//阻止a链接的跳转行为
            $(this).tab('show');//显示当前选中的链接及关联的content
        });

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