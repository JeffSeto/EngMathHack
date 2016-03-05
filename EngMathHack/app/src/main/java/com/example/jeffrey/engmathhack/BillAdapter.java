package com.example.jeffrey.engmathhack;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kitty on 2016-03-05.
 */
public class BillAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<User> contacts;
    DBHandler db;
    Context context;


    public static class ContactViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView desc;
        TextView amount;
        View item;

        ContactViewHolder(View item){
            super(item);
            this.item = item;
            name = (TextView) itemView.findViewById(R.id.name_main);
            desc = (TextView) itemView.findViewById(R.id.desc_main);
            amount = (TextView) itemView.findViewById(R.id.amount_main);

        }

    }

    BillAdapter(Context context, ArrayList<User> contacts){
        this.context = context;
        db= new DBHandler(context,null,null,1);
        this.contacts = contacts;
    }

    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards,parent,false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        contacts = db.getUsers();
        ContactViewHolder v = (ContactViewHolder)holder;
        final String name_str = contacts.get(i).getName();
        final String desc_str = contacts.get(i).getNote();
        final double amount = contacts.get(i).getAmount();

        v.name.setText(name_str);
        v.desc.setText(desc_str);
        v.amount.setText("$" + amount);

        //final Context context = v.item.getContext();
        v.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Pay_Bill.class);
                String[] extra = new String[3];

                extra[0] = name_str;
                extra[1] = desc_str;
                extra[2] = "" + amount;
                intent.putExtra("1111", extra);
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void addUser(){
        notifyDataSetChanged();
    }



}

