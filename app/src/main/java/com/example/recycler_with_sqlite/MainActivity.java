package com.example.recycler_with_sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addBtn;

    private EditText phone, name;

    private SearchView searchView;
    private RecyclerView recyclerView;
    private UserAdp adp;
    private List<User> users;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        fetchData();
    }


    private void init() // used for widget initialization
    {
        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler); // initialize recycler
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        users = new ArrayList<>();

        databaseHelper = new DatabaseHelper(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addBtn:
                createDialog();
                break;
        }
    }

    private void createDialog() { // this method creates dialog for add contact
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        Button cancelBtn = dialog.findViewById(R.id.cancelBtn);
        Button okBtn = dialog.findViewById(R.id.okBtn);
        name = dialog.findViewById(R.id.name);
        phone = dialog.findViewById(R.id.phone);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid()) {
                    insert();
                    fetchData();
                    dialog.dismiss();
                }

            }
        });
    }

    private void insert() {
        Long res = databaseHelper.insertData(name.getText().toString().trim(), phone.getText().toString().trim());
        if(res >= 1)
        {
            Toast.makeText(this, "Data Added Successfully" , Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Data not Added Successfully" , Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValid() { // this method valid the data for firebase

        boolean valid = true;

        if (name.getText().length() < 3) {
            name.setError("Enter valid name");
            valid = false;
        }
        if (phone.getText().length() < 11 || phone.getText().length() > 11) {
            phone.setError("Enter valid phone no");
            valid = false;
        }
        return valid;
    }
    private void fetchData()
    {
        users.clear();
        Cursor cursor = databaseHelper.fetchData();
        while (cursor.moveToNext())
        {
            User user = new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
            users.add(user);
        }
        setDataToRecycler(users);
    }

    private void setDataToRecycler(List<User> list) {  // this method sets data on recycler view
        adp = new UserAdp(MainActivity.this, list);
        recyclerView.setAdapter(adp);
    }
}
