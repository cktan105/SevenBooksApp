package com.sa45team7.sevenbooksapp.dummy;

import com.sa45team7.sevenbooksapp.model.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Book> BOOKS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Integer, Book> BOOK_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addBook(createDummyBook(i));
        }
    }

    private static void addBook(Book book) {
        BOOKS.add(book);
        BOOK_MAP.put(book.getId(), book);
    }

    private static Book createDummyBook(int position) {
        return new Book(position, "Book " + position, "Author " + position, "123456", "cat", 10, 10);
    }


}
