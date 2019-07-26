package main.com.busmanagement;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ViewBus extends AppCompatActivity {
    TextView date;
    Button search;
    Spinner sourcer,destinationr;
    DatePickerDialog datepicker;
    Calendar myCalendar = Calendar.getInstance();
    String source="",dest="",daten="";
    String searchURL = IPaddress.ip+"searchtrain.php";
    String searchstopsURL = IPaddress.ip+"searchstops.php";
    static String[] stops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bus);

        date = (TextView) findViewById(R.id.textSearchBusDate);
        sourcer = (Spinner) findViewById(R.id.editSearchBusSource);
        destinationr = (Spinner) findViewById(R.id.editSearchBusDestination);
        search = (Button) findViewById(R.id.btnSearchBusSearch);

        searchStops();

        final DatePickerDialog.OnDateSetListener dat = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        sourcer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: source="";
                        break;
                    default:
                        source = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        destinationr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: dest="";
                        break;
                    default:
                        dest = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(daten.equals("") || source.equals("") || dest.equals("")){
                    Toast.makeText(ViewBus.this,"Plaese enter all information to search bus!",Toast.LENGTH_SHORT).show();
                }else {
                    UserData.daten = daten;
                    TicketDetails.date = daten;
                    UserData.source = source;
                    UserData.dest = dest;
                    Intent i = new Intent(ViewBus.this,SearchedBus.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datepicker = new DatePickerDialog(ViewBus.this, dat, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datepicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datepicker.show();
            }
        });
    }


    private void searchStops(){
        RequestParams params = new RequestParams();
        params.put("id","1");

        final ProgressDialog pDialog = new ProgressDialog(ViewBus.this);
        pDialog.setMessage("processing...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);

        pDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(searchstopsURL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                pDialog.dismiss();
                String res = new String(responseBody);
                try{
                    JSONObject o = new JSONObject(res);
                    if(o.getString("success").equals("200")){
                        JSONArray a = o.getJSONArray("stops");
                        stops = new String[a.length()+1];
                        stops[0] = "Select stop";
                        for(int i=1; i<=a.length(); i++){
                            stops[i] = a.getString(i-1);
                        }
                        ArrayAdapter a1 = new ArrayAdapter(ViewBus.this, android.R.layout.simple_spinner_dropdown_item, stops);
                        ArrayAdapter a2 = new ArrayAdapter(ViewBus.this, android.R.layout.simple_spinner_dropdown_item, stops);
                        sourcer.setAdapter(a1);
                        destinationr.setAdapter(a2);
                    }else{
                        Toast.makeText(ViewBus.this,res,Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(ViewBus.this,"JSON Error!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pDialog.dismiss();
                Toast.makeText(ViewBus.this,"Connectivity failed!",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setPaintFlags(date.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        date.setText(sdf.format(myCalendar.getTime()));
        daten = sdf.format(myCalendar.getTime());
    }
}
