<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>代理商推广链接</title>
    <style type="text/css">
        html, body {
            margin: 0;
            padding: 0;
            border: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
        }
    </style>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <script src="/scripts/boot.js" type="text/javascript"></script>
    <script src="/js/jquery-qrcode.min.js" type="text/javascript"></script>
</head>
<body>

<h1>您的推广链接：</h1><h1 id="link"></h1>
<div id="qrcode"></div>
<script type="text/javascript">

	$.ajax({
		url:'/agent/queryAgentLink.action',
		success:function(text){
			$("#link").text(text.agentExtensionLink);
			$("#qrcode").qrcode({
                    // render method: 'canvas', 'image' or 'div'
                    render: 'canvas',

                    // version range somewhere in 1 .. 40
                    minVersion: 1,
                    maxVersion: 40,

                    // error correction level: 'L', 'M', 'Q' or 'H'
                    ecLevel: 'L',

                    // offset in pixel if drawn onto existing canvas
                    left: 0,
                    top: 0,

                    // size in pixel
                    size: 200,

                    // code color or image element
                    fill: '#000',

                    // background color or image element, null for transparent background
                    background: null,

                    // content
                    text: text.agentExtensionQrcodeLink,

                    // corner radius relative to module width: 0.0 .. 0.5
                    radius: 0,

                    // quiet zone in modules
                    quiet: 0,

                    // modes
                    // 0: normal
                    // 1: label strip
                    // 2: label box
                    // 3: image strip
                    // 4: image box
                    mode: 0,

                    mSize: 0.1,
                    mPosX: 0.5,
                    mPosY: 0.5,

                    label: 'no label',
                    fontname: 'sans',
                    fontcolor: '#000',

                    image: null
                });
		}
	})
</script>
</body>
</html>
