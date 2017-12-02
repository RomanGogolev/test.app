package org.some.test.task.api.entity;

/**
 * Created by thefp on 02.12.2017.
 */
public interface Profile {

    static final String GET_PROFILE_BY_USER_PHONE = "getProfileByUserPhone";

    String getEmail();

    void setEmail(String email);

    String getFio();

    void setFio(String fio);

    String getHrefToImg();

    void setHrefToImg(String hrefToImg);

    User getUser();

}
