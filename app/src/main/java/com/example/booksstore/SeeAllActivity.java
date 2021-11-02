package com.example.booksstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.booksstore.adapter.SeeAllAdapter;
import com.example.booksstore.common.Constants;
import com.example.booksstore.fragment.BookFragment;
import com.example.booksstore.interfaces.BookClickInterface;
import com.example.booksstore.model.Book;
import com.example.booksstore.model.BookResponseModel;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SeeAllActivity extends AppCompatActivity implements BookClickInterface {
    public static final String TAG = SeeAllActivity.class.getSimpleName();
    public static final String SECTION_TYPE_EXTRA = "section_extra";
    public static final String BOOK_TYPE_EXTRA = "book_extra";

    int sectionType;
    int bookType;
    RecyclerView recyclerView;
    SeeAllAdapter adapter;
    Gson gson;
    ArrayList<Book> bookList = new ArrayList<>();
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);
        gson = new Gson();

        Intent intent = getIntent();
        if (getIntent().getExtras() != null){
            sectionType = intent.getIntExtra(SECTION_TYPE_EXTRA, -1);
            bookType = intent.getIntExtra(BOOK_TYPE_EXTRA, -1);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_seeall);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_seeall);
        loadBooks(sectionType);

    }

    private void loadBooks(final int section){
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://www.googleapis.com/books/v1/volumes").newBuilder();
        switch (section) {
            case Constants.SectionType.HEALTHY_LIFESTYLE:
                urlBuilder.addQueryParameter("q", bookType == Constants.BookType.BOOKS ? "intitle:healthy+lifestyle" : "healthy+lifestyle");
                break;
            case Constants.SectionType.WELLNESS:
                urlBuilder.addQueryParameter("q", bookType == Constants.BookType.BOOKS ? "intitle:wellness" : "wellness");
                break;
            case Constants.SectionType.HEALTHY_MEALS:
                urlBuilder.addQueryParameter("q", bookType == Constants.BookType.BOOKS ? "intitle:healthy+meals" : "healthy+meals");
                break;
        }

        urlBuilder.addQueryParameter("printType", bookType == Constants.BookType.BOOKS ? "books" : "magazines");
        String url = urlBuilder.build().toString();

        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String jsonString = response.body().string();
                    BookResponseModel bookResponseModel = gson.fromJson(jsonString, BookResponseModel.class);
                    Log.d(TAG, bookResponseModel.getKind());
                    bookResponseModel.setSectionType(section);
                    bookList = bookResponseModel.getItems();

                    SeeAllActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new SeeAllAdapter(bookList, SeeAllActivity.this, SeeAllActivity.this);
                            recyclerView.setLayoutManager(new GridLayoutManager(SeeAllActivity.this, 2));
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                }
            }
        });
    }


    @Override
    public void onBookClick(Book book) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra(BookDetailActivity.BOOK, book);
        startActivity(intent);
    }

    @Override
    public void onSeeAllClick(int sectionType, int bookType) {

    }
}

