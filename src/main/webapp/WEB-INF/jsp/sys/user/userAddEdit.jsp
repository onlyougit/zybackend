<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>用户修改</title>
    <script type="text/javascript" src="<c:url value='/scripts/boot.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/Portal.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/jquery.form.js'/>"></script>
    <link rel="stylesheet" href="<c:url value='/scripts/portal.css'/>"/>

    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            border: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
        }
    </style>
</head>
<body>
<input type="hidden" name="action" id="action" class="mini-hidden">
<form id="form1" method="post" enctype="multipart/form-data">
    <input type="hidden" name="userId" id="userId" class="mini-hidden">
    <div style="padding-right: 11px;: 11px;padding-left: 11px; padding-bottom: 5px; padding-top: 10px;">
        <fieldset style="border:solid 1px #aaa;padding:3px;">
            <legend>基本信息</legend>
            <div style="padding:5px;">

                <table style="table-layout:fixed;">
                    <tr>
                        <td style="width:100px;">登录账号：</td>
                        <td style="width:170px;">
                            <input id="userName" name="userName" class="mini-textbox" required="true" emptyText="请输入登录账号"/>
                        </td>
                        <td style="width:100px;"> 用户名称：</td>
                        <td style="width:170px;">
                            <input name="realName" class="mini-textbox" required="true" emptyText="请输入真实姓名"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:100px;">邮箱：</td>
                        <td style="width:170px;">
                            <input name="userEmail" vtype="email" class="mini-textbox" emptyText="请输入常用邮箱"/></td>
                        <td style="width:100px;"> 状态：</td>
                        <td style="width:170px;">
                            <input id="status" name="status" class="mini-combobox" style="width:150px;"
                                   textField="text" valueField="code" emptyText="请选择..."
                                   url="/user/getStatus.action" required="true" showNullItem="true"
                                   nullItemText="请选择..."/>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:100px;"> 角色：</td>
                        <td style="width:170px;">
                            <input id="role" name="roleCustom.roleGroup" class="mini-combobox" style="width:150px;"
                                   textField="roleName" valueField="roleId" emptyText="请选择..." popupHeight="100px;"
                                   url="/user/getRole.action" multiSelect="true" required="true" showNullItem="true"
                                   nullItemText="请选择..."/>
                        </td>
                        <td style="width:100px;">密码：</td>
                        <td style="width:170px;">
                            <div style="color: red;">用户初始密码:888888</div>
                        </td>
                    </tr>
                </table>
            </div>
        </fieldset>
    </div>

    <div style="text-align:center;padding:10px;">
        <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>
        <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>
    </div>
</form>
<script type="text/javascript">
    mini.parse();

    var form = new mini.Form("form1");

    //标准方法接口定义
    function SetData(data) {
        mini.get("action").setValue(data.action);
        mini.get("userId").setValue(data.userId);
        if (data.action == "edit") {
            mini.get("userName").allowInput=false;
            //跨页面传递的数据对象，克隆后才可以安全使用
            data = mini.clone(data);
            $.ajax({
                url: "<c:url value='/user/editQueryUser.action'/>",
                type: "post",
                data: {userId: data.userId},
                cache: false,
                success: function (text) {
                    var o = mini.decode(text);
                    form.setData(o);
                    mini.get("status").setValue(o.status.code);
                    form.setChanged(false);
                }
            });
        }
    }
    function SaveData() {
        //$("form").submit();
        //表单验证
        form.validate();
        if (form.isValid() == false) return;
        var action = mini.get("action").getValue();
        var url;
        if ("edit" == action) {
            url = "<c:url value='/user/updateUserSubmit.action'/>";
        } else if ("new" == action) {
            url = "<c:url value='/user/insertUserSubmit.action'/>";
        } else {
            return;
        }
        var msgid = mini.loading("数据处理中，请稍后......", "处理数据");
        $('#form1').ajaxSubmit({
            url: url,//默认是form action
            success: function () {
                mini.hideMessageBox(msgid);
                CloseWindow("save");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                mini.hideMessageBox(msgid);
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

    function GetData() {
        var o = form.getData();
        return o;
    }
    function CloseWindow(action) {
        if (action == "close" && form.isChanged()) {
            if (confirm("数据被修改了，是否先保存？")) {
                return false;
            }
        }
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    }
    function onOk(e) {
        SaveData();
    }
    function onCancel(e) {
        CloseWindow("close");
    }

</script>

</body>
</html>