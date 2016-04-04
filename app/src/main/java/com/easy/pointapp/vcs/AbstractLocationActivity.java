package com.easy.pointapp.vcs;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.easy.pointapp.BuildConfig;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class AbstractLocationActivity extends ActionBarActivity
        implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LocationActivity";

    private static final long INTERVAL = 1000;

    private static final long FASTEST_INTERVAL = 1000;

    LocationRequest mLocationRequest;

    GoogleApiClient mGoogleApiClient;

    public Location mCurrentLocation;

    public ProgressDialog locationDialog;

    String mLastUpdateTime;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setSmallestDisplacement(500);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate ...............................");
        //show error dialog if GoolglePlayServices not available
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API)
                .addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        locationDialog = new ProgressDialog(this);
        locationDialog.setTitle("Retrieving location");
        locationDialog
                .setMessage("Please enable high accuracy location tracking for love and peace");


    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");
        if(mGoogleApiClient!=null) {
            mGoogleApiClient.connect();
        }
        if (!locationDialog.isShowing() && mCurrentLocation == null) {
            locationDialog.show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "onStop fired ..............");
        if(mGoogleApiClient!=null){
            mGoogleApiClient.disconnect();
            Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
        }
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            Toast.makeText(this, GooglePlayServicesUtil.getErrorString(status), Toast.LENGTH_LONG)
                    .show();
            return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (BuildConfig.DEBUG) {
            if (mCurrentLocation != null) {
                Toast.makeText(this,
                        mCurrentLocation.getLatitude() + " " + mCurrentLocation.getLongitude(),
                        Toast.LENGTH_SHORT);

            } else {
                Log.d(TAG, "Last known location is null");
            }
        }
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi
                .requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Location update started ..............: ");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged..............................................");
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        if (locationDialog.isShowing()) {
            locationDialog.dismiss();

        }
        if (mCurrentLocation != null) {
            Toast.makeText(this,
                    mCurrentLocation.getLatitude() + " " + mCurrentLocation.getLongitude(),
                    Toast.LENGTH_SHORT);

        } else {
            Log.d(TAG, "Last known location is null");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.isGooglePlayServicesAvailable()) {
            this.stopLocationUpdates();
            if (locationDialog.isShowing()) {
                locationDialog.dismiss();
            }
        }

    }

    protected void stopLocationUpdates() {
        if(mGoogleApiClient!=null)
        {
            if(mGoogleApiClient.isConnected())
            {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                Log.d(TAG, "Location update stopped .......................");
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }
        if (!locationDialog.isShowing() && mCurrentLocation == null) {
            locationDialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(this, PostsActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(myIntent);
    }
}
