/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd_Library.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "borrow")
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate borrowDate ;
    private LocalDate returnDate ;
    @Enumerated(EnumType.STRING )
    private Status status ;
    
   @ManyToOne
   @Column(name = "user_id")
   private User user;
   
   @ManyToOne
   @Column(name = "book_id")
   private Book book;

   public Borrow() {}

   public Borrow(LocalDate borrowDate, LocalDate returnDate, Status status, User user, Book book) {
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
        this.user = user;
        this.book = book;
   }

    public Long getId(){
        return id ;
    }
    
    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
   
}
