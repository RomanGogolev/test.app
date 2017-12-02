package org.some.test.task.api.services;

import org.some.test.task.api.entity.User;

/**
 * Created by thefp on 01.12.2017.
 */
public interface UserRepository {

    User get(String phone);

    void create(User user);

    void update(User user);

    void delete(String phone);

}
