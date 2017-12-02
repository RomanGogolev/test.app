package org.some.test.task.backend.controllers;

import org.some.test.task.api.model.ProfileModel;
import org.some.test.task.api.model.UserModel;
import org.some.test.task.api.services.Facade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by thefp on 29.11.2017.
 */
@RestController
public class ApiController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Facade facade;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String hello(){
        return messageSource.getMessage("message.welcome", null, "Default", new Locale("en"));
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<?> registration(UserModel user) {
        String errors = validateUserModel(user);
        if(errors.length()>0){
            return ResponseEntity.badRequest().body(errors);
        } else {
            facade.registration(user);
            return ResponseEntity.ok(null);
        }
    }

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public String welcomeUser(){
        return String.format(messageSource.getMessage("welcome.user", null, "Default", new Locale("en")),
                facade.getCurrentUserProfile().getFio()!=null?
                        facade.getCurrentUserProfile().getFio():
                        messageSource.getMessage("sorry", null, "Default", new Locale("en")));
    }

    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public ProfileModel profile(){
        return facade.getCurrentUserProfile();
    }

    @RequestMapping(value = "/user/profile", method = RequestMethod.POST)
    public ResponseEntity<?> updateProfile(ProfileModel profileModel) {
        String errors =  validateProfileModel(profileModel);
        if(errors.length()>0){
            return ResponseEntity.badRequest().body(errors);
        } else {
            facade.updateProfile(profileModel);
            return ResponseEntity.ok(null);
        }
    }

    private String validateProfileModel(ProfileModel profileModel){
        StringBuilder errors = new StringBuilder();
        if(profileModel.getFio()==null)
            errors.append(messageSource.getMessage("fio.empty", null, "Default", new Locale("en"))).append("\n");
        Pattern pattern = Pattern.compile("^(?:[a-zA-Z0-9_'^&/+-])+(?:\\\\.(?:[a-zA-Z0-9_'^&/+-])+)\" +\n" +
                "\" +\n" +
                "            \"      \\\"*@(?:(?:\\\\\\\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\\\\\\\.)\\\" +\\n\" +\n" +
                "            \"      \\\"{3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\\\\\\\]?)|(?:[a-zA-Z0-9-]+\\\\\\\\.)\\\" +\\n\" +\n" +
                "            \"      \"+(?:[a-zA-Z]){2,}\\\\.?)$");
        if (!(pattern.matcher(profileModel.getEmail()).matches()))
            errors.append(messageSource.getMessage("email.invalid", null, "Default", new Locale("en"))).append("\n");
        return errors.toString();
    }

    private String validateUserModel(UserModel userModel){
        StringBuilder errors = new StringBuilder();
        if(userModel.getPhone()==null)
            errors.append(messageSource.getMessage("phone.empty", null, "Default", new Locale("en"))).append("\n");
        if(userModel.getPassword()==null)
            errors.append(messageSource.getMessage("password.empty", null, "Default", new Locale("en"))).append("\n");
        if (userModel.getPhone().length() != 11)
            errors.append(messageSource.getMessage("phone.invalid", null, "Default", new Locale("en"))).append("\n");
        if (userModel.getPassword().length() < 6 || userModel.getPassword().length() > 32)
            errors.append(messageSource.getMessage("password.invalid", null, "Default", new Locale("en"))).append("\n");
        if (facade.existUserWithThisPhone(userModel.getPhone()))
            errors.append(messageSource.getMessage("user.exist", null, "Default", new Locale("en"))).append("\n");
        return errors.toString();
    }

}
