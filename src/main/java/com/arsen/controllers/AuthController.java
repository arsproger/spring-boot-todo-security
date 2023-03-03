package com.arsen.controllers;

import com.arsen.dto.UserDTO;
import com.arsen.models.User;
import com.arsen.services.RegistrationService;
import com.arsen.util.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final ModelMapper modelMapper;
    private final UserValidator userValidator;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(ModelMapper modelMapper, UserValidator userValidator,
                          RegistrationService registrationService) {
        this.modelMapper = modelMapper;
        this.userValidator = userValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/main")
    public String main() {
        return "auth/main";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") UserDTO userDTO) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid UserDTO userDTO,
                                      BindingResult bindingResult) {
        userValidator.validate(convertToUser(userDTO), bindingResult);

        if (bindingResult.hasErrors())
            return "auth/registration";

        registrationService.register(convertToUser(userDTO));

        return "redirect:/auth/login";
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
