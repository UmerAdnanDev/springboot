package com.example.topic3a.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.topic3a.Entity.Cart;
import com.example.topic3a.Entity.CartItem;
import com.example.topic3a.Entity.Product;
import com.example.topic3a.Entity.User;
import com.example.topic3a.Repository.CartRepository;
import com.example.topic3a.Repository.ProductRepository;
import com.example.topic3a.Repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @PostMapping
    public Cart createCart(@RequestBody CartRequest request) {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found: " + request.getUserId()));
        
        Cart cart = new Cart(LocalDateTime.now(), "ACTIVE", user);
        return cartRepository.save(cart);
    }
    
    /*
     ADD item to cart (demonstrates unidirectional OneToMany)
     POST /api/carts/{cartId}/items
     Body: { "productId": 1, "quantity": 2 }
     */
    @PostMapping("/{cartId}/items")
    public Cart addItemToCart(@PathVariable Long cartId, @RequestBody AddItemRequest request) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found: " + cartId));
        
        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found: " + request.getProductId()));
        
        // Calculate subtotal
        Double subtotal = product.getPrice() * request.getQuantity();
        
        // Create cart item (no reference back to cart - unidirectional!)
        CartItem item = new CartItem(
            request.getQuantity(),
            product.getPrice(),
            subtotal,
            product
        );
        
        // Add to cart (cart knows about items, but items don't know about cart)
        cart.addItem(item);
        
        return cartRepository.save(cart);
    }
    
    // GET cart by ID (with items - using JOIN FETCH to load items eagerly)
    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable Long id) {
        return cartRepository.findActiveCartWithItems(id)
            .orElseThrow(() -> new RuntimeException("Cart not found: " + id));
    }
    
    // GET cart by user ID
    @GetMapping("/user/{userId}")
    public Cart getCartByUser(@PathVariable Long userId) {
        return cartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
    }
    
    // GET cart total value (JPQL aggregation)
    @GetMapping("/{cartId}/total")
    public Double getCartTotal(@PathVariable Long cartId) {
        Double total = cartRepository.calculateCartTotal(cartId);
        return total != null ? total : 0.0;
    }
    
    // GET abandoned carts (JPQL query)
    @GetMapping("/abandoned")
    public List<Cart> getAbandonedCarts() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(7);
        return cartRepository.findAbandonedCarts(cutoffDate);
    }
    
    // PUT checkout cart
    @PutMapping("/{cartId}/checkout")
    public String checkoutCart(@PathVariable Long cartId) {
        int updated = cartRepository.checkoutCart(cartId);
        return updated > 0 ? "Cart checked out" : "Checkout failed";
    }
    
    // DELETE cart (cascade will delete all items due to orphanRemoval)
    @DeleteMapping("/{id}")
    public String deleteCart(@PathVariable Long id) {
        cartRepository.deleteById(id);
        return "Cart deleted: " + id;
    }
    
    // Inner classes for requests
    static class CartRequest {
        private Long userId;
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
    }
    
    static class AddItemRequest {
        private Long productId;
        private Integer quantity;
        
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
}