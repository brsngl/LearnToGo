package com.example.cemo1.menuwithwelcomescreen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class KarteActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout schublade;

    DatabaseHelper db;

    Button add_data;
    EditText add_name;

    ListView userlist;

    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karte);
        Toolbar toolbar = findViewById(R.id. toolbar); //üc cizgili Menuyu gosterir
        schublade = findViewById(R.id.karte);
        NavigationView navigationView = findViewById(R.id. nav_view );
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, schublade,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        schublade.addDrawerListener(toggle);
        toggle.syncState();

        db = new DatabaseHelper(this);

        listItem = new ArrayList<>();

        add_data = findViewById(R.id.add_data);
        add_name = findViewById(R.id.add_name);
        userlist = findViewById(R.id.users_list);

        viewData();

        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = userlist.getItemAtPosition(i).toString();
                Toast.makeText(KarteActivity.this, ""+text, Toast.LENGTH_SHORT).show();
            }
        });

        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = add_name.getText().toString();
                if (!name.equals("") && db.insertData(name)){
                    Toast.makeText(KarteActivity.this, "Data added", Toast.LENGTH_SHORT).show();
                    add_name.setText("");
                    listItem.clear(); //nach eingabei säubern
                    viewData();
                } else {
                    Toast.makeText(KarteActivity.this, "Data not added", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R. id. nav_mathe:
                Intent mathe = new Intent(this,MatheActivity.class);
                startActivity(mathe);
                //getSupportFragmentManager().beginTransaction().replace(R. id. fragment_container, new MatheFragment()).commit();
                break;
            case R. id. nav_kartei:
                Intent karte = new Intent(this,KarteActivity.class);
                startActivity(karte);
                //getSupportFragmentManager().beginTransaction().replace(R. id. fragment_container, new KarteiFragment()).commit();
                break;
            case R. id. nav_setting:
                Intent m2 = new Intent(this,SettingActivity.class);
                startActivity(m2);
                //getSupportFragmentManager().beginTransaction().replace(R. id. fragment_container, new SettingFragment()).commit();
                break;
            case R. id. nav_statistik:
                Intent stati = new Intent(this, StatistikActivity.class);
                startActivity(stati);
               // getSupportFragmentManager().beginTransaction().replace(R. id. fragment_container, new StatistikFragment()).commit();
                break;
        }
        schublade.closeDrawer(GravityCompat.START);
        return true;
    }

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karte);

        db = new DatabaseHelper(this);

        listItem = new ArrayList<>();

        add_data = findViewById(R.id.add_data);
        add_name = findViewById(R.id.add_name);
        userlist = findViewById(R.id.users_list);

        viewData();

        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = userlist.getItemAtPosition(i).toString();
                Toast.makeText(KarteActivity.this, ""+text, Toast.LENGTH_SHORT).show();
            }
        });

        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = add_name.getText().toString();
                if (!name.equals("") && db.insertData(name)){
                    Toast.makeText(KarteActivity.this, "Data added", Toast.LENGTH_SHORT).show();
                    add_name.setText("");
                    listItem.clear(); //nach eingabei säubern
                    viewData();
                } else {
                    Toast.makeText(KarteActivity.this, "Data not added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/

    private void viewData() {
        Cursor cursor = db.viewData();

        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data to show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                listItem.add(cursor.getString(1)); //index 1 is name , index 0 is id
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
            userlist.setAdapter(adapter);
        }
    }
}
