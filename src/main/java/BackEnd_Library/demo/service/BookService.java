/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd_Library.demo.service;

import BackEnd_Library.demo.repository.BookRepository;
import BackEnd_Library.demo.repository.BorrowRepository;
import BackEnd_Library.demo.entity.Book;
import BackEnd_Library.demo.entity.Borrow;
import BackEnd_Library.demo.entity.Status;
import jakarta.transaction.Transactional;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author USER
 */
@Service
public class BookService {
    
    private final BookRepository bookRepo;
    private final BorrowRepository borrowRepo;
    
    public BookService(BookRepository bookRepo, BorrowRepository borrowRepo){
        this.bookRepo = bookRepo ;
        this.borrowRepo = borrowRepo ;
    }
    
    public List<Book> getAllBooks(){
        return bookRepo.findAll();
    }
    
    public Book getBookById(Long id){
        return bookRepo.findById(id).orElseThrow(()-> new RuntimeException("book not found with thisid :"+id));
    }
    
    public Book addBook(Book book){
        return bookRepo.save(book);
    }
    
    public Book updateBook(Long id, Book bookDetails){
        Book book = bookRepo.findById(id).orElseThrow(()-> new RuntimeException("book not found with this id :"+id));
        
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setCategory(bookDetails.getCategory());
        book.setQuantity(bookDetails.getQuantity());
        
        return bookRepo.save(book);
    }
    
    public void deleteBook(Long id){
        bookRepo.deleteById(id);
    }
    
    public List<Book> searchBookByTiitle(String title){
        List<Book> books = bookRepo.findByTitleContaining(title);
        if(books.isEmpty()){
            throw new RuntimeException("book not found with this Title :"+title);
        }
        return books;
    }
    
    public List<Book> searchBookByAuthor(String author){
        List<Book> books = bookRepo.findByAuthorContaining(author);
        if(books.isEmpty()){
            throw new RuntimeException("book not found with this Author : "+ author);
        }
        return books;
    }
    
    public List<Book> searchBookByCategory(String category){
        List<Book> books = bookRepo.findByCategory(category);
        if(books.isEmpty()){
            throw new RuntimeException("book not found with this Category : "+ category);
        }
        return books;
    }
    
    public List<Book> getAvailiableBooks(){
        return bookRepo.findAvailiableBooks();
    }
    
   @Transactional
   public void addBookQuantity(Long id, int quantityToAdd){
       if (quantityToAdd <= 0) throw new IllegalArgumentException("Quantity must be > 0");
       
       int update = bookRepo.updateQuantity(id, quantityToAdd);
       if(update == 0) throw new RuntimeException("Cannot add quantity: book not found");
   }
   
   @Transactional
   public void decreaseBookQuantity(Long id, int quantityToDecrease){
       if(quantityToDecrease == 0)throw new IllegalArgumentException("Quantity must be > 0");
       
       int update = bookRepo.updateQuantity(id, -quantityToDecrease);
       if(update == 0 ) throw new RuntimeException("Cannot decrease quantity: book not found or not enough stock");
   }
   
   public void markBookAsBorrowed(Long bookId){
       
       Book book = bookRepo.findById(bookId).orElseThrow(()-> new RuntimeException("Book nit found"));
       
       if(book.getQuantity()<= 0) throw new RuntimeException("Book not available");
       
       book.setQuantity(book.getQuantity()-1);
       
       Borrow borrow= new Borrow();
       borrow.setBook(book);
       borrow.setStatus(Status.BORROWED);
       borrow.setBorrowDate(LocalDate.now());
       
       borrowRepo.save(borrow);
       bookRepo.save(book);
       
   }
   
   public void markBookAsReturned(Long borrowId){
       
       Book book = bookRepo.findById(borrowId).orElseThrow(()->new RuntimeException("Borow not found"));
       
       book.setQuantity(book.getQuantity()+1);
       
       Borrow borrow = new Borrow();
       borrow.setBook(book);
       borrow.setStatus(Status.RETURNED);
       borrow.setReturnDate(LocalDate.now());

       
       borrowRepo.save(borrow);
       bookRepo.save(book);
       
   }
   
   public long countBooks(){
       return bookRepo.count();
   }
   
   public long countAvailableBooks(){
       return bookRepo.countByQuantityGreaterThan(0);
   }
   
   public long countBorrowedBooks(){
       return borrowRepo.countBYStatus( Status.BORROWED);
   }
   
}
