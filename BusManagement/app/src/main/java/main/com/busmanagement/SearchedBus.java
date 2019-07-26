package main.com.busmanagement;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchedBus extends AppCompatActivity {
    ProgressDialog progressDialog;
    RecyclerView recList;
    String flag ="3";
    String searchURL = IPaddress.ip+"searchbus.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_bus);

        recList = (RecyclerView) findViewById(R.id.SearchBusList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recList.getContext(),
                llm.getOrientation());
        recList.addItemDecoration(dividerItemDecoration);
        recList.setLayoutManager(llm);

        callFunction();

    }

    public void callFunction(){
        final List<BusObject> list= new ArrayList<BusObject>();
        RequestParams params = new RequestParams();
        params.put("date",UserData.daten);
        params.put("source",UserData.source);
        params.put("destination",UserData.dest);

        progressDialog=new ProgressDialog(SearchedBus.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(searchURL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressDialog.dismiss();
                String result=new String(responseBody);
                System.out.println(result);
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.getString("success").equals("200")){
                        JSONArray jsonArray=object.getJSONArray("result");
                        System.out.println(jsonArray);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object1=jsonArray.getJSONObject(i);
                            BusObject ci = new BusObject();
                            ci.id = object1.getString("id");
                            ci.busid = object1.getString("busid");
                            ci.busnumber = object1.getString("busno");
                            ci.source=object1.getString("source");
                            ci.destination= object1.getString("destination");
                            ci.departuretime=object1.getString("time");
                            ci.cost=object1.getString("cost");
                            list.add(ci);
                            BusData.daten = UserData.daten;
                        }
                        BusAdapter ca = new BusAdapter(list);
                        recList.setAdapter(ca);


                    }else{
                        Toast.makeText(SearchedBus.this,object.getString("message"), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SearchedBus.this,"JSON ERROR", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressDialog.dismiss();
                Toast.makeText(SearchedBus.this,"Connection ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        //return list;
    }
}

