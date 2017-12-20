package com.sa45team7.sevenbooksapp.dao;

import com.sa45team7.sevenbooksapp.dummy.DummyContent;
import com.sa45team7.sevenbooksapp.model.Book;

import java.util.HashMap;
import java.util.List;

/**
 * Created by nhatton on 12/20/17.
 */

public class BookDAO {

    private static final String ROOT_URL = "";
    private static BookDAO instance;
    private HashMap<Integer, Book> booksMap = new HashMap<>();

    private BookDAO() {

    }

    public static BookDAO getInstance() {
        if (instance == null) {
            instance = new BookDAO();
        }
        return instance;
    }

    public List<Book> getAllBooks() {
        return DummyContent.BOOKS;
    }

    public void setBooksMap(List<Book> books) {
        for (Book book : books) {
            booksMap.put(book.getId(), book);
        }
    }

    public HashMap<Integer, Book> getBooksMap() {
        return booksMap;
    }

}
