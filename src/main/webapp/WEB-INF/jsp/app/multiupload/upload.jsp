<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="../../include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script type="text/javascript" src="<c:url value='/scripts/boot.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/swfupload/swfupload.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/multiupload.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/scripts/multiupload.css'/>" />
<style type="text/css">
html, body {
	height: 100%;
	width: 100%;
	padding: 0;
	margin: 0;
	overflow: hidden;
}
</style>
</head>
<body>
	<div class="mini-panel" style="width: 100%; height: 100%"
		showfooter="true" bodystyle="padding:0" borderStyle="border:0"
		showheader="false">

		<div id="multiupload1" class="uc-multiupload"
			style="width: 100%; height: 100%"
			flashurl="/scripts/swfupload/swfupload.swf" uploadName="filedata"
			uploadurl="/menu/fileUpload.action" borderstyle="border:0"
			queueLimit="3" onuploaderror="onUploadError"
			onuploadsuccess="onUploadSuccess"></div>

		<div property="footer" style="padding: 8px; text-align: center">
			<a class="mini-button" onclick="onOk" style="width: 80px"
				iconcls="icon-ok">确定</a> <a class="mini-button" onclick="onCancel"
				style="width: 80px; margin-left: 50px" iconcls="icon-cancel">取消</a>
		</div>

	</div>
<script type="text/javascript">
    mini.parse();
    var grid = mini.get("multiupload1");

    function SaveData() {
        CloseWindow("ok");
    }
    function CloseWindow(action) {
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    }
    function onOk(e) {
        SaveData();
    }
    function onCancel(e) {        
        CloseWindow("cancel");
   }
    function SetData(params) {
        
        grid.setPostParam(params);
    }

    function GetData() {
         var rows = grid.findRows(function (row) {
             if (row.status == 1) {
                 return true;
             }
         })
         return rows;
     }
    function onUploadSuccess(e) {
    	console.log("服务器返回值："+e.serverData);
        //e = { file, serverData }
        //alert("上传成功：" + e.serverData);

    }
    function onUploadError(e) {
    	console.log(e);
        //e = { file, code, message }
        alert("上传失败：" + e.file + ' ' + e.message);
    }
</script>

</body>
</html>