package org.some.test.task.impl.entity;

import org.some.test.task.api.entity.Profile;

import javax.persistence.*;

import static org.some.test.task.impl.entity.ProfileEntity.Queries.GET_PROFILE_BY_USER_PHONE_QUERY;

/**
 * Created by thefp on 02.12.2017.
 */
@Entity
@Table(name = "profiles")
@NamedQueries(
        @NamedQuery(name = Profile.GET_PROFILE_BY_USER_PHONE, query = GET_PROFILE_BY_USER_PHONE_QUERY)
)
public class ProfileEntity implements Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "email")
    private String email;

    @Column(name = "fio")
    private String fio;

    @Column(name = "href_to_img")
    private String hrefToImg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getHrefToImg() {
        return hrefToImg;
    }

    public void setHrefToImg(String hrefToImg) {
        this.hrefToImg = hrefToImg;
    }

    public static class Queries {
        static final String GET_PROFILE_BY_USER_PHONE_QUERY = "select p from ProfileEntity p where p.user.phone=?1";
    }
}
