<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>登录页面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <!--<link rel="shortcut icon" href="/images/favicon.png" type="image/png"/>-->
    <link href="/css/demo.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        body {
            width: 100%;
            height: 100%;
            margin: 0;
            overflow: hidden;
        }
        .errorText {
            color: red;
        }
    </style>
    <script src="/scripts/boot.js" type="text/javascript"></script>
    <script src="/js/my.js" type="text/javascript"></script>
</head>
<body>
<div id="loginWindow" class="mini-window" title="用户登录" style="width:350px;height:200px;"
     showModal="true" showCloseButton="false"
>

    <div id="loginForm" style="padding:15px;padding-top:10px;">
        <table>
            <tr>
                <td style="width:60px;"><label for="userName">帐号：</label></td>
                <td>
                    <input id="userName" name="userName" class="mini-textbox" requiredErrorText="帐号不能为空"
                           required="true" style="width:150px;"/>
                </td>
            </tr>
            <tr>
                <td style="width:60px;"><label for="userPw">密码：</label></td>
                <td>
                    <input id="userPw" name="userPw" vtype="minLength:5" class="mini-password"
                           requiredErrorText="密码不能少于5个字符" required="true" style="width:150px;"/>
                    <!-- &nbsp;&nbsp;<a href="#">忘记密码?</a> -->
                </td>
            </tr>
            <!-- <tr>
                <td style="width:60px;"><label for="userPw">身份：</label></td>
                <td>
                    <input name="identity" class="mini-combobox"
					   style="width: 150px;" textField="text"
					   valueField="code" emptyText="请选择..." required="true"
					   url="/login/getIdentity.action" />
                </td>
            </tr> -->
            <tr>
                <td style="width:60px;">验证码：</td>
                <td><input id="authCode" name="authCode" class="mini-textbox" requiredErrorText="验证码不能为空"
                           required="true" vtype="int;maxLength:4" style="width:80px;" onenter="onLoginClick"/>
                    <span style="margin-left:8px;margin-bottom: 0px;">
                        <img id="img_code" title="看不清，点击换一张" style="cursor:pointer;margin-bottom: -10px; "
                             onclick="this.src='<c:url
                                     value='/Captcah/index.action'/>?now=' + new Date().getTime()"
                             src="<c:url value='/Captcah/index.action'/>"/>
                    </span>
                </td>
            </tr>
            <tr>
                <td></td>
                <td style="padding-top:5px;">
                    <a onclick="onLoginClick" class="mini-button" style="width:60px;">登录</a>
                    <a onclick="onResetClick" class="mini-button" style="width:60px;">重置</a>
                </td>
            </tr>
        </table>
    </div>

</div>


<script type="text/javascript">
    mini.parse();
    mini.get("userName").focus();
    var loginWindow = mini.get("loginWindow");
    loginWindow.show();
    /*window.onload = function(){
     $("#userName").focus();
     }*/
    function onLoginClick(e) {
        var form = new mini.Form("#loginWindow");
        //表单验证
        form.validate();
        if (form.isValid() == false) return;
        var data = form.getData();      //获取表单多个控件的数据
        var json = mini.encode(data); //转换成JSON数据
        //alert(json);
        $.ajax({
            url: "<c:url value='/login/loginCheck.action'/>",
            data: {json: json},
            type: "post",
            success: function () {
                loginWindow.hide();
                mini.loading("登录成功，马上转到系统...", "登录成功");
                setTimeout(function () {
                    window.location = "<c:url value='/login/welcome.action'/>";
                }, 1500);
            },

            error: function (jqXHR, textStatus, errorThrown) {
            	$("#img_code").trigger("onclick");
                mini.showMessageBox({
                    showModal: false,
                    width: 250,
                    title: "提示",
                    iconCls: "mini-messagebox-warning",
                    message: jqXHR.responseText,
                    timeout: 3000,
                    x: "right",
                    y: "bottom"
                });
            }
        });
    }
    function onResetClick(e) {
        var form = new mini.Form("#loginWindow");
        form.clear();
    }
    /////////////////////////////////////

</script>

</body>
</html>
