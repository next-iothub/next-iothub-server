package com.wangsl.sys.controller;

import com.wangsl.common.api.Result;
import com.wangsl.sys.model.Dictionary;
import com.wangsl.sys.service.DictionaryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/dict")
public class DictionaryController {
	private final DictionaryService dictionaryService;

	public DictionaryController(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	@GetMapping
	public Result<List<Dictionary>> getAllDictionaries() {
		return Result.success(dictionaryService.getAllDictionaries());
	}

	@GetMapping("/{type}")
	public Result<List<Dictionary>> getDictionariesByType(@PathVariable String type) {
		return Result.success(dictionaryService.getDictionariesByType(type));
	}

	@GetMapping("/{type}/{code}")
	public Result<Dictionary> getDictionaryByTypeAndCode(@PathVariable String type, @PathVariable String code) {
		return Result.success(dictionaryService.getDictionaryByTypeAndCode(type, code));

	}

	@PostMapping
	public Result<Dictionary> createDictionary(@RequestBody Dictionary dictionary) {
		return Result.success(dictionaryService.saveDictionary(dictionary));
	}

	@DeleteMapping("/{id}")
	public Result deleteDictionary(@PathVariable String id) {
		dictionaryService.deleteDictionary(id);
		return Result.success();
	}
}
