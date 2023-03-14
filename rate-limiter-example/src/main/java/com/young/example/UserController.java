package com.young.example;

import com.young.ratelimiter.annotation.RateLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {


    @RateLimit(limit = 5)
    @GetMapping()
    public String getUser() {
        return UUID.randomUUID().toString();
    }
}
