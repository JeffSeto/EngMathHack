package com.example.jeffrey.engmathhack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Add_bill extends Activity {

    EditText contact;
    EditText value;
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        contact = (EditText)findViewById(R.id.name);
        value = (EditText)findViewById(R.id.amount);
        description = (EditText)findViewById(R.id.note);
    }

    public void confirm (View text){
        String name = contact.getText().toString();
        double amount = Double.parseDouble(value.getText().toString());
        String note = description.getText().toString();
        User person = new User(name,amount,note);

        // send to database code here......
        super.onBackPressed();
    }


}
