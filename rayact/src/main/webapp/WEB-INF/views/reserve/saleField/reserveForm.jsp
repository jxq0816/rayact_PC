<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form id="reserveFormBean" class="form-horizontal">
    <input type="hidden" name="token" value="${token}"/>
    <input type="hidden" name="project.id" value="${reserveField.reserveProject.id}"/>
    <input type="hidden" name="halfCourt" value="${isHalfCourt}"/>

    <div class="content text-justify" style="text-align: center;vertical-align: middle;">
        <div class="row">
            <div class="col-lg-6  reserve_mid_line">
                <div class="row">
                    <div class="form-group">
                        <label for="reserveFieldName" class="col-sm-2 control-label"> 场地:</label>
                        <div class="col-lg-10">
                            <input readonly="readonly" id="reserveFieldName" class="form-control"
                                   value="${reserveField.name}"/>
                            <input type="hidden" id="consDate" name="consDate"
                                   value="${consDate}"/>
                            <input type="hidden" name="projectId" value="${reserveField.reserveProject.id}"/>
                            <input type="hidden" name="reserveVenue.id" value="${venueId}"/>
                            <input type="hidden" id="fieldId" name="venueConsList[0].reserveField.id"
                                   value="${reserveField.id}"/>
                            <input type="hidden" name="venueConsList[0].reserveVenue.id"
                                   value="${reserveField.reserveVenue.id}"/>
                            <input type="hidden" name="venueConsList[0].consPrice" value="${reserveField.actualPrice}"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label for="startTime" class="col-sm-2 control-label">时间:</label>
                        <div class="col-sm-4">
                            <select id="startTime" class="form-control input-sm" name="venueConsList[0].startTime">
                                <c:forEach items="${times}" var="t">
                                    <option
                                            <j:if test="${t eq startTime}">selected="selected"</j:if>
                                            value="${t}">${t}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <label for="endTime" class="col-lg-2 control-label" style="text-align: center">至</label>
                        <div class="col-sm-4">
                            <select id="endTime" class="form-control input-sm"  name="venueConsList[0].endTime">
                                <c:forEach items="${times}" var="t">
                                    <option
                                            <j:if test="${t eq endTime}">selected="selected"</j:if>
                                            value="${t}">${t}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label for="frequency" class="col-sm-2 control-label">频率:</label>
                        <div class="col-sm-4">
                            <select class=" form-control input-sm" id="frequency" name="frequency" class="select2">
                                <option value="1">单次</option>
                                <option value="2">每天</option>
                                <option value="3">每周</option>
                            </select>
                        </div>
                        <div class="col-lg-2">
                            <label for="isHalfCourt" class="control-label">类型:</label>
                        </div>
                        <div class="col-lg-4">
                            <j:if test="${'1' eq isHalfCourt}">
                                <input readonly="readonly" id="isHalfCourt" class="form-control"
                                       value="半场"/>
                            </j:if>
                            <j:if test="${'0' eq isHalfCourt}">
                                <input readonly="readonly" id="isHalfCourt" class="form-control"
                                       value="全场"/>
                            </j:if>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label for="tutorId" class="col-sm-2 control-label">教练:</label>
                        <div class="col-sm-4">
                            <select id="tutorId" name="tutor.id" class="form-control input-sm">
                                <option value="">请选择</option>
                                <c:forEach items="${tutors}" var="t">
                                    <option data-price="${t.price}" value="${t.id}" onclick="">${t.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-lg-2">
                            <label for="tutor_price" class="control-label">价格:</label>
                        </div>
                        <div class="col-lg-4">
                            <input readonly="readonly" id="tutor_price" class="form-control"
                                   value="0元/小时"/>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="row" id="date_div" style="display: none">
                    <div class="form-group">
                        <label for="startDate" class="col-sm-2 control-label">开始:</label>
                        <div class="col-sm-4">
                            <input name="startDate" value="${consDate}"
                                   type="text" id="startDate"
                                   maxlength="20"
                                   class="input-medium form-control Wdate "
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
                            />
                        </div>
                        <div class="col-lg-2">
                            <label for="endDate" class="control-label">结束:</label>
                        </div>
                        <div class="col-lg-4">
                            <input name="endDate" value="${consDate}"
                                   type="text" id="endDate"
                                   maxlength="20"
                                   class="input-medium form-control Wdate "
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
                            />
                            </span>
                        </div>
                    </div>
                </div>

            </div>

            <div class="col-lg-6">
                <div class="row">
                    <div class="form-group">
                        <label for="isMember" class="control-label col-lg-3">顾客标准:</label>
                        <div class="col-lg-3">
                            <input type="radio" id="isMember" class="icheck" value="2" checked="checked"
                                   name="consType"/>会员价
                        </div>
                        <div class="col-lg-3">
                            <input type="radio" id="nMember" class="icheck" value="1" name="consType"/>门市价
                        </div>
                    </div>
                </div>
                <div class="row" id="memberSelect">
                    <div class="form-group">
                        <label for="memberId" class="control-label col-lg-3">会员姓名:</label>
                        <div class="col-sm-6">
                            <select style="width: 100%;" id="memberId" class="select2" name="member.id">
                                <option value="">--请选择--</option>
                                <c:forEach items="${memberList}" var="m">
                                    <option value="${m.id}">${m.cardNo}-${m.name}-${m.mobile}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label for="userName" class="control-label col-lg-3">姓名:</label>
                        <div class="col-lg-6"><input id="userName" name="userName"
                                                     type="text" readonly="readonly"
                                                     class="form-control input-sm"/></div>
                    </div>
                </div>
                <div class="row" id="deposit" style="display: none">
                    <div class="form-group">
                        <label for="consPrice" class="control-label col-lg-3">押金:</label>
                        <div class="col-lg-6">
                            <input type="text" id="consPrice" name="consPrice"
                                   class="form-control"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label for="consMobile" class="control-label col-lg-3">手机:</label>
                        <div class="col-lg-6"><input id="consMobile" name="consMobile"
                                                     type="text" readonly="readonly"
                                                     class="form-control"/></div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label for="remarks" class="control-label col-sm-3">备注:</label>
                        <div class="col-sm-9">
                            <textarea id="remarks" name="remarks" class="form-control"></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<script src="${ctxStatic}/cleanzone/js/jquery.select2/select2.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/modules/reserve/js/reserve_field_form.js" type="text/javascript"></script>

