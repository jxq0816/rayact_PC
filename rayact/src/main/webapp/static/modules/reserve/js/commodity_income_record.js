var now;
var nowMonth;
var nowYear;
$(document).ready(function () {
    now = new Date();

    nowMonth = now.getMonth();
    nowYear = now.getFullYear();

    var startDateFromBack=$("#startDate").val();
    var endDateFromBack=$("#endDate").val();
    now=formatDate(now);
    var startDateOfMonth = getMonthStartDate();
    var endDateOfMonth = getMonthEndDate();
    var startDateOfYear = nowYear + "-01-01";
    var endDateOfYear = nowYear + "-12-31";

    if(startDateFromBack==now && endDateFromBack==now){
        $("#today").attr("class", "on");
    }else if(startDateFromBack==startDateOfMonth && endDateFromBack==endDateOfMonth){
        $("#month").attr("class", "on");
    }else if(startDateFromBack==startDateOfYear && endDateFromBack==endDateOfYear){
        $("#year").attr("class", "on");
    }else if(startDateFromBack!=''||endDateOfYear!='' ){
        $("#self").attr("class", "on");
    }

})
function  querySelf(){
    $("#self").attr("class", "on");
    $("#today").removeClass("on");
    $("#month").removeClass("on");
    $("#year").removeClass("on");
}
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
function queryToday() {
     $("#startDate").val(now);
     $("#endDate").val(now);

     $("#searchForm").submit();
}
function queryMonth() {
    var startDate = getMonthStartDate();
    var endDate = getMonthEndDate();
    $("#startDate").val(startDate);
    $("#endDate").val(endDate);
    $("#searchForm").submit();
}
function queryYear(){

    var startDate = nowYear + "-01-01";
    var endDate = nowYear + "-12-31";
    $("#startDate").val(startDate);
    $("#endDate").val(endDate);
    $("#searchForm").submit();
}