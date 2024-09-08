package com.saad.servicebooking.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private  UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody User user){
        userService.registerUser(user);

        return userService.addUser(user);
    }

    @GetMapping("/getUser")
    public List<User> getUser(){
        return userService.getUser();
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id){
        return userService.deleteUser(id);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody User user){
        return userService.updateUser(id, user);
    }


}
