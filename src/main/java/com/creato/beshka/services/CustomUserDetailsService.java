package com.creato.beshka.services;

import com.creato.beshka.persistence.dao.UserRepository;
import com.creato.beshka.persistence.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("User with email '" + email + "' not found.");

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                getAuthorities(user.getEmail()));
    }

    private Collection<GrantedAuthority> getAuthorities(String email) {
        List<GrantedAuthority> l = new ArrayList<GrantedAuthority>();

        User user = userRepository.findByEmail(email);
        if(user == null) {
            return l;
        }
//        TODO add roles
//        for(final RoleEntity role : user.getRoles()) {
//            l.add( new GrantedAuthority() {
//                private static final long serialVersionUID = 1L;
//
//                @Override
//                public String getAuthority() {
//                    return role.getAuthority();
//                }
//
//                @Override
//                public String toString() {
//                    return getAuthority();
//                }
//            });
//        }

        return l;
    }
}
