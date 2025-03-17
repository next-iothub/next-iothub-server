package com.wangsl.device.service;

import com.wangsl.common.exception.ex.IothubException;
import com.wangsl.common.utils.SecurityContextUtil;
import com.wangsl.device.model.Device;
import com.wangsl.device.model.Product;
import com.wangsl.device.model.dto.DeviceDTO;
import com.wangsl.device.repository.DeviceRepository;
import com.wangsl.device.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.sound.midi.MidiDeviceReceiver;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {

	private final DeviceRepository deviceRepository;
	private final ProductRepository productRepository;

	@Autowired
	public DeviceService(DeviceRepository deviceRepository, ProductRepository productRepository) {
		this.deviceRepository = deviceRepository;
		this.productRepository = productRepository;
	}


	/**
	 * 注册设备
	 *  deviceName 设备名
	 *  nickName 设备别名
	 *  productKey 产品 key
	 * @return
	 */
	public Device registerDevice(DeviceDTO deviceDTO) {
		// 检查productKey是否存在于当前用户
		ObjectId currentUserId = SecurityContextUtil.getCurrentUserId();
		Optional<Product> productOptional = productRepository.findByUserIdAndProductKey(currentUserId, deviceDTO.getProductKey());
		if (productOptional.isEmpty()) {
			IothubException.cast("Invalid product key.");
		}

		// 检查设备名称是否已存在
		Optional<Device> existingDevice = deviceRepository.findByDeviceNameAndProductKey(deviceDTO.getDeviceName(), deviceDTO.getProductKey());
		if (existingDevice.isPresent()) {
			IothubException.cast("Device name already exists.");
		}

		// 创建设备对象
		Device device = new Device();
		device.setDeviceName(deviceDTO.getDeviceName());
		device.setNickName(deviceDTO.getNickName());
		device.setProductId(productOptional.get().getId());
		device.setProductKey(deviceDTO.getProductKey());
		device.setStatus("offline");  // 默认设备状态为离线
		device.setCreateTime(new Date());
		device.setClientId(generateClientId(device, currentUserId));
		device.setDeviceSecret(generateSecret());
		device.setUsername(generateUsername(device));
		device.setPassword(device.getDeviceSecret());
		device.setActive(true);

		return deviceRepository.save(device);
	}

	/**
	 * 生成连接 mqtt broker 的 clientId
	 * @param device
	 * @return
	 */
	private String generateClientId(Device device, ObjectId userId) {
		return device.getProductKey() + "." + device.getDeviceName() + "." + userId.toString();
	}

	/**
	 * 生成用户名
	 * @param device
	 * @return
	 */
	private String generateUsername(Device device){
		return device.getProductKey() + "&" + device.getDeviceName();
	}

	/**
	 * 生成一个随机的 secret (可以使用 UUID)
	 * @return
	 */
	private String generateSecret() {
		return UUID.randomUUID()
			.toString()
			.replace("-", "")
			.substring(0, 24); // 生成 24 位的随机 secret
	}

	/**
	 * 分页查询
	 * @param productKey
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Device> getDevicesByUserId(String productKey, int page, int size) {
		// 检查productKey是否存在于当前用户
		ObjectId currentUserId = SecurityContextUtil.getCurrentUserId();
		Optional<Product> productOptional = productRepository.findByUserIdAndProductKey(currentUserId, productKey);
		if (productOptional.isEmpty()) {
			IothubException.cast("Invalid product key.");
		}

		// 分页参数
		Pageable pageable = PageRequest
			.of(page, size, Sort.by(Sort.Order.asc("createTime"))); // 按创建时间升序排序
		return deviceRepository.findByProductKey(productKey, pageable);
	}

	/**
	 * 查询状态
	 * @param productKey
	 * @param deviceName
	 * @return
	 */
	public String getDeviceStatus(String productKey, String deviceName) {
		// 检查productKey是否存在于当前用户
		ObjectId currentUserId = SecurityContextUtil.getCurrentUserId();
		Optional<Product> productOptional = productRepository.findByUserIdAndProductKey(currentUserId, productKey);
		if (productOptional.isEmpty()) {
			IothubException.cast("Invalid product key.");
		}

		Optional<Device> optionalDevice = deviceRepository.findByDeviceNameAndProductKey(deviceName, productKey);
		if(optionalDevice.isPresent()){
			return optionalDevice.get().getStatus();
		}
		return "offline";
	}
}
