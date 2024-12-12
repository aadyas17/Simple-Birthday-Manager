package com.example.simplebirthdaymanager;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter; //importing
import android.widget.Button; //importing
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etDate;
    private Button btnAdd;
    private ListView lvBirthdays;
    private DBHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> birthdayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etDate = findViewById(R.id.etDate);
        btnAdd = findViewById(R.id.btnAdd);
        lvBirthdays = findViewById(R.id.lvBirthdays);

        dbHelper = new DBHelper(this);
        birthdayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, birthdayList);
        lvBirthdays.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String date = etDate.getText().toString();
            if (!name.isEmpty() && !date.isEmpty()) {
                Birthday birthday = new Birthday(name, date);
                dbHelper.addBirthday(birthday);
                loadBirthdays();
            }
        });

        loadBirthdays();
    }

    private void loadBirthdays() {
        Cursor cursor = dbHelper.getAllBirthdays();
        birthdayList.clear();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DBHelper.COL_NAME));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DBHelper.COL_DATE));
                birthdayList.add(name + " - " + date);
            } while (cursor.moveToNext());
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }
}
