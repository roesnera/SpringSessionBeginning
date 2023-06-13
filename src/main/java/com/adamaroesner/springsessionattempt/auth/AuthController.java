package com.adamaroesner.springsessionattempt.auth;

import com.adamaroesner.springsessionattempt.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserDetailsService userDetailsService;

    @PostMapping("/login/process")
    public boolean processLogin(@RequestBody LoginDTO login){
        return userDetailsService.login(login);
    }

    @PostMapping("/register/new")
    public boolean registerUser(@RequestBody RegisterDTO registerDTO){
        return userDetailsService.registerUser(registerDTO);
    }
}
