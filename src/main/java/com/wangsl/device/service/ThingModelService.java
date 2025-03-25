package com.wangsl.device.service;

import com.wangsl.device.model.Product;
import com.wangsl.device.model.dto.ThingModelDTO;

import java.util.List;

public interface ThingModelService {


	void addProperty(String productKey, ThingModelDTO.PropertyDTO propertyDTO);
	void updateProperty(String productKey, String propertyId, ThingModelDTO.PropertyDTO propertyDTO);
	void deleteProperty(String productKey, String propertyId);


	Product addEvent(String productKey, ThingModelDTO.EventDTO eventDTO);

	Product updateEvent(String productKey, String eventId, ThingModelDTO.EventDTO eventDTO);

	Product deleteEvent(String productKey, String eventId);

	Product addService(String productKey, ThingModelDTO.ServiceDTO serviceDTO);

	Product updateService(String productKey, String serviceId, ThingModelDTO.ServiceDTO serviceDTO);

	Product deleteService(String productKey, String serviceId);

	Product.ThingModel.Service getServiceById(String productKey, String serviceId);

	List<Product.ThingModel.Service> getServices(String productKey);

	Product.ThingModel.Event getEventById(String productKey, String eventId);

	List<Product.ThingModel.Event> getEvents(String productKey);

	Product.ThingModel.Property getPropertyById(String productKey, String propertyId);

	List<Product.ThingModel.Property> getProperties(String productKey);

	Product.ThingModel getThingModel(String productKey);
}
