package com.codegym.shopyy.security;

import com.codegym.shopyy.entities.Role;
import com.codegym.shopyy.entities.User;
import com.codegym.shopyy.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User " + username + "was not found in database!");
        }

//        List<Role> roles = userRepository.findRolesByUsername(username);
        Set<Role> roles = user.getRoles();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role: roles) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
            grantedAuthorities.add(authority);
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities);

        return userDetails;
    }
}

