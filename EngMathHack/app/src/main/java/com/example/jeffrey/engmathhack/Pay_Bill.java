package com.example.jeffrey.engmathhack;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import io.triangle.reader.PaymentCard;
import io.triangle.reader.ScanActivity;


public class Pay_Bill extends Activity {

    final public int SCAN_REQUEST_CODE = 1000;
    public boolean scanning;
    AlertDialog.Builder toast;

    Button nfcButton;
    Button cameraButton;
    TextView name,amount,note;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_pay__bill);

        nfcButton = (Button) findViewById(R.id.nfcScanButton);
        cameraButton = (Button) findViewById(R.id.cameraButton);
        name = (TextView) findViewById(R.id.nameTextView);
        amount = (TextView) findViewById(R.id.amountTextView);
        note = (TextView) findViewById(R.id.notesTextView);

    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private void startScan(){
        System.out.println("Start scan method");
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null || !nfcAdapter.isEnabled()){
            System.out.println("Prompt user");
            toast = new AlertDialog.Builder(this);
            toast.setMessage("NFC was not initialized.  Initialize NFC?");
            toast.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Send the user to the settings page and hope they turn it on
                    if (android.os.Build.VERSION.SDK_INT >= 16) {
                        startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS));
                    } else {
                        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                    }
                }
            });
            toast.setNegativeButton("No", null);
            toast.show();
        }
        if(nfcAdapter.isEnabled()){
            Intent scanIntent = new Intent(this, io.triangle.reader.ScanActivity.class);
            scanIntent.putExtra(ScanActivity.INTENT_EXTRA_RETRY_ON_ERROR,true);
            this.startActivityForResult(scanIntent, SCAN_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == SCAN_REQUEST_CODE){

            if(resultCode == RESULT_OK){
                PaymentCard card = data.getParcelableExtra(ScanActivity.INTENT_EXTRA_PAYMENT_CARD);
                this.cardScanned(card);
            } else {
                toast = new AlertDialog.Builder(this);
                toast.setMessage("NFC Scanning cancelled");
                toast.show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void cardScanned(PaymentCard card){

    }

    public void nfcClicked(View v){
        startScan();
    }

    public void cameraClicked(View v){

    }

    public void onPayClicked(View v){
        super.onBackPressed();
    }
}
