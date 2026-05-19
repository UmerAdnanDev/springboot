package com.example.topic3a.Entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 - CART entity - The "owner" side
 - UNIDIRECTIONAL: Cart knows about CartItems, but CartItems do  not know about Cart
 - Items are useless without a cart, so no need to navigate from item to cart
 */
@Entity
@Table(name = "cart")
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime createdDate;
    private String status;  // ACTIVE, CHECKED_OUT, ABANDONED
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;  // Which user owns this cart
    
    /*
     * ONE-TO-MANY UNIDIRECTIONAL
     - OneToMany: One cart can have many cart items
     - cascade = CascadeType.ALL: When cart is saved/deleted, all items are saved/deleted
     - orphanRemoval = true: If item removed from list, it's deleted from database
     - JoinColumn: Foreign key in cart_item table (NO mappedBy! This is unidirectional)
   
     * NAVIGATION: Cart → Items (YES), Items → Cart (NO)
      
      CRITICAL: Notice there's NO "mappedBy" attribute! 
      That's what makes this unidirectional - Cart owns the relationship completely
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")  // This creates cart_id column in cart_item table
    private List<CartItem> items = new ArrayList<>();
    
    // Constructors
    public Cart() {}
    
    public Cart(LocalDateTime createdDate, String status, User user) {
        this.createdDate = createdDate;
        this.status = status;
        this.user = user;
    }
    
    // Helper method to add item (maintains consistency)
    public void addItem(CartItem item) {
        items.add(item);
    }
    
    // Helper method to remove item
    public void removeItem(CartItem item) {
        items.remove(item);
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
}