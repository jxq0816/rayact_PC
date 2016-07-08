<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
    <title>场地预定</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
</head>
<style type="text/css">
    .table-chang{margin-left: 0px;overflow: hidden;float: left;border-collapse: separate;border-spacing:5px}
    .table-chang thead th{font-size: 12px;border:1px;background:transparent}
    .table-chang th{width: 60px;min-width:60px;max-width:60px;height: 30px;font-weight: normal;ext-align: center;color: #323232}
    .table-chang td{width: 60px;min-width:60px;max-width:60px;height: 30px;background: #FFF;text-align: center;color: #fff;font-size: 15px;padding:0px;border: 1px solid  #FFFFFF;}
    .table-chang td a{display: block;width: 100%;height: 100%;}
    .table-chang td.access{background: #FFF;color:#A62A04;border: 1px dashed #A62A04;}
    .table-chang td.unavailable{background: #F0F0F0;color:#C8C8C8}
    .table-chang td.unPayed{background: #F0860C;}
    .table-chang td div.unPayed{background: #F0860C;}
    .table-chang tbody tr td .time_cell {width:30px;height:30px;font-size:13px;color:#323232;border:0px;background: transparent;}
</style>
<body>
<input value="${phone}" id="phone" type="hidden"/>
<div id="reserveStatus" style="OVERFLOW-X: scroll;" align=center>
    <table class="table-chang">
        <thead id="fieldThead"  style="position:fixed;top:0px;margin-left:-5px;background-color: #ffffff">
        <%--场地--%>
        <tr>
            <th></th>
            <c:forEach items="${venueFieldPriceList}" var="field" varStatus="status">
                <th>${field.fieldName}</th>
            </c:forEach>
        </tr>
        <%--场地--%>
        </thead>

        <tbody>
        <div style="margin-top:30px;">
            <%-- 遍历所有场地开始--%>
            <c:forEach items="${times}" var="t">
                <tr>
                        <%-- 纵坐标：时间--%>
                    <td style="width: 30px;position: relative;bottom:15px;">
                        <span class="time_cell">
                                ${fn:substring(t, 0, 5)}
                        </span>
                    </td>

                        <%-- 横坐标：场地状态--%>
                    <c:forEach items="${venueFieldPriceList}" var="field" varStatus="status">
                        <c:set var="status" value="0"/>
                        <%--遍历单个场地的时间、价格组成的Jason 获得状态--%>
                        <c:forEach items="${field.timePriceList}" var="tp">
                            <%--场地jason的时间与横坐标的时间一致 --%>
                            <j:if test="${t eq tp.time}">
                                <%--设置单个场地 时间T 的状态--%>
                                <c:set var="status" value="${tp.status}"/>
                                <c:set var="price" value="${tp.price}"/>
                                <%-- A场地 B时间 的状态 结束--%>
                            </j:if>
                        </c:forEach>


                        <%--设置td的class--%>
                        <j:if test="${'0' eq status}">
                            <c:set var="tdClass" value="reserveTd access"/>
                        </j:if>
                        <j:if test="${!('0' eq status)}">
                            <c:set var="tdClass" value="reserveTd unavailable"/>
                        </j:if>
                        <%--设置td的class end--%>

                        <%-- 场地 B时间 的状态展示--%>

                        <td status="${status}"
                            class="${tdClass}"
                            data-price="${price}"
                            data-field-id="${field.fieldId}"
                            data-field-name="${field.fieldName}"
                            data-time="${t}"
                        >
                            <j:if test="${'0' eq status}">
                                ¥ ${price}
                            </j:if>
                            <j:if test="${!('0' eq status)}">
                                <span>已预订</span>
                            </j:if>

                        </td>
                        <%-- A场地 B时间 的状态展示 结束--%>
                    </c:forEach>
                        <%-- 横坐标：时间 结束--%>
                </tr>
                <%-- 纵坐标：场地 结束--%>
            </c:forEach>
        </div>
        <%-- 遍历所有全场 场地结束--%>
        </tbody>

    </table>
</div>
<div class="row" style="display: none">
    <div id="unPayed" class="row">
    </div>
    <form id="orderForm">
        <input name="consDate" value="${consDate}" type="hidden">
        <input name="reserveVenueId" value="${venueId}" type="hidden">
        <%--<span id="reserve_submit"><a class="btn btn-success col-sm-10" style="height: 80px;width:100%;font-size: 50px;line-height: 80px;" onclick="filedSelectJson()">提交</a></span>--%>
    </form>
</div>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/reserve_app_field.js"></script>
<%--<script>
    window.alert = function(name){
        var iframe = document.createElement("IFRAME");
        iframe.style.display="none";
        iframe.setAttribute("src", 'data:text/plain,');
        document.documentElement.appendChild(iframe);
        window.frames[0].window.alert(name);
        iframe.parentNode.removeChild(iframe);
    }
</script>--%>
</body>
</html>
