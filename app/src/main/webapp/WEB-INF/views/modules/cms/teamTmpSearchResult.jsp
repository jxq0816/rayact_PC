<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jquery-form/jquery.form.js" type="text/javascript"></script>
    <link href="${ctxStatic}/jquery-mobile/list.css" type="text/css" rel="stylesheet"/>
    <link href="${ctxStatic}/bootstrap/2.3.1/css_default/bootstrap.min.css" type="text/css" rel="stylesheet"/>
</head>
<body style="font-family: Microsoft Yahei">
<style>
    .step {
        margin: 2px;
        margin-top: 100px;
    }

    .s1 {
        background-color: #000000;
        color: #ffffff;
        width: 100%;
        height: 30px;
        margin: 10px 0;
        padding: 20px 0;
    }

    .title {
        width: 100%;
        border: 1px solid #000000;
        color: #000000;
    }

    .photo {
        height: 120px;
        width: 90px;
        border-radius: 5px; /* 所有角都使用半径为5px的圆角，此属性为CSS3标准属性 */
        -moz-border-radius: 5px; /* Mozilla浏览器的私有属性 */
        -webkit-border-radius: 5px; /* Webkit浏览器的私有属性 */
        border-radius: 5px; /* 四个半径值分别是左上角、右上角、右下角和左下角 */
    }

    .item {
        display: inline-block;
        margin: 5px;
    }
</style>
<div style="width: 100%;text-align: center">
    <p style="color: #000000;font-size: 28px;margin: 50px 0;line-height:30px">2016青草足球夏季联赛报名</p>
</div>
<div class="step" id="searchResult">
    <div style="font-size: 30px;text-align: center">
        <div class="s1">队伍信息：</div>
        <div class="teamInfo" style="font-size: 15px">
            <div>队名：${team.name}</div>
            <div>参赛组别：${team.zb}</div>
            <div>参赛制式：<c:if test="${team.rz == '5'}">五人制</c:if><c:if test="${team.rz == '8'}">八人制</c:if>
            </div>
            <div>球队状态：<c:if test="${team.delFlag == '0'}">待审核</c:if>
                <c:if test="${team.delFlag == '2'}">已通过</c:if>
                <c:if test="${team.delFlag == '1'}">已驳回</c:if>
            </div>
        </div>
        <div class="s1">管理人员</div>
        <div class="managerInfo" style="font-size: 15px">
            <div class="item">
                <div>
                    <script>
                        var leaderImg = "${leader.remarks}";
                        var img = leaderImg.split(";")[0];
                        document.write("<img src='" + img + "' class='photo'/>");
                    </script>
                </div>
                <div>领队：${leader.name}</div>
                <div>手机号：${leader.phone}</div>
            </div>
            <div class="item">
                <div>
                    <script>
                        var teacherImg = "${teacher.remarks}";
                        var img = teacherImg.split(";")[0];
                        document.write("<img src='" + img + "' class='photo'/>");
                    </script>
                </div>
                <div>教练：${teacher.name}</div>
                <div>手机号：${teacher.phone}</div>
            </div>
            <div class="item">
                <div>
                    <script>
                        var captainImg = "${captain.remarks}";
                        var img = captainImg.split(";")[0];
                        document.write("<img src='" + img + "' class='photo' />");
                    </script>
                </div>
                <div>队长：${captain.name}</div>
                <div>手机号：${captain.phone}</div>
            </div>
        </div>
        <div class="s1">球员</div>
        <div class="membersInfo" style="font-size: 15px">
            <c:forEach items="${members}" var="member">
                <div class="item">
                    <div>
                        <script>
                            var memberImg = "${member.remarks}";
                            var img = memberImg.split(";")[0];
                            document.write("<img src='" + img + "' class='photo'/>");
                        </script>
                    </div>
                    <div>姓名：${member.name}</div>
                    <div>电话号：${member.phone}</div>
                </div>
            </c:forEach>
        </div>
        <div class="s1" onclick="backToIndex();">返回主页</div>
    </div>
</div>
<script>
    function backToIndex() {
        window.open("${ctx}/cms/teamTmp/teamIndex", "_self");
    }
</script>
</body>
</html>