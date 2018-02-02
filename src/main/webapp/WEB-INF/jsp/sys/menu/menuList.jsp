<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>菜单列表</title>
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

<div style="padding-bottom:5px;padding-left: 0px;">

    <div id="form1">

        <table border="0" cellpadding="1" cellspacing="2">
            <tr>
                <td>
                    菜单名称：
                </td>
                <td>
                    <input id="menuName" emptyText="请输入菜单名称" name="menuName" class="mini-textbox"/>
                </td>
                <td>上一级菜单：</td>
                <td>
                    <input id="menuId" name="menuParentId" class="mini-combobox" style="width:150px;"
                           textField="menuName" valueField="menuId" emptyText="请选择..."
                           url="/menu/getParentMenu.action" showNullItem="true"
                           nullItemText="请选择..."/>
                </td>
                <td colspan="2" align="center"><a class="mini-button" iconCls="icon-search" onClick="search()">搜索</a>&nbsp;<a
                        class="mini-button" iconCls="icon-reload" onClick="clean()">重置</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div style="width:99%;">
    <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
        <table style="width:100%;">
            <tr>
                <td style="width:100%;">
                    <a class="mini-button" iconCls="icon-add" onclick="add()">增加</a>
                    <a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
                    <a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="datagrid1" class="mini-datagrid" style="width:99%;height:80%;" multiSelect="true"
     url="/menu/queryMenu.action" idField="menuId" allowResize="true"
     >
    <div property="columns">
        <div type="checkcolumn"></div>
        <div field="menuName" width="100" headerAlign="center" align="center">菜单名称</div>
        <div field="menuUrl" width="120" headerAlign="center" align="center">菜单URL</div>
        <div field="menuSeq" width="80" headerAlign="center" align="center">菜单顺序</div>
        <div field="iconClass" width="80" headerAlign="center" align="center">图标类型</div>
        <div field="iconPosition" width="80" headerAlign="center" align="center">图标位置</div>
        <div field="menuParentName" width="100" headerAlign="center" align="center">上一级菜单</div>
    </div>
</div>

<script type="text/javascript">

    mini.parse();
    var grid = mini.get("datagrid1");
    var form = new mini.Form("#form1"); //获取表单对象
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
            url: "<c:url value='/menu/addMenuPage.action'/>",
            title: "菜单添加", width: 558, height: 300,
            onload: function () {
                var iframe = this.getIFrameEl();
                var data = {action: "new"};
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {
                if (action == "save") {
                    grid.reload();
                    mini.showTips({
                        content: "<b>成功</b> <br/>菜单添加成功",
                        state: "success",
                        x: "center",
                        y: "center",
                        timeout: 3000
                    });
                }
            }
        });
    }
    function edit(id) {
        var row = grid.getSelected();
        if (row) {
            mini.open({
                url: "<c:url value='/menu/addMenuPage.action'/>",
                title: "菜单修改", width: 558, height: 300,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = {action: "edit", menuId: row.menuId};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    if (action == "save") {
                        grid.reload();
                        mini.showTips({
                            content: "<b>成功</b> <br/>菜单修改成功",
                            state: "success",
                            x: "center",
                            y: "center",
                            timeout: 3000
                        });
                    }
                }
            });
        }
    }
    function remove() {
        var rows = grid.getSelecteds();
        if (rows.length > 0) {
            if (confirm("确定删除选中记录？")) {
                var ids = [];
                for (var i = 0, l = rows.length; i < l; i++) {
                    var r = rows[i];
                    ids.push(r.menuId);
                }
                var menuId = ids.join(',');
                grid.loading("操作中，请稍后......");
                $.ajax({
                    url: "/menu/batchDeleteMenu.action?menuId="+menuId,
                    success: function () {
                        grid.reload();
                        mini.showTips({
                            content: "<b>成功</b> <br/>删除成功",
                            state: "success",
                            x: "center",
                            y: "center",
                            timeout: 3000
                        });
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        grid.reload();
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
        } else {
            alert("请选中一条记录");
        }
    }
</script>
</body>
</html>
