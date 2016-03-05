package com.example.jeffrey.engmathhack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    RecyclerView.Adapter adapter;

    DBHandler dbHandler;

    //FloatingActionButton fab;

    ArrayList<User> contacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(this,null,null,1);

        RecyclerView rv = (RecyclerView) findViewById(R.id.contacts_list);
        rv.setLayoutManager(new LinearLayoutManager(this));

        //contacts = dbHandler.getUsers();
        User test = new User("Jeff", 5, "");
        contacts = new ArrayList();
        contacts.add(test);

        adapter = new BillAdapter(this,contacts);

        rv.setAdapter(adapter);

        /* init database */

        //fab = (FloatingActionButton)findViewById(R.id.fab);
        /**
        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,Add_Bill.class);
                startActivity(intent);
            }
        });
         */




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void add (View text){
        Intent intent = new Intent(MainActivity.this,Add_Bill.class);
        startActivity(intent);
    }
}
