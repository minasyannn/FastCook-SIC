package com.samsung.fastcook;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
public class SearchActivity extends AppCompatActivity {

        FloatingActionButton fab;
        DatabaseReference databaseReference;
        ValueEventListener eventListener;
        RecyclerView recyclerView;
        List<DataClass> dataList;
        MyAdapter adapter;
        SearchView searchView;

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);
            recyclerView = findViewById(R.id.recyclerView);
            fab = findViewById(R.id.fab);
            searchView = findViewById(R.id.search);
            searchView.clearFocus();
            String query = getIntent().getStringExtra("query");
            GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchActivity.this, 1);
            recyclerView.setLayoutManager(gridLayoutManager);
            AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
            builder.setCancelable(false);
            builder.setView(R.layout.progress_layout);
            AlertDialog dialog = builder.create();
            dialog.show();
            dataList = new ArrayList<>();
            adapter = new MyAdapter(SearchActivity.this, dataList);
            recyclerView.setAdapter(adapter);
            databaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorials");
            dialog.show();
            eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dataList.clear();
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                        dataClass.setKey(itemSnapshot.getKey());
                        dataList.add(dataClass);
                    }
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    dialog.dismiss();
                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    searchList(newText);
                    return true;
                }
            });
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SearchActivity.this, UploadActivity.class);
                    startActivity(intent);
                }
            });
        }

        public void searchList(String text) {
            ArrayList<DataClass> searchList = new ArrayList<>();
            for (DataClass dataClass : dataList) {
                if (dataClass.getDataTitle().toLowerCase().contains(text.toLowerCase())) {
                    searchList.add(dataClass);
                }
            }
            adapter.searchDataList(searchList);
        }
    }

