package com.netmax.ubpro.smartank;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginReg extends AppCompatActivity implements View.OnClickListener {

    EditText e1, e2;
    Button b1,b2,b3;
    private static final String TAG = "MyAPP";


    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mref;
    String lusername, lpassword;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginreg);

        b1 = (Button)findViewById(R.id.button12);
        b1.setOnClickListener(this);


        b2 = (Button)findViewById(R.id.button13);
        b2.setOnClickListener(this);


        e1 = (EditText)findViewById(R.id.editText13);
        e2 = (EditText)findViewById(R.id.editText8);


        mAuth = FirebaseAuth.getInstance();

        mref = firebaseDatabase.getReference();


        e1.setText("test1@gmail.com");
        e2.setText("123456");





    }





    @Override
    public void onClick(View v) {

        lusername = e1.getText().toString();
        lpassword = e2.getText().toString();
        switch (v.getId())
        {
            case R.id.button13:

                createAccount(lusername,lpassword );

                break;

            case R.id.button12:
                boolean b= haveNetworkConnection();
                if(b==true) {
                    signIn(lusername, lpassword);
                }
                else
                {
                    buildAlertMessageNointernet();
                    signIn(lusername,lpassword);
                }

                break;


        }


    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
    private void buildAlertMessageNointernet() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Internet Connection seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }



    private void createAccount(String email, String password) {



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());


                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginReg.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            FirebaseUser user = task.getResult().getUser();


                            createUserDatabase(user);
                        }

                    }
                });

    }

    private void signIn(String email, String password) {



        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(LoginReg.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            Toast.makeText(LoginReg.this, "Authentication ok.",
                                    Toast.LENGTH_SHORT).show();

                                Intent in = new Intent(LoginReg.this, MainActivity.class);
                                startActivity(in);

                        }

                    }
                });

    }

    private void signOut() {
        mAuth.signOut();
        finish();
    }




    private void updateUI(FirebaseUser user) {

        if (user != null) {

            b3.setVisibility(View.VISIBLE);
        } else {

            b3.setVisibility(View.GONE);
        }
    }


    private void createUserDatabase(FirebaseUser user)
    {

        String uid = user.getUid();
        String email = user.getEmail();
        String uname;

        if (email.contains("@")) {
            uname =   email.split("@")[0];
        } else {
            uname =  email;
        }


        User u = new User(uname, email);

        mref.child("alluser").child(uid).setValue(u);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edt = sp.edit();
        edt.putString("lusername", lusername);
        edt.putString("lpassword", lpassword);
        edt.putString("uid", uid);

        edt.commit();

    }






}
