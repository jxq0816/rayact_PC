//保存数据
$(document).ready(function () {
    //-------预定---------
    $(".reserveTd").on('mousedown', function () {
        var fieldId = $(this).attr("data-field-id");
        var fieldName = $(this).attr("data-field-name");
        var price = $(this).attr("data-price");
        var time = $(this).attr("data-time");
        var time1=time.substring(0,2);
        var time2=time.substring(3,5);
        var time3=time.substring(6,8);
        var time4=time.substring(9,11);
        var timeId=time1+time2+time3+time4;
        var trId=fieldId+timeId;
        if ($(this).hasClass("access")) {//预定
            $(this).removeClass("access");
            $(this).addClass("unPayed");
            var s='<div id='+trId+' class="col-sm-2" style="margin:1%;border: 1px solid #009ff0;border-radius:5px;-moz-border-radius: 5px;-webkit-border-radius: 5px;-o-border-radius: 5px;"> <div class="row text-center" style="background-color:#009ff0;">'+time+'</div><div class="row text-center">'+fieldName+'</div></div>';
            $("#unPayed").append(s);
        }else{//取消预定
            $(this).removeClass("unPayed");
            $(this).addClass("access");
            $("#"+trId).remove();
        }
    });
})