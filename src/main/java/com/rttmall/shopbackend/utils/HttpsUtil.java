package com.rttmall.shopbackend.utils;


import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
import javax.net.ssl.*;
import com.rttmall.shopbackend.pojo.Result;
import com.rttmall.shopbackend.pojo.Root;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

public class HttpsUtil {

	private static final String ACCOUNT_URL = "https://101.132.99.108:13134/createaccount";
	private static final String BOND_URL = "https://101.132.99.108:13134/setmarginrate";
	private static final String SERVICE_CHARGE_URL = "https://101.132.99.108:13134/setcommissionrate";
	private static final String RISK_URL = "https://101.132.99.108:13134/setriskcontrol";
	private static final String DEPOSIT_URL = "https://101.132.99.108:13134/deposit";
	private static final String QUERY_DEPOSIT_URL = "https://101.132.99.108:13134/queryaccount";
	public static final String CREDIT_VALUE = "0";
	
	final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};
	public static String httpGet(String httpsUrl) {
		StringBuffer tempStr = new StringBuffer();
		String responseContent = "";
		HttpURLConnection conn = null;
		try {
			trustAllHosts();

			URL url = new URL(httpsUrl);

			HttpsURLConnection https = (HttpsURLConnection) url
					.openConnection();
			if (url.getProtocol().toLowerCase().equals("https")) {
				https.setHostnameVerifier(DO_NOT_VERIFY);
				conn = https;
			} else {
				conn = (HttpURLConnection) url.openConnection();
			}
			conn.connect();
			InputStream in = conn.getInputStream();
			conn.setReadTimeout(10 * 1000);
			BufferedReader rd = new BufferedReader(new InputStreamReader(in,
					"GB2312"));
			String tempLine;
			while ((tempLine = rd.readLine()) != null) {
				tempStr.append(tempLine);
			}
			responseContent = tempStr.toString();
			rd.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseContent;
	}

	private static void trustAllHosts() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}

			public void checkClientTrusted(X509Certificate[] chain,
					String authType) {

			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) {

			}
		} };

		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Root xmlToBean1(String url){
		XMLSerializer serializer = new XMLSerializer();
		String response = httpGet(url);
		JSON json = serializer.read(response);
		return com.alibaba.fastjson.JSON.parseObject(json.toString(), Root.class);
	}
	public static Result xmlToBean2(String url){
		XMLSerializer serializer = new XMLSerializer();
		String response = httpGet(url);
		JSON json = serializer.read(response);
		return com.alibaba.fastjson.JSON.parseObject(json.toString().substring(1,json.toString().length()-1), Result.class);
	}
	public static Root createaccount(String customerPassword,String customerPhone,String sa,String sapass){
		StringBuilder accountUrl = new StringBuilder();
		accountUrl.append(ACCOUNT_URL);
		accountUrl.append("?sa=");
		accountUrl.append(sa);
		accountUrl.append("&sapass=");
		accountUrl.append(sapass);
		accountUrl.append("&account=0");
		accountUrl.append("&password=");
		accountUrl.append(customerPassword);
		//accountUrl.append("&group=");
		//accountUrl.append(group+agent.getTemplateAccount());
		accountUrl.append("&name=");
		accountUrl.append(customerPhone);
		return xmlToBean1(accountUrl.toString());
	}
	public static Root setmarginrate(String templateAccount,String account,String sa,String sapass){
		StringBuilder bondUrl = new StringBuilder();
		bondUrl.append(BOND_URL);
		bondUrl.append("?sa=");
		bondUrl.append(sa);
		bondUrl.append("&sapass=");
		bondUrl.append(sapass);
		bondUrl.append("&source=");
		bondUrl.append(templateAccount);
		bondUrl.append("&account=");
		bondUrl.append(account);
		return xmlToBean1(bondUrl.toString());
	}
	public static Root setcommissionrate(String templateAccount,String account,String sa,String sapass){
		StringBuilder serviceChargeUrl = new StringBuilder();
		serviceChargeUrl.append(SERVICE_CHARGE_URL);
		serviceChargeUrl.append("?sa=");
		serviceChargeUrl.append(sa);
		serviceChargeUrl.append("&sapass=");
		serviceChargeUrl.append(sapass);
		serviceChargeUrl.append("&source=");
		serviceChargeUrl.append(templateAccount);
		serviceChargeUrl.append("&account=");
		serviceChargeUrl.append(account);
		return xmlToBean1(serviceChargeUrl.toString());
	}
	public static Root setriskcontrol(String templateAccount,String account,String sa,String sapass){
		StringBuilder riskUrl = new StringBuilder();
		riskUrl.append(RISK_URL);
		riskUrl.append("?sa=");
		riskUrl.append(sa);
		riskUrl.append("&sapass=");
		riskUrl.append(sapass);
		riskUrl.append("&source=");
		riskUrl.append(templateAccount);
		riskUrl.append("&account=");
		riskUrl.append(account);
		return xmlToBean1(riskUrl.toString());
	}
	public static Root deposit(String customerName,double amount,String sa,String sapass){
		StringBuilder depositUrl = new StringBuilder();
		depositUrl.append(DEPOSIT_URL);
		depositUrl.append("?sa=");
		depositUrl.append(sa);
		depositUrl.append("&sapass=");
		depositUrl.append(sapass);
		depositUrl.append("&account=");
		depositUrl.append(customerName);
		depositUrl.append("&amount=");
		depositUrl.append(amount);
		depositUrl.append("&credit=");
		depositUrl.append(CREDIT_VALUE);
		return xmlToBean1(depositUrl.toString());
	}
	public static Result queryaccount(String customerName,String sa,String sapass){
		StringBuilder queryDepositUrl = new StringBuilder();
		queryDepositUrl.append(QUERY_DEPOSIT_URL);
		queryDepositUrl.append("?sa=");
		queryDepositUrl.append(sa);
		queryDepositUrl.append("&sapass=");
		queryDepositUrl.append(sapass);
		queryDepositUrl.append("&account=");
		queryDepositUrl.append(customerName);
		return xmlToBean2(queryDepositUrl.toString());
	}
}
