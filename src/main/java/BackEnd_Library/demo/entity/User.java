/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd_Library.demo.entity;

import jakarta.persistence.*;
import java.util.List;
/**
 *
 * @author USER
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ; 
    
    private String name ;
    
    @Column(unique = true)
    private String email;
    private String password;
    private String role;
    
    @OneToMany(mappedBy="usesr", cascade = CascadeType.ALL)
    private List<Borrow> borrows ;
    
    public User(){}
    
    public User(String name, String email, String password, String role){
        this.name=name;
        this.email = email;
        this.password = password ; 
        this.role = role ;
    }
    
    public Long getID(){ return id ; }
    
    public String getName(){ return name ; }
    public void setName(String name ){ this.name = name ;}
    
    public String getEmail() { return email ; }
    public void setEmail(String email){ this.email = email ; }
    
    public String getPassword(){ return password ; }
    public void setPassword(String password){ this.password = password ; }
    
    public String getRole(){return role ; }
    public void setRole(String role){ this.role = role ; }
    
    public List<Borrow> getBorrow(){ return borrows ; }
    public void setBorrow(List<Borrow> borrows){this.borrows=borrows;}
    
}
