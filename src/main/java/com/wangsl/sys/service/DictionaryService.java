package com.wangsl.sys.service;

import com.wangsl.sys.model.Dictionary;
import com.wangsl.sys.repository.DictionaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryService {
	private final DictionaryRepository dictionaryRepository;

	public DictionaryService(DictionaryRepository dictionaryRepository) {
		this.dictionaryRepository = dictionaryRepository;
	}

	public List<Dictionary> getAllDictionaries() {
		return dictionaryRepository.findAll();
	}

	public List<Dictionary> getDictionariesByType(String type) {
		return dictionaryRepository.findByTypeOrderBySortAsc(type);
	}

	public Dictionary getDictionaryByTypeAndCode(String type, String code) {
		return dictionaryRepository.findByTypeAndCode(type, code).get();
	}

	public Dictionary saveDictionary(Dictionary dictionary) {
		return dictionaryRepository.save(dictionary);
	}

	public void deleteDictionary(String id) {
		dictionaryRepository.deleteById(id);
	}
}
