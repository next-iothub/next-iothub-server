package com.wangsl.device.service.impl;

import com.wangsl.common.enums.ProductStatus;
import com.wangsl.common.exception.ExceptionUtil;
import com.wangsl.common.exception.IothubExceptionEnum;
import com.wangsl.common.utils.SecurityContextUtil;
import com.wangsl.device.model.Product;
import com.wangsl.device.model.dto.ProductBasicDTO;
import com.wangsl.device.model.dto.ProductCreateDTO;
import com.wangsl.device.model.dto.ProductEditDTO;
import com.wangsl.device.model.dto.ProductTypeOptionsDTO;
import com.wangsl.device.repository.ProductRepository;
import com.wangsl.device.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}


	// 生成productKey
	private String generateProductKey2() {
		return getRandomString(12);
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

	// /**
	//  * 获取产品详细信息
	//  * @param productKey
	//  * @return
	//  */
	// public Product getProductDetail(String productKey) {
	// 	Optional<Product> product = productRepository
	// 		.findByUserIdAndProductKey(SecurityContextUtil.getCurrentUserId(), productKey);
	// 	if(product.isPresent()){
	// 		return product.get();
	// 	}
	// 	ExceptionUtil.throwEx("1111", "productKet not found");
	// 	return null;
	// }
	//
	// /**
	//  * 生成版本号
	//  */
	// private String generateVersionNumber() {
	// 	// 简单版本号格式: v1.0.0
	// 	return "v1.0.0";
	// }

	public Product createMockProduct() {
		return Product.builder()
			.productKey(generateProductKey2())
			.productId(generateProductId())
			.name("智能网关")
			.description("用于连接物联网设备的智能网关")
			.category("IoT Gateway")
			.categoryPath(Arrays.asList("IoT", "Gateway"))
			.model("GW-1000")
			.manufacturer("IoT Company")
			.userId(SecurityContextUtil.getCurrentUserId())
			.productType("gateway")
			.networkType("wifi")
			.protocolType("mqtt")
			.authType("secret")
			.dataFormat("json")
			.status("testing")
			.tags(Arrays.asList("IoT", "Gateway", "MQTT"))
			.nodeType("gateway")
			.dataEncryption(true)
			.features(Product.Features.builder()
				.otaSupport(true)
				.groupControl(true)
				.localControl(false)
				.sceneAutomation(true)
				.edgeComputing(true)
				.build())
			.deviceCount(new Product.DeviceCount(100, 80, 50))
			.thingModel(Product.ThingModel.builder()
				.properties(Arrays.asList(
					new Product.ThingModel.Property("p1", "温度", "float", "°C", -40.0, 100.0, 0.1, "rw", true, null),
					new Product.ThingModel.Property("p2", "湿度", "int", "%", 0.0, 100.0, 1.0, "rw", true, null)
				))
				.events(Arrays.asList(
					new Product.ThingModel.Event("e1", "温度过高", "alert", List.of(
						new Product.ThingModel.Event.Parameter("param1", "温度值", "float")
					))
				))
				.services(Arrays.asList(
					new Product.ThingModel.Service("s1", "重启设备", null, null)
				))
				.build())
			.configurations(Product.Configurations.builder()
				.mqttTopic(new Product.Configurations.MqttTopic(
					new Product.Configurations.MqttTopic.TopicConfig("device/property", "device/event", "device/service"),
					new Product.Configurations.MqttTopic.TopicConfig("cloud/property", "cloud/event", "cloud/service")
				))
				.defaultSettings(new Product.Configurations.DefaultSettings(30, true))
				.networkSettings(new Product.Configurations.NetworkSettings(10, 60))
				.build())
			.createdAt(new Date())
			.updatedAt(new Date())
			.build();
	}

	public String generateProductId() {
		// 生成随机字符串作为产品id
		return "prod-" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12);
	}

	/**
	 * 创建产品
	 */
	@Override
	public Product createProduct(ProductCreateDTO productCreateDTO) {

		// 获取当前用户ID
		String userId = SecurityContextUtil.getCurrentUserId();

		// 查重
		if (productRepository.findByUserIdAndName(userId, productCreateDTO.getName()).isPresent()) {
			ExceptionUtil.throwEx(IothubExceptionEnum.ERROR_PRODUCT_NAME_EXIST);
		}

		// 创建产品对象
		Product product = new Product();
		BeanUtils.copyProperties(productCreateDTO, product);

		// 设置产品唯一标识符
		String productKey = generateProductKey2();
		product.setProductKey(productKey);

		// 设置产品ID
		product.setProductId(generateProductId());

		product.setStatus(ProductStatus.DEVELOPMENT.getCode());
		// 设置用户ID（用于数据分区）
		product.setUserId(userId);

		// 初始化设备统计数据
		Product.DeviceCount deviceCount = new Product.DeviceCount();
		deviceCount.setTotal(0);
		deviceCount.setActive(0);
		deviceCount.setOnline(0);
		product.setDeviceCount(deviceCount);

		// 设置物模型
		product.setThingModel(new Product.ThingModel(Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));

		// 设置创建和更新时间
		Date now = new Date();
		product.setCreatedAt(now);
		product.setUpdatedAt(now);

		// 保存产品
		return productRepository.save(product);
	}

	/**
	 * 更新产品
	 */
	@Override
	public Product updateProduct(String productKey, ProductEditDTO productEditDTO) {
		// 获取当前用户ID
		String userId = String.valueOf(SecurityContextUtil.getCurrentUserId());

		// 查询产品
		Product product = productRepository.findByUserIdAndProductKey(userId, productKey)
			.orElseThrow(() -> {
				ExceptionUtil.throwEx(IothubExceptionEnum.ERROR_PRODUCT_KEY_INVALID);
				return null;
			});

		// 查重
		if (productRepository.findByUserIdAndName(userId, productEditDTO.getName()).isPresent()) {
			ExceptionUtil.throwEx(IothubExceptionEnum.ERROR_PRODUCT_NAME_EXIST);
		}

		// 更新产品信息
		BeanUtils.copyProperties(productEditDTO, product);

		// 更新时间
		product.setUpdatedAt(new Date());

		// 保存更新
		return productRepository.save(product);
	}

	/**
	 * 获取产品详情
	 */
	@Override
	public Product getProductDetail(String productKey) {
		// 获取当前用户ID
		String userId = String.valueOf(SecurityContextUtil.getCurrentUserId());

		// 查询产品
		return productRepository.findByUserIdAndProductKey(userId, productKey)
			.orElseThrow(() -> {
				ExceptionUtil.throwEx(IothubExceptionEnum.ERROR_PRODUCT_KEY_INVALID);
				return null;
			});
	}

	/**
	 * 获取产品详情（通过ID）
	 */
	@Override
	public Product getProductDetailById(String id) {
		// 获取当前用户ID
		String userId = String.valueOf(SecurityContextUtil.getCurrentUserId());

		// 查询产品
		return productRepository.findByUserIdAndId(userId, id)
			.orElseThrow();
	}

	/**
	 * 获取产品列表（分页）
	 */
	@Override
	public Page<ProductBasicDTO> getProductList(Pageable pageable) {
		// 获取当前用户ID
		String userId = String.valueOf(SecurityContextUtil.getCurrentUserId());

		// 分页查询产品
		Page<Product> productPage = productRepository.findByUserId(userId, pageable);

		// 转换为DTO
		return productPage.map(this::convertToBasicDTO);
	}

	/**
	 * 按名称搜索产品（分页）
	 */
	@Override
	public Page<ProductBasicDTO> searchProductsByName(String name, Pageable pageable) {
		// 获取当前用户ID
		String userId = String.valueOf(SecurityContextUtil.getCurrentUserId());

		// 分页查询产品
		Page<Product> productPage = productRepository.findByUserIdAndNameLike(userId, name, pageable);

		// 转换为DTO
		return productPage.map(this::convertToBasicDTO);
	}

	/**
	 * 按产品类型过滤产品（分页）
	 */
	@Override
	public Page<ProductBasicDTO> filterProductsByType(String productType, Pageable pageable) {
		// 获取当前用户ID
		String userId = String.valueOf(SecurityContextUtil.getCurrentUserId());

		// 分页查询产品
		Page<Product> productPage = productRepository.findByUserIdAndProductType(userId, productType, pageable);

		// 转换为DTO
		return productPage.map(this::convertToBasicDTO);
	}

	/**
	 * 按状态过滤产品（分页）
	 */
	@Override
	public Page<ProductBasicDTO> filterProductsByStatus(String status, Pageable pageable) {
		// 获取当前用户ID
		String userId = String.valueOf(SecurityContextUtil.getCurrentUserId());

		// 分页查询产品
		Page<Product> productPage = productRepository.findByUserIdAndStatus(userId, status, pageable);

		// 转换为DTO
		return productPage.map(this::convertToBasicDTO);
	}

	/**
	 * 删除产品（通过产品Key）
	 */
	@Override
	public void deleteProduct(String productKey) {
		// 获取当前用户ID
		String userId = String.valueOf(SecurityContextUtil.getCurrentUserId());

		// 检查产品是否存在
		if (!productRepository.findByUserIdAndProductKey(userId, productKey).isPresent()) {
			ExceptionUtil.throwEx(IothubExceptionEnum.ERROR_PRODUCT_KEY_INVALID);
		}

		// 删除产品
		productRepository.deleteByUserIdAndProductKey(userId, productKey);
	}

	/**
	 * 删除产品（通过ID）
	 */
	@Override
	public void deleteProductById(String id) {
		// 获取当前用户ID
		String userId = String.valueOf(SecurityContextUtil.getCurrentUserId());

		// 检查产品是否存在
		if (!productRepository.findByUserIdAndId(userId, id).isPresent()) {
			ExceptionUtil.throwEx(IothubExceptionEnum.ERROR_PRODUCT_KEY_INVALID);
		}

		// 删除产品
		productRepository.deleteByUserIdAndId(userId, id);
	}

	/**
	 * 获取产品类型选项
	 */
	@Override
	public ProductTypeOptionsDTO getProductTypeOptions() {
		ProductTypeOptionsDTO options = new ProductTypeOptionsDTO();

		// 设置产品类型选项
		options.setProductTypes(Arrays.asList("device", "gateway", "edge"));

		// 设置网络类型选项
		options.setNetworkTypes(Arrays.asList("wifi", "cellular", "ethernet", "zigbee", "lora", "bluetooth"));

		// 设置协议类型选项
		options.setProtocolTypes(Arrays.asList("mqtt", "coap", "http", "modbus", "websocket"));

		// 设置认证类型选项
		options.setAuthTypes(Arrays.asList("secret", "certificate", "token"));

		// 设置数据格式选项
		options.setDataFormats(Arrays.asList("json", "custom", "binary"));

		// 设置状态选项
		options.setStatusOptions(Arrays.asList("draft", "testing", "published", "deprecated"));

		// 设置节点类型选项
		options.setNodeTypes(Arrays.asList("direct", "gateway"));

		return options;
	}

	/**
	 * 生成产品Key
	 */
	private String generateProductKey() {
		// 生成12位字母数字混合的唯一标识符
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid.substring(0, 16);
	}

	/**
	 * 将Product转换为ProductBasicDTO
	 */
	private ProductBasicDTO convertToBasicDTO(Product product) {
		ProductBasicDTO dto = new ProductBasicDTO();
		dto.setId(product.getId());
		dto.setProductKey(product.getProductKey());
		dto.setProductId(product.getProductId());
		dto.setName(product.getName());
		dto.setCategory(product.getCategory());
		dto.setProductType(product.getProductType());
		dto.setStatus(product.getStatus());
		dto.setNodeType(product.getNodeType());
		dto.setDeviceCount(product.getDeviceCount());
		dto.setCreatedAt(product.getCreatedAt().toString());
		dto.setUpdatedAt(product.getUpdatedAt().toString());
		return dto;
	}
}
