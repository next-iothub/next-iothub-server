package com.wangsl.device.manager;

import com.wangsl.device.dao.Connection;
import com.wangsl.device.dao.Device;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DeviceDetailsDto {
	private Device device;
	private List<Connection> connections;
}
