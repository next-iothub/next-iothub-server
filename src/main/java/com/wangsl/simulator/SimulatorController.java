package com.wangsl.simulator;

import com.wangsl.common.web.model.Result;
import com.wangsl.simulator.model.ConnectParam;
import com.wangsl.simulator.model.PublishMessageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/simulator")
public class SimulatorController {

	private final ConnectService connectService;

	@Autowired
	public SimulatorController(ConnectService connectService) {
		this.connectService = connectService;
	}

	@PostMapping("/connect")
	public Result<String> connect(@RequestBody ConnectParam param) {
		boolean flag = connectService.connect(param);
		return flag ? Result.success("连接成功") : Result.fail("连接失败");
	}

	/**
	 * 上行数据
	 * @param param
	 * @return
	 */
	@PostMapping("/upload/data")
	public Result<String> publishMsg(@RequestBody PublishMessageParam param){
		return connectService.uploadData(param) ? Result.success("发送成功") : Result.fail("发送失败");
	}

	@PostMapping("/disconnect")
	public Result<String> disConnect(@RequestBody ConnectParam param) {
		boolean flag = connectService.disConnect(param);
		return flag ? Result.success("断连成功") : Result.fail("断连失败");
	}
}
