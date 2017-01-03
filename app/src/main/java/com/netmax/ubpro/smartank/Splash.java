package com.netmax.ubpro.smartank;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class Splash extends AppCompatActivity {


    Thread th1;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    boolean res = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        checkPlayServices();




        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED

                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED

                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED

                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED

                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.RECEIVE_BOOT_COMPLETED) != PackageManager.PERMISSION_GRANTED


                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED


                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED


                    ) {

                String[] perms = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.RECEIVE_BOOT_COMPLETED};
                ActivityCompat.requestPermissions(this, perms, 10);

                return;
            }
            else
            {

                //   Intent in = new Intent(this, Myservice.class);
                //    startService(in);

                Toast.makeText(this,"in", Toast.LENGTH_LONG).show();
                StartAnimations();
                th1 = new Thread(r1);
                th1.start();


            }
        }
        else
        {

        //   Intent in = new Intent(this, Myservice.class);
        //    startService(in);

            Toast.makeText(this,"in", Toast.LENGTH_LONG).show();
            StartAnimations();
            th1 = new Thread(r1);
            th1.start();


        }




    }


    private void StartAnimations()
    {




        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.scale);
        anim2.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim2);

    }



    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }

            return false;
        }

        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        res = false;

        for(int i=0; i < grantResults.length; i++)
        {

            Log.e("firetest", String.valueOf(grantResults[i]));

            if(grantResults[i] != 0)
            {
              break;
            }
            else
            {
                res = true;
            }


        }

        if(res)
        {
           //start Thread
          th1 = new Thread(r1);
            th1.start();

        }
        else
        {
            Toast.makeText(this, "App will not work without permissions", Toast.LENGTH_LONG).show();
        }

    }



    Runnable r1 = new Runnable() {
        @Override
        public void run() {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            h1.sendMessage(h1.obtainMessage());
        }
    };

    Handler h1 = new Handler()
    {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);



            Intent in = new Intent(Splash.this,LoginReg.class);
            startActivity(in);
            finish();

        }
    };

}
