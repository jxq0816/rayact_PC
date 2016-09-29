$.ajaxSetup({
    async : false
});
var previewHeight = 100;
var previewWidth = 100;
function previewPic(dom,preClass){
    $(preClass).html("");
    for(var i = 0 ; i<dom.files.length ; i++){
        var reader = new FileReader();
        var img;
        reader.onload = function(e){
            img = document.createElement("img")
            img.src = e.target.result;
            $(img).load(function(){
                var size = autoSize(this.naturalWidth, this.naturalHeight);
                $(this).css({
                    width: size.width,
                    height: size.height,
                    margin: 2,
                    top: (previewHeight - size.height) / 2,
                    left: (previewWidth - size.width) / 2
                }).show();
            });
            $(preClass).append(img);
        }
        reader.readAsDataURL(dom.files[i]);
    }
}
function autoSize(width, height) {
    var scale = width / height;
    width = previewHeight * scale;
    height = previewHeight;
    return {
        width: width,
        height: height
    }
}
function step01next(){
    var rz = $("#rz").val();
    var teamName = $("#teamName").val();
    var flag=true;
    if($.trim(teamName)==""){
        alert("请填写队名！");
        flag=false;
        return;
    }
    if($("#logoFiles").val()==""){
        alert("请上传队徽！");
        flag=false;
        return;
    }
    if(checkSame(teamName)){
        flag=false;
        return;
    }
    if(flag){
        $("#step01").hide();
        $("#step02").show();
    }
}
function step02prev(){
    $("#step01").show();
    $("#step02").hide();
}
function submitData(dom){
    var flag = true;
    var idNo = $("input[name='idNo']").val();
    var phone = $("input[name='phone']").val();
    $("#baseDataWrap input").each(function(){
        if($(this).val()==""){
            flag=false;
        }
    });
    if(flag==false){
        alert("请完善信息！");
        return;
    }
    if(checkMobile(phone)==false){
        alert("手机号格式错误！");
        return;
        flag=false;
    }
    if(checkIdCard(idNo)==false){
        alert("身份证号码有误！");
        return;
        flag=false;
    }
    if(checkIdCardSame(idNo)==true){
        return;
        flag=false;
    }
    if(checkPhoneSame()==true){
        return;
        flag=false;
    }
    if(flag){
        var options={
            type:"post",
            url:ctx+"/cms/teamTmp/app/save",
            async: false,
            enctype:"multipart/form-data",
            iframe:true,
            dataType:"json",
            error:function(data){
                alert(data);
            },
            success:function(data) {
                alert("申请成功！");
                window.open(ctx+"/cms/teamTmp/teamIndex","_self");
            }
        };
        if(confirm("您确认信息填写无误吗？")){
            $(dom).attr("onclick","");
            $('#teamForm').ajaxSubmit(options);
        }
    }
}

function checkSame(dom){
    var name = $(dom).val();
    var flag=false;
    if($.trim(name)!=""){
        $.get(ctx+"/cms/teamTmp/app/checkSame?name="+name,function(data){
            if(data=="true"){
                $(dom).val("");
                alert("已有同名的队伍了！");
                flag=true;
            }else{
                flag=false;
            }
        })
    }
    return flag;
}
function checkIdCardSame(dom){
    var card = $(dom).val();
    var rz = $("#rz").val();
    var rs=false;
    if($.trim(card)!=""){
        $.get(ctx+"/cms/teamMemberTmp/app/checkIdCard?card="+card+"&rz="+rz,function(data){
            if(data=="true"){
                alert("该身份证信息已经在同一组别中报过名了！");
                rs=true;
            }
        })
    }
    return rs;
}
function checkPhoneSame(){
    var phone = $("#phone").val();
    var rs=false;
    if($.trim(phone)!=""){
        $.get(ctx+"/cms/teamMemberTmp/app/checkPhoneSame?phone="+phone,function(data){
            if(data=="true"){
                alert("该手机号已经注册！");
                rs=true;
            }
        })
    }
    return rs;
}
function backToIndex(){
    window.open(ctx+"/cms/teamTmp/teamIndex","_self");
}