package com.example.topic3a.Entity;
import jakarta.persistence.*;

/**
 CART ITEM entity - The "inverse" side (but no back reference!)
 - UNIDIRECTIONAL: This entity has NO reference back to Cart
 - Items are always accessed through their cart, never standalone
 
 - IMPORTANT: No @ManyToOne here! That would create bidirectional relationship
 */
@Entity
@Table(name = "cart_item")
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer quantity;
    private Double unitPrice;
    private Double subtotal;  // quantity * unitPrice
    
    /**
     * Many-to-One to Product (This IS allowed - different relationship!)
     - Each cart item points to one product
     - This is not the back reference to Cart - it's a separate relationship
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    
    // Constructors
    public CartItem() {}
    
    public CartItem(Integer quantity, Double unitPrice, Double subtotal, Product product) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
        this.product = product;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }
    
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
    
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}