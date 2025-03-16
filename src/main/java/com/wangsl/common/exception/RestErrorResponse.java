package com.wangsl.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 异常返回信息模型
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestErrorResponse implements Serializable {

	private int code; // 状态码
	private String exMsg; // 异常信息

}
