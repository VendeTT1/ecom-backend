package com.online.shop.ecombackend.controller;

import com.online.shop.ecombackend.dao.AddressRepository;
import com.online.shop.ecombackend.model.Address;
import com.online.shop.ecombackend.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    /** The Address DAO. */
    private AddressRepository addressRepository;

    /**
     * Constructor for spring injection.
     * @param addressRepository
     */
    public UserController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**
     * Gets all addresses for the given user and presents them.
     * @param user The authenticated user account.
     * @param userId The user ID to get the addresses of.
     * @return The list of addresses.
     */
    @GetMapping("/{userId}/address")
    public ResponseEntity<List<Address>> getAddress(
            @AuthenticationPrincipal User user,
            @PathVariable Long userId) {
        if (!userHasPermission(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(addressRepository.findByUser_Id(userId));
    }

    /**
     * Allows the user to add a new address.
     * @param user The authenticated user.
     * @param userId The user id for the new address.
     * @param address The Address to be added.
     * @return The saved address.
     */

    //TODO: remove this method or delete the previous address
    @PutMapping("/{userId}/address")
    public ResponseEntity<Address> putAddress(
            @AuthenticationPrincipal User user, @PathVariable Long userId,
            @RequestBody Address address) {
        if (!userHasPermission(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        address.setId(null);
        User refUser = new User();
        refUser.setId(userId);
        address.setUser(refUser);
        return ResponseEntity.ok(addressRepository.save(address));
    }

    /**
     * Updates the given address.
     * @param user The authenticated user.
     * @param userId The user ID the address belongs to.
     * @param addressId The address ID to alter.
     * @param address The updated address object.
     * @return The saved address object.
     */
    @PatchMapping("/{userId}/address/{addressId}")
    public ResponseEntity<Address> patchAddress(
            @AuthenticationPrincipal User user, @PathVariable Long userId,
            @PathVariable Long addressId, @RequestBody Address address) {
        if (!userHasPermission(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (address.getId() == addressId) {
            Optional<Address> opOriginalAddress = addressRepository.findById(addressId);
            if (opOriginalAddress.isPresent()) {
                User originalUser = opOriginalAddress.get().getUser();
                if (originalUser.getId() == userId) {
                    address.setUser(originalUser);
                    return ResponseEntity.ok(addressRepository.save(address));
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Method to check if an authenticated user has permission to a user ID.
     * @param user The authenticated user.
     * @param id The user ID.
     * @return True if they have permission, false otherwise.
     */
    private boolean userHasPermission(User user, Long id) {
        return user.getId() == id;
    }


}
