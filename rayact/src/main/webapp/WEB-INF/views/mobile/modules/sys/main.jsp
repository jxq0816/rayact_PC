<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<section id="main_section">
    <header>
        <nav class="left">
            <a href="#" data-icon="previous" data-target="back">返回</a>
        </nav>
        <h1 class="title">首页</h1>
    </header>
    <article class="active" data-scroll="true">

    </article>
    <article class="active">
        <%--<ul class="control-group" style="margin:10px 30px;">
            <li id="memberSale"></li>
            <li>场地售卖:${fieldTodayPrice}</li>
            <li>商品售卖:${sellOfToday}</li>
        </ul>--%>

        <ul class="control-group" id="changeLineType" style="margin:10px 30px;">
            <li class="active" data-type="normal">会员充值</li>
            <li data-type="smooth">场地售卖</li>
            <li data-type="area">商品售卖</li>
        </ul>
        <div>
            <div class="chartTitle">公司产品销售数据</div>
            <canvas id="line_canvas"></canvas>
            <!--<div class="legend">
                <div data-icon="stop" style="color: #72caed">A产品</div>
                <div data-icon="stop" style="color: #a6d854">B产品</div>
            </div>-->
        </div>
    </article>
</section>