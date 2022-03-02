package com.proyect.disneyApi.service;

import com.proyect.disneyApi.model.Role;
import com.proyect.disneyApi.model.User;

import java.util.List;

public interface UserService {

    public User saveUser(User user);
    public Role saveRole(Role role);
    public List<User> getAllUsers();
    public User getUser(String username);
    public Role getRole(String roleName);
    public void addRoleToUser(String username, String role);
}
