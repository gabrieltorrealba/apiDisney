package com.proyect.disneyApi.service;

import com.proyect.disneyApi.dto.CharacterDetailsDto;
import com.proyect.disneyApi.dto.CharacterDto;
import com.proyect.disneyApi.model.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CharacterService {

    public Character saveCharacter(Character character);
    public List<Character> getAllCharacters();
    public Page<Character> getCharacterByFilter(CharacterDetailsDto filter, Pageable pageable);
    public Character getCharacterByName(String name);
    public Optional<Character> getCharacterById(Long id);
    public void addCharacter(String characterName, String movieTitle);
    public void deleteCharacter(Character character);
}
