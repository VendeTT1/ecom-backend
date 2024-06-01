package com.online.shop.ecombackend.dto;


import com.online.shop.ecombackend.model.WishlistItem;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * The information required to register a user.
 */
public class RegistrationBody {
    @NotNull
    @NotBlank
    private String id;

    /** The email. */
    @NotNull
    @NotBlank
    @Email
    private String email;
    /** The password. */
    @NotNull
    @NotBlank
    @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$")
    @Size(min=6, max=32)
    private String password;
    /** The first name. */
    @NotNull
    @NotBlank
    private String name;
    /** The last name. */
    @NotNull
    @NotBlank
    private String lastname;
    @NotNull
    @NotBlank
    private String phone;

    @NotNull
    @NotBlank
    private String address;

    private List<WishlistItem> userWishlist;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<WishlistItem> getUserWishlist() {
        return userWishlist;
    }

    public void setUserWishlist(List<WishlistItem> userWishlist) {
        this.userWishlist = userWishlist;
    }
}