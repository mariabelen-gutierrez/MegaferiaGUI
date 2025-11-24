/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.model.persona;

import core.model.persona.Person;
import core.model.libro.AudioBook;
import java.util.ArrayList;

/**
 *
 * @author edangulo
 */
public class Narrator extends Person {
    
    private ArrayList<AudioBook> books;

    public Narrator(long id, String firstname, String lastname) {
        super(id, firstname, lastname);
        this.books = new ArrayList<>();
    }
    
    public int getBookQuantity() {
        return this.books.size();
    }
    
    public void addBook(AudioBook book) {
        this.books.add(book);
    }
    
}
