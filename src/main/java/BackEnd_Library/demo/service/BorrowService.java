/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd_Library.demo.service;

import BackEnd_Library.demo.entity.Book;
import BackEnd_Library.demo.entity.Borrow;
import BackEnd_Library.demo.entity.Status;
import BackEnd_Library.demo.entity.User;
import BackEnd_Library.demo.repository.BookRepository;
import BackEnd_Library.demo.repository.BorrowRepository;
import BackEnd_Library.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author USER
 */
@Service
public class BorrowService {
    
    private final BorrowRepository borrowRepo ;
    private final BookRepository bookRepo;
    private final UserRepository userRepo;

    public BorrowService(BorrowRepository borrowRepo, BookRepository bookRepo, UserRepository userRepo) {
        this.borrowRepo = borrowRepo;
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
    }
    
    
    @Transactional
    public Borrow borrowBook(Long userId, Long bookId){
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new RuntimeException("user not found with this id"));
        
        Book book = bookRepo.findById(bookId)
                .orElseThrow(()-> new RuntimeException("book not found with this id"));
        
        if(book.getQuantity()<=0)
            throw new RuntimeException("book not available :"+book.getTitle());
        
        if(borrowRepo.existsByBookAndStatus(book, Status.BORROWED))
            throw new RuntimeException("this book is already borrowed");
        
        Borrow borrow = new Borrow();
        borrow.setUser(user);
        borrow.setBook(book);
        borrow.setStatus(Status.BORROWED);
        borrow.setBorrowDate(LocalDate.now());
        borrow.setReturnDate(LocalDate.now().plusDays(14));
        
        book.setQuantity(book.getQuantity()-1);
        bookRepo.save(book);
        
        return borrowRepo.save(borrow);
    }
    
    @Transactional
    public Borrow returnBook(Long borrowId){
        Borrow borrow = borrowRepo.findById(borrowId)
                .orElseThrow(()->new RuntimeException("borrow not found with this id :"+borrowId));
        
        if(borrow.getStatus() == Status.RETURNED)
            throw new RuntimeException("book already returned");
        
        borrow.setStatus(Status.RETURNED);
        borrow.setReturnDate(LocalDate.now());
        
        Book book = borrow.getBook();
        book.setQuantity(book.getQuantity()+1);
        bookRepo.save(book);
        
        return borrowRepo.save(borrow);
    }
    
    public List<Borrow> getAllBorrows(){
        return borrowRepo.findAll();
    }
    
    public List<Borrow> getBorrowByUser(Long userId){
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new RuntimeException("user not found with this id :"+userId));
        
        return borrowRepo.findByUser(user);
    }
    
    public List<Borrow> getBorrowByBook(Long bookId){
        Book book = bookRepo.findById(bookId)
                .orElseThrow(()->new RuntimeException("book not found with this id :"+bookId));
        
        return borrowRepo.findByBook(book);
    }
    
    public Borrow getBorrowById(Long borrowId){
        return borrowRepo.findById(borrowId)
                .orElseThrow(()-> new RuntimeException("borrow not found with this id :"+borrowId));
    }
    
    public List<Borrow> getActiveBorrows(){
        return borrowRepo.findByStatus(Status.BORROWED);
    }
    
    public long countActiveBorrows(){
        return borrowRepo.countByStatus(Status.BORROWED);
    }
}
