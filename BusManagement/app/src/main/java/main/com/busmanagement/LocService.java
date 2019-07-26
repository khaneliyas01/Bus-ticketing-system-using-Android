package main.com.busmanagement;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LocService extends Service implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    static String uid = "";
    static double lat = 0.0,lon=0.0;
    String updatelocURL = IPaddress.ip+"updateloc.php";
    public static final String TAG = "MyServiceTag";

    public LocService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        Toast.makeText(getApplicationContext(), "Started", Toast.LENGTH_SHORT).show();
        super.onCreate();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(5000);
        //mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mGoogleApiClient.connect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        uid = UserData.service.getExtras().getString("id");
        //uid = intent.getStringExtra("id");
        // TODO Auto-generated method stub
        UserData.serviceloc = true;
        return START_STICKY;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            stopSelf();
        }else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        UserData.lat = 0.0;
        UserData.lon = 0.0;
        UserData.count++;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        UserData.lat = 0.0;
        UserData.lon = 0.0;
    }

    @Override
    public void onLocationChanged(Location location) {
        UserData.lat = location.getLatitude();
        UserData.lon = location.getLongitude();
        lat = location.getLatitude();
        lon = location.getLongitude();

        updateLoc();
    }

    public void updateLoc(){
        RequestParams params = new RequestParams();
        params.put("uid", TCLogin.tcid);
        params.put("lat",lat);
        params.put("lon",lon);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(updatelocURL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String res = new String(responseBody);
                try {
                    JSONObject o = new JSONObject(res);
                    if (o.getString("success").equals("200")) {
                        UserData.ulat = o.getDouble("lat");
                        UserData.ulon = o.getDouble("lon");
                    } else {
                        Toast.makeText(LocService.this, res, Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(LocService.this, res, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                updateLoc();
            }
        });
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        lat = 0.0;
        lon = 0.0;
        UserData.serviceloc = false;
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy(){
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
        lat = 0.0;
        lon = 0.0;
        UserData.serviceloc = false;
    }
}