package com.example.jeffrey.engmathhack;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Add_Bill extends Activity {

    BillAdapter adapter;
    EditText contact;
    EditText value;
    EditText description;
    private static final String tag = Add_Bill.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(tag, "add_bill created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        contact = (EditText)findViewById(R.id.name);
        value = (EditText)findViewById(R.id.amount);
        description = (EditText)findViewById(R.id.note);

//        View text = new View(this);
//        confirm(text);
    }

    public void confirm (View text){
        Log.d(tag, "Confirm created");
        String name = contact.getText().toString();
        double amount = Double.parseDouble(value.getText().toString());
        String note = description.getText().toString();
        User person = new User(name,amount,note);


        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        dbHandler.createTransaction(person);

        // send to database code here......
        super.onBackPressed();
    }

}