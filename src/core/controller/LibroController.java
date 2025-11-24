/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controller;

import core.controller.utils.Response;
import core.controller.utils.Status;
import core.model.Publisher;
import core.model.libro.AudioBook;
import core.model.libro.Book;
import core.model.libro.DigitalBook;
import core.model.libro.PrintedBook;
import core.model.persona.Author;
import core.model.persona.Narrator;
import core.model.persona.Person;
import core.model.storage.EditorialStorage;
import core.model.storage.LibroStorage;
import core.model.storage.PersonaStorage;
import java.util.ArrayList;


public class LibroController {

    public static Response addLibroFisico(String title, String autorData, String isbn, String genre, String format, String valuetxt, String editorialData, String nroPaginas, String nroCopias) {
        try {
            PersonaStorage personas = PersonaStorage.getInstance();
            EditorialStorage editorales = EditorialStorage.getInstance();
            LibroStorage creteLibro = LibroStorage.getInstance();
            ArrayList<Book> libros = creteLibro.getLibros();
            if (title.isBlank() || title.isEmpty()) {
                return new Response("Title must be not empty", Status.BAD_REQUEST);
            }
            if (autorData.isBlank() || autorData.isEmpty()) {
                return new Response("Autors datas must be not empty", Status.BAD_REQUEST);
            }
            String[] autorsData = autorData.split("\n");
            ArrayList<Author> authors = new ArrayList<>();
            for(Person p : personas.getPersonas()){
                if (p instanceof Author) {
                    authors.add((Author)p);
                }
            }
            if (authors.isEmpty()) {
                return new Response("You must have authors registered", Status.BAD_REQUEST);
            }
            for (String authorData : autorsData) {
                long authorId;
                try {
                    authorId = Long.parseLong(authorData.split(" - ")[0]);
                } catch (NumberFormatException e) {
                    return new Response("Autor ID must be a number", Status.BAD_REQUEST);
                }
                for (Author author : authors ) {
                    if (author.getId() == authorId) {
                        authors.add(author);
                    }
                }
            }
            if (isbn.isBlank() || isbn.isEmpty()) {
                return new Response("ISBN must be not empty", Status.BAD_REQUEST);
            }
            if (!isbn.matches("^\\d{3}-\\d{2}-\\d{6}-\\d$")) {
                return new Response("ISBN must hav this structure XXX-X-XX-XXXXXX-X", Status.BAD_REQUEST);
            }
            if (genre.equalsIgnoreCase("Seleccione uno...")) {
                return new Response("Genre must be not empty", Status.BAD_REQUEST);
            }
            if (format.equalsIgnoreCase("Seleccione uno...")) {
                return new Response("Format must be not empty", Status.BAD_REQUEST);
            }
            if (valuetxt.isBlank() || valuetxt.isEmpty()) {
                return new Response("Value must be not empty", Status.BAD_REQUEST);
            }
            double value;
            try {
                value = Double.parseDouble(valuetxt);
            } catch (NumberFormatException e) {
                return new Response("Value must be a number", Status.BAD_REQUEST);
            }
            if (editorialData.isBlank() || editorialData.isEmpty()) {
                return new Response("Editorial must be not empty", Status.BAD_REQUEST);
            }
            String publisherNit = editorialData.split(" ")[1].replace("(", "").replace(")", "");
            Publisher editorial = null;
            for (Publisher p : editorales.getEditoriales()){
                if (p.getNit() == publisherNit) {
                    editorial = p;
                }
            }
            if (nroCopias.isBlank() || nroCopias.isEmpty()) {
                return new Response("Copies must be not empty", Status.BAD_REQUEST);
            }
            int copias;
            try {
                copias = Integer.parseInt(nroCopias);
            } catch (NumberFormatException e) {
                return new Response("Copies must be a number", Status.BAD_REQUEST);
            }
            if (nroPaginas.isBlank() || nroPaginas.isEmpty()) {
                return new Response("Pages must be not empty", Status.BAD_REQUEST);
            }
            int paginas;
            try {
                paginas = Integer.parseInt(nroPaginas);
            } catch (Exception e) {
                return new Response("Pages must be a number", Status.BAD_REQUEST);
            }
            PrintedBook libroTemp = new PrintedBook(title, authors, isbn, genre, format, value, editorial, paginas, copias);
            libros.add(libroTemp);
            return new Response("PrintedBook created successfuly", Status.CREATED);
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response addLibroVirtual(String title, String autorData, String isbn, String genre, String format, String valuetxt, String editorialData, String url) {
        try {
            PersonaStorage personas = PersonaStorage.getInstance();
            EditorialStorage editorales = EditorialStorage.getInstance();
            LibroStorage creteLibro = LibroStorage.getInstance();
            ArrayList<Book> libros = creteLibro.getLibros();
            if (title.isBlank() || title.isEmpty()) {
                return new Response("Title must be not empty", Status.BAD_REQUEST);
            }
            if (autorData.isBlank() || autorData.isEmpty()) {
                return new Response("Autors datas must be not empty", Status.BAD_REQUEST);
            }
            String[] autorsData = autorData.split("\n");
            ArrayList<Author> authors = new ArrayList<>();
            for(Person p : personas.getPersonas()){
                if (p instanceof Author) {
                    authors.add((Author)p);
                }
            }
            if (authors.isEmpty()) {
                return new Response("You must have authors registered", Status.BAD_REQUEST);
            }
            for (String authorData : autorsData) {
                long authorId;
                try {
                    authorId = Long.parseLong(authorData.split(" - ")[0]);
                } catch (NumberFormatException e) {
                    return new Response("Autor ID must be a number", Status.BAD_REQUEST);
                }
                for (Author author : authors ) {
                    if (author.getId() == authorId) {
                        authors.add(author);
                    }
                }
            }
            if (isbn.isBlank() || isbn.isEmpty()) {
                return new Response("ISBN must be not empty", Status.BAD_REQUEST);
            }
            if (!isbn.matches("^\\d{3}-\\d{2}-\\d{6}-\\d$")) {
                return new Response("ISBN must hav this structure XXX-X-XX-XXXXXX-X", Status.BAD_REQUEST);
            }
            if (genre.equalsIgnoreCase("Seleccione uno...")) {
                return new Response("Genre must be not empty", Status.BAD_REQUEST);
            }
            if (format.equalsIgnoreCase("Seleccione uno...")) {
                return new Response("Format must be not empty", Status.BAD_REQUEST);
            }
            if (valuetxt.isBlank() || valuetxt.isEmpty()) {
                return new Response("Value must be not empty", Status.BAD_REQUEST);
            }
            double value;
            try {
                value = Double.parseDouble(valuetxt);
            } catch (NumberFormatException e) {
                return new Response("Value must be a number", Status.BAD_REQUEST);
            }
            if (editorialData.isBlank() || editorialData.isEmpty()) {
                return new Response("Editorial must be not empty", Status.BAD_REQUEST);
            }
            String publisherNit = editorialData.split(" ")[1].replace("(", "").replace(")", "");
            Publisher editorial = null;
            for (Publisher p : editorales.getEditoriales()){
                if (p.getNit() == publisherNit) {
                    editorial = p;
                }
            }
            DigitalBook libroTemp = null;
            if (url.isBlank() || url.isEmpty()) {
                libroTemp = new DigitalBook(title, authors, isbn, genre, format, value, editorial);
            }
            else{
                libroTemp = new DigitalBook(title, authors, isbn, genre, format, value, editorial, url);
            }
            
            libros.add(libroTemp);
            return new Response("DigitalBook created successfuly", Status.CREATED);
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }


    public static Response addAudioLibro(String title, String autorData, String isbn, String genre, String format, String valuetxt, String editorialData, String durationtxt, String narradorData) {
        try {
            PersonaStorage personas = PersonaStorage.getInstance();
            EditorialStorage editorales = EditorialStorage.getInstance();
            LibroStorage creteLibro = LibroStorage.getInstance();
            ArrayList<Book> libros = creteLibro.getLibros();
            if (title.isBlank() || title.isEmpty()) {
                return new Response("Title must be not empty", Status.BAD_REQUEST);
            }
            if (autorData.isBlank() || autorData.isEmpty()) {
                return new Response("Autors datas must be not empty", Status.BAD_REQUEST);
            }
            String[] autorsData = autorData.split("\n");
            ArrayList<Author> authors = new ArrayList<>();
            for(Person p : personas.getPersonas()){
                if (p instanceof Author) {
                    authors.add((Author)p);
                }
            }
            if (authors.isEmpty()) {
                return new Response("You must have authors registered", Status.BAD_REQUEST);
            }
            for (String authorData : autorsData) {
                long authorId;
                try {
                    authorId = Long.parseLong(authorData.split(" - ")[0]);
                } catch (NumberFormatException e) {
                    return new Response("Autor ID must be a number", Status.BAD_REQUEST);
                }
                for (Author author : authors ) {
                    if (author.getId() == authorId) {
                        authors.add(author);
                    }
                }
            }
            if (isbn.isBlank() || isbn.isEmpty()) {
                return new Response("ISBN must be not empty", Status.BAD_REQUEST);
            }
            if (!isbn.matches("^\\d{3}-\\d{2}-\\d{6}-\\d$")) {
                return new Response("ISBN must hav this structure XXX-X-XX-XXXXXX-X", Status.BAD_REQUEST);
            }
            if (genre.equalsIgnoreCase("Seleccione uno...")) {
                return new Response("Genre must be not empty", Status.BAD_REQUEST);
            }
            if (format.equalsIgnoreCase("Seleccione uno...")) {
                return new Response("Format must be not empty", Status.BAD_REQUEST);
            }
            if (valuetxt.isBlank() || valuetxt.isEmpty()) {
                return new Response("Value must be not empty", Status.BAD_REQUEST);
            }
            double value;
            try {
                value = Double.parseDouble(valuetxt);
            } catch (NumberFormatException e) {
                return new Response("Value must be a number", Status.BAD_REQUEST);
            }
            if (editorialData.isBlank() || editorialData.isEmpty()) {
                return new Response("Editorial must be not empty", Status.BAD_REQUEST);
            }
            String publisherNit = editorialData.split(" ")[1].replace("(", "").replace(")", "");
            Publisher editorial = null;
            for (Publisher p : editorales.getEditoriales()){
                if (p.getNit() == publisherNit) {
                    editorial = p;
                }
            }
            if (durationtxt.isBlank() || durationtxt.isEmpty()) {
                return new Response("Copies must be not empty", Status.BAD_REQUEST);
            }
            int duracion;
            try {
                duracion = Integer.parseInt(durationtxt);
            } catch (NumberFormatException e) {
                return new Response("Copies must be a number", Status.BAD_REQUEST);
            }
            if (narradorData.isBlank() || narradorData.isEmpty()) {
                return new Response("Pages must be not empty", Status.BAD_REQUEST);
            }
            String[] narratorsData = narradorData.split(" - ");
            ArrayList<Narrator> narradores = new ArrayList<>();
            for(Person p : personas.getPersonas()){
                if (p instanceof Narrator) {
                    narradores.add((Narrator)p);
                }
            }
            if (narradores.isEmpty()) {
                return new Response("You must have authors registered", Status.BAD_REQUEST);
            }
            Narrator narradorA = null;
            for (String narratorData : narratorsData) {
                long narratorId;
                try {
                    narratorId = Long.parseLong(narratorData.split(" - ")[0]);
                } catch (NumberFormatException e) {
                    return new Response("Autor ID must be a number", Status.BAD_REQUEST);
                }
                for (Narrator narrador : narradores ) {
                    if (narrador.getId() == narratorId) {
                        narradorA = narrador;
                    }
                }
            }
            AudioBook libroTemp = new AudioBook(title, authors, isbn, genre, format, value, editorial, duracion, narradorA);
            libros.add(libroTemp);
            return new Response("AudioBook created successfuly", Status.CREATED);
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
