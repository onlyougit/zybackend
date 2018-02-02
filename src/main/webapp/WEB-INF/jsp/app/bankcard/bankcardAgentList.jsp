<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>代理商提款申请列表</title>
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
<div id="datagrid1" class="mini-datagrid" style="width:99%;height:80%;" sizeList="[20,30,50,100]" pageSize="20"
     url="/bankcard/queryBankCardAgent.action" idField="id" allowResize="true" allowCellWrap="true"
     allowCellSelect="true">
    <div property="columns">
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
		<div field="operationTime" width="120" align="center" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss"
			 allowSort="true">操作时间</div>
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
</script>
</body>
</html>
