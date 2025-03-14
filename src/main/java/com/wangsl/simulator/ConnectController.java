package com.wangsl.simulator;

import com.wangsl.common.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simulator")
public class ConnectController {

	private final ConnectService connectService;

	@Autowired
	public ConnectController(ConnectService connectService) {
		this.connectService = connectService;
	}

	@PostMapping("/connect")
	public Result<String> connect(@RequestBody ConnectParam param) {
		boolean flag = connectService.connect(param);
		return flag ? Result.success("连接成功") : Result.fail("连接失败");
	}

	@PostMapping("/disconnect")
	public Result<String> disConnect(@RequestBody ConnectParam param) {
		boolean flag = connectService.disConnect(param);
		return flag ? Result.success("断连成功") : Result.fail("断连失败");
	}
}
