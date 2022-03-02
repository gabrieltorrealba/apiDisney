package com.proyect.disneyApi.repository;

import com.proyect.disneyApi.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CharacterRepository extends JpaRepository<Character, Long>, JpaSpecificationExecutor<Character>{

    Character findCharacterByName(String name);
}
