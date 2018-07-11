/**
 * copyright(c) 2013 FuJian star-net Information Corp.Ltd
 *
 * @File name:  Constants.java
 * @Version : 1.0
 * @Create on:  2013-07-17
 * @Author :  lujunming
 * @ChangeList ---------------------------------------------------
 * Date         Editor              ChangeReasons
 */
package com.rttmall.shopbackend.utils;

/**
 * 功能: 全局常量
 */
public class Constants {

	public static final String SESSION_BEAN = "sessionBean";

	public static final int PAGE_SIZE = 20;
	
	public static final Integer SKUATTR_EXCEL_NUMBER=1010000;

	// 错误异常信息
	public static final String AUTH_CODE_ERROR = "验证码错误";
	public static final String USER_INVALID = "用户已被注销";
	public static final String USER_NOT_ERROR = "用户名或密码错误";
	public static final String REQUEST_DATA_ERROR = "请求数据错误";
	public static final String PASSWORD_ERROR = "密码不正确";
	public static final String UPDATEPW_ERROR = "密码修改失败";
	public static final String USERNAME_EXIST = "账户已被注册";
	public static final String ROLENAME_EXIST = "角色名称已存在";
	public static final String USING_THIS_ROLE = "角色不能删除";
	public static final String MENUNAME_EXIST = "菜单名称已存在";
	public static final String USING_THIS_MENU = "菜单不能删除";
	public static final String USING_THIS_PERMISSION = "权限不能删除";
	public static final String SHORT_NAME_REPEAT = "简称重复";

	// 商品提示信息
	public static final String STATUS_ATYPISM = "商品状态不一致";
	public static final String UPDATE_ERROR = "商品添加失败";
	public static final String HAVE_IN_HAND = "有商品正在抽奖";
	public static final String VIRTUAL_END = "该商品虚拟卡已用完";
	public static final String PRODUCT_NOT_EXIST = "商品不存在";

	//状态码
	public static final String SUCCESS_CODE = "1";
	public static final String SUCCESS_MSG = "success";
	public static final String FAIL_CODE = "0";
	public static final String FAIL_MSG = "system error!";
	public static final String TOKEN_ERROR_CODE = "2";
	public static final String TOKEN_ERROR_MSG = "token error!";
	public static final String USERNAME_PASSWORD_ERROR_MSG = "username or password error!";

	public static final String FILE_FORMAT_ERROR = "文件格式不对（xls、xlsx）";

	public static final String TEMPLATE_ACCOUNT_NOT_EXIST = "模版账号未添加";
	public static final String TEMPLATE_ACCOUNT_ADD_ONCE = "模版账号只能添加一次";
	public static final String AGENT_ADD_EDIT_ERROR = "手续费标准或保证金标准不对";

	public static final String CUSTOMER_NOT_EXIST = "客户信息不对";

	public static final String BANKCARD_NOT_EXIST = "卡号不对";
	public static final String BALANCE_NOT_ENOUGH = "余额不足";
	public static final String BALANCE_QUERY_FAILED = "刷新失败";
}
