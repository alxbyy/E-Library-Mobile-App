package com.example.projectkelompok1e_library;


import retrofit2.Call;
import retrofit2.http.GET;

public interface BookAPI {
    @GET("books.json")
    Call<BookResponse> getBooks();
}
