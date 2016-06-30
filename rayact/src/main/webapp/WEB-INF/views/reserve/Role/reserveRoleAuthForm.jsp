<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
	<jsp:param name="action" value="roleAuth"></jsp:param>
</jsp:include>
<div class="cl-mcont" id="pcont">
	<div class="row">
		<div class="col-md-12">
			<div class="block-flat">
				<div class="header">
					<h3>角色权限配置</h3>
				</div>
				<div class="content">
					<div class="tab-container">
						<div class="form-horizontal group-border-dashed">
							<form:form id="inputForm" modelAttribute="reserveRoleAuth" action="${ctx}/reserve/reserveRoleAuth/save"
									   method="post"
									   class="form-horizontal">
								<form:hidden path="id"/>
								<input type="hidden" name="token" value="${token}"/>
								<sys:message content="${message}"/>

								<table id="contentTable" class="table table-bordered">
									<tr>
										<td>角色：</td>
										<td>
											<div class="row">
												<div class="col-lg-9">
													<form:input id="name" path="name"
																maxlength="19"
																class="form-control"
													/>
												</div>
											</div>
										</td>
									</tr>
								</table>
										<%--<td>对应用户权限(json字符串)：</td>
										<td>
											<div class="row">
												<div class="col-lg-9">
													<form:input path="authority" htmlEscape="false" maxlength="30"
																class="form-control required"/>
												</div>
												<div class="col-lg-3">
													<span class="help-inline pull-right"><font color="red">*</font> </span>
												</div>
											</div>
										</td>--%>
										<div class="form-group">
											<label class="col-sm-2 control-label">权限:</label>

											<div class="col-sm-10">

												<c:forEach items="${authList}" var="auth" varStatus="astatus">
													<%--遍历所有权限--%>
													<div class="row">
														<div class="col-sm-6 col-md-8 col-lg-10 cl-mcont">
															<div class="block-flat">
																	<%--权限组--%>
																<div class="header">
																	<c:set value="" var="checked"></c:set>
																	<c:forEach items="${userRole.authorityList}" var="ur">
																		<%--遍历用户的已有权限--%>
																		<c:if test="${ur.code eq auth.code}">
																			<c:set value="checked='checked'" var="checked"></c:set>
																		</c:if>
																		<%--遍历用户的已有权限 end--%>
																	</c:forEach>
																	<label>
																		<input type="checkbox" ${checked}
																			   name="reserveRole.authorityList[${astatus.index}].code"
																			   class="icheck authCheck"
																			   value="${auth.code}"/>
																			${auth.name}
																	</label>
																</div>
																	<%--权限组结束--%>

																<c:forEach items="${auth.authorityList}" var="a" varStatus="s">
																	<%-- 权限组的子权限--%>
																	<div class="radio col-lg-4">
																		<c:set value="" var="childchecked"></c:set>
																		<c:forEach items="${userRole.authorityList}" var="ur">
																			<%--遍历用户的已有权限--%>
																			<c:if test="${ur.code eq auth.code}">
																				<c:forEach items="${ur.authorityList}" var="child">
																					<c:if test="${a.code eq child.code}">
																						<c:set value="checked='checked'"
																							   var="childchecked"></c:set>
																					</c:if>
																				</c:forEach>
																			</c:if>
																			<%--遍历用户的已有权限 end--%>
																		</c:forEach>

																		<label> <input data-parent="${auth.code}"
																					   type="checkbox" ${childchecked} value="${a.code}"
																					   name="reserveRole.authorityList[${astatus.index}].authorityList[${s.index}].code"
																					   class="icheck childAuthCheck"> ${a.name}
																			<input
																					type="hidden"
																					name="reserveRole.authorityList[${astatus.index}].authorityList[${s.index}].code"
																			>
																		</label>
																	</div>
																	<%-- 子权限 end--%>
																</c:forEach>
															</div>
														</div>
													</div>
													<%--遍历所有权限 end--%>
												</c:forEach>
											</div>
										</div>


								<div>
									<input id="btnSubmit"
										   class="btn btn-primary"
										   type="submit"
										   value="保 存"/>&nbsp;
									<input id="btnCancel" class="btn" type="button" value="返 回"
										   onclick="history.go(-1)"/>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/validate.js"></script>
</body>
</html>