package com.proyect.disneyApi.controller;

import com.proyect.disneyApi.dto.CharacterDto;
import com.proyect.disneyApi.model.Role;
import com.proyect.disneyApi.model.User;
import com.proyect.disneyApi.service.CharacterService;
import com.proyect.disneyApi.service.EmailService;
import com.proyect.disneyApi.service.MovieService;
import com.proyect.disneyApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;


    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(){

        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Object> saveUser(@RequestParam String firstName, @RequestParam String lastName,
                                           @RequestParam String username, @RequestParam String password){

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Empty fields", HttpStatus.BAD_REQUEST);
        }

        if (userService.getUser(username) != null) {
            return new ResponseEntity<>("User already exists. Try it again", HttpStatus.CONFLICT);
        }

        User user = new User(null, firstName, lastName, username, password, new ArrayList<>());

        userService.saveUser(user);
        userService.addRoleToUser(user.getUsername(), "USER");
        sendEmail(user.getUsername());

        return new ResponseEntity<>("User created",HttpStatus.CREATED);
    }

    @PostMapping("/roles")
    public ResponseEntity<Object> saveRole(@RequestBody Role role){

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/roles").toUriString());

        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @GetMapping("/sendMail/{email}")
    public String sendEmail(@PathVariable(value="email", required=true) String email){

        return emailService.sendEmail(email);
    }

}
