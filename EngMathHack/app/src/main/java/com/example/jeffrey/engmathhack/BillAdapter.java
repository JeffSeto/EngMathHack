package com.example.jeffrey.engmathhack;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kitty on 2016-03-05.
 */
public class BillAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> contacts;


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

    BillAdapter(List<User> contacts){
        this.contacts = contacts;
    }

    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards,parent,false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        ContactViewHolder v = (ContactViewHolder)holder;
        String name_str = contacts.get(i).getName();
        String desc_str = contacts.get(i).getNote();
        double amount = contacts.get(i).getAmount();

        v.name.setText(name_str);
        v.desc.setText(desc_str);
        v.amount.setText(""+amount);





    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }


}

