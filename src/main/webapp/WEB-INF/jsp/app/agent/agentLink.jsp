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
</head>
<body>

<h1>您的推广链接：</h1><h1 id="link"></h1>
<script type="text/javascript">

	$.ajax({
		url:'/agent/queryAgentLink.action',
		success:function(text){
			$("#link").text(text);
		}
	})
</script>
</body>
</html>
