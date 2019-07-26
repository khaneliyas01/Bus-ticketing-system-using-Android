package main.com.busmanagement;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TrackBusLocationMap extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    String slocation="",dlocation="";
    String lat="",lon="";
    static String sdate="";
    static int markerflag = 0;
    static Marker start=null,end=null;
    EditText starting,ending;
    static int auto=0;
    static double slat=0.0,slon=0.0,dlat=0.0,dlon=0.0;
    TextView date;
    int count = 1;
    Button search;
    static List<BusInfoData> allplaces = new ArrayList<>();
    static Marker markers[] = new Marker[2];
    static Marker markers1[] = new Marker[2];

    ArrayList<String> myList;
    //List<DataModel> list = new ArrayList<>();
    String searchrBusURL = IPaddress.ip+"trackbusloc.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_bus_location_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        starting = (EditText) findViewById(R.id.editSearchRidesStarting);
        ending = (EditText) findViewById(R.id.editSearchRidesEnding);
        search = (Button) findViewById(R.id.btnSearchRidesSearch);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slocation=starting.getText().toString().trim();
                dlocation=ending.getText().toString().trim();
                if(slocation.isEmpty()||dlocation.isEmpty()){
                    Toast.makeText(TrackBusLocationMap.this, "Please provide all details!", Toast.LENGTH_SHORT).show();
                }else{
                    searchBus();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        Intent intent=new Intent(TrackBusLocationMap.this,LocService.class);
        startService(intent);
    }

    public void searchBus(){
        RequestParams params = new RequestParams();
        params.put("slocation",slocation);
        params.put("dlocation",dlocation);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(searchrBusURL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String res = new String(responseBody);
                try {
                    JSONObject o = new JSONObject(res);
                    if (o.getString("success").equals("200")) {
                        allplaces.clear();
                        JSONArray a = o.getJSONArray("resultlist");
                        for (int i = 0; i < a.length(); i++) {
                            BusInfoData binfo = new BusInfoData();
                            JSONObject obj = a.getJSONObject(i);
                            binfo.busno = obj.getString("busno");
                            binfo.lat1 = Double.parseDouble(obj.getString("lat"));
                            binfo.lon1 = Double.parseDouble(obj.getString("lon"));
                            binfo.source1 = obj.getString("source");
                            binfo.destination1 = obj.getString("destination");
                            allplaces.add(binfo);
                        }
                        //displayOnMap();
                        disp();
                    }
                }catch(Exception e){
                    Toast.makeText(TrackBusLocationMap.this, "Exception: "+e, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(TrackBusLocationMap.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

/*    public void displayOnMap() {
        markers = new Marker[allplaces.size()];
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < allplaces.size(); i++) {
            int cflag =0;
            Marker start1=null;
            BusInfoData p = allplaces.get(i);
            LatLng start = new LatLng(p.lat, p.lon);
            for(int j=0;j<UserData.route.size();j++) {
                BusInfoData rp = new BusInfoData();
                rp = UserData.route.get(j);
                if(rp.name.equals(p.name)){
                    cflag =1;
                    //start1 = mMap.addMarker(new MarkerOptions().position(start).title(p.name + ": " + p.pollution).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                }
            }
            if(cflag==1){
                start1 = mMap.addMarker(new MarkerOptions().position(start).title(p.name + ": " + p.pollution).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }else {
                start1 = mMap.addMarker(new MarkerOptions().position(start).title(p.name + ": " + p.pollution).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
            builder.include(start1.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 100;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        try {
            if(flagcamera==0 && viewroute==0) {
                mMap.animateCamera(cu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        waypoints = "";
        LatLngBounds.Builder builder1 = new LatLngBounds.Builder();
        int last = UserData.route.size()-1;
        BusInfoData pstart = new BusInfoData();
        BusInfoData pend = new BusInfoData();
        for (int i = 0; i < UserData.route.size(); i++) {
            BusInfoData p = UserData.route.get(i);
            LatLng start = new LatLng(p.lat, p.lon);
            if(i==0){
                pstart.name = p.name;
                pstart.lon = p.lon;
                pstart.lat = p.lat;
            }
            if(i==last){
                pend.name = p.name;
                pend.lat = p.lat;
                pend.lon = p.lon;
            }
            if(i!=0 && i!=last){
                waypoints += p.lat + "," + p.lon + "|";
            }
            Marker start1 = mMap.addMarker(new MarkerOptions().position(start).title(p.name + ": " + p.pollution).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            builder1.include(start1.getPosition());
        }
        routebounds = builder1.build();
        String directionApiPath="";
        if(waypoints.isEmpty()){
            directionApiPath = "https://maps.googleapis.com/maps/api/directions/json?origin=" + pstart.lat + "," + pstart.lon + "&destination=" + pend.lat + "," + pend.lon;
        }else {
            directionApiPath = "https://maps.googleapis.com/maps/api/directions/json?origin=" + pstart.lat + "," + pstart.lon + "&destination=" + pend.lat + "," + pend.lon + "&" + sensor + "&waypoints=" + waypoints;
        }
        getDirectionFromDirectionApiServer(directionApiPath);
    }*/

    public void disp(){
        markers = new Marker[allplaces.size()];
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        myList=new ArrayList<String>();
        mMap.clear();
        for (int i = 0; i < allplaces.size(); i++) {
            BusInfoData p = allplaces.get(i);
            LatLng latLng1 = new LatLng(p.lat1,p.lon1);
            mMap.setOnMarkerClickListener(TrackBusLocationMap.this);
            markers[i] = mMap.addMarker(new MarkerOptions().position(latLng1).title(p.busno).snippet(p.destination1));
            myList.add("Source: "+p.source1+"\nDestination: "+p.destination1);
            markers[i].setTag(p.busno);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
        }
        markers1 = new Marker[allplaces.size()];
        LatLngBounds.Builder builder1 = new LatLngBounds.Builder();
        myList=new ArrayList<String>();
        mMap.clear();
        for (int i = 0; i < allplaces.size(); i++) {
            BusInfoData p = allplaces.get(i);
            LatLng latLng1 = new LatLng(p.lat1,p.lon1);
            mMap.setOnMarkerClickListener(TrackBusLocationMap.this);
            markers[i] = mMap.addMarker(new MarkerOptions().position(latLng1).title(p.busno).snippet(p.destination1));
            myList.add("Source: "+p.source1+"\nDestination: "+p.destination1);
            markers[i].setTag(p.busno);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
        }
        LatLng sydney = new LatLng(UserData.lat, UserData.lon);
        mMap.addMarker(new MarkerOptions().position(sydney).title("current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if(allplaces.size()==0){
            Toast.makeText(TrackBusLocationMap.this,"No Bus Found",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        /*ParkingData.currentmarkerid = (String) marker.getTag();
        Intent i = new Intent(ParkingMap.this,ParkingDetails.class);
        startActivity(i);*/
        return false;
    }
}