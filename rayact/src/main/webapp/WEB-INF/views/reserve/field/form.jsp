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
                    <form:form id="inputForm" modelAttribute="reserveField" action="${ctx}/reserve/reserveField/save"
                               method="post"
                               class="form-horizontal">
                    <form:hidden path="id"/>
                        <input type="hidden" name="token" value="${token}"/>

                    <div class="tab-container">
                        <ul class="nav nav-tabs" id="myTab">
                            <li class="active"><a href="#home" data-toggle="tab">基本信息</a></li>
                            <li><a href="#profile">常规价格设定</a></li>
                            <li><a href="#datePrice">按日期价格设定</a></li>
                        </ul>

                        <div class="tab-content">
                            <div class="tab-pane active" id="home">
                                <div class="form-horizontal group-border-dashed">
                                    <div class="form-group">
                                        <label for="name" class="col-sm-3 control-label">场地名称</label>

                                        <div class="col-sm-6">
                                            <form:input path="name" htmlEscape="false" maxlength="30"
                                                        class="form-control"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">所属场馆</label>
                                        <div class="col-sm-6">
                                            <sys:select cssClass="input-medium" name="reserveVenue.id"
                                                        value="${reserveField.reserveVenue.id}"
                                                        items="${venues}" itemLabel="name" itemValue="id"
                                                        defaultLabel="----请选择-----"
                                                        defaultValue=""></sys:select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">是否启用</label>
                                        <div class="col-sm-6">
                                            <form:radiobuttons path="available" items="${fns:getDictList('yes_no')}"
                                                               itemLabel="label"
                                                               itemValue="value"
                                                               htmlEscape="false" class="icheck"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">所属项目</label>
                                        <div class="col-sm-6">
                                            <sys:select cssClass="input-medium" name="reserveProject.id"
                                                        value="${reserveField.reserveProject.id}"
                                                        items="${projects}" itemLabel="name" itemValue="id"
                                                        defaultLabel="请选择"
                                                        defaultValue=""></sys:select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">父场地</label>
                                        <div class="col-sm-6">
                                            <sys:select cssClass="input-medium" name="reserveParentField.id"
                                                        value="reserveField.reserveParentField.id"
                                                        items="${fields}" itemLabel="name" itemValue="id"
                                                        defaultLabel="如果该场地为半场,请选择所属全场"
                                                        defaultValue=""></sys:select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">备注</label>
                                        <div class="col-sm-6">
                                            <form:textarea path="remarks" htmlEscape="false" rows="4"
                                                           maxlength="255" class="form-control"/>
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">场地图片</label>
                                        <div class="col-sm-3">
                                            <mechanism:upload id="financeSchoolPic" fdKey="fieldPic"
                                                              name="attMains1" exts=""
                                                              btnText="添加"
                                                              modelId="${reserveField.id}"
                                                              showImg="true" resizeImg="true" resizeWidth="454"
                                                              resizeHeight="247"
                                                              imgWidth="120" imgHeight="80"
                                                              modelName="reserveField" multi="true"/>
                                        </div>
                                    </div>
                                </div>
                        </div>
                        <!--常规价格设置-->
                        <div class="tab-pane" id="profile">
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
                                    <td>市场价:</td>
                                    <td>
                                        <input type="text" id="retail" class="number form-control" style="width: 40px;height:30px"/>
                                    </td>
                                    <td>会员价:</td>
                                    <td>
                                        <input type="text" id="member" class="number form-control" style="width: 40px;height:30px"/>
                                    </td>
                                    <td>团体:</td>
                                    <td><input type="text" id="group" class="number form-control" style="width: 30px;height:30px"/></td>
                                    <td valign="top"><input id="globalPrice" data="all" class="btn btn-primary"
                                                            type="button"
                                                            value="价格设定"/></td>
                                </tr>
                            </table>
                            <table class="table table-bordered">
                                <tr>
                                    <td colspan="2"><a style="color: red"
                                           title="点击,设计全局数值" href="#" data="all" class="weekPriceTable">时间</a></td>
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
                                        <input type="hidden" name="fieldPriceSetList[${status.index}].id"
                                               value="${priceSet.id}"/>
                                        <input type="hidden" name="fieldPriceSetList[${status.index}].week"
                                               value="${priceSet.week}"/>
                                        <input type="hidden" name="fieldPriceSetList[${status.index}].consType"
                                               value="${priceSet.consType}"/>
                                        <c:forEach items="${priceSet.timePriceList}" var="t" varStatus="priceSetStatus">
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

                        <div class="tab-pane" id="datePrice">
                            <table class="table table-bordered">
                                <tr>
                                    <td>日期：</td>
                                    <td colspan="8">
                                        <div>
                                            <input name="startDate" id="startDate" type="text" readonly="readonly"
                                                   maxlength="20"
                                                   style="width: 90px;"
                                                   class="input-medium Wdate "
                                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>至
                                            <input name="endDate" id="endDate" type="text" readonly="readonly"
                                                   maxlength="20"
                                                   style="width: 90px;"
                                                   class="input-medium Wdate "
                                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>时间段：</td>
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
                                        <input type="text" id="dataRetail" class="form-control number" style="width: 30px;height: 30px;"/>
                                    </td>
                                    <td>会员:</td>
                                    <td>
                                        <input type="text" id="dataMember" class="form-control number" style="width: 30px;height: 30px;"/>
                                    </td>
                                    <td>团体:</td>
                                    <td><input type="text" id="dataGroup" class="form-control number" style="width: 30px;height: 30px;"/></td>
                                    <td valign="top"><input id="addGlobalPrice" data="all" class="btn btn-primary"
                                                            type="button"
                                                            value="添加"/></td>
                                </tr>
                            </table>

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
                                    <tr><input type="hidden" name="fieldHolidayPriceSetList[${status.index}].startDate"
                                               value="${holiday.startDate}"/>
                                        <input type="hidden" name="fieldHolidayPriceSetList[${status.index}].endDate"
                                               value="${holiday.endDate}"/>
                                        <input type="hidden"
                                               name="fieldHolidayPriceSetList[${status.index}].fieldStartTime"
                                               value="${holiday.fieldStartTime}"/>
                                        <input type="hidden"
                                               name="fieldHolidayPriceSetList[${status.index}].fieldEndTime"
                                               value="${holiday.fieldEndTime}"/>
                                        <c:forEach items="${holiday.userTypePriceList}" var="userTypePrice"
                                                   varStatus="typeStatus">
                                            <input type="hidden"
                                                   name="fieldHolidayPriceSetList[${status.index}].userTypePriceList[${typeStatus.index}].userType"
                                                   value="${userTypePrice.userType}"/>
                                            <input type="hidden"
                                                   name="fieldHolidayPriceSetList[${status.index}].userTypePriceList[${typeStatus.index}].price"
                                                   value="${userTypePrice.price}"/>
                                        </c:forEach>
                                        <td>${holiday.startDate}至${holiday.endDate}</td>
                                        <td>${holiday.fieldStartTime}至${holiday.fieldEndTime}</td>
                                        <c:forEach items="${holiday.userTypePriceList}" var="userTypePrice"
                                                   varStatus="typeStatus">
                                            <td>${userTypePrice.price}</td>
                                        </c:forEach>
                                        <td><a href="#" onclick="deleteHolidayRow(this)">删除</a></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
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
        if (localTime >= startTime && localTime <= endTime) {
            return true;
        }
        return false;
    }

    function deleteHolidayRow(t) {
        $(t).parents("tr").remove();
    }

    //alert(checkEndTime('09:00', '10:00', '09:00'));

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
                console.log("------startTime-"+startTime+"---endTime==="+endTime);
                if (checkEndTime(startTime, endTime, localTime)) {
                    console.log("-------"+retail);
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
            var dateStartTime = $("#dateStartTime").val();
            var dataEndTime = $("#dataEndTime").val();
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
            var tr = '<tr><input type="hidden" name="fieldHolidayPriceSetList[' + index + '].startDate" value="' + startDate + '"/>' +
                    '<input type="hidden" name="fieldHolidayPriceSetList[' + index + '].endDate" value="' + endDate + '"/>' +
                    '<input type="hidden" name="fieldHolidayPriceSetList[' + index + '].fieldStartTime" value="' + dateStartTime + '"/>' +
                    '<input type="hidden" name="fieldHolidayPriceSetList[' + index + '].fieldEndTime" value="' + dataEndTime + '"/>' +
                    '<input type="hidden" name="fieldHolidayPriceSetList[' + index + '].userTypePriceList[0].userType" value="1"/>' +
                    '<input type="hidden" name="fieldHolidayPriceSetList[' + index + '].userTypePriceList[0].price" value="' + dataRetail + '"/>' +
                    '<input type="hidden" name="fieldHolidayPriceSetList[' + index + '].userTypePriceList[1].userType" value="2"/>' +
                    '<input type="hidden" name="fieldHolidayPriceSetList[' + index + '].userTypePriceList[1].price" value="' + dataMember + '"/>' +
                    '<input type="hidden" name="fieldHolidayPriceSetList[' + index + '].userTypePriceList[2].userType" value="3"/>' +
                    '<input type="hidden" name="fieldHolidayPriceSetList[' + index + '].userTypePriceList[2].price" value="' + dataGroup + '"/>' +
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
