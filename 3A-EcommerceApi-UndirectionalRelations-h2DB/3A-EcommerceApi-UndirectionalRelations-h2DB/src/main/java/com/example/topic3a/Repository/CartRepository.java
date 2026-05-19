package com.example.topic3a.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.example.topic3a.Entity.Cart;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
    //DERIVED QUERIES
    
    // Find cart by user ID
    Optional<Cart> findByUserId(Long userId);
    
    // Find carts by status
    List<Cart> findByStatus(String status);
    
    // Find carts created after date
    List<Cart> findByCreatedDateAfter(java.time.LocalDateTime date);
    
    // Count carts by status
    Long countByStatus(String status);
    
    
    //JPQL QUERIES
    
    // Find active cart for a user (using JOIN FETCH to avoid lazy loading issues)
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.items WHERE c.user.id = :userId AND c.status = 'ACTIVE'")
    Optional<Cart> findActiveCartWithItems(@Param("userId") Long userId);
    
    // Calculate total value of cart (sum of all item subtotals)
    @Query("SELECT SUM(ci.subtotal) FROM Cart c JOIN c.items ci WHERE c.id = :cartId")
    Double calculateCartTotal(@Param("cartId") Long cartId);
    
    // Find carts with more than N items
    @Query("SELECT c FROM Cart c WHERE SIZE(c.items) > :minItems")
    List<Cart> findCartsWithMinItems(@Param("minItems") Integer minItems);
    
    // Get average number of items per cart
    @Query("SELECT AVG(SIZE(c.items)) FROM Cart c")
    Double getAverageItemsPerCart();
    
    // Find abandoned carts (created more than 7 days ago, still ACTIVE)
    @Query("SELECT c FROM Cart c WHERE c.status = 'ACTIVE' AND c.createdDate < :cutoffDate")
    List<Cart> findAbandonedCarts(@Param("cutoffDate") java.time.LocalDateTime cutoffDate);
    
    // Update cart status (checkout)
    @Modifying
    @Transactional
    @Query("UPDATE Cart c SET c.status = 'CHECKED_OUT' WHERE c.id = :cartId AND c.status = 'ACTIVE'")
    int checkoutCart(@Param("cartId") Long cartId);
}
