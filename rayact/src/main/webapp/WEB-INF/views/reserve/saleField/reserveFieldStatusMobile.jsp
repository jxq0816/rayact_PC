<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/modules/reserve/css/field.css?id=7862256"/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/jquery/smartMenu.css"/>
    <script type="text/javascript"
            src="${ctxStatic}/jquery/jquery-smartMenu-min.js"></script>
    <script type="text/javascript">var ctx = '${ctx}', consDate = '${consDate.time}', venueId = '${reserveVenue.id}';</script>
    <style>
        .table-chang td.normal {
            background:#62ab00;
        }
        .table-chang td.abnormal {
            background:#ff981d;
        }
        .table-chang td.fullFieldHasAbnormal {
            background:#ff981d;
        }
    </style>
</head>
<body>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="tab-tit-first">
                    <ul class="table-ul">
                        <li style="margin-left: 0px;"><span class="green-bg-color"></span>可预订</li>
                        <%-- <li><span class="blue-bg-color"></span>已选场次</li>--%>
                        <li><span class="grey-bg-color"></span>已占用</li>
                        <li><span class="red-bg-color"></span>已付款</li>
                    </ul>
                </div>
                </div>

                <%-- 周几，日期 结束--%>

                <div class="sy-tab-cont">

                    <div class="table-left">
                        <%@include file="reserveAMField.jsp" %>
                        <%@include file="reservePMField.jsp" %>
                        <%@include file="reserveEveningField.jsp" %>
                    </div>

                    <script type="text/javascript">
                        $(".table-left-l ul").css("padding-top", parseInt($(".table-chang thead").eq(0).height()) + 3 + "px");
                    </script>

                </div>
            </div>
        </div>

    </div>
</div>
<!--end dialog-->
<script>
    document.write("<script type='text/javascript' src='${ctxStatic}/modules/reserve/js/reserve_field_status.js?t=" + Math.random() + "'><\/script>");
</script>
</body>
</html>
