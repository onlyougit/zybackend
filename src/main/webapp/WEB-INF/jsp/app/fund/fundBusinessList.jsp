<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>营业部资金列表</title>
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
                    代理商名称：
                </td>
                <td>
                    <input name="agent.agentName" emptyText="请输入代理商名称" class="mini-textbox" onenter="search"/>
                </td>
                <td>代理商电话：</td>
                <td>
                    <input name="agent.agentPhone" emptyText="请输入代理商电话" class="mini-textbox" onenter="search"/>
                </td>
                <td>
                    电话号码：
                </td>
                <td>
                    <input name="customer.customerPhone" emptyText="请输入电话号码" class="mini-textbox" onenter="search"/>
                </td>
                <td>
                    客户姓名：
                </td>
                <td>
                    <input name="customer.customerRealName" emptyText="请输入姓名" class="mini-textbox" onenter="search"/>
                </td>
                <td align="center">
                    <a class="mini-button" iconCls="icon-search" onClick="search()">搜索</a>&nbsp;
                    <a class="mini-button" iconCls="icon-reload" onClick="clean()">重置</a>
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
                    <form action="/fund/exportFundBusiness.action" method="post" id="form2">
                        <input type="hidden" name="queryParam" id="queryParam" class="mini-hidden">
                        <a class="mini-button" iconCls="icon-download" onclick="exportFundBusiness()">导出</a>
                    </form>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="datagrid1" class="mini-datagrid" style="width:99%;height:80%;" sizeList="[20,30,50,100]" pageSize="20"
     url="/fund/queryFundBusiness.action" idField="id" allowResize="true" allowCellWrap="true"
     allowCellSelect="true">
    <div property="columns">
        <div type="indexcolumn"></div>
        <div field="agent.agentName" width="100" headerAlign="center" align="center">代理商名称</div>
        <div field="agent.agentPhone" width="120" headerAlign="center" align="center">代理电话</div>
        <div field="customer.customerName" width="120" headerAlign="center" align="center">交易账号</div>
        <div field="customer.customerPhone" width="120" headerAlign="center" align="center">电话号码</div>
        <div field="customer.customerRealName" width="120" headerAlign="center" align="center">客户姓名</div>
        <div field="balance" width="100" headerAlign="center" align="center">网页账户余额</div>
        <div field="depositBalance" width="150" headerAlign="center" align="center">盘中权益资金</div>
        <div field="flushTime" width="150" align="center" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss"
			 allowSort="true">刷新时间</div>
        <div name="action" width="100" headerAlign="center" align="center"
             renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
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

    function onActionRenderer(e) {
        var record = e.record;
        var uid = record.id;
        var rowIndex = e.rowIndex;
        var s  = ' <a class="Edit_Button" href="javascript:flush(\''
                + record.id+'\')">刷新</a>';
        return s;
    }
    function flush(e){
       grid.loading("操作中，请稍后......");
       $.ajax({
           url : "/fund/flushBalance.action",
           data : {
               id : e
           },
           success : function() {
               grid.reload();
               mini.showTips({
                   content : "<b>成功</b> <br/>刷新成功",
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
    function exportFundBusiness(){
        var data = form.getData();
        var json = mini.encode(data);
        mini.get("queryParam").setValue(json);
        $("#form2").submit();
    }
</script>
</body>
</html>
