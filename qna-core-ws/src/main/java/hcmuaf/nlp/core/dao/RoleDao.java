/**
 * @author Manh Phan
 *
 * Edited date Mar 4, 2017
 */
package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.Role;

import java.util.List;

/**
 * The Interface RoleDao.
 */
public interface RoleDao {
	
	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public List<Role> getRoles();
	
	/**
	 * Gets the role by name.
	 *
	 * @param roleName the role name
	 * @return the role by name
	 */
	public Role getRoleByName(String roleName);
}
