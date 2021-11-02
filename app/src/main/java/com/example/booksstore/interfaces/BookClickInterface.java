package com.example.booksstore.interfaces;

import com.example.booksstore.model.Book;

public interface BookClickInterface {

    void onBookClick (Book book);
    void onSeeAllClick (int sectionType, int bookType);
}
