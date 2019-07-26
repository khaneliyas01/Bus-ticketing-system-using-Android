package main.com.busmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

public class BActivity extends AppCompatActivity {
String url= IPaddress.ip+"break.php";
String turl=IPaddress.ip+"total.php";
TextView t1;
    ArrayList <String> clist= new ArrayList<String>();
    private ProgressDialog pDialog;
    ListView list;
    ArrayList arrayList;
    String menu,price;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
       // t1=(TextView)findViewById(R.id.textView6);
        list=(ListView)findViewById(R.id.l1);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
       // b1=(Button)findViewById(R.id.button);
        /*b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IPaddress.total==0){
                    Toast.makeText(BActivity.this,"Add items cart",Toast.LENGTH_SHORT);

                }else {
                    Intent inte = new Intent(BActivity.this, Payment.class);
                    startActivity(inte);
                }
            }
        });*/
        show();
        //total();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menu= parent.getItemAtPosition(position).toString();
                price=parent.getItemAtPosition(position).toString();

                String select=((TextView)view).getText().toString();
                if(clist.contains(select)){
                        clist.remove(select);

                }else{

                    clist.add(select);
                }
            }
        });

    }

    public void show(){
        RequestParams params = new RequestParams();
        params.put("sr", ScheduleActivity.sr);
        params.put("dest", ScheduleActivity.dest);
        pDialog = new ProgressDialog(BActivity.this);
        pDialog.setMessage("Verifing Details..");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                pDialog.dismiss();
                String response = new String(responseBody);
                try {
//                    System.out.println(response.toString());
//                    JSONObject obj = new JSONObject(response.toString());
//                    JSONArray array = obj.getJSONArray("result");

                    JSONObject object = new JSONObject(response);
                    System.out.println(object);
                    JSONArray array = object.getJSONArray("result");

                    arrayList = new ArrayList();



                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.getJSONObject(i);
                        String bno = object1.getString("busno");
                        String bid = object1.getString("busid");
                        String sur = object1.getString("source");
                        String des = object1.getString("destination");
                        String date = object1.getString("daten");
                        String time = object1.getString("timen");
                        String stop = object1.getString("stop");
                        int rate = object1.getInt("rate");
                       // IPsetting.total = object1.getString("sum");
                        int sum=0;
                       //arrayList.add(array.get(i).toString());
                        //arr.add(Double.valueOf(array.get(i).toString()));
                        //a[i] = price;



                        // String det1 = object1.getString("Mobile");
//                        arrayList.add(det + "\n" + det1);
                        arrayList.add("Bus no-"+bno+"Busid-"+bid+"From-"+sur+"To-"+des+"Date-"+date+"Time-"+time+"Stop-"+stop+"Rate-"+rate);
                       // arrayList.add(price);
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(BActivity.this, android.R.layout.simple_list_item_1, arrayList);
                    // listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);

                    list.setAdapter(arrayAdapter);


//                    if (obj.getString("success").equals("200")) {
//
//
//
//                        Toast.makeText(UserRequestConscious.this, "Login Successfully", Toast.LENGTH_LONG).show();
////                        Intent i = new Intent(UserRequestConscious.this, UserRequest.class);
////                        startActivity(i);
////                        finish();
//
//
//                    } else {
//                        Toast.makeText(UserRequestConscious.this, "Login Failed", Toast.LENGTH_LONG).show();
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(BActivity.this, " Failed At Exception" + e, Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                pDialog.dismiss();
            }

        });


    }




    public void total(){
        RequestParams params = new RequestParams();
     //   params.put("uid", IPsetting.uid);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(turl, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                pDialog.dismiss();
                String response = new String(responseBody);
                try {
//
                    JSONObject object = new JSONObject(response);
                    System.out.println(object);
                    JSONArray array = object.getJSONArray("result");

                    arrayList = new ArrayList();



                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.getJSONObject(i);
                       // IPsetting.total = object1.getInt("sum");
                //      String tot=  String.valueOf(IPsetting.total);
                       // t1.setText(tot);
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(BActivity.this, android.R.layout.simple_list_item_1, arrayList);
                    // listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(BActivity.this, " Failed At Exception" + e, Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                pDialog.dismiss();
            }

        });


    }





}
