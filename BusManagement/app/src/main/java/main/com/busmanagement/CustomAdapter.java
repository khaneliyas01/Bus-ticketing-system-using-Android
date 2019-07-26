package main.com.busmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by user on 20/01/2018.
 */

public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener {
    List<DataModel> list = new ArrayList<DataModel>();
    Context mContext;
    File path,dir,fl;
    String deleteticketURL = IPaddress.ip+"deleteticket.php";
    boolean isImageFitToScreen;

    public CustomAdapter(@NonNull Context context, int resource, List<DataModel> list) {
        super(context, R.layout.rowitem);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder{
        TextView busid,from,to,total,date,ticketno,cost;
        ImageView info;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        DataModel dm = list.get(position);
        /*Intent i = new Intent(v.getContext(),EmpDetails.class);
        i.putExtra("data",dm.toString());
        v.getContext().startActivity(i);*/
    }

    @Override
    public int getCount() {
        return list.size();
    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final DataModel dm = list.get(position);
        final ViewHolder viewHolder;

        View result;
        if(convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.rowitem,parent,false);
            viewHolder.busid = (TextView) convertView.findViewById(R.id.rowbusid);
            viewHolder.from = (TextView) convertView.findViewById(R.id.rowfrom);
            viewHolder.to = (TextView) convertView.findViewById(R.id.rowto);
            viewHolder.total = (TextView) convertView.findViewById(R.id.rowtotalpersons);
            viewHolder.ticketno = (TextView) convertView.findViewById(R.id.rowticketno);
            viewHolder.cost = (TextView) convertView.findViewById(R.id.rowticketcost);
            viewHolder.date = (TextView) convertView.findViewById(R.id.rowticketdate);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);
            result = convertView;

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("Delete Ticket!");
                alert.setMessage("Do you want to delete this ticket?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(position);
                        UserData.newtickets=0;
                        path = Environment.getExternalStorageDirectory();
                        dir = new File(path + "/tickets/");
                        dir.mkdirs();
                        fl = new File(dir, dm.ticketno + ".JPEG");
                        fl.delete();
                        deleteTicket(dm.id);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.create();
                alert.show();
                return false;
            }
        });
        /*Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;*/

        viewHolder.busid.setText("Bus id: "+dm.busid);
        viewHolder.from.setText("From: "+dm.startinglocation);
        viewHolder.to.setText("To: "+dm.destination);
        viewHolder.total.setText("Total persons: "+dm.totaltickets);
        viewHolder.ticketno.setText(dm.ticketno);
        viewHolder.cost.setText(dm.cost);
        viewHolder.date.setText(dm.date);
        viewHolder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(v.getContext(),EmpDetails.class);
                i.putExtra("data",dm.toString());
                v.getContext().startActivity(i);*/
                Intent i = new Intent(mContext,FullScreenImageActivity.class);
                Bitmap b = ((BitmapDrawable)viewHolder.info.getDrawable()).getBitmap();
                i.putExtra("bitmap",b);
                mContext.startActivity(i);
            }
        });
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        GetImage gi = new GetImage();
        gi.viewHolder = viewHolder;
        gi.execute(IPaddress.ip+"tickets/"+dm.ticketno+".jpeg");

        return convertView;
    }

    class GetImage extends AsyncTask<String, Void, Bitmap> {
        //ProgressDialog loading;
        ViewHolder viewHolder;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //loading = ProgressDialog.show(CustomAdapter.this, "processing...", null, true, true);
        }

        @Override
        protected void onPostExecute(Bitmap b) {
            super.onPostExecute(b);
            //loading.dismiss();
            viewHolder.info.setImageBitmap(b);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String id = params[0];
//                String add = "http://10.0.3.2/Smart_Institute/images/image1.jpg";
            URL url = null;
            Bitmap image1 = null;
            try {
                url = new URL(id);
                image1 = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                Log.d("Result", String.valueOf(image1));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return image1;
        }

    }

    public void deleteTicket(String id){
        RequestParams params = new RequestParams();
        params.put("id",id);

        final ProgressDialog pDialog = ProgressDialog.show(mContext, null, "deleting ticket...",true,false);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(deleteticketURL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                pDialog.dismiss();
                String res= new String(responseBody);
                if(res.equals("200")){
                    Toast.makeText(mContext, "Ticket deleted!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(mContext, BookedTickets.class);
                    mContext.startActivity(i);
                    ((Activity)mContext).finish();
                }else{
                    Toast.makeText(mContext, res, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pDialog.dismiss();
                Toast.makeText(mContext, "Connectivity failed!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
