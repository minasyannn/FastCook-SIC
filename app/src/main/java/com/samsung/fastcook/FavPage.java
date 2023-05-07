package com.samsung.fastcook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FavPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button mLogoutButton;
    private TextView mUserDetailsTextView;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_page);

        mAuth = FirebaseAuth.getInstance();
        mLogoutButton = findViewById(R.id.logout);
        mUserDetailsTextView = findViewById(R.id.user_details);

        mCurrentUser = mAuth.getCurrentUser();
        if (mCurrentUser == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            mUserDetailsTextView.setText(mCurrentUser.getEmail());
        }

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

  BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_item1:
                        Intent intent1 = new Intent(FavPage.this, Activity2.class);
                        startActivity(intent1);
                        return true;
                    case R.id.navigation_item2:
                        Intent intent2 = new Intent(FavPage.this, FavPage.class);
                        startActivity(intent2);
                        return true;
                    case R.id.navigation_item3:
                        Intent intent3 = new Intent(FavPage.this, UserActivity.class);
                        startActivity(intent3);
                        return true;
                }
                return false;
            }
        });
    }
}
