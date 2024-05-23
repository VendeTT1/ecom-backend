package com.online.shop.ecombackend.dto;

import jakarta.validation.constraints.*;

public class Registration {
    /** The username. */
    @NotNull
    @NotBlank
    @Size(min=3, max=255, message = "Username must have at least 3 characters")
    private String username;
    /** The email. */
    @NotNull
    @NotBlank
    @Email
    private String email;
    /** The password. */
    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
    @Size(min=8, max=32, message = "Password must be between 8 and 32 characters")
    private String password;
    /** The first name. */
    @NotNull
    @NotBlank
    private String firstname;
    /** The last name. */

    @NotNull
    @NotBlank
    private String lastname;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
