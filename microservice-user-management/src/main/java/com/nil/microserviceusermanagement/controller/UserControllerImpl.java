 package com.nil.microserviceusermanagement.controller;

import com.nil.microserviceusermanagement.model.Role;
import com.nil.microserviceusermanagement.model.User;
import com.nil.microserviceusermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;

@Controller
public class UserControllerImpl {
    @Autowired
    private UserService userService;

    @PostMapping("/service/registration")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        if(userService.findByUsername(user.getUsername())!= null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        user.setRole(Role.USER);
        return new ResponseEntity<>(userService.save(user),HttpStatus.CREATED);
    }
    @GetMapping("/service/login")
    public ResponseEntity<?> getUser(Principal principal){
        //Principal principal = request.getUserPrincipal();
        // this means logout is successful
        if(principal==null||principal.getName()==null) return new ResponseEntity<>(HttpStatus.OK);
        //username=principle.getName();
        return ResponseEntity.ok(
                userService.findByUsername(principal.getName())
        );

    }
    @PostMapping("/service/name")
    public ResponseEntity<?> getNameOfUsers(@RequestBody List<Long>idList){
        return ResponseEntity.ok(userService.findUsers(idList));
    }
    @GetMapping("/service/test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("It is Working...");
    }
}
