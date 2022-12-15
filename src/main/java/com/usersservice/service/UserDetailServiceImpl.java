package com.usersservice.service;

import com.usersservice.entities.Users;
import com.usersservice.model.UserDetailsImpl;
import com.usersservice.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username).orElseGet(null);
        // do something
        return UserDetailsImpl.build(user);
    }

//    public UserDetails loadUserByEmail(String email){
//        Users user = usersRepository.findByEmail(email);
//        return UserDetailsImpl.build(user);
//    }

}

