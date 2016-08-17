<%@ tag import="com.bra.common.utils.Collections3" %>
<%@ tag import="com.bra.common.utils.SpringContextHolder" %>
<%@ tag import="com.bra.common.utils.StringUtils" %>
<%@ tag import="com.bra.modules.mechanism.entity.AttMain" %>
<%@ tag import="com.bra.modules.mechanism.service.AttMainService" %>
<%@ tag import="java.util.ArrayList" %>
<%@ tag import="java.util.List" %>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="上传标签id" %>
<%@ attribute name="name" type="java.lang.String" required="true"
              description="上传标签的name,必须为attMain1、attMain2、attMain3、、。。" %>
<%@ attribute name="exts" type="java.lang.String" required="true" description="允许上传文件的格式,格式：.txt,.rpm" %>
<%@ attribute name="modelId" type="java.lang.String" required="true" description="modelId" %>
<%@ attribute name="fdKey" type="java.lang.String" required="true" description="fdKey" %>
<%@ attribute name="callBack" type="java.lang.String" required="false" description="回调函数" %>
<%@ attribute name="modelName" type="java.lang.String" required="true" description="modelName" %>
<%@ attribute name="multi" type="java.lang.Boolean" required="true" description="是否是多附件" %>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="cssClass" %>
<%@ attribute name="dataRule" type="java.lang.String" required="false" description="数据校验" %>
<%@ attribute name="method" type="java.lang.String" required="false" description="编辑：edit；查看:view" %>
<%@ attribute name="btnClass" type="java.lang.String" required="false" description="btnClass" %>
<%@ attribute name="btnText" type="java.lang.String" required="false" description="上传按钮文本" %>
<%@ attribute name="tipInfo" type="java.lang.String" required="false" description="上传文本提示" %>
<%@ attribute name="imgCheck" type="java.lang.String" required="false" description="校验图片的格式" %>
<%@ attribute name="showImg" type="java.lang.Boolean" required="false" description="是否显示图片" %>
<%@ attribute name="resizeImg" type="java.lang.Boolean" required="false" description="是否裁剪图片" %>
<%@ attribute name="resizeWidth" type="java.lang.String" required="false" description="剪裁图片的宽度" %>
<%@ attribute name="resizeHeight" type="java.lang.String" required="false" description="剪裁图片的高度" %>
<%@ attribute name="showUploadInfo" type="java.lang.Boolean" required="false" description="是否显示上传进度" %>
<%@ attribute name="imgWidth" type="java.lang.String" required="false" description="图片显示宽度" %>
<%@ attribute name="imgHeight" type="java.lang.String" required="false" description="图片显示高度" %>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/jodd.tld" %>
<j:set name="ctx" value="${pageContext.request.contextPath}"/>
<%
    String topAttMainId = "";
    String btnWidth = "80px";
    List<AttMain> attMains = new ArrayList<AttMain>();
    if (StringUtils.isNotBlank(modelId) && StringUtils.isNotBlank(modelName)) {
        AttMainService attMainService = SpringContextHolder.getBean("attMainService");
        attMains = attMainService.getAttMain(modelId, modelName, fdKey);
        if (!Collections3.isEmpty(attMains)) {
            topAttMainId = attMains.get(0).getId();
        }
    }
    if (StringUtils.isBlank(btnText)) {
        btnText = "添加";
    }
    if (btnText.length() > 3) {
        btnWidth = "120px";
    }


%>
<%
    String ext = "";
    if (StringUtils.isNotBlank(exts)) {
        String[] extArray = exts.split(",");
        StringBuilder sb = new StringBuilder();
        for (String string : extArray) {
            sb.append("\"").append(string).append("\",");
        }
        ext = sb.toString();
        ext = ext.substring(0, ext.length() - 1);
    }

    StringBuffer style = new StringBuffer();

    if (StringUtils.isNotBlank(imgWidth)) {
        imgWidth = "width:" + imgWidth + "px;";
    }

    if (StringUtils.isNotBlank(imgHeight)) {
        imgHeight = "height:" + imgHeight + "px";
    }

    if (StringUtils.isNotBlank(imgWidth) || StringUtils.isNotBlank(imgHeight)) {
        style.append("style='").append(imgWidth).append(imgHeight).append("'");
    }
    request.setAttribute("style", style.toString());
%>

