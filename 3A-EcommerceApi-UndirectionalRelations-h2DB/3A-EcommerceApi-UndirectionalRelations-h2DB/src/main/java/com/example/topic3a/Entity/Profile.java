package com.example.topic3a.Entity;
import jakarta.persistence.*;

/*
  PROFILE entity - The "inverse" side (but no back reference!)
  - UNIDIRECTIONAL: This entity has NO reference back to User
  - You never need to query "find user by profile" in this system
  - IMPORTANT: No @OneToOne(mappedBy = "profile") here! That would make it bidirectional
 */
@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String phone;
    private String address;
    private String city;
    private String postalCode;
    
    // Constructors
    public Profile() {}
    
    public Profile(String fullName, String phone, String address, String city, String postalCode) {
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
}