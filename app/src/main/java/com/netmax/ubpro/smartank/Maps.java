package com.netmax.ubpro.smartank;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by preeti on 5/7/16.
 */
public class Maps extends AppCompatActivity implements LocationListener, View.OnClickListener {
    LocationManager l1;
    Button b1;
    TextView t1;
    double lat, longitude, alt;
    float spd, acc;
    long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);
        l1 = (LocationManager) getSystemService(LOCATION_SERVICE);
        t1 = (TextView) findViewById(R.id.textView);
        b1 = (Button) findViewById(R.id.button4);
        b1.setOnClickListener(this);
        Location location = l1.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        longitude = location.getLongitude();
        lat = location.getLatitude();
        alt = location.getAltitude();
        spd = location.getSpeed();
        acc = location.getAccuracy();
        time =location.getTime();
        StringBuilder sb= new StringBuilder();
        sb.append("latitude:");
        sb.append(lat);
        sb.append("\n");

        sb.append("longitude:");
        sb.append(longitude);
        sb.append("\n");

        sb.append("altitude:");
        sb.append(alt);
        sb.append("\n");

        sb.append("speed:");
        sb.append(spd);
        sb.append("\n");

        sb.append("accuracy:");
        sb.append(acc);
        sb.append("\n");
        t1.setText(sb.toString());


    }


  /*  @Override
    protected void onResume()

    {
        super.onResume();
        String[] permissions = new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(this,permissions,10);

        l1.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,2000,10,this);

    }

    @Override
    protected void onPause() {
        super.onPause();

        l1.removeUpdates(this);

    }

    @Override
    public void onLocationChanged(Location location)
    {
         lat = location.getLatitude();//these three are available in gps provider.
        //in network provider onlylatitude nd longitude is accesible.
         longitude = location.getLongitude();
         alt= location.getAltitude();
        spd= location.getSpeed();
         acc =location.getAccuracy();
        StringBuilder sb= new StringBuilder();
        sb.append("latitude:");
        sb.append(lat);
        sb.append("\n");

        sb.append("longitude:");
        sb.append(longitude);
        sb.append("\n");

        sb.append("altitude:");
        sb.append(alt);
        sb.append("\n");

        sb.append("speed:");
        sb.append(spd);
        sb.append("\n");

        sb.append("accuracy:");
        sb.append(acc);
        sb.append("\n");
        t1.setText(sb.toString());
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
*/

    @Override
    public void onClick(View v)
    {

        /*

            Toast.makeText(this,"Location submitted",Toast.LENGTH_SHORT).show();
            Intent i2= new Intent(this,Profile_1.class);
            i2.putExtra("longitude",longitude);
            i2.putExtra("latitude",lat);
            i2.putExtra("Time",time);
            startActivity(i2);
*/

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
