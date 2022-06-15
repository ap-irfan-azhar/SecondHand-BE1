package id.binaracademy.secondhand.service.impl;

import id.binaracademy.secondhand.entity.Role;
import id.binaracademy.secondhand.repository.RoleRepository;
import id.binaracademy.secondhand.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Role saveRole(String roleName) {
        Role existingRole = roleRepository.findByName(roleName).orElse(null);
        if (existingRole != null) {
            throw new IllegalArgumentException(
                    String.format("Role with name %s already exists", roleName)
            );
        }
        Role roleToSave = new Role();
        roleToSave.setName(roleName);
        return roleRepository.save(roleToSave);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRoleById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Role with id %s not found", id)
            );
        }
        return role.get();
    }

    @Override
    public Role findRoleByName(String name) {
        Optional<Role> role = roleRepository.findByName(name);
        if (role.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Role with name %s not found", name)
            );
        }
        return role.get();
    }

    @Override
    public Role updateRole(Long id, Role role) {
        Role existingRole = findRoleByName(role.getName());
        if (existingRole == null) {
            throw new IllegalArgumentException(
                    String.format("Role with name %s not found", role.getName())
            );
        }
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Role with id %s not found", id)
            );
        }
        roleRepository.deleteById(id);
    }
}