<%if (!"view".equals(method)) {%>
<div class="btn-toolbar" role="toolbar">
    <div class="btn-group">
        <button type="button" style="width: <%=btnWidth%>;" class="${btnClass}" id="i_select_files_${id}"><%=btnText%>
        </button>
        <%=tipInfo == null ? "" : tipInfo%>
        <input id="hidden_${id}" data-rule="${dataRule}" class="${cssClass}" value="<%=topAttMainId%>" type="hidden"/>
    </div>
</div>
<%}%>
<table class="tablesorter">
    <tbody id="bootstrap-stream-container_${id}">
    <%
        int index = 1;
    %>
    <%for (AttMain attMain : attMains) {%>

    <tr id="tr_<%=attMain.getId()%>" class="template-upload fade in">
        <td><p class="name">
            <%
                if (showImg != null && showImg) {
            %>
            <img ${requestScope.style}
                    src='${ctx}/mechanism/file/image/<%=attMain.getId()%>'/>
            <%
            } else {
            %>
            <%=attMain.getFdFileName()%>
            <%}%>
        </p>
        </td>
        <td>
            <%if (!"view".equals(method)) {%>
            <span style="cursor: pointer;" class="glyphicon glyphicon-remove"
                  onclick="javascript:deleteAttMain('<%=attMain.getId()%>')">移除</span>|
            <%}%>
            <a style="cursor: pointer;" class="glyphicon glyphicon-remove" target="_blank"
               href="${ctx}/mechanism/file/view/<%=attMain.getId()%>">查看</a>
        </td>
    </tr>
    <%index++;%>
    <%}%>
    </tbody>
</table>

<div id="inputHidden${id }" style="display: none">

</div>

