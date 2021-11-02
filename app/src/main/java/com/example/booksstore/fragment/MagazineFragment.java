package com.example.booksstore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.booksstore.R;

public class MagazineFragment extends Fragment {
    public static final String TAG = MagazineFragment.class.getSimpleName();

    public static MagazineFragment newInstance(){
        return new MagazineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_magazine, container, false);
    }


}
