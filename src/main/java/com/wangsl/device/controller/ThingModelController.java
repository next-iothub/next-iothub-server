package com.wangsl.device.controller;

import com.wangsl.common.api.Result;
import com.wangsl.device.model.Product;
import com.wangsl.device.model.dto.ThingModelDTO;
import com.wangsl.device.service.ThingModelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/{productKey}/thing-model")
@Validated
public class ThingModelController {

	private final ThingModelService thingModelService;

	@Autowired
	public ThingModelController(ThingModelService thingModelService) {
		this.thingModelService = thingModelService;
	}

	/**
	 * 添加物模型属性
	 * @param productKey
	 * @param propertyDTO
	 * @return
	 */
	@PostMapping("/properties")
	public Result addProperty(
		@PathVariable String productKey,
		@Valid @RequestBody ThingModelDTO.PropertyDTO propertyDTO
	) {
		thingModelService.addProperty(productKey, propertyDTO);
		return Result.success();
	}

	/**
	 * 修改物模型属性
	 * @param productKey
	 * @param propertyId
	 * @param propertyDTO
	 * @return
	 */
	@PutMapping("/properties/{propertyId}")
	public Result updateProperty(
		@PathVariable String productKey,
		@PathVariable String propertyId,
		@Valid @RequestBody ThingModelDTO.PropertyDTO propertyDTO
	) {
		thingModelService.updateProperty(productKey, propertyId, propertyDTO);
		return Result.success();
	}

	/**
	 * 删除物模型属性
	 * @param productKey
	 * @param propertyId
	 * @return
	 */
	@DeleteMapping("/properties/{propertyId}")
	public Result deleteProperty(
		@PathVariable String productKey,
		@PathVariable String propertyId
	) {
		thingModelService.deleteProperty(productKey, propertyId);
		return Result.success();
	}


	// 事件管理接口
	@PostMapping("/events")
	public Result<Product> addEvent(
		@PathVariable String productKey,
		@Valid @RequestBody ThingModelDTO.EventDTO eventDTO
	) {
		Product updatedProduct = thingModelService.addEvent(productKey, eventDTO);
		return Result.success(updatedProduct);
	}

	@PutMapping("/events/{eventId}")
	public Result<Product> updateEvent(
		@PathVariable String productKey,
		@PathVariable String eventId,
		@Valid @RequestBody ThingModelDTO.EventDTO eventDTO
	) {
		Product updatedProduct = thingModelService.updateEvent(productKey, eventId, eventDTO);
		return Result.success(updatedProduct);
	}

	@DeleteMapping("/events/{eventId}")
	public Result<Product> deleteEvent(
		@PathVariable String productKey,
		@PathVariable String eventId
	) {
		Product updatedProduct = thingModelService.deleteEvent(productKey, eventId);
		return Result.success(updatedProduct);
	}

	// 服务管理接口
	@PostMapping("/services")
	public Result<Product> addService(
		@PathVariable String productKey,
		@Valid @RequestBody ThingModelDTO.ServiceDTO serviceDTO
	) {
		Product updatedProduct = thingModelService.addService(productKey, serviceDTO);
		return Result.success(updatedProduct);
	}

	@PutMapping("/services/{serviceId}")
	public Result<Product> updateService(
		@PathVariable String productKey,
		@PathVariable String serviceId,
		@Valid @RequestBody ThingModelDTO.ServiceDTO serviceDTO
	) {
		Product updatedProduct = thingModelService.updateService(productKey, serviceId, serviceDTO);
		return Result.success(updatedProduct);
	}

	@DeleteMapping("/services/{serviceId}")
	public Result<Product> deleteService(
		@PathVariable String productKey,
		@PathVariable String serviceId
	) {
		Product updatedProduct = thingModelService.deleteService(productKey, serviceId);
		return Result.success(updatedProduct);
	}

	// 获取所有属性
	@GetMapping("/properties")
	public Result<List<Product.ThingModel.Property>> listProperties(
		@PathVariable String productKey
	) {
		List<Product.ThingModel.Property> properties = thingModelService.getProperties(productKey);
		return Result.success(properties);
	}

	// 获取单个属性
	@GetMapping("/properties/{propertyId}")
	public Result<Product.ThingModel.Property> getProperty(
		@PathVariable String productKey,
		@PathVariable String propertyId
	) {
		Product.ThingModel.Property property = thingModelService.getPropertyById(productKey, propertyId);
		return Result.success(property);
	}

	/**
	 * 聚合接口
	 * @param productKey
	 * @return
	 */
	@GetMapping
	public Result<Product.ThingModel> listThingModel(
		@PathVariable String productKey
	) {
		return Result.success(thingModelService.getThingModel(productKey));
	}

	// 事件和服务的类似查询方法
	@GetMapping("/events")
	public Result<List<Product.ThingModel.Event>> listEvents(
		@PathVariable String productKey
	) {
		List<Product.ThingModel.Event> events = thingModelService.getEvents(productKey);
		return Result.success(events);
	}


	@GetMapping("/events/{eventId}")
	public Result<Product.ThingModel.Event> getEvent(
		@PathVariable String productKey,
		@PathVariable String eventId
	) {
		Product.ThingModel.Event event = thingModelService.getEventById(productKey, eventId);
		return Result.success(event);
	}

	@GetMapping("/services")
	public Result<List<Product.ThingModel.Service>> listServices(
		@PathVariable String productKey
	) {
		List<Product.ThingModel.Service> services = thingModelService.getServices(productKey);
		return Result.success(services);
	}

	@GetMapping("/services/{serviceId}")
	public Result<Product.ThingModel.Service> getService(
		@PathVariable String productKey,
		@PathVariable String serviceId
	) {
		Product.ThingModel.Service service = thingModelService.getServiceById(productKey, serviceId);
		return Result.success(service);
	}
}
