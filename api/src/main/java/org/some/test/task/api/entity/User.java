package org.some.test.task.api.entity;

/**
 * Created by thefp on 02.12.2017.
 */
public interface User {

    static final String GET_USER_BY_PHONE = "getUserByPhone";

    Long getId();

    void setId(Long id);

    void setPassword(String password);

    String getPassword();

    void setPhone(String phone);

    String getPhone();

    String getAuthoritiesString();

}
