package com.example.projectkelompok1e_library;

import android.content.Context;

public class MyBook {
    BookAPI bookApi;

    public MyBook(Context context) {
        bookApi = RetrofitBuilder
                .builder(context)
                .create(BookAPI.class);
    }

    public BookAPI getInstance() {
        return bookApi;
    }
}