<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form id="applyCutFormBean" class="form-horizontal">
    <input type="hidden" name="id" value="${applyCut.id}"/>
    <div class="content text-justify" style="text-align: center;vertical-align: middle;">
        <div class="row">
            <div class="col-lg-12  reserve_mid_line">
                <div class="form-group">
                    <label for="consume" class="control-label col-lg-3">预定人:</label>
                    <div class="col-lg-9">
                        <input id="consume" class="form-control" value="${cons.userName}" readonly="readonly"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12  reserve_mid_line">
                <div class="form-group">
                    <label for="phone" class="control-label col-lg-3">手机:</label>
                    <div class="col-lg-9">
                        <input id="phone" class="form-control" value="${cons.consMobile}" readonly="readonly"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12  reserve_mid_line">
                <div class="form-group">
                    <label for="venue" class="control-label col-lg-3">场馆:</label>
                    <div class="col-lg-9">
                        <input id="venue" class="form-control" value="${cons.userName}" readonly="readonly"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12  reserve_mid_line">
                <div class="form-group">
                    <label for="constime" class="control-label col-lg-3">时间:</label>
                    <div class="col-lg-9">
                        <input id="constime" class="form-control" value="${cons.consDate}" readonly="readonly"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12  reserve_mid_line">
                <div class="form-group">
                    <label for="consprice" class="control-label col-lg-3">价格:</label>
                    <div class="col-lg-9">
                        <input id="consprice" class="form-control" value="${cons.orderPrice}" readonly="readonly"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12  reserve_mid_line">
                <div class="form-group">
                    <label for="applyer"
                           class="col-lg-3 control-label">通知人:</label>
                    <div class="col-lg-9">
                        <input id="applyer" class="form-control" value="${applyCut.applyer.name}" readonly="readonly"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12  reserve_mid_line">
                <div class="form-group">
                    <label for="remarks" class="col-lg-3 control-label">备注:</label>
                    <div class="col-lg-9">
                        <textarea id="remarks" name="remarks" class="form-control" readonly="readonly" >${applyCut.remarks}</textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
    </div>
    </div>
</form>