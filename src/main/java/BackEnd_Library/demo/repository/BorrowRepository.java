/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package BackEnd_Library.demo.repository;

import BackEnd_Library.demo.entity.Book;
import BackEnd_Library.demo.entity.Borrow;
import BackEnd_Library.demo.entity.Status;
import BackEnd_Library.demo.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author USER
 */
public interface BorrowRepository extends JpaRepository<Borrow, Long>{
    
    List<Borrow> findByUser(User user);
    List<Borrow> findByBook(Book book);
    List<Borrow> findByStatus(Status status);
    List<Borrow> findByUserAndStatus(User user, Status status);
    List<Borrow> findByBookAndStatus(Book book, Status status);
    
    long countByStatus(Status status);
    boolean existsByBookAndStatus(Book book, Status status);
}
