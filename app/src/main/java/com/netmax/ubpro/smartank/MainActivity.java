package com.netmax.ubpro.smartank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.OneoffTask;
import com.google.android.gms.gcm.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by preeti on 14/7/16.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button b1,b2;


    TextView txt_internet,txt_device;
    EditText et1,et2;

    boolean status;

    mytask m1;


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu3,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_settings:
                Intent in = new Intent(this,Settings.class);
                startActivity(in);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_act);


        txt_internet = (TextView)findViewById(R.id.txt_network);
        txt_device = (TextView)findViewById(R.id.txt_device);
        txt_device.setVisibility(View.GONE);


        b1=(Button)findViewById(R.id.btn_command);
        b1.setOnClickListener(this);

        b2=(Button)findViewById(R.id.btn_manual);
        b2.setOnClickListener(this);

        et1 = (EditText)findViewById(R.id.edt_litre);
        et2 = (EditText)findViewById(R.id.edt_time);

        //b1.setEnabled(false);


        et2.setVisibility(View.GONE);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("smartank");
       // ref.child("status").addValueEventListener(vl1);

        uploadQuantity("0.0");




    }





    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.btn_command:


                String s = txt_device.getText().toString();
                if(s.equals("Pump On"))
                {
                    Toast.makeText(this, "Last Command Not Finished", Toast.LENGTH_LONG);
                    return;
                }
                else
                {
                    if(!TextUtils.isEmpty(et1.getText()))
                    {
                        uploadQuantity(et1.getText().toString());

                    }
                    else if(!TextUtils.isEmpty(et2.getText()))
                    {
                        uploadTime(et2.getText().toString());

                    }
                    else
                    {
                        Toast.makeText(this, "Please Enter Value", Toast.LENGTH_LONG);

                    }
                }


                break;
            case R.id.btn_manual:
                finish();
                break;




        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        m1 = new mytask();
        m1.execute();


    }

    @Override
    protected void onPause() {
        super.onPause();


        m1.cancel(true);

    }

    /*

    private void startTask()
    {

        Toast.makeText(this,"Tracking Running", Toast.LENGTH_LONG).show();

        myManager.cancelAllTasks(CustomService.class);

        OneoffTask task = new OneoffTask.Builder()
                .setService(CustomService.class)
                .setTag("Task1")
                .setExecutionWindow(0L, 3600L)
                .setRequiredNetwork(Task.NETWORK_STATE_ANY)
                .setPersisted(true)
                .build();

        myManager.schedule(task);

        // Toast.makeText(this, "Background Update started", Toast.LENGTH_LONG).show();

    }
    */

/*
    private void startService()
    {
        Intent in = new Intent(this, Myservice.class);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        int s = sp.getInt("service", 0);
        if(s == 0) {

            PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("service", 1).commit();



            startService(in);

            Toast.makeText(this, "Background Update started", Toast.LENGTH_LONG).show();

        }
        else
        {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("service", 0).commit();

            stopService(in);

            Toast.makeText(this, "Background Update stopped", Toast.LENGTH_LONG).show();


        }


    }

    */

    ValueEventListener vl1 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            DeviceAC a1 = dataSnapshot.getValue(DeviceAC.class);

            if(a1.getState().equals("running"))
            {
                txt_device.setText("Pump On");
                status = true;
            }
            else
            {
                txt_device.setText("Pump Off");
                status = false;

            }
            b1.setEnabled(true);


        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    private  void uploadTime(String st)
    {

        Tank t1 = new Tank("Running", "", st);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("smartank").child("chitkara");

        ref.setValue(t1);

    }

    private  void uploadQuantity(String st)
    {

        Tank t1 = new Tank("Running", st, "");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("smartank").child("chitkara");

        ref.setValue(t1);

    }


    private class mytask extends AsyncTask<Void, Boolean, Boolean>
    {

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO Auto-generated method stub
           while (true)
           {

               ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
               NetworkInfo nf = cm.getActiveNetworkInfo();
               if (nf == null) {
                  publishProgress(false);
               } else {
                  publishProgress(true);
               }
               try {
                   Thread.sleep(5000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }


           }


        }


        @Override
        protected void onProgressUpdate(Boolean... values) {
            super.onProgressUpdate(values);
            Log.e("Value", String.valueOf(values[0]));

            if(values[0])
            {
                txt_internet.setText("Internet Connected");
            }
            else
            {
                txt_internet.setText("No Internet");
            }

        }
    }







}
