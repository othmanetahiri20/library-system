/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package BackEnd_Library.demo.repository;

import BackEnd_Library.demo.entity.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;

import java.util.List;
/**
 *
 * @author USER
 */
public interface BookRepository extends JpaRepository<Book, Long>{
    
    List<Book> findByTitleContaining(String title);
    List<Book> findByAuthorContaining(String author);
    List<Book> FindByCategory(String category);
    
    @Query("SELECT b FROM Book b WHERE b NOT IN"+
            "(SELECT bo.book FROM Borrow bo WHERE bo.statut = 'BORROWED')")
    List<Book> findAvailiableBooks();
    
    
    
}
