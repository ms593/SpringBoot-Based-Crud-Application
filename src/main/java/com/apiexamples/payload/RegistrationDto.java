package com.apiexamples.payload;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
public class RegistrationDto {

    private Long id;

    @Size(min=2, message="Should be more than 2 characters ")
    private String name;

    @Email(message="invalid email address")
    private String email;

    @Size(min=10,max = 12, message="mobile number should be at least 10 characters long")
    private String mobile;

    private String message;
}