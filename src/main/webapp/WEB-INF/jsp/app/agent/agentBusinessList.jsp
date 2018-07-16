<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>营业部代理列表</title>
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
    <link href="/css/demo.css" rel="stylesheet" type="text/css"/>
    <script src="/scripts/boot.js" type="text/javascript"></script>
</head>
<body>

<div style="width:99%;">
    <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
    <div id="form1">
        <table style="width:100%;">
            <tr>
	            <td style="width:100%;">
	                <a class="mini-button" iconCls="icon-add" onclick="add()">添加</a>
	            </td>
	            <td style="white-space:nowrap;">
	                <input name="agentName" class="mini-textbox" emptyText="请输入代理商名称" style="width:150px;" onenter="search"/>
	            </td>
	            <td style="white-space:nowrap;">
	                <input name="agentPhone" class="mini-textbox" emptyText="请输入代理商电话" style="width:150px;" onenter="search"/>
	            </td>
            </tr>
        </table>
        </div>
    </div>
</div>
<div id="datagrid1" class="mini-datagrid" style="width:99%;height:80%;" sizeList="[20,30,50,100]" pageSize="20"
     url="/agent/queryBusinessAgent.action" idField="id" allowResize="true" allowCellWrap="true" 
     allowCellSelect="true">
    <div property="columns">
        <div type="indexcolumn"></div>
        <div field="agentName" width="120" headerAlign="center" align="center">代理商名称</div>
        <div field="agentExtensionLink" width="150" headerAlign="center" align="center">代理推广链接</div>
        <div field="agentPhone" width="120" headerAlign="center" align="center">代理电话</div>
        <div field="userName" width="100" headerAlign="center" align="center">登录账号</div>
        <div field="templateAccount" width="100" headerAlign="center" align="center">模板账户</div>
        <!--<div field="serviceChargeStandard" width="100" headerAlign="center" align="center">手续费标准</div>
        <div field="serviceChargeCost" width="100" headerAlign="center" align="center">手续费成本</div>
		<div field="bondStandard" width="100" headerAlign="center" align="center">保证金标准</div>-->
        <div field="agentStatusEnum.text" width="80" headerAlign="center" align="center">状态</div>
        <div name="action" width="100" headerAlign="center" align="center"
             renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
    </div>
</div>
<div id="showWindow" class="mini-window" title="重置密码结果"
           style="width: 200px;" showModal="true" allowResize="true"
           allowDrag="true">
    <table border="0" cellpadding="1" cellspacing="2">
        <tr>
            <td>登录账号：</td>
            <td id="loginAccount"></td>
        </tr>
        <tr>
            <td>重置密码：</td>
            <td id="resetPw"></td>
        </tr>
    </table>
</div>
<script type="text/javascript">

    mini.parse();
    var grid = mini.get("datagrid1");
    var form = new mini.Form("#form1"); //获取表单对象
    var showWindow = mini.get("showWindow");
    grid.load();
    //查询
    function search() {
        var data = form.getData();      //获取表单多个控件的数据
        var json = mini.encode(data);   //序列化成JSON
        grid.load({json: json});
    }

    function clean() {
        form.reset();
    }

    function add() {
        mini.open({
            url: "<c:url value='/agent/addAgentPage.action'/>",
            title: "代理商添加", width: 350, height: 200,
            onload: function () {
            },
            ondestroy: function (action) {
                if (action == "save") {
                    grid.reload();
                    mini.showTips({
                        content: "<b>成功</b> <br/>代理商添加成功",
                        state: "success",
                        x: "center",
                        y: "center",
                        timeout: 3000
                    });
                }
            }
        });
    }

    function onActionRenderer(e) {
        var record = e.record;
        var uid = record.id;
        var rowIndex = e.rowIndex;
        var s = '<a class="Edit_Button" href="javascript:resetPw(\''+ record.id+'\')">重置密码</a>&nbsp;'
        +'<a class="Edit_Button" href="javascript:edit(\''+ record.id+'\')">编辑</a>';
        return s;
    }
    function edit(id) {
        mini.open({
            url: "<c:url value='/agent/businessEditAgentPage.action'/>",
            title: "代理商编辑", width: 350, height: 200,
            onload: function () {
                var iframe = this.getIFrameEl();
                var data = {action: "edit", agentId: id};
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {
                if (action == "save") {
                    grid.reload();
                    mini.showTips({
                        content: "<b>成功</b> <br/>代理商编辑成功",
                        state: "success",
                        x: "center",
                        y: "center",
                        timeout: 3000
                    });
                }
            }
        });
    }
    function resetPw(id){
        if (confirm("确定重置密码？")) {
            grid.loading("操作中，请稍后......");
            $.ajax({
                url : "/agent/updateAgentPw.action",
                data : {
                    id : id,
                },
                success : function(text) {
                    $("#loginAccount").text(text.userName);
                    $("#resetPw").text(text.resetPw);
                    showWindow.show();
                    grid.reload();
                    mini.showTips({
                        content : "<b>成功</b> <br/>重置成功",
                        state : "success",
                        x : "center",
                        y : "center",
                        timeout : 3000
                    });
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    grid.reload();
                    mini.showMessageBox({
                        showModal : false,
                        width : 250,
                        title : "提示",
                        iconCls : "mini-messagebox-warning",
                        message : jqXHR.responseText,
                        timeout : 3000,
                        x : "right",
                        y : "bottom"
                    });
                }
            });
        }
    }
</script>
</body>
</html>
