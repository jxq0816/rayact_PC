//保存数据
$(document).ready(function () {
    //-------预定---------
    $(".reserveTd").on('mousedown', function () {
        var fieldId = $(this).attr("data-field-id");
        var fieldName = $(this).attr("data-field-name");
        var price = $(this).attr("data-price");
        var time = $(this).attr("data-time");
        var startTime=time.substring(0,5);
        var endTime=time.substring(6,12);
        var time1=time.substring(0,2);
        var time2=time.substring(3,5);
        var time3=time.substring(6,8);
        var time4=time.substring(9,11);
        var timeId=time1+time2+time3+time4;
        var tr_id=fieldId+timeId;
        var order_item_id=tr_id+'item';
        if($(this).hasClass("unavailable")){
            return;
        }

        if ($(this).hasClass("access")) {//预定
            var index = $("#orderForm div").length;
            if(index>=0){
                $("#reserve_submit").show();
            }
            if(index>=8){
                alert("您选择的场地太多啦，请分两次下单结算哦。");
                return;
            }
            $(this).removeClass("access");
            $(this).addClass("unPayed");
            var s='<div id='+tr_id+' class="col-sm-2" style="height:100px;margin:1%;border: 1px solid #009ff0;border-radius:5px;-moz-border-radius: 5px;-webkit-border-radius: 5px;-o-border-radius: 5px;"> ' +
                '<div class="row text-center" style="height:50px;background-color:#009ff0;font-size:20px;color:#fff;line-height: 50px">'+time+'</div>' +
                '<div class="row text-center" style="height:50px;font-size:20px;line-height: 50px">'+fieldName+'</div></div>';
            $("#unPayed").append(s);
            var order_info='<div id='+order_item_id+'><input name="venueConsList['+index+'].reserveField.id" value=\''+fieldId+'\' type="hidden">'
                + '<input name="venueConsList['+index+'].reserveField.name" value=\''+fieldName+'\' type="hidden">'
                + '<input name="venueConsList['+index+'].orderPrice" value=\''+price+'\' type="hidden">'
                + '<input name="venueConsList['+index+'].startTime" value=\''+startTime+'\' type="hidden">'
                + '<input name="venueConsList['+index+'].endTime" value=\''+endTime+'\' type="hidden"></div>';
            $("#orderForm").append(order_info);

        }else{//取消预定
            $(this).removeClass("unPayed");
            $(this).addClass("access");
            $("#"+tr_id).remove();
            $("#"+order_item_id).remove();
            var index = $("#orderForm div").length;
            if(index<=0){
                $("#reserve_submit").hide();
            }
        }
    });
});
function filedSelectJason(){
    var reserveVenueCons = $("#orderForm").serializeJSON();
   /* jQuery.postItems({
        url: ctx + '/app/reserve/field/trans',
        data: reserveVenueCons,
        success: function (result) {

        }
    });*/
    var rtn=JSON.stringify(reserveVenueCons);
    return rtn;
}
function filedSelectArray(){
    var reserveVenueConsArray = $("#orderForm").serializeArray();
    var rtn=JSON.stringify(reserveVenueConsArray);
    orderSubmit(reserveVenueConsArray);
    return rtn;

}
function orderSubmit(reserveVenueCons){
    jQuery.postItems({
         url: ctx+'/app/reserve/field/reservation',
         data: reserveVenueCons,
         success: function (result) {
         if(result.bool){
             alert("预订成功");
         }else{
             alert("预订失败");
         }
         location.reload();
         }
     });
}