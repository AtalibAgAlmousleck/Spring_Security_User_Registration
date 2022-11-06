package com.almousleck.userregistration.web.dto;

import com.almousleck.userregistration.constraint.FieldMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor @NoArgsConstructor
@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword",
                message = "The password fields must match"),
        @FieldMatch(first = "email", second = "confirmEmail",
                message = "The email fields must match")
})
public class UserRegistrationDto {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;

    @Email
    @NotEmpty
    private String email;
    @Email
    @NotEmpty
    private String confirmEmail;

    @AssertTrue
    private Boolean terms;
}
