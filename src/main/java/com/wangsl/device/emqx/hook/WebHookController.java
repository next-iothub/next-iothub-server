package com.wangsl.device.emqx.hook;

import com.wangsl.common.web.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/emqx")
public class WebHookController {


	private final HandleService handleService;

	@Autowired
	public WebHookController(HandleService handleService) {
		this.handleService = handleService;
	}

	@PostMapping("/hook")
	public Result<String> hook(@RequestBody Map<String, Object> payload) {
		handleService.handle(payload);
		return Result.success();
	}
}
