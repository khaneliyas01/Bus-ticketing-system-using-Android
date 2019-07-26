package main.com.busmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Ganesh on 11/21/2017.
 */

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BookViewHolder> {
    private List<BusObject> BusList;

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card, viewGroup, false);

        return new BookViewHolder(itemView);
    }

    public BusAdapter(List<BusObject> bookList) {
        this.BusList = bookList;
    }

    @Override
    public int getItemCount() {
        return BusList.size();
    }


    @Override
    public void onBindViewHolder(final BookViewHolder holder, int position) {
        final BusObject ci = BusList.get(position);
        holder.trainname.setText(ci.busid);
        holder.source.setText(ci.source);
        holder.destination.setText(ci.destination);
        holder.Cost.setText(ci.cost);
        holder.Time.setText(ci.departuretime);
        holder.trainnumber.setText(ci.busnumber);

        holder.bookticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                BusData.id = ci.id;
                BusData.busid = ci.busid;
                BusData.source = ci.source;
                BusData.destination = ci.destination;
                BusData.cost = ci.cost;
                BusData.time = ci.departuretime;
                BusData.number = ci.busnumber;
                if(UserData.cardno.equals("0") || UserData.cvv.equals("0")){
                    Toast.makeText(holder.itemView.getContext(),"Please add your card details first to book tickets!",1000).show();
                }else {
                    /*Intent intent = new Intent(view.getContext(), SeatSelection.class);
                    view.getContext().startActivity(intent);*/
                    AlertDialog.Builder alert = new AlertDialog.Builder(holder.itemView.getContext());
                    final EditText editseats = new EditText(holder.itemView.getContext());
                    editseats.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editseats.setMaxLines(1);
                    LinearLayout ll = new LinearLayout(holder.itemView.getContext());
                    ll.setOrientation(LinearLayout.VERTICAL);
                    ll.setPadding(20,20,20,20);
                    alert.setTitle("Provide details to book tickets.");
                    TextView seats = new TextView(holder.itemView.getContext());
                    seats.setText("Total number of tickets:");
                    ll.addView(seats);
                    ll.addView(editseats);
                    TextView adultseats = new TextView(holder.itemView.getContext());
                    adultseats.setText("Total adult tickets:");
                    final EditText seatsadults = new EditText(holder.itemView.getContext());
                    seatsadults.setInputType(InputType.TYPE_CLASS_NUMBER);
                    seatsadults.setMaxLines(1);
                    ll.addView(adultseats);
                    ll.addView(seatsadults);
                    TextView childseats = new TextView(holder.itemView.getContext());
                    childseats.setText("Total child tickets:");
                    final EditText seatschild = new EditText(holder.itemView.getContext());
                    seatschild.setInputType(InputType.TYPE_CLASS_NUMBER);
                    seatschild.setMaxLines(1);
                    ll.addView(childseats);
                    ll.addView(seatschild);
                    alert.setView(ll);
                    alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TicketDetails.busid = ci.busid;
                            TicketDetails.busno = ci.busnumber;
                            TicketDetails.perseatcost = Float.parseFloat(ci.cost);
                            TicketDetails.startinglocation = ci.source;
                            TicketDetails.destination = ci.destination;
                            TicketDetails.totaltickets = editseats.getText().toString().trim();
                            TicketDetails.adulttickets = seatsadults.getText().toString().trim();
                            TicketDetails.childtickets = seatschild.getText().toString().trim();
                            if(TicketDetails.totaltickets.isEmpty() || TicketDetails.adulttickets.isEmpty() || TicketDetails.childtickets.isEmpty()){
                                Toast.makeText(holder.itemView.getContext(), "Please provide all required information!", Toast.LENGTH_SHORT).show();
                            }else{
                                int total = Integer.parseInt(TicketDetails.totaltickets);
                                int child = Integer.parseInt(TicketDetails.childtickets);
                                int adult = Integer.parseInt(TicketDetails.adulttickets);
                                if(total != child+adult){
                                    editseats.setError("Invalid count");
                                    Toast.makeText(holder.itemView.getContext(), "Total tickets must be equal to sum of total adult tickets and total child tickets!", Toast.LENGTH_SHORT).show();
                                }else {
                                    UserData.newtickets = 1;
                                    Intent i = new Intent(holder.itemView.getContext(), BookedTickets.class);
                                    holder.itemView.getContext().startActivity(i);
                                    ((Activity) holder.itemView.getContext()).finish();
                                }
                            }
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TicketDetails.busno="";
                            TicketDetails.busid ="";
                            TicketDetails.totaltickets = "";
                            TicketDetails.adulttickets = "";
                            TicketDetails.childtickets = "";
                        }
                    });
                    alert.create();
                    alert.show();

                }
            }
        });
    }


    public static class BookViewHolder extends RecyclerView.ViewHolder {

        protected TextView trainname;
        protected TextView source;
        protected TextView destination;
        protected TextView Time;
        protected TextView Cost;
        protected TextView trainnumber;
        protected Button bookticket;

        public BookViewHolder(View v) {
            super(v);
            trainname =  (TextView) v.findViewById(R.id.cardTrainName);
            source = (TextView)  v.findViewById(R.id.txtSource);
            destination = (TextView)  v.findViewById(R.id.txtDestination);
            Time = (TextView)  v.findViewById(R.id.txtDepartureTime);
            Cost = (TextView)  v.findViewById(R.id.txtCost);
            trainnumber = (TextView) v.findViewById(R.id.txtTrainNumber);
            bookticket = (Button) v.findViewById(R.id.buttonAccept);
        }
    }

}
