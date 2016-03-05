package com.example.jeffrey.engmathhack;

import android.net.SSLSessionCache;
import android.os.AsyncTask;

import io.triangle.Session;
import io.triangle.TriangleException;

/**
 * Created by Jeffrey on 2016-03-04.
 */
public class MyApplication extends android.app.Application {

    final String tApplicationId = "aU6FxRS5BGKvheK";
    final String tAccessKey = "SBG1XkubQT";
    final String tSecretKey = "o3f538fEYQyNpdE0iAILJsc3fBRr03rnFaA2nV70L8ZOl5ctmW2Us4aVRcQrhNEU";

    public MyApplication(){
        super();
    }

    public void onCreate() {
        super.onCreate();

        final Session triangleSession = Session.getInstance();

        if(!triangleSession.isInitialized()){
            AsyncTask<Void, Void, Void> triangleInitializationTask = new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... voids)
                {
                    try
                    {
                        triangleSession.initialize(
                                tApplicationId, // Application ID
                                tAccessKey,      // Access Key
                                tSecretKey, // Secret Key
                                MyApplication.this);
                        System.out.println("Yay it worked");
                    }
                    catch (Exception e)
                    {
                        System.out.println("TRIANGLE DISASTER");
                    }
                    return null;
                }
            };
        }
    }
}
