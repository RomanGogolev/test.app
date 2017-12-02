package org.some.test.task.impl.entity;

import org.some.test.task.api.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static org.some.test.task.api.entity.User.GET_USER_BY_PHONE;
import static org.some.test.task.impl.entity.UserEntity.Queries.GET_USER_BY_PHONE_QUERY;

/**
 * Created by thefp on 02.12.2017.
 */
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "phone_unique_constraint", columnNames = {"phone"}))
@NamedQueries({
        @NamedQuery(name = GET_USER_BY_PHONE, query = GET_USER_BY_PHONE_QUERY)
})
public class UserEntity implements User, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "authorities")
    private String authoritiesString;

    @Transient
    private Collection<SimpleGrantedAuthority> authorityCollection;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        authorityCollection = new ArrayList<>();
        for (String role : authoritiesString.split(",")) {
            authorityCollection.add(new SimpleGrantedAuthority(role));
        }
        return authorityCollection;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthoritiesString() {
        return authoritiesString;
    }

    public void setAuthoritiesString(String authoritiesString) {
        this.authoritiesString = authoritiesString;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static class Queries {
        static final String GET_USER_BY_PHONE_QUERY = "select u from UserEntity u where u.phone=?1";
    }

}
