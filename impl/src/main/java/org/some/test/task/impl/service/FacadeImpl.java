package org.some.test.task.impl.service;

import org.some.test.task.api.entity.Profile;
import org.some.test.task.api.model.ProfileModel;
import org.some.test.task.api.model.UserModel;
import org.some.test.task.api.services.Facade;
import org.some.test.task.api.services.ProfileRepository;
import org.some.test.task.api.services.UserRepository;
import org.some.test.task.impl.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationException;
import java.util.Locale;

/**
 * Created by thefp on 02.12.2017.
 */
@Component
public class FacadeImpl implements Facade {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public void registration(UserModel userModel){
        UserEntity userEntity = new UserEntity();
        userEntity.setPhone(userModel.getPhone());
        userEntity.setAuthoritiesString("ROLE_USER");
        userEntity.setPassword(userModel.getPassword());
        userRepository.create(userEntity);
    }

    @Override
    public ProfileModel getCurrentUserProfile() {
        Profile profile = profileRepository.get(SecurityContextHolder.getContext().getAuthentication().getName());
        return transferToProfileModel(profile);
    }

    @Override
    public void updateProfile(ProfileModel profileModel) {
        Profile profile = profileRepository.get(SecurityContextHolder.getContext().getAuthentication().getName());
        profile.setEmail(profileModel.getEmail());
        profile.setFio(profileModel.getFio());
        profile.setHrefToImg(profileModel.getHrefToImg());
        profileRepository.update(profile);
    }

    @Override
    public boolean existUserWithThisPhone(String phone) {
        return userRepository.get(phone)!=null?true:false;
    }

    private ProfileModel transferToProfileModel(Profile profile) {
        ProfileModel model = new ProfileModel();
        model.setHrefToImg(profile.getHrefToImg());
        model.setFio(profile.getFio());
        model.setEmail(profile.getEmail());
        model.setPhone(SecurityContextHolder.getContext().getAuthentication().getName());
        return model;
    }

}
