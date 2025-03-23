package com.wangsl.sys.repository;

import com.wangsl.sys.model.SysRole;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysRoleRepository extends MongoRepository<SysRole, ObjectId> {

	Optional<SysRole> findByRoleCode(String roleCode);

	List<SysRole> findByStatus(String status);

	List<SysRole> findByType(Integer type);

	@Query("{ 'isDeleted': 0, 'status': '1' }")
	List<SysRole> findActiveRoles();
}
