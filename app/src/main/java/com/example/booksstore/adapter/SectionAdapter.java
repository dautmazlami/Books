package com.example.booksstore.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksstore.R;
import com.example.booksstore.common.Constants;
import com.example.booksstore.fragment.BookFragment;
import com.example.booksstore.interfaces.BookClickInterface;
import com.example.booksstore.model.BookResponseModel;

import java.util.ArrayList;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder>{

    ArrayList<BookResponseModel> bookList;
    LayoutInflater inflater;
    Context context;
    BookClickInterface bookClickInterface;
    int bookType;

    public SectionAdapter(Context context, ArrayList<BookResponseModel>  bookList, BookClickInterface clickInterface, int bookType) {
        this.context = context;
        this.bookClickInterface = clickInterface;
        this.bookList = bookList;
        this.inflater = LayoutInflater.from(context);
        this.bookType = bookType;
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_item_section, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {
        final BookResponseModel bookResponse = bookList.get(position);
        BookAdapter bookAdapter = new BookAdapter(context, bookResponse.getItems(), bookClickInterface);
        if (holder.recyclerView == null){
            Log.d("REC ERROR", "error");
        }
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(bookAdapter);

        holder.seeAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookClickInterface != null){
                    bookClickInterface.onSeeAllClick(bookResponse.getSectionType(), bookType);
                }
            }
        });

        switch (bookResponse.getSectionType()){
            case Constants.SectionType.HEALTHY_LIFESTYLE:
                holder.sectionTitle.setText(context.getResources().getString(R.string.healthy_lifestyle));
                break;
            case Constants.SectionType.WELLNESS:
                holder.sectionTitle.setText(context.getResources().getString(R.string.wellness));
                break;
            case Constants.SectionType.HEALTHY_MEALS:
                holder.sectionTitle.setText(context.getResources().getString(R.string.healtjy_meals));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {
        TextView sectionTitle, seeAllButton;
        RecyclerView recyclerView;

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            sectionTitle = (TextView) itemView.findViewById(R.id.section_title);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view_items);
            seeAllButton = (TextView) itemView.findViewById(R.id.section_seeall_button);

        }
    }
}
