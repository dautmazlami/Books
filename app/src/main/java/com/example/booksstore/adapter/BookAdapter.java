package com.example.booksstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booksstore.R;
import com.example.booksstore.interfaces.BookClickInterface;
import com.example.booksstore.model.Book;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    ArrayList<Book> books;
    LayoutInflater inflater;
    Context context;
    BookClickInterface bookClickInterface;

    public BookAdapter(Context context, ArrayList<Book> books, BookClickInterface bookClickInterface) {
        this.books = books;
        this.bookClickInterface = bookClickInterface;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        String imgUrl = book.getVolumeInfo().getImageLinks().getSmallThumbnail();
        Glide
                .with(context)
                .load(imgUrl)
                .centerCrop()
                .into(holder.bookImg);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImg;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImg = (ImageView) itemView.findViewById(R.id.bookImg);
            bookImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bookClickInterface != null){
                        bookClickInterface.onBookClick(books.get(getAdapterPosition()));
                    }
                }
            });

        }
    }
}
