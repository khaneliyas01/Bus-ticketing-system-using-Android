package main.com.busmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    public static TextView adminlink;
    public static EditText userid,password;
    public static Button signin,signup;
    public static String uid,pass;
    public static String loginurl = IPaddress.ip+"login.php";
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userid = (EditText) findViewById(R.id.editUserid);
        password = (EditText) findViewById(R.id.editPassword);

        signin = (Button) findViewById(R.id.btnsignin);
        signup = (Button) findViewById(R.id.btnsignup);
        adminlink = (TextView) findViewById(R.id.textLinkToAdmin);

        adminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,TCLogin.class);
                startActivity(i);
                finish();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Registration.class);
                startActivity(i);
            }
        });
    }

    public void login(){
        uid = userid.getText().toString().trim();
        pass = password.getText().toString().trim();

        RequestParams params =  new RequestParams();
        params.put("uid",uid);
        params.put("password",pass);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Processing...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(loginurl, params, new AsyncHttpResponseHandler() {
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
                        Toast.makeText(MainActivity.this,message,1000).show();
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
                        Intent i = new Intent(MainActivity.this,UserHome.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this,message,1000).show();

                    }
                }catch(JSONException e){
                    Toast.makeText(MainActivity.this,"Error in JSON",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pDialog.dismiss();
                Toast.makeText(MainActivity.this,"Connectivity failed with server! Try again.",Toast.LENGTH_LONG).show();
            }
        });
    }
}
