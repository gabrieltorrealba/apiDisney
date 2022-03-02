package com.proyect.disneyApi.service.implementations;

import com.proyect.disneyApi.dto.CharacterDetailsDto;
import com.proyect.disneyApi.dto.CharacterDto;
import com.proyect.disneyApi.model.Character;
import com.proyect.disneyApi.model.Movie;
import com.proyect.disneyApi.repository.CharacterRepository;
import com.proyect.disneyApi.repository.MovieRepository;
import com.proyect.disneyApi.service.CharacterService;
import com.proyect.disneyApi.service.specifications.CharacterSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Character saveCharacter(Character character) {
        return characterRepository.save(character);
    }

    @Override
    public List<Character> getAllCharacters() {
        return characterRepository.findAll();
    }

    @Override
    public Page<Character> getCharacterByFilter(CharacterDetailsDto filter, Pageable pageable) {
        return characterRepository.findAll(CharacterSpecification.characterFilter(filter), pageable);
    }

    @Override
    public Character getCharacterByName(String name) {
        return characterRepository.findCharacterByName(name);
    }

    @Override
    public Optional<Character> getCharacterById(Long id) {
        return characterRepository.findById(id);
    }

    @Override
    public void addCharacter(String characterName, String movieTitle) {
        Character character = characterRepository.findCharacterByName(characterName);
        Movie movie = movieRepository.findMovieByTitle(movieTitle);

        movie.getCharacters().add(character);

    }

    @Override
    public void deleteCharacter(Character character) {
        characterRepository.delete(character);
    }
}
