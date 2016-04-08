var now;
var nowMonth;
$(document).ready(function () {
    now = new Date();
    nowMonth = now.getMonth();
    nowYear = now.getFullYear();
})
//格式化日期：yyyy-MM-dd
function formatDate(date) {
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();

    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }
    return (year + "-" + month + "-" + day);
}
//获得某月的天数
function getMonthDays(myMonth) {
    var monthStartDate = new Date(nowYear, myMonth, 1);
    var monthEndDate = new Date(nowYear, myMonth + 1, 1);
    var days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
    return days;
}
//获得本月的开始日期
function getMonthStartDate() {
    var monthStartDate = new Date(nowYear, nowMonth, 1);
    return formatDate(monthStartDate);
}
//获得本月的结束日期
function getMonthEndDate() {
    var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth));
    return formatDate(monthEndDate);
}

function queryByDayForMemberIncome() {
    var startDate=formatDate(now);
    var endDate=formatDate(now);
    location.href=ctx + "/reserve/reserveCardStatements/memberIncomeReport?alone=${alone}&transactionType=1&startDate="+startDate+"&endDate="+endDate;
}
function queryByMonthForMemberIncome() {
    var startDate=getMonthStartDate();
    var endDate=getMonthEndDate();
    location.href=ctx + "/reserve/reserveCardStatements/memberIncomeReport?alone=${alone}&transactionType=1&startDate="+startDate+"&endDate="+endDate;
}

function queryByDayForCommoditySell() {
    var startDate=formatDate(now);
    var endDate=formatDate(now);
    location.href=ctx + "/reserve/reserveCommoditySell/commodityIncomeIntervalReport?alone=${alone}&startDate="+startDate+"&endDate="+endDate;
}
function queryByMonthForCommoditySell() {
    var startDate=getMonthStartDate();
    var endDate=getMonthEndDate();
    location.href=ctx + "/reserve/reserveCommoditySell/commodityIncomeIntervalReport?alone=${alone}&&startDate="+startDate+"&endDate="+endDate;
}