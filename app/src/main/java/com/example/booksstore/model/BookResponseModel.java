package com.example.booksstore.model;

import java.util.ArrayList;

public class BookResponseModel {

    String kind;
    int totalItems;
    ArrayList<Book> items;
    int sectionType;

    BookResponseModel(){}

    public int getSectionType() {
        return sectionType;
    }

    public void setSectionType(int sectionType) {
        this.sectionType = sectionType;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public ArrayList<Book> getItems() {
        return items;
    }

    public void setItems(ArrayList<Book> items) {
        this.items = items;
    }
}
