package com.arsen.util;

import com.arsen.models.User;
import com.arsen.services.DetailsUserService;
import com.arsen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserService userService;
    private final DetailsUserService detailsUserService;

    @Autowired
    public UserValidator(UserService userService, DetailsUserService detailsUserService) {
        this.userService = userService;
        this.detailsUserService = detailsUserService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        detailsUserService.loadUserByUsername(user.getEmail());
        if (userService.findByEmail(user.getEmail()).isPresent())
            errors.rejectValue("email", "", "Email уже зарегистрирован!");
    }
}
