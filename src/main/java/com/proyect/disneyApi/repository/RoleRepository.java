package com.proyect.disneyApi.repository;

import com.proyect.disneyApi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(String name);
}
