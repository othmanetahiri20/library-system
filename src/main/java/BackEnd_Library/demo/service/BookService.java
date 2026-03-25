/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd_Library.demo.service;

import BackEnd_Library.demo.repository.BookRepository;
import BackEnd_Library.demo.entity.Book;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author USER
 */
@Service
public class BookService {
    
    private final BookRepository repo;
    
    public BookService(BookRepository repo){
        this.repo = repo ;
    }
    
    public List<Book> getAllBooks(){
        return repo.findAll();
    }
    
    public Book getBookById(Long id){
        return repo.findById(id).orElseThrow(()-> new RuntimeException("book not found with thisid :"+id));
    }
    
    public Book addBook(Book book){
        return repo.save(book);
    }
    
    public Book updateBook(Long id, Book bookDetails){
        Book book = repo.findById(id).orElseThrow(()-> new RuntimeException("book not found with this id :"+id));
        
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setCategory(bookDetails.getCategory());
        book.setQuantity(bookDetails.getQuantity());
        
        return repo.save(book);
    }
    
    public void deleteBook(Long id){
        repo.deleteById(id);
    }
    
    public List<Book> searchBookByTiitle(String title){
        List<Book> books = repo.findByAuthorContaining(title);
        if(books.isEmpty()){
            throw new RuntimeException("book not found with this Title :"+title);
        }
        return books;
    }
    
    public List<Book> searchBookByAuthor(String author){
        List<Book> books = repo.findByAuthorContaining(author);
        if(books.isEmpty()){
            throw new RuntimeException("book not found with this Author : "+ author);
        }
        return books;
    }
    
    public List<Book> searchBookByCategory(String category){
        List<Book> books = repo.FindByCategory(category);
        if(books.isEmpty()){
            throw new RuntimeException("book not found with this Category : "+ category);
        }
        return books;
    }
    
    public List<Book> getAvailiableBooks(){
        return repo.findAvailiableBooks();
    }
    
   
}
