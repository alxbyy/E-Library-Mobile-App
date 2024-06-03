package com.example.projectkelompok1e_library;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private EditText searchBar;
    private ImageView userIcon;
    private Button btnUmum, btnKedokteran, btnSejarah, btnBisnis, btnKomputer, btnHukum;
    private LinearLayout rekomendasiBookContainer, populerBookContainer;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;
    private TextView textUsername;
    private BookAPI bookAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize Drawer Layout
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
                    Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                } else if (id == R.id.nav_home) {
                    // Navigate to Home
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                } else if (id == R.id.nav_book_list) {
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                } else if (id == R.id.nav_account) {
                    Intent intent = new Intent(HomeActivity.this, AccountActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                } else if (id == R.id.nav_logout) {
                    logout();
                    return true;
                }
                return false;
            }
        });



        View headerView = navigationView.getHeaderView(0);
        textUsername = headerView.findViewById(R.id.textUsername);
        textUsername.setText(username);

        searchBar = findViewById(R.id.search_bar);
        userIcon = findViewById(R.id.user_icon);
        btnUmum = findViewById(R.id.btn_umum);
        btnKedokteran = findViewById(R.id.btn_kedokteran);
        btnSejarah = findViewById(R.id.btn_sejarah);
        btnBisnis = findViewById(R.id.btn_bisnis);
        btnKomputer = findViewById(R.id.btn_komputer);
        btnHukum = findViewById(R.id.btn_hukum);
        rekomendasiBookContainer = findViewById(R.id.rekomendasi_book_container);
        populerBookContainer = findViewById(R.id.populer_book_container);

        bookAPI = RetrofitBuilder.builder(this).create(BookAPI.class);

        btnUmum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainActivity("Umum");
            }
        });

        btnKedokteran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainActivity("Kedokteran");
            }
        });

        btnSejarah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainActivity("Sejarah");
            }
        });

        btnBisnis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainActivity("Bisnis");
            }
        });

        btnKomputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainActivity("Komputer");
            }
        });

        btnHukum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainActivity("Hukum");
            }
        });

        fetchBooks();
    }

    private void navigateToMainActivity(String category) {
        // Navigate to MainActivity and pass the selected category
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    private void fetchBooks() {
        Call<BookResponse> call = bookAPI.getBooks();
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Books fetched successfully");
                    Map<String, List<BookItem>> categories = response.body().getCategories();
                    setRandomBook(rekomendasiBookContainer, categories);
                    setRandomBook(populerBookContainer, categories);
                } else {
                    Log.e(TAG, "Failed to fetch books: Response not successful or body is null");
                    Toast.makeText(HomeActivity.this, "Failed to fetch books", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Log.e(TAG, "Error fetching books: " + t.getMessage());
                Toast.makeText(HomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRandomBook(LinearLayout bookContainer, Map<String, List<BookItem>> categories) {
        if (categories == null || categories.isEmpty()) {
            Log.e(TAG, "No categories available");
            return;
        }

        Random random = new Random();
        Object[] categoryKeys = categories.keySet().toArray();
        String randomCategory = (String) categoryKeys[random.nextInt(categoryKeys.length)];
        List<BookItem> books = categories.get(randomCategory);

        if (books == null || books.isEmpty()) {
            Log.e(TAG, "No books available in category: " + randomCategory);
            return;
        }

        BookItem randomBook = books.get(random.nextInt(books.size()));
        View bookView = getLayoutInflater().inflate(R.layout.item_beranda, bookContainer, false);

        ImageView posterView = bookView.findViewById(R.id.posterView);
        TextView titleView = bookView.findViewById(R.id.tv_title);
        TextView authorView = bookView.findViewById(R.id.tv_author);
        TextView descriptionView = bookView.findViewById(R.id.tv_description);
        Button bacaButton = bookView.findViewById(R.id.btn_baca);

        // Set data
        Glide.with(this).load(randomBook.getImage_url()).into(posterView);
        titleView.setText(randomBook.getTitle());
        authorView.setText(randomBook.getAuthor());
        descriptionView.setText(randomBook.getDescription());

        bacaButton.setOnClickListener(v -> {
            // Implement action to read the book
            Toast.makeText(HomeActivity.this, "Read " + randomBook.getTitle(), Toast.LENGTH_SHORT).show();
        });

        bookContainer.addView(bookView);
    }

    private void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.apply();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
