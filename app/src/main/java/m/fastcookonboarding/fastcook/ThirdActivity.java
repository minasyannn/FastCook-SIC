package m.fastcookonboarding.fastcook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.samsung.fastcook.R;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button goToMainActivityButton = findViewById(R.id.go_to_home_activity_button);
        goToMainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdActivity.this, HomePage.class);
                startActivity(intent);
            }
        });


    }
}
