package com.wangsl.common.api;


import org.springframework.data.domain.Page;

public class PageUtil {

	public static <T> CommonPage<T> transform(Page<T> page) {
		CommonPage<T> commonPage = new CommonPage<>();
		commonPage.setTotal(page.getTotalElements());
		commonPage.setCurrent(page.getPageable().getPageNumber());
		commonPage.setSize(page.getPageable().getPageSize());
		commonPage.setRecords(page.getContent());
		return commonPage;
	}
}
