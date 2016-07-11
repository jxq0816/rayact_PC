//保存数据
$.ajaxSetup({
    async : false
});
$(document).ready(function () {
    //-------预定---------
    $("#reserveStatus").scroll(function () {
        var scrollLeft=$("#reserveStatus").scrollLeft();
        var margin_left=Number(-5)-Number(scrollLeft);
        $("#fieldThead").css({"margin-left":margin_left});
    });
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
        var phone =$("#phone").val();
        var orderId=null;
        if ($(this).hasClass("access")) {//预定
            var index = $("#orderForm div").length;
            if(index==0){
                $.get(ctx+ '/app/reserve/field/checkUnPayOrder', {phone:phone},function (result) {
                        orderId=result;
                        if(result!='' && result!=null && result!=undefined){
                            if(typeof iOScheckOrder === 'function'){
                                iOScheckOrder(result);
                            }else if(window.orderId){
                                window.orderId.orderIdCallAndroid(result);
                            }
                        }
                    }
                );
            }
            if(orderId==''||orderId==null||orderId==undefined){
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
                var order_info='<div id='+order_item_id+'><input name="venueConsList['+index+'].reserveFieldId" value=\''+fieldId+'\' type="hidden">'
                    + '<input name="venueConsList['+index+'].reserveFieldName" value=\''+fieldName+'\' type="hidden">'
                    + '<input name="venueConsList['+index+'].orderPrice" value=\''+price+'\' type="hidden">'
                    + '<input name="venueConsList['+index+'].startTime" value=\''+startTime+'\' type="hidden">'
                    + '<input name="venueConsList['+index+'].endTime" value=\''+endTime+'\' type="hidden"></div>';
                $("#orderForm").append(order_info);
            }
        }else{//取消预定
            $(this).removeClass("unPayed");
            $(this).addClass("access");
            $("#"+tr_id).remove();
            $("#"+order_item_id).remove();
          /*  var index = $("#orderForm div").length;
            if(index<=0){
                $("#reserve_submit").hide();
            }*/
        }
    });
});
function filedSelectJson(isAndroid){
    var a = {};
    var reserveVenueCons = $("#orderForm").serializeArray();
    var numreg = /\[[0-9]*\]\./;
    var index = 0;
    var attnum = 5;
    var tmp = 0 ;
    $.each(reserveVenueCons,function(n,v){
        var name = v.name;
        var names = name.split(numreg);
        if(names.length > 1){//数组属性
            if(!a[names[0]])//如果a[]没有属性names[0]
                a[names[0]]= []; //新建
            if(!a[names[0]][index]) //如果a[names[0]]没有属性index
                a[names[0]][index]= {};//新建
            a[names[0]][index][names[1]] = v.value;//设置value
            tmp++;
            if((tmp)%attnum==0){
                index++;
            }
        }else{//普通属性
            a[v.name] = v.value;
        }
    });
    var rtn=JSON.stringify(a);
    if(isAndroid=='1'){
        myObj.JsCallAndroid(rtn);//给Android 传输预订数据
    }
    return rtn;
}