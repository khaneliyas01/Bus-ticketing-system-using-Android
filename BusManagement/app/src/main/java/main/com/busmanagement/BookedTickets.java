package main.com.busmanagement;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import cz.msebera.android.httpclient.Header;

public class BookedTickets extends AppCompatActivity {

    String bookticketURL = IPaddress.ip+"bookticket.php";
    String getbookedURL = IPaddress.ip+"getbooked.php";
    ListView listView;
    List<DataModel> list = new ArrayList<>();
    String key="",image="",cost="";
    File path,dir,fl;
    OutputStream out=null;
    Bitmap qr;
    Calendar calendar = Calendar.getInstance();
    String ticketno="";
    String [] files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_tickets);

        listView = (ListView) findViewById(R.id.bookedList);

        getBookedTickets();
    }

    public void bookTicket(){
        if (ActivityCompat.checkSelfPermission(BookedTickets.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BookedTickets.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else {

            key = UUID.randomUUID().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String daten = sdf.format(calendar.getTime());
            String t[] = daten.split("/");
            ticketno = t[2]+ "" + (int)((Math.random() * 9000)+1000) + "" + t[1] + "_" + t[0];

            MultiFormatWriter mfw = new MultiFormatWriter();
            try {
                BitMatrix bitmat = mfw.encode(key, BarcodeFormat.QR_CODE, 150, 150);

                BarcodeEncoder barenc = new BarcodeEncoder();
                qr = barenc.createBitmap(bitmat);

                path = Environment.getExternalStorageDirectory();
                dir = new File(path + "/tickets/");
                dir.mkdirs();
                fl = new File(dir, ticketno + ".JPEG");

                out = new FileOutputStream(fl);
                qr.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (qr != null) {
                image = getStringImage(qr);
                uploadImage();
                //uploadImage(image,SharedData.ino,SharedData.id);
            }
        }

    }

    private void uploadImage() {

        cost=""+(float)(Float.parseFloat(TicketDetails.adulttickets)*TicketDetails.perseatcost + Float.parseFloat(TicketDetails.childtickets)*(TicketDetails.perseatcost/2));
        if(Float.parseFloat(UserData.amount)>=Float.parseFloat(cost)) {

            RequestParams params = new RequestParams();
            params.put("image", image);
            params.put("uid", UserData.id);
            params.put("daten", TicketDetails.date);
            params.put("ticketno", ticketno);
            params.put("busid", TicketDetails.busid);
            params.put("busno", TicketDetails.busno);
            params.put("startloc", UserData.source);
            params.put("endloc", UserData.dest);
            params.put("totaltickets", TicketDetails.totaltickets);
            params.put("adult", TicketDetails.adulttickets);
            params.put("child", TicketDetails.childtickets);
            params.put("cost", cost);
            params.put("qrcode", key);

            final ProgressDialog progressDialog = new ProgressDialog(BookedTickets.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(bookticketURL, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    progressDialog.dismiss();
                    String res = new String(responseBody);
                    if (res.equals("200")) {
                        Toast.makeText(BookedTickets.this, "Tickets booked successfully!", 1000).show();
                        UserData.newtickets = 0;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SendMailSSL sendMailSSL = new SendMailSSL();
                                String message = "";
                                files = new String[]{fl.getAbsolutePath()};
                                try {
                                    sendMailSSL.sendEmailWithAttachments("smtp.gmail.com", "587", "mynewjava@gmail.com", "javarocks", UserData.email, "Suspecious activity reporting mail", message, files);
                                } catch (MessagingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        getBookedTickets();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    progressDialog.dismiss();
                    Toast.makeText(BookedTickets.this, "Connectivity failed!", 1000).show();
                }
            });
        }else{
            Toast.makeText(this, "Insufficient balance!", Toast.LENGTH_SHORT).show();
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){

        switch (requestCode){
            case 1: if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted for write external storage!", Toast.LENGTH_SHORT).show();
                bookTicket();
                break;
            }
            default:
                break;
        }
    }

    public void getBookedTickets(){
        RequestParams params = new RequestParams();
        params.put("uid",UserData.id);

        final ProgressDialog pDialog = ProgressDialog.show(BookedTickets.this, "processing...", null, true, true);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getbookedURL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                pDialog.dismiss();
                String res= new String(responseBody);
                try{
                    JSONObject o = new JSONObject(res);
                    if(o.getString("success").equals("200")){
                        list.clear();
                        JSONArray a = o.getJSONArray("result");
                        for(int i=0;i<a.length();i++){
                            DataModel dm = new DataModel();
                            JSONObject obj = a.getJSONObject(i);
                            dm.id = obj.getString("id");
                            dm.ticketno = obj.getString("ticketno");
                            dm.cost = obj.getString("cost");
                            dm.date = obj.getString("date");
                            dm.startinglocation = obj.getString("startloc");
                            dm.destination = obj.getString("endloc");
                            dm.busid = obj.getString("busid");
                            dm.busno = obj.getString("busno");
                            dm.totaltickets = obj.getString("total");
                            dm.adulttickets = obj.getString("adults");
                            dm.childtickets = obj.getString("child");
                            list.add(dm);
                        }
                        CustomAdapter ca = new CustomAdapter(BookedTickets.this,0,list);
                        listView.setAdapter(ca);
                    }
                }catch(Exception e){
                    Toast.makeText(BookedTickets.this, res, Toast.LENGTH_SHORT).show();
                }
                if(UserData.newtickets==1){
                    bookTicket();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pDialog.dismiss();
                Toast.makeText(BookedTickets.this, "Connectivity failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
