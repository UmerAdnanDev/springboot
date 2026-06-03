package com.example.topic4a.mapper;

import com.example.topic4a.dto.request.UserRequest;
import com.example.topic4a.dto.response.UserResponse;
import com.example.topic4a.entity.User;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    
    // Convert Request to Entity
    public static User toEntity(UserRequest request) {
        if (request == null) {
            return null;
        }
        
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        // createdAt/updatedAt set in constructor or manually
        
        return user;
    }
    
    // Convert Entity to Response
    public static UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setAddress(user.getAddress());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        
        return response;
    }
    
    // Convert List<Entity> to List<Response>
    public static List<UserResponse> toResponseList(List<User> users) {
        if (users == null) {
            return null;
        }
        return users.stream()
            .map(UserMapper::toResponse)
            .collect(Collectors.toList());
    }
}