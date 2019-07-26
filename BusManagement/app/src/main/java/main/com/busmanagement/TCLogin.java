package main.com.busmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class TCLogin extends AppCompatActivity {
    public static int tcid;
    EditText userid,password;
    String uid="",pass="";
    TextView linktouser;
    Button confirm;
    String tcloginURL = IPaddress.ip+"tclogin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tclogin);

        userid= (EditText) findViewById(R.id.editTCLoginUserId);
        password = (EditText) findViewById(R.id.editTCLoginPassword);
        linktouser = (TextView) findViewById(R.id.textLinktoUserLogin);
        confirm = (Button) findViewById(R.id.btnTCLogin);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uid = userid.getText().toString().trim();
                pass = password.getText().toString().trim();
                if(uid.isEmpty() || pass.isEmpty()){

                }else{
                    login();
                }
            }
        });

        linktouser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TCLogin.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void login(){
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        params.put("password",pass);

        final ProgressDialog pDialog = new ProgressDialog(TCLogin.this);
        pDialog.setMessage("Processing...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(tcloginURL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                pDialog.dismiss();
                String res = new String(responseBody);
                if(res.equals("200")){
                    BusData.nusnumber = uid;
                    Toast.makeText(TCLogin.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(TCLogin.this,TCHome.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(TCLogin.this, "Login failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pDialog.dismiss();
                Toast.makeText(TCLogin.this, "Connectivity failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
