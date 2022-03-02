package com.proyect.disneyApi.service.implementations;

import com.proyect.disneyApi.model.Character;
import com.proyect.disneyApi.repository.CharacterRepository;
import com.proyect.disneyApi.repository.MovieRepository;
import com.proyect.disneyApi.service.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CharacterServiceImplTest {

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private CharacterServiceImpl characterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);


    }

    @Test
    void saveCharacter() throws MalformedURLException {
        Character character = new Character(10L, new URL("https://static.wikia.nocookie.net/disney/images/b/ba/A.R.F.png"), "A.R.F.", 2, 10F,"A.R.F. is a supporting character in the Disney Junior animated series Puppy Dog Pals. He is a robot dog invented by Bob to spend time with Bingo and Rolly while he is away at work and to help clean their messes. He often accompanies Bingo and Rolly on their adventures. In his debut episode, A.R.F., his main function was to clean up after Bingo and Rolly (strictly), but he has been upgraded frequently throughout the series.", new ArrayList<>());

        when(characterRepository.save(new Character())).thenReturn(character);
        assertNotNull(characterService.saveCharacter(new Character()));

    }

    @Test
    void getAllCharacters() throws MalformedURLException {
        Character character = new Character(10L, new URL("https://static.wikia.nocookie.net/disney/images/b/ba/A.R.F.png"), "A.R.F.", 2, 10F,"A.R.F. is a supporting character in the Disney Junior animated series Puppy Dog Pals. He is a robot dog invented by Bob to spend time with Bingo and Rolly while he is away at work and to help clean their messes. He often accompanies Bingo and Rolly on their adventures. In his debut episode, A.R.F., his main function was to clean up after Bingo and Rolly (strictly), but he has been upgraded frequently throughout the series.", new ArrayList<>());

        when(characterRepository.findAll()).thenReturn(Arrays.asList(character));
        assertNotNull(characterService.getAllCharacters());
    }

    @Test
    void getCharacterByFilter() {
    }

    @Test
    void getCharacterByName() {
    }

    @Test
    void getCharacterById() {
    }

    @Test
    void addCharacter() {
    }

    @Test
    void deleteCharacter() {
    }
}