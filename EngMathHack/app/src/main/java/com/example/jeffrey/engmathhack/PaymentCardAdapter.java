package com.example.jeffrey.engmathhack;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.View;

/**
 * Created by Jeffrey on 2016-03-05.
 */
public class PaymentCardAdapter extends RecyclerView.Adapter<PaymentCardAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public PaymentCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PaymentCardAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
