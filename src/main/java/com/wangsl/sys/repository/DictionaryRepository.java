package com.wangsl.sys.repository;

import com.wangsl.sys.model.Dictionary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DictionaryRepository extends MongoRepository<Dictionary, String> {

	/**
	 * 根据字典类型查询字典列表
	 * @param type 字典类型
	 * @return 字典列表
	 */
	List<Dictionary> findByTypeOrderBySortAsc(String type);

	/**
	 * 根据字典类型和代码查询字典
	 * @param type 字典类型
	 * @param code 字典代码
	 * @return 字典对象
	 */
	Optional<Dictionary> findByTypeAndCode(String type, String code);

	/**
	 * 根据字典类型和启用状态查询字典列表
	 * @param type 字典类型
	 * @param enabled 启用状态
	 * @return 字典列表
	 */
	List<Dictionary> findByTypeAndEnabledOrderBySortAsc(String type, Boolean enabled);

	/**
	 * 根据父级ID查询子字典列表
	 * @param parentId 父级ID
	 * @return 子字典列表
	 */
	List<Dictionary> findByParentIdOrderBySortAsc(String parentId);

	/**
	 * 根据字典类型和父级ID查询子字典列表
	 * @param type 字典类型
	 * @param parentId 父级ID
	 * @return 子字典列表
	 */
	List<Dictionary> findByTypeAndParentIdOrderBySortAsc(String type, String parentId);

	/**
	 * 根据字典类型查询并判断是否存在
	 * @param type 字典类型
	 * @return 是否存在
	 */
	boolean existsByType(String type);

	/**
	 * 根据字典类型和代码查询并判断是否存在
	 * @param type 字典类型
	 * @param code 字典代码
	 * @return 是否存在
	 */
	boolean existsByTypeAndCode(String type, String code);

	/**
	 * 根据字典类型删除字典
	 * @param type 字典类型
	 */
	void deleteByType(String type);
}
