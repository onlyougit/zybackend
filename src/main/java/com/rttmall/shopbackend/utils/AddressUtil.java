package com.rttmall.shopbackend.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class AddressUtil {
	private static Logger logger = LoggerFactory.getLogger(AddressUtil.class);

	/**
	 * 获得相同地址的所有具体地址：
	 * 如，获得https://www.baidu.com/(*).html的https://www.baidu.com/1.html和https://
	 * www.baidu.com/2.html
	 * 
	 * @param collectionAddress
	 * @return
	 */
	/*public static List<String> getSiteList(List<CollectCustom> collectCustoms) {
		List<String> addressDetailList = new ArrayList<String>();

		try {
			for (CollectCustom address : collectCustoms) {
				addressDetailList.addAll(getUrlList(address));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return addressDetailList;
	}*/

	/**
	 * 判断是否已采集过
	 * 
	 * @param address
	 *            将要采集的网址
	 * @param addresses
	 *            已经采集过的网址
	 * @return false 不采集address true 采集address
	 */
	/*public boolean ifCollection(String address, List<CollectCustom> addresses) {

		try {

			List<String> allAddress = getSiteList(addresses);
			// 如果已采集过的商品包含将要采集的网址，则不采集
			for (String string : allAddress) {
				if (address.equals(string)) {
					return false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}*/

	/**
	 * 获得详细地址,如：http://www.baidu.com/page1.html
	 * 
	 * @param collectionAddress
	 * @return
	 */
	/*public static List<String> getUrlList(CollectCustom collectCustom) {
		List<String> addressList = new ArrayList<String>();
		if (collectCustom != null) {
			String url = collectCustom.getCollectUrl();
			Integer firstItem = collectCustom.getFirstItem();
			Integer itemsNumber = collectCustom.getItemNumber();
			Integer tolerance = collectCustom.getTolerance();
			if (!StringUtils.isEmpty(url) && null != itemsNumber
					&& null != firstItem && null != tolerance) {
				// 等差
				for (int i = 1; i <= itemsNumber; i++) {
					String previewHtml = url.replace("(*)",(firstItem+(i-1)*tolerance)+"");
					addressList.add(previewHtml);
				}
			}
		}
		return addressList;
	}*/

	/**
	 * list转String 以逗号隔开
	 * 
	 * @param list
	 * @return
	 */
	public static String listToString(List<String> list) {
		String string = "'";
		for (int i = 0; i < list.size(); i++) {
			if (i != list.size() - 1) {
				string += list.get(i) + "','";
			} else {
				string += list.get(i) + "'";
			}
		}
		return string;
	}

	/**
	 * 采集网址详情
	 * 
	 * @param addressDetail
	 * @return
	 */
	public static String collection(String addressDetail) {
		String addressContent = "";
		int number = 0;
		for (int i = 0; i < 100; i++) {
			Document doc = null;
			try {
				doc = Jsoup.connect(addressDetail).get();
				// 采集商品分类页
				Element listItems = doc.getElementById("list-items");
				Elements picRinds = null;
				if (listItems != null) {
					picRinds = listItems.getElementsByClass("product");
				}
				// 采集商品店铺分类页
				Elements itemsList = doc.getElementsByClass("items-list");

				if (itemsList != null && itemsList.size() > 0) {
					Element aItemsList = itemsList.get(0);
					picRinds = aItemsList.getElementsByClass("pic-rind");
				}
				if (picRinds != null) {
					for (int j = 0; j < picRinds.size(); j++) {
						Element aPicRinds = picRinds.get(j);
						String string = aPicRinds.attr("href")
								.replace("https://", "").replace("http://", "")
								.replace("//", "");
						String[] splitUrl = string.split("\\?");
						if (splitUrl.length == 2) {
							addressContent += splitUrl[0] + "&&";
						} else {
							addressContent += string + "&&";
						}
					}
				}

				// 采集店铺首页
				Elements remItems = doc.getElementsByClass("rem-items");

				for (int j = 0; j < remItems.size(); j++) {
					Element remItem = remItems.get(j);
					Elements imgwrap = remItem.getElementsByClass("imgwrap");

					for (int k = 0; k < imgwrap.size(); k++) {
						Element aImgWrap = imgwrap.get(k);
						String string = aImgWrap.attr("href")
								.replace("https://", "").replace("http://", "");
						addressContent += string + "&&";
					}
				}

				if (listItems == null && itemsList.size() == 0
						&& remItems.size() == 0) {

				} else {
					logger.info("采集成功：" + addressDetail);
					i = 101;
				}

				number++;

			} catch (Exception e) {
				i = 0;
				if (number > 4005) {
					i = 101;
				}
			}

		}

		return addressContent;
	}

	/**
	 * 获得商品详情html
	 * 
	 * @param productUrl
	 * @return
	 */
	public static String collectionProductHtml(String productUrl) {
		String productHtml = "";
		long st = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			long et = System.currentTimeMillis();
			if ((et - st) > 300000) {
				System.out.println("collectionProductHtml 线程结束 productUrl ="
						+ productUrl);
				break;
			}
			Document doc = null;
			try {
				doc = Jsoup.connect("https://" + productUrl).get();

				Elements elements = doc.getElementsByTag("body");
				productHtml = elements.get(0).toString();
				logger.info("采集成功：" + productUrl);
				break;
			} catch (Exception e) {
				i = 0;
			}

		}

		return productHtml;
	}

	/**
	 * 获得商品详情html
	 * 
	 * @param productUrl
	 * @return
	 */
	public static Element collectionProductElement(String productUrl) {
		Element productHtml = null;
		Document doc = null;
		try {
			doc = Jsoup.connect("https://" + productUrl).get();

			Elements elements = doc.getElementsByTag("body");
			productHtml = elements.get(0);
		} catch (Exception e) {
			try {
				doc = Jsoup.connect("https://" + productUrl).get();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Elements elements = doc.getElementsByTag("body");
			productHtml = elements.get(0);
		}
		return productHtml;
	}

	/**
	 * 截取之间的文本
	 * 
	 * @author huangwenjie
	 *
	 * @param start
	 * @param end
	 * @return
	 *
	 * @date 2017年1月8日 下午2:13:42
	 */
	public static String getTagHtml(String productHtml, String startHtml,
			String endHtml) {
		if (productHtml.indexOf(startHtml) != -1) {
			int start = productHtml.indexOf(startHtml);
			// 开始位置后的那段html
			String ratHtml = productHtml.substring(start,
					productHtml.length() - 1);
			int middle = ratHtml.indexOf(endHtml);
			if (middle != -1) {
				int end = middle + start;
				String newHtml = productHtml.substring(
						start + startHtml.length(), end);
				return newHtml;
			}
		} else {

		}
		return "未获取到";
	}

	public static String romveTag(String productHtml, String startHtml,
			String endHtml) {
		for (int i = 0; i < 2; i++) {
			if (productHtml.indexOf(startHtml) != -1) {
				int start = productHtml.indexOf(startHtml);
				int end = productHtml.indexOf(endHtml);
				String newHtml = productHtml.substring(start, end);
				productHtml = productHtml.replace(newHtml + endHtml, "");
				i = 0;
			} else {

			}
		}
		return productHtml;
	}

	public static List<String> getImgUrl(String productHtml, String startHtml,
			String endHtml) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 2; i++) {
			if (productHtml.indexOf(startHtml) != -1) {
				int start = productHtml.indexOf(startHtml);
				// 开始位置后的那段html
				String ratHtml = productHtml.substring(start,
						productHtml.length() - 1);
				int middle = ratHtml.indexOf(endHtml);
				if (middle != -1) {
					int end = middle + start;
					String newHtml = productHtml.substring(start, end);
					productHtml = productHtml.replace(newHtml + endHtml, "");
					String html = newHtml.replace("src=\"", "").replace(
							"https", "http");
					if (!html.contains("HTB1R4dUMXXXXXaZXXXXq6xXFXXXZ")
							&& !html.contains("HTB1oNlaKXXXXXawXVXXq6xXFXXXx")
							&& !html.contains("HTB18yoRKVXXXXXCXFXXq6xXFXXX6")
							&& !html.contains("HTB1mOZRKXXXXXaTXXXXq6xXFXXX9")
							&& !html.contains("HTB1BcNzMXXXXXbXaXXXq6xXFXXXt")
							&& !html.contains("HTB11q8AMXXXXXaMaXXXq6xXFXXXi")
							&& !html.contains("UT8V99GXlJcXXagOFbXJ")
							&& !html.contains("HTB1ym6ANFXXXXcsaXXXq6xXFXXXs")
							&& !html.contains("HTB1eNgbJpXXXXbDXXXXq6xXFXXXY")) {
						String[] array = html.split("\" width=\"");
						array = array[0].split("\" style=\"");
						array = array[0].split(";");
						list.add(array[0]);
					}
					i = 0;
				}
			} else {

			}
		}
		//
		// Set<String> set = new HashSet<String>();
		// List<String> newList = new ArrayList<String>();
		// set.addAll(list);
		// newList.addAll(set);

		return list;
	}

	public static String replaceTag(String productHtml, String startHtml,
			String endHtml, String newAppendHtml) {

		for (int i = 0; i < 2; i++) {
			if (productHtml.indexOf(startHtml) != -1) {
				int start = productHtml.indexOf(startHtml);
				// 开始位置后的那段html
				String ratHtml = productHtml.substring(start,
						productHtml.length());
				int middle = ratHtml.indexOf(endHtml);
				if (middle != -1) {
					int end = middle + start;
					productHtml = productHtml.substring(
							start + startHtml.length(), end);
					i = 0;
				}
			} else {

			}
			if (productHtml.contains("jpg_")) {
				String[] htmls = productHtml.split("jpg_");
				if (htmls.length > 0 && htmls[0].contains("http")) {
					productHtml = htmls[0] + "jpg";
				}
			}
		}
		return productHtml.replace("https", "http");
	}

	/**
	 * 属性
	 * 
	 * @param span
	 * @return
	 */
	public static String getAttrVal(Element span) {

		String attrVal = "";
		String spanHtml = span.toString();
		if (spanHtml.contains("span")) {
			// <span>M</span>或<span class="sku-color-175" title="Green"></span>
			String startHtml = "title=\"";
			int start = spanHtml.indexOf(startHtml);
			int end = spanHtml.indexOf("\">");

			if (start == -1) {
				attrVal = span.html();
			} else {
				attrVal = spanHtml.substring(start + startHtml.length(), end);
			}
		} else if (span.toString().contains("img")) {
			// <img src="https://ae01.alicdn.com/Mini.jpg_50x50.jpg"
			// title="Grey" bigpic="https://ae01.alicdn.com/kf/Mini.jpg">
			String startHtml = "title=\"";
			int start = spanHtml.indexOf(startHtml);
			String str1 = spanHtml.substring(start + startHtml.length(),
					spanHtml.length());

			attrVal = str1.split("\"")[0];

		}

		return attrVal;
	}

	public static List<String> getDetailImg(String productId) {
		String detailImgUrl = "www.aliexpress.com/getDescModuleAjax.htm?productId=";
		Document doc = null;
		long st = System.currentTimeMillis();
		for (int i = 0; i < 2; i++) {
			try {
				long et = System.currentTimeMillis();
				if ((et - st) > 300000) {
					System.out.println("getDetailImg 线程结束 productId ="
							+ productId);
					break;
				}
				doc = Jsoup.connect("https://" + detailImgUrl + productId)
						.get();

				String jProductDescriptioHtml = doc.toString();
				jProductDescriptioHtml = AddressUtil.romveTag(
						jProductDescriptioHtml, "<a", "</a>");
				List<String> list = AddressUtil.getImgUrl(
						jProductDescriptioHtml, "src=\"", "\">");
				return list;
			} catch (Exception e) {
				i = 0;
			}
		}

		return null;
	}

	public static List<String> getProductUrl(String productUrls) {
		List<String> list = new ArrayList<String>();
		String[] urls = productUrls.split("&&");

		for (String productUrl : urls) {
			list.add(productUrl);
		}

		return list;
	}
}
