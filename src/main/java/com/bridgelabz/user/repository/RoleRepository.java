package com.bridgelabz.user.repository;
import org.springframework.data.repository.CrudRepository;
import com.bridgelabz.user.model.Role;

/**
 * @author Ashwini Patil
 *
 */
public interface RoleRepository extends CrudRepository<Role, Integer> {

	/** find role by role name
	 * @param roleName
	 * @return
	 */
	Role findRoleByRoleName(String roleName);
}
