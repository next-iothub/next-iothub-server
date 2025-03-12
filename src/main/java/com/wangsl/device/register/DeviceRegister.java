package com.wangsl.device.register;

import com.wangsl.device.dao.Device;
import com.wangsl.device.dao.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/device")
public class DeviceRegister {

	private final DeviceRepository deviceRepository;

	@Autowired
	public DeviceRegister(DeviceRepository deviceRepository) {
		this.deviceRepository = deviceRepository;
	}

	/**
	 * 根据产品名称生成设备三元组
	 * @param productName
	 * @return
	 */
	@PostMapping("/register")
	public Map<String, Object> registerDevice(@RequestParam String productName) {
		// 生成三元组
		String deviceName = generateDeviceName(productName);
		String secret = generateSecret();

		// 存入mongodb
		String username = productName + "/" + deviceName;
		Device device = Device.builder()
			.productName(productName)
			.deviceName(deviceName)
			.secret(secret)
			.username(username)
			.password(secret)
			.build();
		deviceRepository.save(device);

		Map<String, Object> res = new HashMap<>();
		res.put("productName", productName);
		res.put("deviceName", deviceName);
		res.put("secret", secret);
		System.out.println("1");
		return res;
	}

	// 基于 productName 生成随机的 deviceName
	private String generateDeviceName(String productName) {
		String randomSuffix = getRandomString(8); // 生成一个随机的后缀
		return productName + "-" + randomSuffix;
	}

	// 生成一个随机的 secret (可以使用 UUID)
	private String generateSecret() {
		return UUID.randomUUID()
			.toString()
			.replace("-", "")
			.substring(0, 14); // 生成 14 位的随机 secret
	}

	// 生成指定长度的随机字符串
	private String getRandomString(int length) {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuilder randomString = new StringBuilder();
		for (int i = 0; i < length; i++) {
			randomString.append(characters.charAt(random.nextInt(characters.length())));
		}
		return randomString.toString();
	}
}
