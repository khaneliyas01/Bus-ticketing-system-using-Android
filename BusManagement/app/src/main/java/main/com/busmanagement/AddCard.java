package main.com.busmanagement;

import android.app.ProgressDialog;
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

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AddCard extends AppCompatActivity {

    EditText ecardno,ecvv,eamount;
    TextView balance;
    String cardno="",cvv="",amount="";
    Button ok;
    String addamountURL = IPaddress.ip+"addcard.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        ecardno = (EditText) findViewById(R.id.editAddCardCardNo);
        ecvv = (EditText) findViewById(R.id.editAddCardCvv);
        eamount = (EditText) findViewById(R.id.editAddCardBalance);
        balance = (TextView) findViewById(R.id.textCurrentBalance);
        ok = (Button) findViewById(R.id.btnAddCardConfirm);

        balance.setText("Your current balance: "+UserData.amount);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardno = ecardno.getText().toString().trim();
                cvv = ecvv.getText().toString().trim();
                amount = eamount.getText().toString().trim();
                if(!cardno.isEmpty() && !cvv.isEmpty() && !amount.isEmpty()){
                    if(cardno.length()!=12){
                        ecardno.setError("Invalid number!");
                    }else{
                        if(cvv.length()!=3){
                            ecvv.setError("Invalid cvv!");
                        }else{
                            callAddDetails();
                        }
                    }
                }
            }
        });
    }

    public void callAddDetails(){
        RequestParams params = new RequestParams();
        params.put("id",UserData.id);
        params.put("cardno",cardno);
        params.put("cvv",cvv);
        params.put("amount",amount);

        final ProgressDialog pDialog = ProgressDialog.show(AddCard.this, null, "adding amount details...",true,false);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(addamountURL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                pDialog.dismiss();
                String res= new String(responseBody);
                try{
                    JSONObject o = new JSONObject(res);
                    if(o.getString("success").equals("200")){
                        UserData.cardno = cardno;
                        UserData.cvv = cvv;
                        UserData.amount = o.getString("amount");
                        balance.setText("Your current balance: "+UserData.amount);
                        Toast.makeText(AddCard.this, "Amount added.", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(AddCard.this, res, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pDialog.dismiss();
                Toast.makeText(AddCard.this, "Connectivity failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
