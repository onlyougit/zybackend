package com.rttmall.shopbackend.converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class DateConverter implements Converter<String, Date> {

	private static final List<String> formarts = new ArrayList<>(4);
	static {
		formarts.add("yyyy-MM");
		formarts.add("yyyy-MM-dd");
		formarts.add("yyyy-MM-dd hh:mm");
		formarts.add("yyyy-MM-dd hh:mm:ss");
	}

	@Override
	public Date convert(String source) {
		if(StringUtils.isEmpty(source)){
			return null;
		}
		try {
			String formatter = "";
			if (source.matches("^\\d{4}-\\d{1,2}$")) {
				formatter = formarts.get(0);
			}else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
				formatter = formarts.get(1);
			}else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
				formatter = formarts.get(2);
			}else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
				formatter = formarts.get(3);
			}else {
				throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
			}
			SimpleDateFormat sdf = new SimpleDateFormat(formatter);
			return sdf.parse(source);
		} catch (Exception e) {
			return null;
		}
	}

}
