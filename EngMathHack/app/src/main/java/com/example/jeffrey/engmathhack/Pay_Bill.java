package com.example.jeffrey.engmathhack;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import io.triangle.reader.PaymentCard;
import io.triangle.reader.ScanActivity;


public class Pay_Bill extends Activity {

    private static final int MY_SCAN_REQUEST_CODE = 2000;
    final public int NFC_SCAN_REQUEST_CODE = 1000;
    public boolean scanning;
    AlertDialog.Builder toast;

    Button nfcButton;
    Button cameraButton;
    TextView name,amount,note;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String extra[] = getIntent().getExtras().getStringArray("1111");
        setContentView(R.layout.activity_pay__bill);

        nfcButton = (Button) findViewById(R.id.nfcScanButton);
        cameraButton = (Button) findViewById(R.id.cameraButton);
        name = (TextView) findViewById(R.id.nameView);
        amount = (TextView) findViewById(R.id.amountView);
        note = (TextView) findViewById(R.id.notesView);

        name.setText(extra[0]);
        note.setText(extra[1]);
        amount.setText("$" + extra[2]);

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
            this.startActivityForResult(scanIntent, NFC_SCAN_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == NFC_SCAN_REQUEST_CODE){

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

        if (requestCode == MY_SCAN_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";

                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );

                if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }

                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }

                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }
            }
            else {
                resultDisplayStr = "Scan was canceled.";
            }
            // do something with resultDisplayStr, maybe display it in a textView
            // resultTextView.setText(resultDisplayStr);
        }
        // else handle other activity results
    }

    private void cardScanned(PaymentCard card){

    }

    public void nfcClicked(View v){
        startScan();
    }

    public void cameraClicked(View v){
        Intent scanIntent = new Intent(this, CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);

    }

    public void onPayClicked(View v){
        super.onBackPressed();
    }


}
