$(function(){
		$('#captchaImg').click(function(e){
			//刷新验证码
			getCaptchaImg();
		});
		$('#captcha').keydown(function(e){
			if(e.keyCode==13){
				//登录系统
				submitForm();    
			}
		});
		//登录框
		$(".logintxt").focus(function(){
			var txt = $(this).val();
			if(txt == this.defaultValue){
				$(this).val("").addClass("logintxt_h");
			}
		});
		$(".logintxt").blur(function(){
			var txt = $(this).val();
			if(txt == ""){
				$(this).val(this.defaultValue).removeClass("logintxt_h");
			}
		});

		//合作伙伴
		$(".part_con01").hover(function(){
			$(".part_con01 .top").addClass("top_h");
		},function(){
			$(".part_con01 .top").removeClass("top_h");
		});
		
		$(".part_con02").hover(function(){
			$(".part_con02 .top").addClass("top_h");
		},function(){
			$(".part_con02 .top").removeClass("top_h");
		});
		
		$(".part_con03").hover(function(){
			$(".part_con03 .top").addClass("top_h");
		},function(){
			$(".part_con03 .top").removeClass("top_h");
		});
		
		$(".part_con04").hover(function(){
			$(".part_con04 .top").addClass("top_h");
		},function(){
			$(".part_con04 .top").removeClass("top_h");
		});
		$(".shut").click(function(){
			closeFeedDiv();
		});
		$(".inptxt").focus(function(){
			var tx = $(this).val();
			if(tx == this.defaultValue){
				$(this).addClass("inptxt_h");
			}
		});
		$(".inptxt").blur(function(){
			var tx = $(this).val();
			if(tx == ""){
				$(this).removeClass("inptxt_h");
			}
		});
		if($("#rememberMe").attr("checked")){
			var cookie_userName=$("#username").val();
			if(cookie_userName!=null && cookie_userName!=""){
				$("#EP_Mail").val(cookie_userName);
			}
		}
		if(typeof(errorCaptchaMsg)=="undefined"){
			errorMsg="";
		}
		if(typeof(errorCaptchaMsg)=="undefined"){
			errorCaptchaMsg="";
		}
		if(errorMsg!=''){
			$("#warn_Error").html(errorMsg);
			$("#warn_Error").show();
			$("#PassWord").focus();
		}
		if(errorCaptchaMsg!=''){
			$("#captcha_Error").html(errorCaptchaMsg);
			$("#captcha_Error").show();
			$("#captcha").focus();
		}
	});
