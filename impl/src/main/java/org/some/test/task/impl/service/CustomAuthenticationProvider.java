package org.some.test.task.impl.service;

import org.some.test.task.api.entity.User;
import org.some.test.task.api.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

/**
 * Created by thefp on 01.12.2017.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userRepository.get(name);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(user, password, getAuthorityCollection(user));
            } else {
                throw new BadCredentialsException(messageSource.getMessage("bad.credentials", null, "Default", new Locale("en")));
            }
        } else {
            throw new BadCredentialsException(messageSource.getMessage("user.not.found", null, "Default", new Locale("en")));
        }
    }

    private Collection<SimpleGrantedAuthority> getAuthorityCollection(User user) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role : user.getAuthoritiesString().split(",")) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
