$(function(){
	$.extend({
		'post':function(parameter,url,callback){ //Ajax POST
			//alert(json);
			var msgid = mini.loading("数据处理中，请稍后......", "处理数据");
			$.ajax({
				url: url,
				type: "post",
				data: parameter,
				success: function (text) {
					var data = mini.decode(text);   //反序列化成对象
					if(data.success){ //如果处理成功
						mini.showMessageBox({ //显示消息提示框
            				width: 280,
            				title: "提示信息",
            				buttons: ["确定"],
            				message: data.message,
            				iconCls: "mini-messagebox-info",
            				callback: function (action) {
                				callback(data,action); //回调函数
								mini.hideMessageBox(msgid);
            				}
        				});
					}else{
						mini.showMessageBox({
            				width: 280,
            				title: "错误信息",
            				buttons: ["确定"],
            				message: data.message,
            				iconCls: "mini-messagebox-error",
            				callback: function (action) {
                				//alert(action);
								mini.hideMessageBox(msgid);
            				}
        				});
					} //end id
				}, //end success
				error: function (jqXHR, textStatus, errorThrown) {
					mini.hideMessageBox(msgid);
            		alert(jqXHR.responseText);
            	}
			}); //end ajax
		}, //end function
		'treeReload':function(name,url){
			var tree = mini.get(name);
			tree.load(url);
		}, //end function
		'information':function(data){
			mini.showMessageBox({ //显示消息提示框
          		width: 280,
            	title: "提示信息",
            	buttons: ["确定"],
            	message: data.message,
            	iconCls: "mini-messagebox-info",
            	callback: function (action) {
					//mini.hideMessageBox(msgid);
            	}
        	});
		}, //end function
		'error':function(data){
			mini.showMessageBox({
           		width: 280,
            	title: "错误信息",
            	buttons: ["确定"],
            	message: data.message,
            	iconCls: "mini-messagebox-error",
            	callback: function (action) {
                	//alert(action);
					//mini.hideMessageBox(msgid);
            	}
        	});
		} //end function
	});
});

//ajax拦截, 拦截用户会话过期
$(document).ajaxComplete(function (evt, request, settings) {
    var data = request.responseText; //返回的json格式数据
	var json = mini.decode(data);   //反序列化成对象
	if(typeof(json.success) != "undefined"){
		//alert(json.code);
		if(json.code == "SESSION_TIMEOUT"){
			var parWin = window.opener;
			if(parWin!=null){
				parWin.top.location="Logout.htm";
				parWin=null;
				window.close();
			}else{
				window.top.location="Logout.htm";
			}
		}else{
			if(!json.success){
				$.error(json);
			}
		}
	}
	
    //判断返回的数据内容，如果是超时，则跳转到登陆页面
    //if (text == "logout") {
    //    top.location = '/login.html';
    //}
});

/**
 * 将数值四舍五入(保留2位小数)后格式化成金额形式
 *
 * @param num 数值(Number或者String)
 * @return 金额格式的字符串,如'1,234,567.45'
 * @type String
 */
function formatCurrency(num) {
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
    num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num+0.50000000001);
    cents = num%100;
    num = Math.floor(num/100).toString();
    if(cents<10)
    cents = "0" + cents;
    //for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
    //num = num.substring(0,num.length-(4*i+3))+','+
    //num.substring(num.length-(4*i+3));
    return (((sign)?'':'-') + num + '.' + cents);
}
 
 
 
/**
 * 将数值四舍五入(保留1位小数)后格式化成金额形式
 *
 * @param num 数值(Number或者String)
 * @return 金额格式的字符串,如'1,234,567.4'
 * @type String
 */
function formatCurrencyTenThou(num) {
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
    num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num*10+0.50000000001);
    cents = num%10;
    num = Math.floor(num/10).toString();
    //for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
    //num = num.substring(0,num.length-(4*i+3))+','+
    //num.substring(num.length-(4*i+3));
    return (((sign)?'':'-') + num + '.' + cents);
}

//限制非法字符
function validateSpecialCharacter(event) {
	var code;
	var e = window.event || event;
	var code = e.keyCode || e.which || e.charCode;
   	var character = String.fromCharCode(code);
   	//特殊字符正则表达式
	//var patt = /\'|\"|\/|\%|\+|\$|\<|\>|\(|\)|\&/;
   	if (isSpecialCharacter(character)) {
    	if (document.all) {
     		window.event.returnValue = false;
    	} else {
     		event.preventDefault();
    	}
   	}
}
document.onkeypress = validateSpecialCharacter;

//forbid backspace k.l
function fbdToBs(event){
	var oev=event||window.event;
	var obj=oev.target||oev.srcElement;
	var t=obj.type||obj.getAttribute('type');
	var oReadOnly=obj.readOnly;
	var oDisabled=obj.disabled;
	oReadOnly=(oReadOnly==undefined)?false:oReadOnly;
	oDisabled=(oDisabled==undefined)?true:oDisabled;
	var ofg1=oev.keyCode==8&&(t=="password"||t=="text"||t=="textarea")&&(oReadOnly==true||oDisabled==true);
	var ofg2=oev.keyCode==8&&t!="password"&&t!="text"&&t!="textarea";
	if(ofg1||ofg2){return false;}
}
document.onkeydown=fbdToBs;
	
