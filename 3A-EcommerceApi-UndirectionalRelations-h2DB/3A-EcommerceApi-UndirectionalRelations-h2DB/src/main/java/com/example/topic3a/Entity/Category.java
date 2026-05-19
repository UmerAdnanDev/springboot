package com.example.topic3a.Entity;
import jakarta.persistence.*;

/*
 CATEGORY entity - The "inverse" side (but no back reference!)
 - UNIDIRECTIONAL: This entity has NO reference back to Product
 - In this e-commerce system, categories are just labels.
   You don't need to query "find all products in a category" from the category side.
  That query would be done through ProductRepository.findByCategoryId()
  IMPORTANT: No @ManyToMany(mappedBy = "categories") here!
  That would make it bidirectional and allow Category → Products navigation
 */
@Entity
@Table(name = "category")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    
    // Constructors
    public Category() {}
    
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}