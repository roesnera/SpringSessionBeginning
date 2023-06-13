package com.adamaroesner.springsessionattempt.service;

import com.adamaroesner.springsessionattempt.auth.LoginDTO;
import com.adamaroesner.springsessionattempt.auth.RegisterDTO;
import com.adamaroesner.springsessionattempt.user.Role;
import com.adamaroesner.springsessionattempt.user.User;
import com.adamaroesner.springsessionattempt.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Email does not belong to active user!"));
    }

    public boolean registerUser(RegisterDTO registerDTO) {
        if(!(registerDTO.username()!=null&&registerDTO.firstName()!=null&&registerDTO.lastName()!=null&&registerDTO.password()!=null&&!registerDTO.username().isEmpty()&&!registerDTO.firstName().isEmpty()&&!registerDTO.lastName().isEmpty()&&!registerDTO.password().isEmpty())){
            return false;
        }
        User userToSave = User.builder()
                .email(registerDTO.username())
                .firstName(registerDTO.firstName())
                .lastName(registerDTO.lastName())
                .password(passwordEncoder.encode(registerDTO.password()))
                .role(Role.USER)
                .locked(false)
                .build();
        repository.save(userToSave);
        return true;
    }

    public boolean login(LoginDTO login) {
        String password = passwordEncoder.encode(login.password());
        Optional<User> userOptional = repository.findByEmail(login.username());
        if(!userOptional.isPresent()||userOptional.get().getPassword()!=password){
            return false;
        }
        return true;
    }
}
