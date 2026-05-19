package com.example.topic3a.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.topic3a.Entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    //DERIVED QUERIES (Spring parses method name)

    // Find by exact username
    Optional<User> findByUsername(String username);
    
    // Find by email containing (LIKE query)
    List<User> findByEmailContaining(String emailDomain);
    
    // Find by username and email (multiple conditions)
    Optional<User> findByUsernameAndEmail(String username, String email);
    
    // Check if user exists by email
    boolean existsByEmail(String email);
    
    // Count users by city (nested - goes through profile)
    // This works because User → Profile is unidirectional but we can still query!
    Long countByProfileCity(String city);
    
    //JPQL QUERIES with @Query
    
    // Find users with profile in specific city
    @Query("SELECT u FROM User u WHERE u.profile.city = :city")
    List<User> findUsersByCity(@Param("city") String city);
    
    // Find users with no profile (profile is null)
    @Query("SELECT u FROM User u WHERE u.profile IS NULL")
    List<User> findUsersWithoutProfile();

    // Find users who have a cart with status ACTIVE
    @Query("SELECT u FROM User u JOIN Cart c ON c.user.id = u.id WHERE c.status = 'ACTIVE'")
    List<User> findUsersWithActiveCart();
}