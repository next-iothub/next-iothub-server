package com.wangsl.device.controller;

import com.wangsl.common.utils.SecurityContextUtil;
import com.wangsl.common.web.model.Result;
import com.wangsl.device.model.Device;
import com.wangsl.device.model.Product;
import com.wangsl.device.model.dto.DeviceDTO;
import com.wangsl.device.service.DeviceService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
public class DeviceController {

	private final DeviceService deviceService;

	@Autowired
	public DeviceController(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	/**
	 * 设备注册
	 * @param deviceDTO
	 * @return
	 */
	@PostMapping("/register")
	public Result<Device> register(@RequestBody DeviceDTO deviceDTO) {
		Device device = deviceService.registerDevice(deviceDTO);
		return Result.success(device);
	}

	/**
	 * 分页查询
	 * @param productKey 产品key
	 * @param page 页码
	 * @param size 每页数量
	 * @return
	 */
	@GetMapping("/product/list")
	public Result<Page<Device>> getProductsByUserId(
		@RequestParam String productKey,
		@RequestParam int page,
		@RequestParam int size) {
		Page<Device> productPage = deviceService.getProductsByUserId(productKey, page, size);
		return Result.success(productPage);
	}

	// 删除设备接口 参数prokey devicename userid


}
