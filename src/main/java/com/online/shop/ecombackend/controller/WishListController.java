package com.online.shop.ecombackend.controller;

import com.online.shop.ecombackend.dao.ProductRepository;
import com.online.shop.ecombackend.dao.UserRepository;
import com.online.shop.ecombackend.dao.WishlistItemRepository;
import com.online.shop.ecombackend.model.Product;
import com.online.shop.ecombackend.model.User;
import com.online.shop.ecombackend.model.WishlistItem;
//import com.online.shop.ecombackend.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
@Autowired
private ProductRepository productRepository;

@Autowired
private UserRepository userRepository;
@Autowired
private WishlistItemRepository wishlistItemRepository;
    @GetMapping("/{userId}")
    public ResponseEntity<List<WishlistItem>> getWishlist(
            @PathVariable String userId) {

        return ResponseEntity.ok(wishlistItemRepository.findByUser_Id(userId));
    }

   @PostMapping("/{userId}/add")
   public ResponseEntity<WishlistItem> addWishlist(
           @PathVariable String userId,
           @RequestBody WishlistItem wishlistItem) {

       Optional<User> userOptional = userRepository.findById(userId);
       if (!userOptional.isPresent()) {
           return ResponseEntity.notFound().build(); // User not found
       }

       User user = userOptional.get();
       wishlistItem.setUser(user);

       // Check if the wishlist item already exists
       Optional<WishlistItem> existingWishlistItem = wishlistItemRepository.findByUserIdAndProductId(userId, wishlistItem.getId());
       if (existingWishlistItem.isPresent()) {
           System.out.println("item already present for this user");
           return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Item already in wishlist
       }

       wishlistItem.setWishlistid(null); // Ensure the wishlist item ID is null for new entries

       // Save the WishlistItem to the repository
       WishlistItem savedWishlistItem = wishlistItemRepository.save(wishlistItem);

       // Return the saved WishlistItem in the response
       return ResponseEntity.ok(savedWishlistItem);
   }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<Void> deleteWishlistItem(
            @PathVariable String userId,
            @PathVariable int productId) {

        // Check if the user exists
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            System.out.println("User not found");
            return ResponseEntity.notFound().build(); // User not found
        }

        // Find the wishlist item with the specified product ID and user ID
        Optional<WishlistItem> wishlistItemOptional = wishlistItemRepository.findByUserIdAndProductId(userId, productId);

        // Check if the wishlist item exists and belongs to the specified user
        if (!wishlistItemOptional.isPresent()) {
            System.out.println("Product not found in the user's wishlist");
            return ResponseEntity.notFound().build(); // Wishlist item not found
        }

        // Remove the wishlist item from the repository
        wishlistItemRepository.delete(wishlistItemOptional.get());
        System.out.println("item deleted from the wishlist of the user.");

        return ResponseEntity.noContent().build(); // Wishlist item successfully removed
    }

}
