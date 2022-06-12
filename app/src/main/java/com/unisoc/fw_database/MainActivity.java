package com.unisoc.fw_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyDataBaseHelper dbHelper = new MyDataBaseHelper(MainActivity.this);
        findViewById(R.id.search_close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addBook("Old Man1", "jiang1");
                dbHelper.addBook("Old Man2", "jiang2");
                dbHelper.addBook("Old Man3", "jiang3");
            }
        });

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addBook("Old Man2", "jiang2");
            }
        });

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dbHelper.deleteBook();
                dbHelper.addBook("Old Man3", "jiang3");
            }
        });

    }
}