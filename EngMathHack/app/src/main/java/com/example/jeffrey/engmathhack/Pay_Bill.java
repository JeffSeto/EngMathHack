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
import android.widget.Toast;

import com.stripe.android.*;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;

import java.util.Date;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import io.triangle.reader.PaymentCard;
import io.triangle.reader.ScanActivity;


public class Pay_Bill extends Activity {

    private static final int MY_SCAN_REQUEST_CODE = 2000;
    final public int NFC_SCAN_REQUEST_CODE = 1000;
    public boolean scanning;
    AlertDialog.Builder toast;
    String extra[];

    Button nfcButton;
    Button cameraButton;
    TextView name,amount,note, status;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        extra = getIntent().getExtras().getStringArray("1111");
        setContentView(R.layout.activity_pay__bill);

        nfcButton = (Button) findViewById(R.id.nfcScanButton);
        cameraButton = (Button) findViewById(R.id.cameraButton);
        name = (TextView) findViewById(R.id.nameView);
        amount = (TextView) findViewById(R.id.amountView);
        note = (TextView) findViewById(R.id.notesView);
        status = (TextView) findViewById(R.id.status);

        String amountStr = String.format("$%.2f", Double.parseDouble(extra[2]));

        name.setText(extra[0]);
        note.setText(extra[1]);
        amount.setText(amountStr);

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
                cardScanned(scanResult);

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
        RSA rsa;
        try {
            rsa = MyApplication.getRSA();
            Date expDate = card.getExpiryDate();
            int year = expDate.getYear()%100 + 2000;
            int month = expDate.getMonth();
            Card sCard = new Card(rsa.decrypt(card.getEncryptedAccountNumber()), month, year, "123");
            String stripeKey = "pk_test_tNGrujIVT9iwZIWnheYyETnA";
            Stripe stripe = new Stripe(stripeKey);
            stripe.createToken(sCard, new TokenCallback() {
                @Override
                public void onError(Exception error) {
                    Toast.makeText(Pay_Bill.this,error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                    toast = new AlertDialog.Builder(Pay_Bill.this);
//                    toast.setMessage("Error sending payment to stripe");
//                    toast.show();
                }

                @Override
                public void onSuccess(Token token) {
                    toast = new AlertDialog.Builder(Pay_Bill.this);
                    toast.setMessage("Succesfully sent payment to stripe");
                    toast.show();
                    status.setText("Status settled");
                    DBHandler db = new DBHandler(Pay_Bill.this, null, null, 1);
                    db.settleTransaction(extra[0]);

                }
            });
        } catch (Exception e){
            toast = new AlertDialog.Builder(this);
            toast.setMessage("Card type isn't supported.  Please try another option");
            toast.show();

        }

    }

    private void cardScanned(CreditCard card){
        card.getFormattedCardNumber();
        Card sCard = new Card(card.getFormattedCardNumber(), card.expiryMonth, card.expiryYear%100 + 2000, "123");
        String stripeKey = "pk_test_tNGrujIVT9iwZIWnheYyETnA";
        Stripe stripe = null;
        try {
            stripe = new Stripe(stripeKey);
            stripe.createToken(sCard, new TokenCallback() {
                @Override
                public void onError(Exception error) {
                    Toast.makeText(Pay_Bill.this,error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                    toast = new AlertDialog.Builder(Pay_Bill.this);
//                    toast.setMessage("Error sending payment to stripe");
//                    toast.show();
                }

                @Override
                public void onSuccess(Token token) {
                    toast = new AlertDialog.Builder(Pay_Bill.this);
                    toast.setMessage("Succesfully sent payment to stripe");
                    toast.show();
                    DBHandler db = new DBHandler(Pay_Bill.this, null, null, 1);
                    db.settleTransaction(extra[0]);
                    status.setText("Transaction settled");


                }
            });
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

    }



    public void nfcClicked(View v){
        startScan();
    }

    public void cameraClicked(View v){
        Intent scanIntent = new Intent(this, CardIOActivity.class);

        // require the expiry date of the card and the CVV
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);

    }

    public void onPayClicked(View v){
        super.onBackPressed();
    }

    /* cash payments */
    public void cashClicked(View v){
        DBHandler db = new DBHandler(this, null, null, 1);
        db.settleTransaction(extra[0]);
        status.setText("Transaction settled");
    }
}
