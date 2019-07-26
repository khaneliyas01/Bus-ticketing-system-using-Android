package main.com.busmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static main.com.busmanagement.MainActivity.loginurl;
import static main.com.busmanagement.UserData.pDialog;

public class ScheduleActivity extends AppCompatActivity {
Button sh;
EditText sur,des;
public static String sr,dest;
    public static String show = IPaddress.ip+"login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        sur=(EditText)findViewById(R.id.source);
        des=(EditText)findViewById(R.id.desti);
        sh=(Button)findViewById(R.id.button2);

        sh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // login();
                sr = sur.getText().toString().trim();
                dest = des.getText().toString().trim();
                Intent in = new Intent(ScheduleActivity.this,BActivity.class);
                startActivity(in);
            }
        });
    }

    public void login(){


        RequestParams params =  new RequestParams();
        params.put("sr",sr);
        params.put("dest",dest);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Processing...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(show, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                pDialog.dismiss();
                String response = new String(responseBody);
                System.out.print(response);

                try {
                    JSONObject obj = new JSONObject(response);
                    String message = obj.getString("message");
                    String success = obj.getString("success");

                    if(success.equals("200")){
                        Toast.makeText(ScheduleActivity.this,message,Toast.LENGTH_LONG).show();
                        UserData.id = obj.getString("id");
                        UserData.email = obj.getString("email");
                        UserData.mobile = obj.getString("mobile");
                        UserData.fname = obj.getString("fname");
                        UserData.lname = obj.getString("lname");
                        //UserData.address = obj.getString("address");
                        UserData.gender = obj.getString("gender");
                        UserData.dob = obj.getString("dob");
                        UserData.imagename = obj.getString("image");
                        UserData.cardno = obj.getString("cardno");
                        UserData.cvv = obj.getString("cvv");
                        UserData.amount = obj.getString("amount");
                        Intent i = new Intent(ScheduleActivity.this,UserHome.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(ScheduleActivity.this,message,Toast.LENGTH_LONG).show();

                    }
                }catch(JSONException e){
                    Toast.makeText(ScheduleActivity.this,"Error in JSON",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pDialog.dismiss();
                Toast.makeText(ScheduleActivity.this,"Connectivity failed with server! Try again.",Toast.LENGTH_LONG).show();
            }
        });
    }
}
