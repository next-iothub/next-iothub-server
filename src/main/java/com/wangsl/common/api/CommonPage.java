package com.wangsl.common.api;

import lombok.Data;

import java.util.List;

@Data
public class CommonPage<T> {
	List<T> records;
	int current;
	int size;
	long total;
}
