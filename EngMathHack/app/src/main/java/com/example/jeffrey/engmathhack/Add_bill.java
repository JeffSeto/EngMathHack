package com.example.jeffrey.engmathhack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Add_bill extends Activity {
<<<<<<< HEAD
    EditText contactName;
    EditText description;
    EditText value;
=======

    EditText contact;
    EditText value;
    EditText description;

>>>>>>> kitty_branch

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

<<<<<<< HEAD


        contactName = (EditText) findViewById(R.id.name);
        description = (EditText) findViewById(R.id.description);
        value = (EditText) findViewById(R.id.value);
    }


    public void confirm(View text){
        String name = contactName.getText().toString();
        String note = description.getText().toString();
        int money_value = Integer.parseInt(value.getText().toString());
        //User person = new User(name,money_value,note);
        super.onBackPressed();

    }
=======
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


>>>>>>> kitty_branch
}
