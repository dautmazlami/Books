package com.example.booksstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booksstore.R;
import com.example.booksstore.interfaces.BookClickInterface;
import com.example.booksstore.model.Book;

import java.util.ArrayList;

public class SeeAllAdapter extends RecyclerView.Adapter<SeeAllAdapter.SeeAllViewHolder> {

    ArrayList<Book> bookList;
    LayoutInflater inflater;
    Context context;
    BookClickInterface bookClickInterface;

    public SeeAllAdapter(ArrayList<Book> bookList, Context context, BookClickInterface bookClickInterface) {
        this.bookList = bookList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.bookClickInterface = bookClickInterface;
    }

    @NonNull
    @Override
    public SeeAllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_item_seeall, parent, false);
        return new SeeAllViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeeAllViewHolder holder, int position) {
        final Book book = bookList.get(position);
        if (book.getVolumeInfo() != null){
            if (book.getVolumeInfo().getImageLinks() != null){
            String imgUrl = book.getVolumeInfo().getImageLinks().getSmallThumbnail();
            Glide
                    .with(context)
                    .load(imgUrl)
                    .placeholder(R.drawable.nothumbnail)
                    .centerCrop()
                    .into(holder.bookImage);
            }
            holder.author.setText(book.getVolumeInfo().getAuthors() != null ? book.getVolumeInfo().getAuthors().toString() : "");
            holder.author.setVisibility(book.getVolumeInfo().getAuthors() != null ? View.VISIBLE : View.INVISIBLE );
            holder.rating.setText(book.getVolumeInfo().getAverageRating() + "");
            holder.title.setText(book.getVolumeInfo().getTitle());
            holder.bookImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bookClickInterface != null){
                        bookClickInterface.onBookClick(book);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class SeeAllViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImage;
        TextView author, title, rating;

        public SeeAllViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = (ImageView) itemView.findViewById(R.id.bookImageSeeAll);
            author = (TextView) itemView.findViewById(R.id.author_seeAll);
            title = (TextView) itemView.findViewById(R.id.title_seeAll);
            rating = (TextView) itemView.findViewById(R.id.rating_seeAll);

        }
    }

}