//是否包含特殊字符
function isSpecialCharacter(characters){
	var patt = /\'|\"|\/|\%|\+|\$|\<|\>|\(|\)|\&/;
	for(var i=0; i<characters.length; i++){
		if(patt.test(characters.substr(i, 1))){
			return true;
		}
	}
	return false;
}

// 验证中文字符和特殊字符
function chineseVaildate(value){
   if (value == null || value=="")
    return true;
     if ((/[\u4E00-\u9FA5]+/.test(value))){
    return false;
   }
   return true;
}

//验证表单数据是否包含
function onSpecialCharacterValidation(e) {
	if (e.isValid) {
    	if (isSpecialCharacter(e.value)) {
        	e.errorText = "输入中包含非法字符";
            e.isValid = false;
        }
    }
}

//验证金额
function onMoneyValidation(e) {
	var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
	if (e.isValid) {
    	if (!exp.test(e.value)) {
        	e.errorText = "金额格式错误";
            e.isValid = false;
        }
    }
}

//验证起始日期
function checkstartdate(e){
	 var startTime=mini.getbyName("startTime").getValue();
	 var endTime=mini.getbyName("endTime").getValue();
  	 if (startTime=="") {
  	    e.errorText = "请输入起始时间";
            e.isValid = false; 
          }
  	 else if (endTime=="") {
         }
  	 else if(startTime>endTime){
  	    e.errorText = "起始时间不能大于截止时间";
            e.isValid = false; 
  	 }
}
	
//验证截止日期
 function checkenddate(e){
	 var startTime=mini.getbyName("startTime").getValue();
	 var endTime=mini.getbyName("endTime").getValue();
  	 if (endTime=="") {
  	    e.errorText = "请输入截止时间";
            e.isValid = false; 
         }
  	     else if (startTime=="") {
         }
  	 else if(startTime>endTime){
  	    e.errorText = "截止时间不能小于起始时间";
            e.isValid = false; 
  	 }
}

 /**
  * 非法字符校验
  * @param e
  */
 function onCharacterValidation(e) {
 	if (e.isValid) {
 		var regx = /[@#\$%\^&\*]+/g ;
 		if(regx.test(e.value) == true){
 			e.errorText = "包含非法字符";
             e.isValid = false;
 	    }
 	}
 }

 /**
  * 商户证号
  * @param e
  */
 function onMerLicenseValidation(e) {
 	var licenseKind = mini.get("licenseKind").getValue();
 	if (licenseKind == 17) {
 		onIDCardsValidation(e);
 	}
 }
 
 /**
  * 身份证号
  * @param e
  */
 function onIDCardsValidation(e) {
 	if (e.isValid) {
 		var regx = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
 		if (regx.test(e.value) == false) {
         	e.errorText = "必须填写15位或18位身份证号";
             e.isValid = false;
         }
 	}
 }

 /**
  * 手机号
  * @param e
  */
 function onMobileValidation(e) {
 	if (e.isValid) {
 		var regx = /^1\d{10}$/;
 		if(regx.test(e.value) == false){
 			e.errorText = "11位手机号码格式错误";
             e.isValid = false;
 	    }
 	}
 }

 /**
  * 座机号
  * @param e
  */
 function onTelephoneValidation(e) {
 	if (e.isValid && e.value != "") {
 		var regx = /^0\d{2,3}-?\d{7,8}$/;
 		if(regx.test(e.value) == false){
 			e.errorText = "请填写正确的座机号码：XXX-XXXXXXX";
             e.isValid = false;
 	    }
 	}
 }

 /**
  * 联系电话
  * @param e
  */
 function onContactValidation(e) {
 	if (e.isValid && e.value != "") {
 		var regx = /^1\d{10}$/;
 		var regx2 = /^0\d{2,3}-?\d{7,8}$/;
 		if(regx.test(e.value) == false && regx2.test(e.value) == false){
 			e.errorText = "请填写正确的手机号码或座机号码";
             e.isValid = false;
 	    }
 	}
 }
 
//验证金额
 function onBigDecimalValidation(e) {
 	if (e.isValid && e.value != "") {
 		onMoneyValidation(e);
     }
 }
 
//验证网络证书
 function onInternetPosRela(e) {
	 var certType = mini.get("certType").getValue();
 	if (certType != "" && e.value == "") {
 		e.errorText = "不能为空";
        e.isValid = false;
     }
 }
 
 //验证百分比
 function onPercentValidation(e) {
	 	if (e.isValid && e.value != "") {
	 		var exp = /^([0-9][\d]{0,2}|0)(\.[\d]{1,5})?$/;
	 		if (e.isValid) {
	 	    	if (!exp.test(e.value)) {
	 	        	e.errorText = "百分比格式错误";
	 	            e.isValid = false;
	 	        }
	 	    }
	     }
	 }
 