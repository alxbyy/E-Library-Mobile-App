package com.example.projectkelompok1e_library;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private MyBook myBook;
    private BookAdapter adapter;
    private RecyclerView rv_book;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;
    private TextView textUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer Layout
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Username");

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.nav_about) {
                    Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START); // Tutup drawer setelah memilih menu
                    return true;
                } else if (id == R.id.nav_home) {
                    // Navigasi ke halaman Beranda
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START); // Tutup drawer setelah memilih menu
                    return true;
                } else if (id == R.id.nav_book_list) {
                    // Navigasi ke halaman List Buku
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                } else if (id == R.id.nav_account) {
                    // Navigasi ke halaman Account
                    Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                } else if (id == R.id.nav_logout) {
                    // Tambahkan logika untuk logout
                    logout();
                    return true;
                }
                return false;
            }
        });

        View headerView = navigationView.getHeaderView(0);
        textUsername = headerView.findViewById(R.id.textUsername);
        textUsername.setText(username);

        rv_book = findViewById(R.id.rv_book);
        myBook = new MyBook(this);

        rv_book.setHasFixedSize(true);
        rv_book.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BookAdapter(new ArrayList<>(), this);
        rv_book.setAdapter(adapter);

        getBooks();
    }

    private void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.apply();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Tutup activity saat logout
    }

    public void getBooks() {
        myBook.getInstance().getBooks().enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                BookResponse resp = response.body();
                if (resp != null && resp.getCategories() != null) {
                    List<BookItem> allBooks = new ArrayList<>();
                    for (List<BookItem> books : resp.getCategories().values()) {
                        allBooks.addAll(books);
                    }
                    adapter = new BookAdapter(allBooks, MainActivity.this);
                    rv_book.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
