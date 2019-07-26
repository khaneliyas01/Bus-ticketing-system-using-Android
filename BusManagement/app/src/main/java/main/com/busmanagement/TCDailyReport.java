package main.com.busmanagement;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TCDailyReport extends AppCompatActivity {

    TextView report,scanner,reportbtn;
    String info="";
    String getreportURL = IPaddress.ip+"report.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcdaily_report);

        scanner = (TextView) findViewById(R.id.textTCHomeScannerBtn);
        reportbtn = (TextView) findViewById(R.id.textTCHomeDailyReportBtn);
        report = (TextView) findViewById(R.id.textTCDailyReportTodayReport);

        getReport();

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getReport(){
        RequestParams params = new RequestParams();
        params.put("busno",BusData.nusnumber);

        final ProgressDialog pDialog = ProgressDialog.show(TCDailyReport.this, null, "please wait...", true,false);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getreportURL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                pDialog.dismiss();
                info="";
                report.setText("");
                String res = new String(responseBody);
                try{
                    JSONObject o = new JSONObject(res);
                    if(o.getString("success").equals("200")){
                        info = "Todays total tickets sold: "+o.getString("total")+"\n\nTotal collection: "+o.getString("balance");
                        report.setText(info);
                    }
                }catch(Exception e){
                    Toast.makeText(TCDailyReport.this, res, Toast.LENGTH_SHORT).show();
                    report.setText(res);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pDialog.dismiss();
                report.setText("");
                Toast.makeText(TCDailyReport.this, "Connectivity failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
