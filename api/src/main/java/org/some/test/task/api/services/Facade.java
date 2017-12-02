package org.some.test.task.api.services;

import org.some.test.task.api.model.ProfileModel;
import org.some.test.task.api.model.UserModel;

import javax.xml.bind.ValidationException;

/**
 * Created by thefp on 02.12.2017.
 */
public interface Facade {

    void registration(UserModel userModel);

    ProfileModel getCurrentUserProfile();

    void updateProfile(ProfileModel profileModel);

    boolean existUserWithThisPhone(String phone);
}
