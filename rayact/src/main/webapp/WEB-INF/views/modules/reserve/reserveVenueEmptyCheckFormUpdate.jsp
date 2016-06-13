<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form id="checkEmptyBean" class="form-horizontal">
	<input type="hidden" name="id" value="${check.id}"/>
	<input type="hidden" name="halfCourt" value="${isHalfCourt}"/>
	<div class="content text-justify" style="text-align: center;vertical-align: middle;">
		<div class="row">
			<div class="col-lg-12  reserve_mid_line">
				<div class="row">
					<div class="form-group">
						<label for="reserveFieldName" class="col-sm-2 control-label"> 场地:</label>
						<div class="col-lg-4">
							<input readonly="readonly" id="reserveFieldName" class="form-control"
								   value="${reserveField.name}"/>
							<input type="hidden" id="checkDate" name="checkDate"
								   value="${checkDate}"/>
							<input type="hidden" name="venue.id" value="${venueId}"/>
							<input type="hidden" id="fieldId" name="field.id"
								   value="${reserveField.id}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group">
						<label for="startTime" class="col-sm-2 control-label">时间:</label>
						<div class="col-sm-2">
							<select id="startTime" class="select2" name="startTime" >
								<c:forEach items="${times}" var="t">
									<option
											<j:if test="${t eq startTime}">selected="selected"</j:if>
											value="${t}">${t}</option>
								</c:forEach>
							</select>
						</div>
						<label for="endTime" class="col-lg-1 control-label" style="text-align: center">至</label>
						<div class="col-sm-2">
							<select id="endTime" class="select2"  name="endTime">
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
						<label for="normalStatus" class="col-sm-2 control-label">审核状态:</label>
						<div class="col-lg-2">
							<input type="radio" id="normalStatus" class="icheck" value="0"
								   <c:if test="${check.checkStatus == '0'}">checked="checked"</c:if>
								   name="checkStatus"/>正常
							<input type="radio" id="abnormalStatus" class="icheck" value="1"
								   <c:if test="${check.checkStatus == '1'}">checked="checked"</c:if>
								   name="checkStatus"/>异常
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group">
						<label for="remarks" class="col-sm-2 control-label">审核人:</label>
						<div class="col-sm-9">
							<input readonly="readonly" id="createby" class="form-control"
								   value="${check.createBy.name}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group">
						<label for="remarks" class="col-sm-2 control-label">备注:</label>
						<div class="col-sm-9">
							<textarea id="remarks" name="remarks" class="form-control">${check.remarks}</textarea>
						</div>
					</div>
				</div>
				</div>

			</div>
		</div>
	</div>
</form>

<script type="text/javascript">
	$(document).ready(function () {

	});
</script>
