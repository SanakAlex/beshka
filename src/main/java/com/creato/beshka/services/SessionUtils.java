package com.creato.beshka.services;

import com.creato.beshka.converters.dto.UserDto;
import com.creato.beshka.exceptions.AuthRequiredException;
import com.creato.beshka.persistence.dao.UserRepository;
import com.creato.beshka.persistence.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SessionUtils {

    static final Map<String , SimpleGrantedAuthority> ROLES_MAP = new HashMap<String , SimpleGrantedAuthority>() {{
        put("admin",    new SimpleGrantedAuthority("ROLE_ADMIN"));
        put("moderator", new SimpleGrantedAuthority("ROLE_MODERATOR"));
        put("user",   new SimpleGrantedAuthority("ROLE_USER"));
    }};
    private final UserRepository userRepository;

    @Autowired
    public SessionUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    User getCurrentUser() throws AuthRequiredException {
        if (isAuthorized()) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userRepository.findByEmail(userDetails.getUsername());
        } else
            throw new AuthRequiredException();
    }

    private boolean isAuthorized() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken);
    }

    public void authorized() throws AuthRequiredException {
        if(!isAuthorized()){
            throw new AuthRequiredException();
        }
    }

    public void logeInUser(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        boolean enabled = true;
        if (!user.isActive())
            enabled = false;
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                enabled, true, true, true, getGrantedAuthorities(user));
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (PermissionEntity perm:user.getRoleEntity().getPermissions()){
//            authorities.add(new SimpleGrantedAuthority(perm.getName()));
//        }
        return authorities;
    }
}
