package com.proyect.disneyApi.service;

import com.proyect.disneyApi.model.Gender;

import java.util.List;
import java.util.Optional;

public interface GenderService {

    public Gender saveGender(Gender gender);
    public List<Gender> getAllGenders();
    public Gender getGenderByName(String genderName);
    public Optional<Gender> getGenreById(Long id);
    public void deleteGender(Gender gender);
}
