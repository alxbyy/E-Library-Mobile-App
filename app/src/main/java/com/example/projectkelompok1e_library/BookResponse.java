package com.example.projectkelompok1e_library;

import android.util.Log;

import java.util.List;
import java.util.Map;

public class BookResponse {
    private static final String TAG = "BookResponse";
    private Map<String, List<BookItem>> categories;

    public Map<String, List<BookItem>> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, List<BookItem>> categories) {
        this.categories = categories;
        Log.d(TAG, "Categories set: " + categories);
    }
}
