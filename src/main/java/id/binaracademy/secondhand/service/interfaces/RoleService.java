package id.binaracademy.secondhand.service.interfaces;

import id.binaracademy.secondhand.entity.Role;

import java.util.List;

public interface RoleService {
    Role saveRole(String roleName);
    List<Role> findAllRoles();
    Role findRoleById(Long id);
    Role findRoleByName(String name);
    Role updateRole(Long id, Role role);
    void deleteRole(Long id);
}
