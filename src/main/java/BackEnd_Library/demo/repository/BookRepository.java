/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package BackEnd_Library.demo.repository;

import BackEnd_Library.demo.entity.Book;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author USER
 */
public interface BookRepository extends JpaRepository<Book, Long>{
    
}
