/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package BackEnd_Library.demo.repository;

import BackEnd_Library.demo.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author USER
 */
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameContaining(String name);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailOrName(String email, String name);
    boolean existsByEmail(String email);
}
