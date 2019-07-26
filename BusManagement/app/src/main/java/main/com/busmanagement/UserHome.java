package main.com.busmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class UserHome extends AppCompatActivity {
    Bitmap image;
    ImageView profile;
    Button searchtrain,logout,addcard,bookedtickets,trackbus,schbus;
    TextView name,email;
    String imageURL = IPaddress.ip+"uploads/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        profile = (ImageView) findViewById(R.id.imgUserHome);
        searchtrain = (Button) findViewById(R.id.btnUserSearchTrain);
        bookedtickets = (Button) findViewById(R.id.btnUserViewBookedTickets);
        logout = (Button) findViewById(R.id.btnUserLogout);
        trackbus=(Button)findViewById(R.id.btnUserTrackBus);
        name = (TextView) findViewById(R.id.textUserHomeName);
        email = (TextView) findViewById(R.id.textUserHomeEmail);
        addcard = (Button) findViewById(R.id.btnUserAddBalance);
        schbus=(Button)findViewById(R.id.sch);

        name.setText(UserData.fname+" "+UserData.lname+"\nContact no: "+UserData.mobile);
        email.setText(UserData.email);

        getDetails();

        schbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(UserHome.this,ScheduleActivity.class);
                startActivity(in);
            }
        });

        searchtrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserHome.this,ViewBus.class);
                startActivity(i);
            }
        });

        addcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserHome.this,AddCard.class);
                startActivity(i);
            }
        });

        bookedtickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserHome.this,BookedTickets.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserHome.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        trackbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserHome.this,TrackBusLocationMap.class);
                startActivity(i);
            }
        });

    }

    public void getDetails(){
        String path = imageURL+UserData.imagename;
        new GetImage().execute(path);
    }


    class GetImage extends AsyncTask<String, Void, Bitmap> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(UserHome.this, "processing...", null, true, true);
        }

        @Override
        protected void onPostExecute(Bitmap b) {
            super.onPostExecute(b);
            loading.dismiss();
            profile.setImageBitmap(b);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String id = params[0];
//                String add = "http://10.0.3.2/Smart_Institute/images/image1.jpg";
            URL url = null;
            Bitmap image1 = null;
            try {
                url = new URL(id);
                image1 = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                Log.d("Result", String.valueOf(image1));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return image1;
        }

    }
}