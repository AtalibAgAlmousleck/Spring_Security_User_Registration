package com.almousleck.userregistration.web;

import com.almousleck.userregistration.model.Users;
import com.almousleck.userregistration.service.UserService;
import com.almousleck.userregistration.web.dto.UserRegistrationDto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("users")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("users") @Valid UserRegistrationDto userDto,
                                      BindingResult result) {
        Users existing = userService.findByEmail(userDto.getEmail());
        if(existing != null) {
            result.rejectValue("email", null, "There is already an account registered with this email");
        }
        if(result.hasErrors()) {
            return "registration";
        }

        userService.save(userDto);
        return "redirect:/registration?success";
    }
}
