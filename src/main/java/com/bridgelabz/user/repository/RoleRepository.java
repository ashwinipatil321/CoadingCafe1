package com.bridgelabz.user.repository;
import org.springframework.data.repository.CrudRepository;
import com.bridgelabz.user.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

	Role findRoleByRoleName(String roleName);
}
