package com.netmax.ubpro.smartank;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GeoReguser extends AppCompatActivity implements View.OnClickListener {

    EditText e1, e2;
    Button b1,b2,b3;
    private static final String TAG = "MyAPP";


    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_reguser);

        b1 = (Button)findViewById(R.id.btn);
        b1.setOnClickListener(this);


        b2 = (Button)findViewById(R.id.button2);
        b2.setOnClickListener(this);

        b3 = (Button)findViewById(R.id.button3);
        b3.setOnClickListener(this);

        e1 = (EditText)findViewById(R.id.editText4);
        e2 = (EditText)findViewById(R.id.editText5);


        e1.setText("abc@gmail.com");
        e2.setText("123456");


        Integer resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode == ConnectionResult.SUCCESS) {
            //Do what you want
        } else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
            if (dialog != null) {
                //This dialog will help the user update to the latest GooglePlayServices
                dialog.show();
            }
        }


        mAuth = FirebaseAuth.getInstance();
        //  mAuth.addAuthStateListener(myauthlis);

        mref = firebaseDatabase.getReference();



    }


    FirebaseAuth.AuthStateListener myauthlis = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            FirebaseUser user = firebaseAuth.getCurrentUser();

            if(user == null)
            {
                // t1.setText("Please SignIn");
            }
            else
            {
                // t1.setText("Signed in:"+user.getEmail());





            }


        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.button2:
                createAccount(e1.getText().toString(), e2.getText().toString());

                break;

            case R.id.btn:
                signIn(e1.getText().toString(), e2.getText().toString());


                break;
        }


    }

    private void createAccount(String email, String password) {



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());


                        if (!task.isSuccessful()) {
                            Toast.makeText(GeoReguser.this, "Authentication failed.",
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
                            Toast.makeText(GeoReguser.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            Intent in = new Intent(GeoReguser.this, MainActivity.class);
                            startActivity(in);

                        }

                    }
                });

    }

    private void signOut() {
        mAuth.signOut();

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

       // mref.child("user2").child(uid).child("username").setValue(uname);
       // mref.child("user2").child(uid).child("email").setValue(email);


        Toast.makeText(this, "User Cretaed", Toast.LENGTH_SHORT).show();

    }



}
