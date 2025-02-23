package taodigital.interview.jpa.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import taodigital.interview.jpa.demo.model.request.UserLoginRequest;
import taodigital.interview.jpa.demo.model.response.UserResponse;
import taodigital.interview.jpa.demo.service.UserService;
import taodigital.interview.jpa.demo.service.ValidationService;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ValidationService validationService;

    @PostMapping(
            path = "/user/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public String login(@RequestBody UserLoginRequest request) {
        return userService.login(request);
    }

    @GetMapping(
            path = "/user/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserResponse get(@PathVariable(value = "id") UUID id) {
        return userService.get(id);
    }
}