var screenClass = function()
{
    /// 解锁
    this.unlock = function()
    {
        var divLock = document.getElementById("divLock");
        if(divLock == null) return;
        document.body.removeChild(divLock);
    };
    
    /// 锁屏
    this.lock = function()
    {
        var sWidth,sHeight;
        var imgPath = "/images/WaitProcess.gif";
        sWidth  = screen.width - 20;
        sHeight = screen.height- 170;
        
        var bgObj=document.createElement("div");
        bgObj.setAttribute("id","divLock");
        bgObj.style.position="absolute";
        bgObj.style.top="0";
        bgObj.style.background="#cccccc";
        bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
        bgObj.style.opacity="0.6";
        bgObj.style.left="0";
        bgObj.style.width=sWidth + "px";
        bgObj.style.height=sHeight + "px";
        bgObj.style.zIndex = "100";
        document.body.appendChild(bgObj);
        var html = "<table border=\"0\" width=\""+sWidth+"\" height=\""+sHeight+"\"><tr><td valign=\"middle\" align=\"center\"><image src=\""+imgPath+"\"></td></tr></table>";
        bgObj.innerHTML = html;
       
        // 解锁
        bgObj.onclick = function()
        {
             //new screenClass().unlock();
        }
    };
}
function selectTag(showContent,selfObj){
	// 标签
	var tag = document.getElementById("tags").getElementsByTagName("li");
	var taglength = tag.length;
	for(i=0; i<taglength; i++){
		tag[i].className = "";
	}
	selfObj.parentNode.className = "selectTag";
	// 操作内容
	for(i=0; j=document.getElementById("tagContent"+i); i++){
		j.style.display = "none";
	}
	document.getElementById(showContent).style.display = "block";	
}
function getCaptchaImg(){
	var url=front+"/servlet/validateCodeServlet?"+parseInt(10*Math.random());
	$("#captchaImg").attr("src",url);
}
function valicate(id){
		var dataType = $("#"+id).attr("dataType");
		var nullmsg = $("#"+id).attr("nullmsg");
		var errormsg = $("#"+id).attr("errormsg");
		if(typeof(dataType)== "undefined"){
			dataType = "*";
		}
		if(typeof(nullmsg)== "undefined"){
			nullmsg = "";
		}
		if(typeof(errormsg)== "undefined"){
			errormsg = "";
		}
		var nullFlag = isNull(id)
		if(!nullFlag){
			return valicateDataType(id,dataType,errormsg);
		}else{
			if(id=="captcha"){
				$("#"+id+"_Error").html(nullmsg);
				$("#"+id+"_Error").show();
				$("#"+id).focus();
				return false;
			}else{
				$("#warn_Error").html(nullmsg);
				$("#warn_Error").show();
				$("#"+id).focus();
			}
		}
	}
	function valicateDataType(id,dataType,errormsg){
		var dataTypes = dataType.split(",");
		if(dataTypes!=null && dataTypes.length>0){
			for(var i=0;i<dataTypes.length;i++){
				var type = 	dataTypes[i];
				var reg = "";
				if(type=="*"){
					continue;
				}else if(type=="isnum"){
					reg = /^(|[+-]?(0|([1-9]\d*)|((0|([1-9]\d*))?\.\d*)){1,1})$/;
				}else if(type=="m"){
					reg = /^(13[0-9]{9}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8})$/;
				}else if(type=="e"){
					reg =  /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
				}else if(type=="num_100"){
					reg = /^[1-9]\d{0,1}(?:\.\d{1})?$/;
				}else if(type=="num_xx"){
					reg = /^[0-9].*$/;
				}
				if(valicateByReg(id,errormsg,reg)){
					continue;
				}else{
					return false;
				}			
			}
		}
		return true;
	}
	function valicateByReg(id,errormsg,reg){
		var num=$.trim($("#"+id).val());
		if(!reg.test(num))
	    {  
	    	if(id=="captcha"){
	    		$("#"+id+"_Error").html(errormsg);
	        	$("#"+id+"_Error").show();
	        	$("#"+id).focus();
	    	}else{
	    		$("#warn_Error").html(errormsg);
		        $("#warn_Error").show();
		        $("#"+id).focus();
	    	}
	        
	        return false;
	    }else{
	    	 $("#"+id+"_Error").html("");
	    	 $("#"+id+"_Error").hide();
	    }
	    return true;
	}
	function isNull(id){
		var val = $.trim($("#"+id).val());
		if((val!=null && val!="" && val!="您注册的邮箱地址")){
			return false;
		}else{
			return true;
		}
		
	}
	function submitForm(){
		if(valicate("EP_Mail") && valicate("PassWord") && valicate("captcha")){
			$("#form1").attr("method","post");
			$("#form1").attr("target","_self");
			$("#form1").attr("action",ctx+"/login");
			$("#form1").submit();
		}
	}
	function recover(id){
		 $("#"+id+"_Error").html("");
    	 $("#"+id+"_Error").hide();
	}
	function showDivFeed(){
		sAlert();
		showDiv($("#userFeed"));
	}
	function showDiv(obj){
		 $(obj).show();
		 center(obj);
		 $(window).scroll(function(){
		  center(obj);
		 });
		 $(window).resize(function(){
		  center(obj);
		 }); 
	}
	function center(obj){
		var windowWidth = document.documentElement.clientWidth;   
		var windowHeight = document.documentElement.clientHeight;   
		var popupHeight = $(obj).height();   
		var popupWidth = $(obj).width();    
		$(obj).css({   
		  "position": "absolute",   
		  "top": (windowHeight-popupHeight)/2+$(document).scrollTop(),   
		  "left": (windowWidth-popupWidth)/2+popupWidth/2  
		}); 
	}
	function closeFeedDiv(){
		$("#Content").val("");
		$("#User_Name").val("");
		$("#Phone").val("");
		$("#userFeed").hide();
		removeObj();
	}
	function submitFeedForm(){
		if(isNull("Content")){
			alertNullWarn("Content");
			return false;		
		}
		if($.trim($("#Content").val()).length>300){
			alertErrorWarn("Content")
			return false;
		}
		if(isNull("User_Name")){
			//alertNullWarn("User_Name");
			//return false;
		}
		if(isNull("Phone")){
			//alertNullWarn("Phone");
			//return false;
		}//else{
			//var reg = /^(13[0-9]{9}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8})$/;
			//if(!reg.test($("#Phone").val())){
				//alertErrorWarn("Phone");
				//return false;
			//}
		//}
		
		
		jQuery.ajax({
               url: "/user/addFeedback.action",   // 提交的页面
               data: $('#feedForm').serialize(), // 从表单中获取数据
               type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
               beforeSend: function()          // 设置表单提交前方法
               {
               		$("#userFeed").hide();
                    //new screenClass().lock();
               },
               error: function(request) {      // 设置表单提交出错
               		$("#userFeed").show();
                    //new screenClass().unlock();
                    alert("表单提交出错，请稍候再试");
               },
               success: function(data) {
               		removeObj();
	               	$("#Content").val("");
					$("#User_Name").val("");
					$("#Phone").val("");
                  	//new screenClass().unlock(); // 设置表单提交完成使用方法
               }
          });
		
	} 
	function alertNullWarn(id){
		var nullmsg = $("#"+id).attr("nullmsg");
		if(typeof(nullmsg)== "undefined"){
			nullmsg = "";
		}
		alert(nullmsg);
		$("#"+id).focus();
		return false;	
	}
	function alertErrorWarn(id){
		var errormsg = $("#"+id).attr("errormsg");
		if(typeof(errormsg)== "undefined"){
			errormsg = "";
		}
		alert(errormsg);
		$("#"+id).focus();
		return false;	
	}
	
	function sAlert(){
		var sWidth,sHeight;
		document.body.style.overflowY = "hidden";
		sWidth=document.body.offsetWidth;//浏览器工作区域内页面宽度 或使用 screen.width//屏幕的宽度
		sHeight=3000;//屏幕高度（垂直分辨率）
		//背景层（大小与窗口有效区域相同，即当弹出对话框时，背景显示为放射状透明灰色）
		var bgObj=document.createElement("div");//创建一个div对象（背景层） //动态创建元素，这里创建的是 div
		//定义div属性，即相当于(相当于，但确不是，必须对对象属性进行定义
		//<div id="bgDiv" style="position:absolute; top:0; background-color:#777; filter:progid:DXImagesTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75); opacity:0.6; left:0; width:918px; height:768px; z-index:10000;"></div>
		bgObj.setAttribute('id','bgDiv');
		bgObj.style.position="absolute";
		bgObj.style.top="0";
		bgObj.style.background="#777";
		bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
		bgObj.style.opacity="0.6";
		bgObj.style.left="0";
		bgObj.style.width=sWidth + "px";
		bgObj.style.height=sHeight + "px";
		bgObj.style.zIndex = "10000";
		document.body.appendChild(bgObj);//在body内添加该div对象
		
	}

	function removeObj(){//点击标题栏触发的事件
		document.body.style.overflowY = "auto";
		var bgDiv = document.getElementById("bgDiv"); 
		bgDiv.parentNode.removeChild(bgDiv);   //删除 
	}