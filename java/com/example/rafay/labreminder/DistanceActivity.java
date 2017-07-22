
package com.example.rafay.labreminder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;


import java.nio.channels.Channel;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DistanceActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,callback {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    double lablat = 33;
    double lablng = 72;
    double lat;
    double lng;
    Location mLastLocation;
    callback cb = new callback() {
        @Override
        public void serviceSuccess(int value) {

        }

        @Override
        public void serviceFailure(Exception e) {

        }
    };
    private timeService service =new timeService(cb);
   // private dataLoc locService;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Create an instance of GoogleAPIClient.
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


        googleApiClient.connect();


    }

    private void setSupportActionBar(Toolbar toolbar) {

    }



    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onConnected(Bundle bundle) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);



        if (mLastLocation != null) {

            lat = mLastLocation.getLatitude();
            lng = mLastLocation.getLongitude();
            LatLng latlng = new LatLng(lat, lng);

            getOrigin();
            //Toast.makeText(this, "Latitude: "+lat , Toast.LENGTH_LONG).show();
            //Toast.makeText(this, "Longitude: "+lng, Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void getOrigin(){

        //locService = new dataLoc(this);

       // locService.getLoc();

        getTime();

    }

    public void getTime(){

        String orglat = Double.toString(lat);
        String orglng = Double.toString(lng);
        String deslat = Double.toString(lablat);
        String deslng = Double.toString(lablng);

        Toast.makeText(this, "CurrLat: "+orglat , Toast.LENGTH_LONG).show();
        Toast.makeText(this, "CurrLng: "+orglng , Toast.LENGTH_LONG).show();
        Toast.makeText(this, "DesLat: "+deslat , Toast.LENGTH_LONG).show();
        Toast.makeText(this, "DesLng: "+deslng , Toast.LENGTH_LONG).show();





        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + orglat + "%2C" + orglng + "&destination=" + deslat + "%2C" + deslng + "&key=AIzaSyBVQzpFR_fzzfEeMWaEma1DDJYs0"/* + this.getString(R.string.google_maps_key)*/;
        Log.d("tag", "Value: "+url);

        

        service.getTime(url);

        //Toast.makeText(this, "timeValue: "+value , Toast.LENGTH_LONG).show();



    }

    @Override
    public void serviceSuccess(int value) {

        dialog.hide();
        /*int time = service.timeValue;
        Toast.makeText(this, "timeValue: "+time , Toast.LENGTH_LONG).show();*/
        Log.d("tag", "" + value);
        Intent i = new Intent(getApplicationContext(), AlarmActivity.class);
        i.putExtra("timeValue", value);
        startActivity(i);

    }




    @Override
    public void serviceFailure(Exception e) {
        dialog.hide();

    }

    //@Override
    //public void locserviceSuccess(double lat, double lng) {


    //}

   // @Override
    //public void locserviceFailure(Exception e) {

    //}
}
