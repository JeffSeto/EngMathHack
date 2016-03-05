package com.example.jeffrey.engmathhack;

import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jeffrey on 2016-03-05.
 */
public class PaymentCardAdapter extends RecyclerView.Adapter<PaymentCardAdapter.PaymentViewHolder>{

    ArrayList<String[]> list;

    public static class PaymentViewHolder extends RecyclerView.ViewHolder{
        TextView paymentTypeTextView;
        EditText paymentAmountEditText;
        ImageView imageView;
        View paymentView;

        public PaymentViewHolder(View v) {
            super(v);
            paymentAmountEditText = (EditText) v.findViewById(R.id.paymentAmountEditText);
            paymentTypeTextView = (TextView) v.findViewById(R.id.paymentTypeTextView);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            paymentView = v;
        }
    }

    public PaymentCardAdapter(ArrayList<String[]> list){
        this.list = list;
    }

    @Override
    public PaymentCardAdapter.PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_card, parent, false);
        //set the view's size, margins, paddings and layout parameters
        PaymentViewHolder vh = new PaymentViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PaymentCardAdapter.PaymentViewHolder holder, int i) {
        final int index = i;
        holder.paymentAmountEditText.setText(list.get(i)[0]);
        holder.paymentAmountEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                list.get(index)[0] = v.getText().toString();
                return false;
            }
        });
        holder.paymentTypeTextView.setText(list.get(i)[1]);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
