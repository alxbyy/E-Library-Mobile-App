package com.example.projectkelompok1e_library;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    private APIService apiService;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Account");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Initialize views
        ImageView imageUser = findViewById(R.id.image_user);
        TextView textUsername = findViewById(R.id.text_username);
        EditText editUsername = findViewById(R.id.edit_username);
        EditText editEmail = findViewById(R.id.edit_email);
        EditText editPassword = findViewById(R.id.edit_password);
        Button btnSave = findViewById(R.id.btn_simpan);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE);

        // Get current username from SharedPreferences
        String currentUsername = sharedPreferences.getString("username", "");
        textUsername.setText(currentUsername);

        // Initialize API Service
        apiService = RetrofitClient.getClient().create(APIService.class);

        // Implement logic to save changes when btnSave is clicked
        btnSave.setOnClickListener(view -> {
            String newUsername = editUsername.getText().toString().trim();
            String newEmail = editEmail.getText().toString().trim();
            String newPassword = editPassword.getText().toString().trim();

            // Call API to update user info
            updateUserInfo(currentUsername, newUsername, newEmail, newPassword);
        });
    }

    private void updateUserInfo(String oldUsername, String newUsername, String email, String password) {
        Call<ResponseBody> call = apiService.updateUser(oldUsername, newUsername, email, password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", newUsername);
                    editor.apply();
                    Toast.makeText(AccountActivity.this, "User info updated successfully", Toast.LENGTH_SHORT).show();
                    // Update UI or take any other actions upon successful update
                    finish();
                } else {
                    Toast.makeText(AccountActivity.this, "Failed to update user info", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AccountActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
