<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>银行卡添加</title>
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
<form id="form1" method="post" enctype="multipart/form-data">
    <div style="padding-right: 11px;: 11px;padding-left: 11px; padding-bottom: 5px; padding-top: 10px;">
        <fieldset style="border:solid 1px #aaa;padding:3px;">
            <legend>基本信息</legend>
            <div style="padding:5px;">
                <table style="table-layout:fixed;">
                    <tr>
                        <td style="width:100px;"> 客户电话：</td>
                        <td style="width:170px;">
                            <input name="customer.customerPhone" class="mini-textbox" style="width: 200px;" required="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:100px;">客户姓名：</td>
                        <td style="width:170px;">
                            <input name="customer.customerRealName" class="mini-textbox" style="width: 200px;" required="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:100px;"> 银行卡号：</td>
                        <td style="width:170px;">
                            <input name="bankCardId" class="mini-textbox" style="width: 200px;" required="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:100px;"> 银行名称：</td>
                        <td style="width:170px;">
                            <input name="bankName" class="mini-textbox" style="width: 200px;" required="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:100px;"> 开户省份：</td>
                        <td style="width:170px;">
                            <input name="bankProvince" class="mini-textbox" style="width: 200px;" required="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:100px;"> 开户城市：</td>
                        <td style="width:170px;">
                            <input name="bankCity" class="mini-textbox" style="width: 200px;" required="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:100px;"> 支行名称：</td>
                        <td style="width:170px;">
                            <input name="bankAddress" class="mini-textbox" style="width: 200px;" required="true"/>
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
    function SaveData() {
        //表单验证
        form.validate();
        if (form.isValid() == false) return;
        var msgid = mini.loading("数据处理中，请稍后......", "处理数据");
        $('#form1').ajaxSubmit({
            url: "<c:url value='/bankcard/insertBankcardSubmit.action'/>",
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