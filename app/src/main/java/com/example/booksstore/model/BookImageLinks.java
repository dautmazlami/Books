package com.example.booksstore.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BookImageLinks implements Parcelable {

    String smallThumbnail, thumbnail;

    public BookImageLinks() {
    }


    protected BookImageLinks(Parcel in) {
        smallThumbnail = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<BookImageLinks> CREATOR = new Creator<BookImageLinks>() {
        @Override
        public BookImageLinks createFromParcel(Parcel in) {
            return new BookImageLinks(in);
        }

        @Override
        public BookImageLinks[] newArray(int size) {
            return new BookImageLinks[size];
        }
    };

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(smallThumbnail);
        dest.writeString(thumbnail);
    }
}
