package com.example.jeffrey.engmathhack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Add_bill extends Activity {
    EditText contactName;
    EditText description;
    EditText value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);



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
}
