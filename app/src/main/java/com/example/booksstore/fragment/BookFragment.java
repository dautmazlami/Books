package com.example.booksstore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksstore.BookDetailActivity;
import com.example.booksstore.R;
import com.example.booksstore.SeeAllActivity;
import com.example.booksstore.adapter.SectionAdapter;
import com.example.booksstore.common.Constants;
import com.example.booksstore.interfaces.BookClickInterface;
import com.example.booksstore.model.Book;
import com.example.booksstore.model.BookImageLinks;
import com.example.booksstore.model.BookResponseModel;
import com.example.booksstore.model.BookVolumeInfo;
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

public class BookFragment extends Fragment implements BookClickInterface {

    public static final String TAG = BookFragment.class.getSimpleName();
    public static final String TYPE = "TYPE";

    RecyclerView recyclerView;
    SectionAdapter adapter;
    ArrayList<BookResponseModel> sectionList = new ArrayList<>();
    ProgressBar progressBar;
    int bookType;

    Gson gson;

    public static BookFragment newInstance(int type){
        BookFragment bookFragment = new BookFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        bookFragment.setArguments(bundle);
        return bookFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            bookType = getArguments().getInt(TYPE, Constants.BookType.BOOKS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        gson = new Gson();
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.recycler_view_sections);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SectionAdapter(getContext(), sectionList, this, bookType);
        recyclerView.setAdapter(adapter);
        loadBooks(Constants.SectionType.HEALTHY_LIFESTYLE);
        loadBooks(Constants.SectionType.WELLNESS);
        loadBooks(Constants.SectionType.HEALTHY_MEALS);
        return view;
    }

    private void loadBooks(final int section){

        HttpUrl.Builder urlBielder = HttpUrl.parse("https://www.googleapis.com/books/v1/volumes").newBuilder();


        /*String baseUrl = "https://www.googleapis.com/books/v1/volumes?q=";
        String query = "";
        String printType = bookType == Constants.BookType.BOOKS ? "&printType=books" : "&printType=magazines";*/

        switch (section) {
            case Constants.SectionType.HEALTHY_LIFESTYLE:
                urlBielder.addQueryParameter("q",bookType == Constants.BookType.BOOKS ? "intitle:healthy+lifestyle" : "healthy+lifestyle");
                //query = bookType == Constants.BookType.BOOKS ? "intitle:healthy+lifestyle" : "healthy+lifestyle";
                break;
            case Constants.SectionType.WELLNESS:
                urlBielder.addQueryParameter("q", bookType == Constants.BookType.BOOKS ? "intitle:wellness" : "wellness");
                //query = bookType == Constants.BookType.BOOKS ? "intitle:wellness" : "wellness";
                break;
            case Constants.SectionType.HEALTHY_MEALS:
                urlBielder.addQueryParameter("q", bookType == Constants.BookType.BOOKS ? "intitle:healthy+meals" : "healthy+meals");
                //query = bookType == Constants.BookType.BOOKS ? "intitle:healthy+meals" : "healthy+meals";
                break;
        }

        urlBielder.addQueryParameter("printType", bookType == Constants.BookType.BOOKS ? "books" : "magazines");
        String url = urlBielder.build().toString();

        //String url = baseUrl + query + printType;

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
                    sectionList.add(bookResponseModel);
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onBookClick(Book book) {
        Intent intent = new Intent(getActivity(), BookDetailActivity.class);
        intent.putExtra(BookDetailActivity.BOOK, book);
        startActivity(intent);
    }

    @Override
    public void onSeeAllClick(int sectionType, int bookType) {
        Intent intent = new Intent(getActivity(), SeeAllActivity.class);
        intent.putExtra(SeeAllActivity.SECTION_TYPE_EXTRA, sectionType);
        intent.putExtra(SeeAllActivity.BOOK_TYPE_EXTRA, bookType);
        startActivity(intent);
    }
}
