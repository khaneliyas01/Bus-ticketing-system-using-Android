package main.com.busmanagement;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TCHome extends AppCompatActivity {

    TextView scanner,report;
    TextView ticketinfo;
    Button scan;
    String scanResult="",info="";
    String scanURL = IPaddress.ip+"scanticket.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tchome);

        scanner = (TextView) findViewById(R.id.textTCHomeScannerBtn);
        report = (TextView) findViewById(R.id.textTCHomeDailyReportBtn);
        ticketinfo = (TextView) findViewById(R.id.textTCHomeScannerInfo);
        scan = (Button) findViewById(R.id.btnTCHomeCheckTicketDetails);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(TCHome.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(TCHome.this,new String[]{Manifest.permission.CAMERA},0);
                }else {
                    ticketinfo.setText("");
                    info = "";
                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE,PRODUCT_MODE");
                    startActivityForResult(intent, 0);
                }
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TCHome.this, TCDailyReport.class);
                startActivity(i);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                scanResult = intent.getStringExtra("SCAN_RESULT");
                checkCode();
            } else if (resultCode == RESULT_CANCELED) {

            }
        }

    }


    public void checkCode(){
        RequestParams params= new RequestParams();
        params.put("key",scanResult);

        final ProgressDialog pDialog = ProgressDialog.show(TCHome.this, null, "processing...",true,false);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(scanURL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                pDialog.dismiss();
                String res = new String(responseBody);
                try{
                    JSONObject o = new JSONObject(res);
                    if(o.getString("success").equals("200")){
                        info = "Ticket scanned successfully!\n\n\nTicket details:\n\nTicket No: "+o.getString("ticketno")+"\nFrom: "+o.getString("from")+"\nTo: "+o.getString("to")+"\nTotal persons: "+o.getString("total")+"\nAdults: "+o.getString("adults")+"\nChildren: "+o.getString("child")+"\nBus ID: "+o.getString("busid")+"\nBus no: "+o.getString("busno")+"\nTicket charges: "+o.getString("charges");
                    }
                    ticketinfo.setText(info);
                }catch(Exception e){
                    Toast.makeText(TCHome.this, res, Toast.LENGTH_SHORT).show();
                    ticketinfo.setText(res);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pDialog.dismiss();
                Toast.makeText(TCHome.this, "Connectivity failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 0: if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                        ticketinfo.setText("");
                        info = "";
                        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                        intent.putExtra("SCAN_MODE", "QR_CODE_MODE,PRODUCT_MODE");
                        startActivityForResult(intent, 0);
                    }else{
                        Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
                    }
                    break;
        }
    }
}
