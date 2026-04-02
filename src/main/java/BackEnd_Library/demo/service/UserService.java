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
        return userRepo.save(user);
    }
    
    public User updateUser(Long id, User userDetails){
        User user = userRepo.findById(id).orElseThrow(()->new RuntimeException("User not found with this id : "+id));
        
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setBorrow(userDetails.getBorrow());
        user.setRole(userDetails.getRole());
        
        return userRepo.save(user);
    }
    
    public void deleteUser(Long id){
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
    
    public List<User> searchUserByEmail(String email){
        List<User> users =userRepo.findByEmail(email);
        
        if(users.isEmpty()){
            throw new RuntimeException("User not found with this email :"+email);
        }
        return users;
    }
    
    public User logInUser(String email, String name, String password){
        User user = userRepo.findByEmailOrName(email, name);
        if(!user.getPassword().equals(password)){
            throw new RuntimeException("Name or email or password ar not correct");
        }
        return user ;
    }
    
    public void logOutUser(){
        
    }
    
    public void ChangePassword(String name, String email, String password, String newPassword){
        User user = userRepo.findByEmailOrName(email, name);
        if(user==null){
            throw new RuntimeException("User not found Name or Email is inccorect");
        }else if(!user.getPassword().equals(password)){
            throw new RuntimeException("Name or email or password ar not correct");
        }
        
        user.setPassword(newPassword);
        
        userRepo.save(user);
    }
    
    public boolean checkUserExcists(Long id){
        return userRepo.existsById(id);
    }
}