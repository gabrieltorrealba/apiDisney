package com.proyect.disneyApi.repository;

import com.proyect.disneyApi.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface GenderRepository extends JpaRepository<Gender, Long> {

    Gender findGenderByName(String genderName);
}