<script type="text/javascript">
    function deleteAttMain(attId) {
        $.ajax({
            type: "POST",
            cache: false,
            url: "${ctx}/mechanism/file/delete/" + attId,
            async: false,
            success: function (result) {
                if (result.status == '1') {
                    $("#div_" + attId).remove();
                    $("#tr_" + attId).remove();
                    var idvalue = $("#hidden_${id}").attr("value");
                    if (idvalue == attId) {
                        $("#hidden_${id}").attr("value", "");
                    }
                }
            }, error: function () {
            }
        });
    }
    <%if(!"view".equals(method)){%>
    var indexx${id } = <%=attMains.size()%>;
    var config_${id} = {
        customered: true,
        multipleFiles: true,
        fileFieldName: "FileData",
        maxSize: 2147483648,
        /** 当_t.bStreaming = false 时（也就是Flash上传时），2G就是最大的文件上传大小！所以一般需要 */
        simLimit: 10000,
        /** 允许同时选择文件上传的个数（包含已经上传过的） */
        extFilters: [<%=ext%>], /** 默认是全部允许，即 [] */
        browseFileId: "i_select_files_${id}",
        /** 文件选择的Dom Id，如果不指定，默认是i_select_files */
        browseFileBtn: "<div>请选择文件</div>",
        /** 选择文件的按钮内容，非自定义UI有效(customered:false) */
        swfURL: "${ctx }/static/stream/swf/FlashUploader.swf", /** SWF文件的位置 */
        tokenURL: "${ctx}/mechanism/token/file?imgCheck=${imgCheck}&resizeImg=${resizeImg}",
        /** 根据文件名、大小等信息获取Token的URI（用于生成断点续传、跨域的令牌） */
        frmUploadURL: "${ctx}/mechanism/file/o_upload?imgCheck=${imgCheck}&resizeImg=${resizeImg}&resizeWidth=${resizeWidth}&resizeHeight=${resizeHeight}&exts=${exts}",
        /** Flash上传的URI */
        uploadURL: "${ctx}/mechanism/html5/upload?imgCheck=${imgCheck}&resizeImg=${resizeImg}&resizeWidth=${resizeWidth}&resizeHeight=${resizeHeight}&exts=${exts}",
        /** HTML5上传的URI */
        onMaxSizeExceed: function (file) {
//console && console.log("-------------onMaxSizeExceed-------------------");
//console && console.log(file);
            $("#i_error_tips > span.text-message").append("文件[name=" + file.name + ", size=" + file.formatSize + "]超过文件大小限制‵" + file.formatLimitSize + "‵，将不会被上传！<br>");
//console && console.log("-------------onMaxSizeExceed-------------------End");
        },
        onFileCountExceed: function (selected, limit) {
//console && console.log("-------------onFileCountExceed-------------------");
//console && console.log(selected + "," + limit);
            $("#i_error_tips > span.text-message").append("同时最多上传<strong>" + limit + "</strong>个文件，但是已选择<strong>" + selected + "</strong>个<br>");
//console && console.log("-------------onFileCountExceed-------------------End");
        },
        onExtNameMismatch: function (info) {
            console && console.log("-------------onExtNameMismatch-------------------");
            console && console.log(info);
            alert("文件类型不匹配");
            console && console.log("-------------onExtNameMismatch-------------------End");
        },
        onAddTask: function (file) {
            <j:ifelse test="${multi}">
            <j:then>
            var fileHtml = '<tr id="' + file.id + '" class="template-upload fade in">' +
                    '<td><p class="name">' + file.name + '</p>' +
                    <j:if test="${showUploadInfo}">
                    ' <div><span class="label label-info">进度：</span> <span class="message-text"></span></div>' +
                    </j:if>
                    '</td>' +
                    '<td><a class="glyphicon glyphicon-remove" onClick="javascript:_t_${id}.cancelOne(\'' + file.id + '\')">移除</a>|' +
                    '<a style="cursor: pointer;" class="glyphicon glyphicon-see" target="_blank" href="">查看</a>' +
                    '</td></tr>';
            $("#bootstrap-stream-container_${id}").append(fileHtml);
            </j:then>
            <j:else>
            var fileHtml = '<tr id="' + file.id + '" class="template-upload fade in">' +
                    '<td><p class="name">' + file.name + '</p>' +
                    <j:if test="${showUploadInfo}">
                    ' <div><span class="label label-info">进度：</span> <span class="message-text"></span></div>' +
                    </j:if>
                    '</td>' +
                    '<td><a class="glyphicon glyphicon-remove" onClick="javascript:_t_${id}.cancelOne(\'' + file.id + '\')">移除</a>|' +
                    '<a style="cursor: pointer;" class="glyphicon glyphicon-see" target="_blank" href="">查看</a>' +
                    '</td></tr>';

            $("#bootstrap-stream-container_${id}").html(fileHtml);
            </j:else>
            </j:ifelse>

        },
        onUploadProgress: function (file) {
            var $bar = $("#" + file.id).find("div.progress-bar");
            $bar.css("width", file.percent + "%");
            var $message = $("#" + file.id).find("span.message-text");
            $message.text("已上传:" + file.formatLoaded + "/" + file.formatSize + " 速 度:" + file.formatSpeed);
            console && console.log("-------------onUploadProgress-------------------End");
        },
        onComplete: function (file) {
//console && console.log("-------------onComplete-------------------");
            console && console.log(file);
            try {
                var value = JSON.parse(file.msg);
                $("#hidden_${id}").attr("value", value.attId);
                if (value && value.status == '1') {
//<j:if test="${callBack!=null }">
                    try {
                        if (${callBack}) {
                            ${callBack}(value.attId, value.fileName, '${fdKey}');
                        }
                    } catch (e) {
                    }

//</j:if>

                    // <j:ifelse test="${multi}">
                    // <j:then>
                    jQuery("#inputHidden${id }").append('<div id="div_' + value.attId + '"><input type="text" name="${name}['
                            + indexx${id } + '].fdFileName" value="' + value.fileName + '" /><input type="text" name="${name}['
                            + indexx${id } + '].id" value="' + value.attId + '" /><input type="text" name="${name}['
                            + indexx${id } + '].fdKey" value="${fdKey}" /></div>');
                    indexx${id}++;
                    $("#" + file.id).find(".glyphicon-remove").attr("onClick", "deleteAttMain('" + value.attId + "')");
                    $("#" + file.id).find(".glyphicon-see").attr("href", "${ctx}/mechanism/file/view/" + value.attId + "");
                    <j:if test="${showImg}">
                    $("#" + file.id).find(".name").html("<img ${requestScope.style} src='${ctx}/mechanism/file/image/" + value.attId + "?time=" + Math.random() + "'/>");
                    </j:if>
                    $("#" + file.id).attr("id", "tr_" + value.attId);
                    // </j:then>
                    //<j:else>
                    jQuery("#inputHidden${id }").html('<div id="div_' + value.attId + '"><input type="text" name="${name}['
                            + indexx${id } + '].fdFileName" value="' + value.fileName + '" /><input type="text" name="${name}['
                            + indexx${id } + '].id" value="' + value.attId + '" /><input type="text" name="${name}['
                            + indexx${id } + '].fdKey" value="${fdKey}" /></div>');
                    indexx${id}++;
                    $("#" + file.id).find(".glyphicon-remove").attr("onClick", "deleteAttMain('" + value.attId + "')");
                    $("#" + file.id).find(".glyphicon-see").attr("href", "${ctx}/mechanism/file/view/" + value.attId + "");
                    <j:if test="${showImg}">
                    $("#" + file.id).find(".name").html("<img ${requestScope.style} src='${ctx}/mechanism/file/image/" + value.attId + "?time=" + Math.random() + "'/>");
                    </j:if>
                    $("#" + file.id).attr("id", "tr_" + value.attId);
                    //</j:else>
                    //</j:ifelse>
                } else {
                    if ('${imgCheck}' != '') {
                        alert('上传失败,产生原因：1:文件名必须为${imgCheck},2:上传出错');
                    } else {
                        alert('上传失败.');
                    }
                }
            } catch (e) {
                alert(e.message);
            }
            //console && console.log("-------------onComplete-------------------End");
        },
        onUploadError: function (status, msg) {
            //console && console.log("-------------onUploadError-------------------");
            //console && console.log(msg + ", 状态码:" + status);

            $("#i_error_tips > span.text-message").append(msg + ", 状态码:" + status);

            //console && console.log("-------------onUploadError-------------------End");
        },
        onCancel: function (file) {
            console && console.log(file)
            alert(file.id);
        }
    };
    var _t_${id} = new Stream(config_${id});

    /** Flash最大支持2G */
    if (!_t_${id}.bStreaming) {
        _t_${id}.config.maxSize = 2147483648;
    }
    <%}%>
</script>
