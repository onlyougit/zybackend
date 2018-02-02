package com.rttmall.shopbackend.utils;

import java.util.ArrayList;
import java.util.List;

public class Descartes {
	/**
	 * 递归实现笛卡尔积
	 * @param dimValue
	 * @param result
	 * @param layer
	 * @param curList
	 */
	public static void recursive(List<List<String>> dimValue,
			List<List<String>> result, int layer, List<String> curList) {
		if (layer < dimValue.size() - 1) {
			if (dimValue.get(layer).size() == 0) {
				recursive(dimValue, result, layer + 1, curList);
			} else {
				for (int i = 0; i < dimValue.get(layer).size(); i++) {
					List<String> list = new ArrayList<String>(curList);
					list.add(dimValue.get(layer).get(i));
					recursive(dimValue, result, layer + 1, list);
				}
			}
		} else if (layer == dimValue.size() - 1) {
			if (dimValue.get(layer).size() == 0) {
				result.add(curList);
			} else {
				for (int i = 0; i < dimValue.get(layer).size(); i++) {
					List<String> list = new ArrayList<String>(curList);
					list.add(dimValue.get(layer).get(i));
					result.add(list);
				}
			}
		}
	}
	/*public static void recursived(List<List<SkuattrCustom>> dimValue,
			List<List<SkuattrCustom>> result, int layer, List<SkuattrCustom> curList) {
		if (layer < dimValue.size() - 1) {
			if (dimValue.get(layer).size() == 0) {
				recursived(dimValue, result, layer + 1, curList);
			} else {
				for (int i = 0; i < dimValue.get(layer).size(); i++) {
					List<SkuattrCustom> list = new ArrayList<SkuattrCustom>(curList);
					list.add(dimValue.get(layer).get(i));
					recursived(dimValue, result, layer + 1, list);
				}
			}
		} else if (layer == dimValue.size() - 1) {
			if (dimValue.get(layer).size() == 0) {
				result.add(curList);
			} else {
				for (int i = 0; i < dimValue.get(layer).size(); i++) {
					List<SkuattrCustom> list = new ArrayList<SkuattrCustom>(curList);
					list.add(dimValue.get(layer).get(i));
					result.add(list);
				}
			}
		}
	}*/
	/**
	 * 测试方法
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list1 = new ArrayList<String>();
		list1.add("1");
		list1.add("2");

		List<String> list2 = new ArrayList<String>();
		list2.add("a");

		List<String> list3 = new ArrayList<String>();
		list3.add("#");
		list3.add("*");
		list3.add("$");

		List<String> list4 = new ArrayList<String>();
		list4.add("c");
		list4.add("d");
		list4.add("e");

		List<List<String>> dimValue = new ArrayList<List<String>>();
		dimValue.add(list1);
		dimValue.add(list2);
		dimValue.add(list3);
		dimValue.add(list4);

		List<List<String>> recursiveResult = new ArrayList<List<String>>();
		// 递归实现笛卡尔积
		recursive(dimValue, recursiveResult, 0, new ArrayList<String>());

		System.out.println("递归实现笛卡尔乘积: 共 " + recursiveResult.size() + " 个结果");
		for (List<String> list : recursiveResult) {
			for (String string : list) {
				System.out.print(string + " ");
			}
			System.out.println();
		}
	}
}
