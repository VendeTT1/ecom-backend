package com.online.shop.ecombackend.model;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Order generated from the website.
 */
@Entity
@Table(name = "orders")
public class Order {

    /** Unique id for the order. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    /** The user of the order. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    /** The shipping address of the order. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
    /** The quantity ordered. */
    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OrderQuantity> quantity = new ArrayList<>();

    /**
     * Gets the quantity ordered.
     * @return The quantity.
     */
    public List<OrderQuantity> getquantity() {
        return quantity;
    }

    /**
     * Sets the quantity ordered.
     * @param quantity The quantity.
     */
    public void setquantity(List<OrderQuantity> quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the address of the order.
     * @return The address.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the address of the order.
     * @param address The address.
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Gets the user of the order.
     * @return The user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user of the order.
     * @param user The user.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the id of the order.
     * @return The id.
     */
    public Long getId() {
        return id;
    }
}
