package org.some.test.task.api.services;

import org.some.test.task.api.entity.Profile;

/**
 * Created by thefp on 02.12.2017.
 */
public interface ProfileRepository {

    Profile get(String phone);

    void update(Profile profile);

}
