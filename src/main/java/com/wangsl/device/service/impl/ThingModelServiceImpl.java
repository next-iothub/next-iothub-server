package com.wangsl.device.service.impl;

import com.wangsl.common.exception.ExceptionUtil;
import com.wangsl.common.exception.IothubExceptionEnum;
import com.wangsl.common.utils.SecurityContextUtil;
import com.wangsl.device.model.Product;
import com.wangsl.device.model.dto.ThingModelDTO;
import com.wangsl.device.repository.ProductRepository;
import com.wangsl.device.service.ThingModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ThingModelServiceImpl implements ThingModelService {

	private final ProductRepository productRepository;

	@Autowired
	public ThingModelServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	// 通用的产品校验方法
	private Product validateProductAccess(String productKey) {
		String userId = SecurityContextUtil.getCurrentUserId();
		Optional<Product> product = productRepository.findByUserIdAndProductKey(userId, productKey);
		if(product.isEmpty()){
			ExceptionUtil.throwEx(IothubExceptionEnum.ERROR_PRODUCT_KEY_INVALID);
		}
		return product.get();
	}

	// 添加属性
	@Transactional
	public void addProperty(String productKey, ThingModelDTO.PropertyDTO propertyDTO) {
		Product product = validateProductAccess(productKey);

		// 检查是否已存在同名属性
		if (product.getThingModel().getProperties().stream()
			.anyMatch(p -> p.getId().equals(propertyDTO.getId()))) {
			ExceptionUtil.throwEx(IothubExceptionEnum.ERROR_PRODUCT_PROPERTY_EXIST);
		}

		// 添加属性
		Product.ThingModel.Property newProperty = convertToProperty(propertyDTO);
		product.getThingModel().getProperties().add(newProperty);
		product.setUpdatedAt(new Date());
		productRepository.save(product);
	}

	// 更新属性
	@Transactional
	public void updateProperty(String productKey, String propertyId, ThingModelDTO.PropertyDTO propertyDTO) {
		Product product = validateProductAccess(productKey);

		product.getThingModel().getProperties().removeIf(p -> p.getId().equals(propertyId));
		Product.ThingModel.Property updatedProperty = convertToProperty(propertyDTO);
		product.getThingModel().getProperties().add(updatedProperty);

		product.setUpdatedAt(new Date());
		productRepository.save(product);
	}

	// 删除属性
	@Transactional
	public void deleteProperty(String productKey, String propertyId) {
		Product product = validateProductAccess(productKey);
		product.getThingModel().getProperties().removeIf(p -> p.getId().equals(propertyId));

		product.setUpdatedAt(new Date());
		productRepository.save(product);
	}

	// 类似方法适用于 Event 和 Service

	// 转换方法
	private Product.ThingModel.Property convertToProperty(ThingModelDTO.PropertyDTO dto) {
		Product.ThingModel.Property property = new Product.ThingModel.Property();
		property.setId(dto.getId());
		property.setName(dto.getName());
		property.setDataType(dto.getDataType());
		property.setUnit(dto.getUnit());
		property.setMin(dto.getMin());
		property.setMax(dto.getMax());
		property.setStep(dto.getStep());
		property.setMode(dto.getMode());
		property.setRequired(dto.getRequired());
		property.setSpecs(dto.getSpecs());
		return property;
	}

	// 事件管理方法
	@Transactional
	public Product addEvent(String productKey, ThingModelDTO.EventDTO eventDTO) {
		Product product = validateProductAccess(productKey);

		// 检查是否已存在同名事件
		if (product.getThingModel().getEvents().stream()
			.anyMatch(e -> e.getId().equals(eventDTO.getId()))) {
			ExceptionUtil.throwEx(IothubExceptionEnum.ERROR_PRODUCT_EVENT_EXIST);
		}

		Product.ThingModel.Event newEvent = convertToEvent(eventDTO);
		product.getThingModel().getEvents().add(newEvent);
		product.setUpdatedAt(new Date());
		return productRepository.save(product);
	}

	@Transactional
	public Product updateEvent(String productKey, String eventId, ThingModelDTO.EventDTO eventDTO) {
		Product product = validateProductAccess(productKey);

		product.getThingModel().getEvents().removeIf(e -> e.getId().equals(eventId));
		Product.ThingModel.Event updatedEvent = convertToEvent(eventDTO);
		product.getThingModel().getEvents().add(updatedEvent);
		product.setUpdatedAt(new Date());
		return productRepository.save(product);
	}

	@Transactional
	public Product deleteEvent(String productKey, String eventId) {
		Product product = validateProductAccess(productKey);
		product.getThingModel().getEvents().removeIf(e -> e.getId().equals(eventId));
		product.setUpdatedAt(new Date());
		return productRepository.save(product);
	}

	// 服务管理方法
	@Transactional
	public Product addService(String productKey, ThingModelDTO.ServiceDTO serviceDTO) {
		Product product = validateProductAccess(productKey);

		// 检查是否已存在同名服务
		if (product.getThingModel().getServices().stream()
			.anyMatch(s -> s.getId().equals(serviceDTO.getId()))) {
			ExceptionUtil.throwEx(IothubExceptionEnum.ERROR_PRODUCT_SERVICE_EXIST);
		}

		Product.ThingModel.Service newService = convertToService(serviceDTO);
		product.getThingModel().getServices().add(newService);
		product.setUpdatedAt(new Date());
		return productRepository.save(product);
	}

	@Transactional
	public Product updateService(String productKey, String serviceId, ThingModelDTO.ServiceDTO serviceDTO) {
		Product product = validateProductAccess(productKey);

		product.getThingModel().getServices().removeIf(s -> s.getId().equals(serviceId));
		Product.ThingModel.Service updatedService = convertToService(serviceDTO);
		product.getThingModel().getServices().add(updatedService);
		product.setUpdatedAt(new Date());
		return productRepository.save(product);
	}

	@Transactional
	public Product deleteService(String productKey, String serviceId) {
		Product product = validateProductAccess(productKey);

		product.getThingModel().getServices().removeIf(s -> s.getId().equals(serviceId));
		product.setUpdatedAt(new Date());
		return productRepository.save(product);
	}

	// 转换方法
	private Product.ThingModel.Event convertToEvent(ThingModelDTO.EventDTO dto) {
		Product.ThingModel.Event event = new Product.ThingModel.Event();
		event.setId(dto.getId());
		event.setName(dto.getName());
		event.setType(dto.getType());

		// 转换事件参数
		List<Product.ThingModel.Event.Parameter> params = dto.getParams().stream()
			.map(this::convertToEventParameter)
			.collect(Collectors.toList());
		event.setParams(params);

		return event;
	}

	private Product.ThingModel.Event.Parameter convertToEventParameter(ThingModelDTO.ParameterDTO dto) {
		Product.ThingModel.Event.Parameter parameter = new Product.ThingModel.Event.Parameter();
		parameter.setId(dto.getId());
		parameter.setName(dto.getName());
		parameter.setDataType(dto.getDataType());
		return parameter;
	}

	private Product.ThingModel.Service convertToService(ThingModelDTO.ServiceDTO dto) {
		Product.ThingModel.Service service = new Product.ThingModel.Service();
		service.setId(dto.getId());
		service.setName(dto.getName());

		// 转换输入参数
		List<Product.ThingModel.Service.Parameter> inputParams = dto.getInput().stream()
			.map(this::convertToServiceParameter)
			.collect(Collectors.toList());
		service.setInput(inputParams);

		// 转换输出参数
		List<Product.ThingModel.Service.Parameter> outputParams = dto.getOutput().stream()
			.map(this::convertToServiceParameter)
			.collect(Collectors.toList());
		service.setOutput(outputParams);

		return service;
	}

	private Product.ThingModel.Service.Parameter convertToServiceParameter(ThingModelDTO.ParameterDTO dto) {
		Product.ThingModel.Service.Parameter parameter = new Product.ThingModel.Service.Parameter();
		parameter.setId(dto.getId());
		parameter.setName(dto.getName());
		parameter.setDataType(dto.getDataType());
		parameter.setSpecs(dto.getSpecs());
		return parameter;
	}

	// 查询属性列表
	public List<Product.ThingModel.Property> getProperties(String productKey) {
		String userId = SecurityContextUtil.getCurrentUserId();
		Optional<Product> product = productRepository.findPropertiesByUserIdAndProductKey(userId, productKey);
		if (product.isPresent()){
			return product.get().getThingModel().getProperties();
		}
		return Collections.emptyList();
	}

	// 根据属性ID查询单个属性
	public Product.ThingModel.Property getPropertyById(String productKey, String propertyId) {
		List<Product.ThingModel.Property> properties = getProperties(productKey);
		return properties.stream()
			.filter(p -> p.getId().equals(propertyId))
			.findFirst()
			.orElseThrow(() -> new RuntimeException("属性不存在"));
	}

	// 查询事件列表
	public List<Product.ThingModel.Event> getEvents(String productKey) {
		String userId = SecurityContextUtil.getCurrentUserId();
		Optional<Product> product = productRepository.findEventsByUserIdAndProductKey(userId, productKey);
		if(product.isPresent()){
			return product.get().getThingModel().getEvents();
		}
		return Collections.emptyList();
	}

	// 根据事件ID查询单个事件
	public Product.ThingModel.Event getEventById(String productKey, String eventId) {
		List<Product.ThingModel.Event> events = getEvents(productKey);
		return events.stream()
			.filter(e -> e.getId().equals(eventId))
			.findFirst()
			.orElseThrow(() -> new RuntimeException("事件不存在"));
	}

	// 查询服务列表
	public List<Product.ThingModel.Service> getServices(String productKey) {
		String userId = SecurityContextUtil.getCurrentUserId();
		Optional<Product> product = productRepository.findServicesByUserIdAndProductKey(userId, productKey);
		if(product.isPresent()){
			return product.get().getThingModel().getServices();
		}
		return Collections.emptyList();
	}

	// 根据服务ID查询单个服务
	public Product.ThingModel.Service getServiceById(String productKey, String serviceId) {
		List<Product.ThingModel.Service> services = getServices(productKey);
		return services.stream()
			.filter(s -> s.getId().equals(serviceId))
			.findFirst()
			.orElseThrow(() -> new RuntimeException("服务不存在"));
	}

	// 查询服务列表
	public Product.ThingModel getThingModel(String productKey) {
		String userId = SecurityContextUtil.getCurrentUserId();
		Optional<Product> product = productRepository.findThingModelByUserIdAndProductKey(userId, productKey);
		if(product.isPresent()){
			return product.get().getThingModel();
		}
		return new Product.ThingModel();
	}
}
