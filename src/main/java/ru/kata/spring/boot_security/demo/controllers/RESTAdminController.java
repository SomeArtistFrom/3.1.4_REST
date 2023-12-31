package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@RestController
@RequestMapping("api/admin")
public class RESTAdminController {
    private final UserService userService;

    @Autowired
    public RESTAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> usersList = userService.showAllUsers();
        if (usersList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
        }
        return new ResponseEntity<>(usersList, HttpStatus.OK); // 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.showOneUser(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //204
        }
        return new ResponseEntity<>(user, HttpStatus.OK); //200
    }

    @PostMapping
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        if (userService.save(user)) {
            return new ResponseEntity<>(user, HttpStatus.CREATED); // 201
        }
        return new ResponseEntity<>(user, HttpStatus.CONFLICT); // 409
    }

    @PutMapping()
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (userService.update(user)) {
            return new ResponseEntity<>(user, HttpStatus.OK); // 200
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT); // 409
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteUser(@PathVariable int id) {
        if (userService.delete(id)) {
            return new ResponseEntity<>(id, HttpStatus.OK); // 200
        }
        return new ResponseEntity<>(id, HttpStatus.NOT_FOUND); //404
    }

}
