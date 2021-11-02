package com.example.booksstore.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

    String id, selfLink;
    BookVolumeInfo volumeInfo;

    public Book() {
    }

    protected Book(Parcel in) {
        id = in.readString();
        selfLink = in.readString();
        volumeInfo = in.readParcelable(BookVolumeInfo.class.getClassLoader());
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public BookVolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(BookVolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(selfLink);
        dest.writeParcelable(volumeInfo, flags);
    }
}
