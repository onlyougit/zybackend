<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>银行卡列表</title>
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
                <td>客户电话：</td>
                <td>
                    <input name="customer.customerPhone" emptyText="请输入客户电话" class="mini-textbox" onenter="search"/>
                </td>
                <td>状态：</td>
                <td>
                    <input name="auditStatus" class="mini-combobox"
					   style="width: 150px;" onvaluechanged="search()" textField="text"
					   valueField="code" emptyText="请选择..."
					   url="/bankcard/getAllBankCardStatus.action" showNullItem="true"
					   nullItemText="请选择..." />
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
                    <a class="mini-button" iconCls="icon-add" onclick="add()">添加银行卡</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="datagrid1" class="mini-datagrid" style="width:99%;height:70%;" sizeList="[20,30,50,100]" pageSize="20"
     url="/bankcard/queryBankCard.action" idField="id" allowResize="true" allowCellWrap="true"
     allowCellSelect="true">
    <div property="columns">
        <div type="indexcolumn"></div>
        <div field="bankName" width="100" headerAlign="center" align="center">银行名称</div>
        <div field="bankCardId" width="150" headerAlign="center" align="center">银行卡号</div>
        <div field="bankProvince" width="80" headerAlign="center" align="center">省份</div>
        <div field="bankCity" width="80" headerAlign="center" align="center">城市</div>
        <div field="bankAddress" width="150" headerAlign="center" align="center">地址</div>
        <div field="customer.customerName" width="100" headerAlign="center" align="center">交易账号</div>
        <div field="customer.customerPhone" width="100" headerAlign="center" align="center">手机号</div>
        <div field="customer.customerRealName" width="100" headerAlign="center" align="center">客户姓名</div>
        <div field="bankCardStatusEnum.text" width="80" headerAlign="center" align="center">状态</div>
        <div field="userName" width="100" headerAlign="center" align="center">操作人</div>
		<div field="operationTime" width="130" align="center" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss"
			 allowSort="true">操作时间</div>
        <div name="action" width="80" headerAlign="center" align="center"
             renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
        <div name="action" width="80" headerAlign="center" align="center"
             renderer="onActionRenderer2" cellStyle="padding:0;">删除</div>
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
        var s;
        if(record.bankCardStatusEnum.code==0){
        s = ' <a class="Edit_Button" href="javascript:audit(\''
                + record.id+'\')">审核</a>';
        	
        }else if(record.bankCardStatusEnum.code==1){
        	s = '通过';
        }else{
        	s = '不通过';
        }
        return s;
    }
    function onActionRenderer2(e) {
        var record = e.record;
        var uid = record.id;
        var rowIndex = e.rowIndex;
        var s = ' <a class="Edit_Button" href="javascript:remove(\''
                    + record.id+'\')">删除</a>';
        return s;
    }
    function add() {
        mini.open({
            url: "<c:url value='/bankcard/addBankcardPage.action'/>",
            title: "银行卡添加", width: 400, height: 400,
            onload: function () {
                var iframe = this.getIFrameEl();
            },
            ondestroy: function (action) {
                if (action == "save") {
                    grid.reload();
                    mini.showTips({
                        content: "<b>成功</b> <br/>银行卡添加成功",
                        state: "success",
                        x: "center",
                        y: "center",
                        timeout: 3000
                    });
                }
            }
        });
    }
    function audit(e){
        mini.open({
            url: "<c:url value='/bankcard/auditBankcardPage.action'/>",
            title: "审核", width: 350, height: 150,
            onload: function () {
                var iframe = this.getIFrameEl();
                var data = {id: e};
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {
                if (action == "save") {
                    grid.reload();
                    mini.showTips({
                        content: "<b>成功</b> <br/>审核成功",
                        state: "success",
                        x: "center",
                        y: "center",
                        timeout: 3000
                    });
                }
            }
        });
    }
    function remove(e){
        if (confirm("确定删除银行卡？")) {
            grid.loading("操作中，请稍后......");
            $.ajax({
                url : "/bankcard/removeBankcard.action",
                data : {
                    id : e
                },
                success : function() {
                    grid.reload();
                    mini.showTips({
                        content : "<b>成功</b> <br/>删除成功",
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
