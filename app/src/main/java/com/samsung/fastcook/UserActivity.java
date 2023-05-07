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
import com.google.firebase.ktx.Firebase;

public class UserActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
FirebaseAuth auth;
    TextView textView;
    Button button;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        textView = findViewById(R.id.user_details);
        user = auth.getCurrentUser();
                if(user == null){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    textView.setText(user.getEmail());

                }

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_item1:
                        Intent intent1 = new Intent(UserActivity.this, Activity2.class);
                        startActivity(intent1);
                        return true;
                    case R.id.navigation_item2:
                        Intent intent2 = new Intent(UserActivity.this, FavPage.class);
                        startActivity(intent2);
                        return true;
                    case R.id.navigation_item3:
                        Intent intent3 = new Intent(UserActivity.this, UserActivity.class);
                        startActivity(intent3);
                        return true;
                    case R.id.navigation_item4:
                        Intent intent4 = new Intent(UserActivity.this, UploadActivity.class);
                        startActivity(intent4);
                        return true;
                }
                return false;
            }
        });
        button = (Button) findViewById(R.id.back_to_HomePage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }

    public void openActivity2() {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }
}



