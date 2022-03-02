package com.proyect.disneyApi.controller;


import com.proyect.disneyApi.dto.GenderDetailsDto;
import com.proyect.disneyApi.dto.GenderDto;
import com.proyect.disneyApi.dto.MovieDto;
import com.proyect.disneyApi.model.Gender;
import com.proyect.disneyApi.model.Role;
import com.proyect.disneyApi.model.User;
import com.proyect.disneyApi.service.GenderService;
import com.proyect.disneyApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class GenderController {

    @Autowired
    GenderService genderService;

    @Autowired
    UserService userService;

    //get all genders
    @GetMapping("/genders")
    public ResponseEntity<?> getAllGenders(){

        try {

            return ResponseEntity.ok().body(genderService.getAllGenders().stream().map(gender ->
                            GenderDto.builder()
                                    .id(gender.getId())
                                    .name(gender.getName())
                                    .build())
                    .collect(Collectors.toList()));

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }

    }

    //get a gender by id
    @GetMapping("/genders/{id}")
    public ResponseEntity<?> getGender(@PathVariable Long id){

        Gender gender = genderService.getGenreById(id).orElse(null);

        try {

        if (gender == null){
            return new ResponseEntity<>("Gender doesn't exists", HttpStatus.NOT_FOUND);
        }

        GenderDetailsDto genderDetailsDto = GenderDetailsDto.builder()
                .id(gender.getId())
                .name(gender.getName())
                .movies(gender.getMovies().stream().map(movie -> MovieDto.builder()
                        .id(movie.getId())
                        .image(movie.getImage().toString())
                        .title(movie.getTitle())
                        .year(movie.getYear().toString())
                        .build()).collect(Collectors.toList()))
                .build();

        return ResponseEntity.ok().body(genderDetailsDto);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }

    //Created a Gender
    @PostMapping("/genders")
    public ResponseEntity<?> createActor(Authentication authentication, @RequestBody GenderDetailsDto genderDetailsDto){
        User user = userService.getUser(authentication.getName());
        Role role = userService.getRole("ADMIN");

        try {

        if (!user.getRoles().contains(role)){
            return new ResponseEntity<>("Not allowed", HttpStatus.METHOD_NOT_ALLOWED);
        }

        if (genderDetailsDto.getName().isEmpty()){
            return new ResponseEntity<>("Empty fields",HttpStatus.BAD_REQUEST);
        }

        Gender gender = genderService.getGenderByName(genderDetailsDto.getName());

        if (gender != null){
            return new ResponseEntity<>("Gender already exists",HttpStatus.CONFLICT);
        }

        Gender newGender = new Gender(null, genderDetailsDto.getName().toUpperCase(), new ArrayList<>());

        genderService.saveGender(newGender);

        return new ResponseEntity<>("Gender created",HttpStatus.CREATED);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }
}
