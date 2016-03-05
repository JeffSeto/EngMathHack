package com.example.jeffrey.engmathhack;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import io.triangle.reader.PaymentCard;
import io.triangle.reader.ScanActivity;


public class Pay_Bill extends Activity {

    final public int SCAN_REQUEST_CODE = 1000;
    public boolean scanning;
    AlertDialog.Builder toast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay__bill);
        scanning = true;
        startScan();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private void startScan(){
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null || !nfcAdapter.isEnabled()){
            toast = new AlertDialog.Builder(this);
            toast.setMessage("NFC was not initialized.  Initialize NFC?");
            toast.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Send the user to the settings page and hope they turn it on
                    if (android.os.Build.VERSION.SDK_INT >= 16)
                    {
                        startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS));
                    }
                    else
                    {
                        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                    }
                }
            });
            toast.setNegativeButton("No", null);
        }
        if(!scanning && nfcAdapter.isEnabled()){
            Intent scanIntent = new Intent(this, io.triangle.reader.ScanActivity.class);
            scanIntent.putExtra(ScanActivity.INTENT_EXTRA_RETRY_ON_ERROR,true);
            this.startActivityForResult(scanIntent, SCAN_REQUEST_CODE);
        }
    }
}
