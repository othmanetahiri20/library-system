/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd_Library.demo.service;

import BackEnd_Library.demo.entity.User;
import BackEnd_Library.demo.repository.UserRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author USER
 */
@Service
public class UserService {
    private final UserRepository userRepo;
    
    public UserService(UserRepository userRepo){
        this.userRepo = userRepo;
    }
    
    public User addUser(User user){
        if(userRepo.existsByEmail(user.getEmail()))
            throw new RuntimeException("Email already used :"+user.getEmail());
        return userRepo.save(user);
    }
    
    public User updateUser(Long id, User userDetails){
        User user = userRepo.findById(id).orElseThrow(()->new RuntimeException("User not found with this id : "+id));
        
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());
        
        return userRepo.save(user);
    }
    
    public void deleteUser(Long id){
        if(!userRepo.existsById(id))
            throw new RuntimeException("User not found with id :" + id);
        userRepo.deleteById(id);
    }
    
    public User getUserById(Long id){
        return userRepo.findById(id).orElseThrow(()-> new RuntimeException("User not found with this id : "+id));
    }
    
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
    
    public List<User> searchUserByName(String name){
        List<User> users = userRepo.findByNameContaining(name);
        
        if(users.isEmpty()){
            throw new RuntimeException("User not found with this name :"+name);
        }
        
        return users;
    }
    
    public User searchUserByEmail(String email){
        return userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found whith email :"+email));
    }
    
    public User logInUser(String email, String password){
        User user = userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("No account found with this email"));
        if(!user.getPassword().equals(password)){
            throw new RuntimeException(" Incorrect password");
        }
        return user ;
    }
    
    public void logOutUser(){
        //géré coter javafx
    }
    
    @Transactional
    public void ChangePassword(Long id, String oldPassword, String newPassword){
        User user = userRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id : " + id));
        
        if (!user.getPassword().equals(oldPassword))
            throw new RuntimeException("Incorrect current password");

        if (newPassword == null || newPassword.isBlank())
            throw new IllegalArgumentException("New password cannot be empty");

        user.setPassword(newPassword);
        userRepo.save(user);
    }
    
    public boolean checkUserExcists(Long id){
        return userRepo.existsById(id);
    }
}