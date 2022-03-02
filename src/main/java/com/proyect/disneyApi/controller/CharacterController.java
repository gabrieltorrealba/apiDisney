package com.proyect.disneyApi.controller;

import com.proyect.disneyApi.dto.CharacterDetailsDto;
import com.proyect.disneyApi.dto.CharacterDto;
import com.proyect.disneyApi.dto.MovieDto;
import com.proyect.disneyApi.model.Character;
import com.proyect.disneyApi.model.Movie;
import com.proyect.disneyApi.model.Role;
import com.proyect.disneyApi.model.User;
import com.proyect.disneyApi.service.CharacterService;
import com.proyect.disneyApi.service.MovieService;
import com.proyect.disneyApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class CharacterController {

    @Autowired
    private UserService userService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private MovieService movieService;

    //Get all characters by 10 per page
    @GetMapping("/characters")
    public ResponseEntity<Object> getAllCharacters(@RequestParam MultiValueMap<String, String> queryMap,
                                            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        try {

            CharacterDetailsDto filter = CharacterDetailsDto.builder()
                    .name(queryMap.getFirst("name"))
                    .age(queryMap.getFirst("age") != null ?
                            Integer.valueOf(Objects.requireNonNull(queryMap.getFirst("age")))
                            : null)
                    .weight(queryMap.getFirst("weight") != null ?
                            Float.parseFloat(Objects.requireNonNull(queryMap.getFirst("weight")))
                            : null)
                    .build();

            Page<Character> page = characterService.getCharacterByFilter(filter, pageable);

            Map<String, Object> characterMap = new HashMap<>();

            characterMap.put("total", page.getTotalElements());
            characterMap.put("pages", page.getTotalPages());
            characterMap.put("page", page.getNumber());
            characterMap.put("size", page.getSize());
            characterMap.put("sort", page.getSort());
            characterMap.put("content", page.getContent().stream().map(character ->
                    CharacterDto.builder()
                            .id(character.getId())
                            .name(character.getName())
                            .image(character.getImage().toString())
                            .build()));

            return ResponseEntity.ok(characterMap);

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }

    //Get character by id
    @GetMapping("/characters/{id}")
    public ResponseEntity<?> getCharacter(@PathVariable Long id) {

        try {
            Character character = characterService.getCharacterById(id).orElse(null);

            if (character != null) {
                CharacterDetailsDto characterDetailsDto = CharacterDetailsDto.builder()
                        .id(character.getId())
                        .name(character.getName())
                        .image(character.getImage().toString())
                        .age(character.getAge())
                        .history(character.getHistory())
                        .weight(character.getWeight())
                        .movies(character.getMovies().stream().map(movie -> MovieDto.builder()
                                .id(movie.getId())
                                .image(movie.getImage().toString())
                                .title(movie.getTitle())
                                .year(movie.getYear().toString())
                                .build()).collect(Collectors.toSet()))
                        .build();

                return ResponseEntity.ok().body(characterDetailsDto);
            }

            return new ResponseEntity<>("Character doesn't exists", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }

    //Created a character
    @PostMapping("/characters")
    public ResponseEntity<?> createCharacter(Authentication authentication, @RequestBody CharacterDetailsDto characterDetailsDto) throws
            MalformedURLException {

        User user = userService.getUser(authentication.getName());
        Role role = userService.getRole("ADMIN");

        try {

        if (!user.getRoles().contains(role)){
            return new ResponseEntity<>("Not allowed", HttpStatus.METHOD_NOT_ALLOWED);
        }

        String age = String.valueOf(characterDetailsDto.getAge());
        String weight = String.valueOf(characterDetailsDto.getWeight());
        if (characterDetailsDto.getName().isEmpty() || characterDetailsDto.getImage().isEmpty() || age.isEmpty() || characterDetailsDto.getAge() == null ||
                characterDetailsDto.getAge() <= 0 || weight.isEmpty() || characterDetailsDto.getWeight() == null || characterDetailsDto.getWeight() <= 0 ||
        characterDetailsDto.getHistory().isEmpty()){
            return new ResponseEntity<>("Empty fields",HttpStatus.BAD_REQUEST);
        }

        Character character = characterService.getCharacterByName(characterDetailsDto.getName().toUpperCase());

        if (character != null){
            return new ResponseEntity<>("Character already exists",HttpStatus.CONFLICT);
        }
        URL image = new URL(characterDetailsDto.getImage());
        Character newCharacter = new Character(null, image, characterDetailsDto.getName(), characterDetailsDto.getAge(), characterDetailsDto.getWeight(), characterDetailsDto.getHistory(), null);

        characterService.saveCharacter(newCharacter);

        return new ResponseEntity<>("Character created",HttpStatus.CREATED);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }


    //Edit a character by id
    @PutMapping("/characters/{id}")
    public ResponseEntity<?> editCharacter(Authentication authentication, @PathVariable("id") Long id,
                                       @RequestBody CharacterDetailsDto characterDetailsDto) throws MalformedURLException {

        User user = userService.getUser(authentication.getName());
        Role role = userService.getRole("ADMIN");

        try {

        if (!user.getRoles().contains(role)){
            return new ResponseEntity<>("Not allowed", HttpStatus.METHOD_NOT_ALLOWED);
        }

        String age = String.valueOf(characterDetailsDto.getAge());
        String weight = String.valueOf(characterDetailsDto.getWeight());

        if (characterDetailsDto.getName().isEmpty() || characterDetailsDto.getImage().isEmpty() || age.isEmpty() || characterDetailsDto.getAge() == null ||
                characterDetailsDto.getAge() <= 0 || weight.isEmpty() || characterDetailsDto.getWeight() == null || characterDetailsDto.getWeight() <= 0 ||
                characterDetailsDto.getHistory().isEmpty()){
            return new ResponseEntity<>("Empty fields",HttpStatus.BAD_REQUEST);
        }

        Character currentCharacter = characterService.getCharacterById(id).orElse(null);

        if(currentCharacter == null){
            return new ResponseEntity<>("Character doesn't exists", HttpStatus.NOT_FOUND);
        }

        URL image = new URL(characterDetailsDto.getImage());

        currentCharacter.setAge(characterDetailsDto.getAge());
        currentCharacter.setHistory(characterDetailsDto.getHistory());
        currentCharacter.setName(characterDetailsDto.getName());
        currentCharacter.setImage(image);
        currentCharacter.setWeight(characterDetailsDto.getWeight());

        characterService.saveCharacter(currentCharacter);

        return new ResponseEntity<>("Actor edited",HttpStatus.OK);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }

    //delete a character by id
    @DeleteMapping("/characters/{id}")
    public ResponseEntity<?> deleteCharacter(Authentication authentication, @PathVariable("id") Long id) {

        User user = userService.getUser(authentication.getName());
        Role role = userService.getRole("ADMIN");

        try {

            if (!user.getRoles().contains(role)) {
                return new ResponseEntity<>("Not allowed", HttpStatus.METHOD_NOT_ALLOWED);
            }

            Character character = characterService.getCharacterById(id).orElse(null);

            if(character == null){
                return new ResponseEntity<>("Character doesn't exists", HttpStatus.BAD_REQUEST);
            }

            List<Movie> movies = character.getMovies();

            movies.forEach(movie -> {
                List<Character> characters = movie.getCharacters().stream().filter(character1 -> character1.getId().equals(id)).collect(Collectors.toList());
                characters.remove(characters.get(0));
                movieService.saveMovie(movie);
            });

            character.setMovies(null);
            characterService.saveCharacter(character);
            characterService.deleteCharacter(character);

            return new ResponseEntity<>("Character deleted",HttpStatus.OK);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }

    //add movie to character
    @PostMapping("/characters/movies")
    public ResponseEntity<?> addActorToMovie(Authentication authentication, @RequestParam String movie, @RequestParam String character){

        User user = userService.getUser(authentication.getName());
        Role role = userService.getRole("ADMIN");

        try {

            if (!user.getRoles().contains(role)){
                return new ResponseEntity<>("Not allowed", HttpStatus.METHOD_NOT_ALLOWED);
            }

            characterService.addCharacter(character, movie);

            return ResponseEntity.ok().build();
        }  catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }

    }
}
