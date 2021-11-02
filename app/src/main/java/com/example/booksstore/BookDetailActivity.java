package com.example.booksstore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.booksstore.fragment.BookFragment;
import com.example.booksstore.model.Book;
import com.example.booksstore.model.BookImageLinks;
import com.example.booksstore.model.BookVolumeInfo;

import java.net.URI;

public class BookDetailActivity extends AppCompatActivity {

    public static final String BOOK= "BOOK";

    ImageView saveButton, closeButton, bookImage;
    TextView bookTitle, bookAuthors, bookYear, bookRating, bookDescription;
    Book book;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        saveButton = (ImageView) findViewById(R.id.saveButton);
        closeButton = (ImageView) findViewById(R.id.closeButon);
        bookImage = (ImageView) findViewById(R.id.bookImage);
        bookTitle = (TextView) findViewById(R.id.bookTitle);
        bookAuthors = (TextView) findViewById(R.id.textAuthors);
        bookYear = (TextView) findViewById(R.id.publishDate);
        bookRating = (TextView) findViewById(R.id.rating);
        bookDescription = (TextView) findViewById(R.id.bookDescription);

        book = (Book) getIntent().getParcelableExtra(BOOK);
        bookTitle.setText(book.getVolumeInfo().getTitle());
        bookAuthors.setText(book.getVolumeInfo().getAuthors() + "");
        bookAuthors.setVisibility(book.getVolumeInfo().getAuthors() != null ? View.VISIBLE : View.GONE);
        bookRating.setText(book.getVolumeInfo().getAverageRating() + "");
        bookDescription.setText(book.getVolumeInfo().getDescription());
        bookYear.setText(book.getVolumeInfo().getPublishedDate());

        if (book.getVolumeInfo().getImageLinks() != null) {
            String imgUrl = book.getVolumeInfo().getImageLinks().getThumbnail();
            Glide.with(this).load(imgUrl).placeholder(R.drawable.nothumbnail).into(bookImage);
        }
    }

    public void moreDetails(View view) {
        if (book != null){
            String url = book.getVolumeInfo().getPreviewLink();
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra(WebViewActivity.URL_EXTRA, url);
            startActivity(intent);
        }
    }

    public void closeBookDetails(View view) {
        finish();
    }
}
