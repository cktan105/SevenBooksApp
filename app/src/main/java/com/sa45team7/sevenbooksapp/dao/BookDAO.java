package com.sa45team7.sevenbooksapp.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sa45team7.sevenbooksapp.dummy.DummyContent;
import com.sa45team7.sevenbooksapp.model.Book;
import com.sa45team7.sevenbooksapp.util.HttpHandler;

import java.util.List;

/**
 * Created by nhatton on 12/20/17.
 */

public class BookDAO {

    private static final String ROOT_URL = "";
    private static BookDAO instance;

    private BookDAO() {

    }

    public static BookDAO getInstance() {
        if (instance == null) {
            instance = new BookDAO();
        }
        return instance;
    }

    public List<Book> getAllBooks() {
//        String json = HttpHandler.makeServiceCall(ROOT_URL + "Books");
//        Gson gson = new Gson();
//        List<Book> books = gson.fromJson(json, new TypeToken<List<Book>>(){}.getType());
//        return books;
        return DummyContent.BOOKS;
    }


}
