package com.example.jeffrey.engmathhack;

import android.net.SSLSessionCache;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import io.triangle.Session;
import io.triangle.TriangleException;

/**
 * Created by Jeffrey on 2016-03-04.
 */
public class MyApplication extends android.app.Application {

    final String tApplicationId = "IY1EzrlG2HP9KzP";
    final String tAccessKey = "SBG1XkubQT";
    final String tSecretKey = "o3f538fEYQyNpdE0iAILJsc3fBRr03rnFaA2nV70L8ZOl5ctmW2Us4aVRcQrhNEU";
    public static RSA rsa = null;

    public MyApplication(){
        super();
    }

    public void onCreate() {
        super.onCreate();

        final Session triangleSession = Session.getInstance();
        if(!triangleSession.isInitialized()){
            System.out.println("Initializing triangle session");
            AsyncTask<Void, Void, Void> triangleInitializationTask = new AsyncTask<Void, Void, Void>(){
                Exception test;
                @Override
                protected Void doInBackground(Void... voids)
                {
                    System.out.println("Attempting to initialize");
                    try
                    {
                        triangleSession.initialize(
                                tApplicationId, // Application ID
                                tAccessKey,      // Access Key
                                tSecretKey, // Secret Key
                                MyApplication.this);
                    }
                    catch (Exception e)
                    {
                        System.out.println("Triangle initialization failed");
                    }
                    return null;


                }
            };
            triangleInitializationTask.execute();
        }

        try {
            rsa = new RSA();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }

    public static RSA getRSA(){
        return rsa;
    }


}
