package main.com.busmanagement;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class Registration extends AppCompatActivity {
    Spinner year;
    String yea,fname,lname,mobile,email,add,image,pass,adhar;
    TextView dob;
    EditText fnam,lnam,mobilno,emailid,address,password,aadhar;
    ImageView addpic;
    Button register;
    int flag = 0;
    String birthdate="";
    Calendar myCalendar = Calendar.getInstance();
    private static int RESULT_LOAD_IMAGE = 1;
    private final int MY_PERMISSION_READ_EXTERNAL_STORAGE=1;
    private static final int PICK_FROM_GALLERY = 2;
    Bitmap thumbnail = null;
    String registerURL = IPaddress.ip+"register.php";
    String uploadimageURL = IPaddress.ip+"uploadimage.php";
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        addpic = (ImageView) findViewById(R.id.imgStudentIDphoto);
        dob = (TextView) findViewById(R.id.textRegStdDob);

        fnam = (EditText) findViewById(R.id.editRegStdName);
        lnam = (EditText) findViewById(R.id.editRegStdRollNo);
        mobilno = (EditText) findViewById(R.id.editRegStdMobileNo);
        emailid = (EditText) findViewById(R.id.editRegStdEmail);
        address = (EditText) findViewById(R.id.editRegStdAddress);
        register = (Button) findViewById(R.id.btnRegStudentSubmit);
        password = (EditText) findViewById(R.id.editRegPassword);
        aadhar = (EditText) findViewById(R.id.editRegAadhar);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Processing...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);

        year = (Spinner) findViewById(R.id.spinnerRegStdYear);
        String[] year1 = {"Select Gender", "Male", "Female"};
        ArrayAdapter adapter1 = new ArrayAdapter(Registration.this,android.R.layout.simple_spinner_item,year1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        year.setAdapter(adapter1);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

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

        addpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Registration.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Registration.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_READ_EXTERNAL_STORAGE);
                }else {
                    Intent in = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(in, RESULT_LOAD_IMAGE);
                }
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Registration.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                yea=adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Registration.this,"Please select your gender",1000).show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = fnam.getText().toString().trim();
                lname = lnam.getText().toString().trim();
                mobile = mobilno.getText().toString().trim();
                email = emailid.getText().toString().trim();
                add = address.getText().toString().trim();
                pass = password.getText().toString().trim();
                adhar = aadhar.getText().toString().trim();
                if(yea.equals("Select Gender") || fname.equals("") || lname.equals("") || mobile.equals("") || email.equals("") || add.equals("") || thumbnail.equals(null) || pass.equals("") || adhar.equals("")){
                    Toast.makeText(Registration.this,"Please provide all details in form!",1000).show();
                }else {
                    registerInfo();
                }
            }
        });
    }


    public void registerInfo(){
        image = getStringImage(thumbnail);

        RequestParams params= new RequestParams();
        params.put("fname",fname);
        params.put("lname",lname);
        params.put("gender",yea);
        params.put("mobile",mobile);
        params.put("email",email);
        params.put("address",add);
        params.put("dob",birthdate);
        params.put("adhar",adhar);
        params.put("password",pass);
        params.put("image",image);

        pDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(registerURL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                pDialog.dismiss();
                String res = new String(responseBody);
                try {
                    JSONObject o = new JSONObject(res);
                    if (o.getString("success").equals("200")) {
                        Toast.makeText(Registration.this, "Registered successfully!", 1000).show();
                        UserData.id = o.getString("id");
                        //uploadImage();
                        flag = 1;
                        finish();
                    } else {
                        Toast.makeText(Registration.this, res, 1000).show();
                    }
                }catch(Exception e){
                    Toast.makeText(Registration.this, "Json exception!", 1000).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pDialog.dismiss();
                Toast.makeText(Registration.this,"Connectivity failed!",1000).show();
            }
        });
    }

    public void uploadImage(){
        image = getStringImage(thumbnail);

        RequestParams params = new RequestParams();
        params.put("id",UserData.id);
        params.put("image",image);

        pDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(uploadimageURL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                pDialog.dismiss();
                String res = new String(responseBody);
                if(res.equals("200")){
                    finish();
                }else{
                    Toast.makeText(Registration.this, res, 1000).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pDialog.dismiss();
                Toast.makeText(Registration.this, "Connectivity failed!", 1000).show();
            }
        });
        flag=0;
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults){
        switch (requestCode) {
            case MY_PERMISSION_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent in = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(in, RESULT_LOAD_IMAGE);

                } else {

                    ActivityCompat.requestPermissions(Registration.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_READ_EXTERNAL_STORAGE);
                }
                return;
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            thumbnail = (BitmapFactory.decodeFile(picturePath));
            thumbnail = Bitmap.createScaledBitmap(thumbnail,200,300,true);
            addpic.setImageBitmap(thumbnail);
        }
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dob.setPaintFlags(dob.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        dob.setText("Date of Birth: "+sdf.format(myCalendar.getTime()));
        birthdate = sdf.format(myCalendar.getTime());
    }


}

