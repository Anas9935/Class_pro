package com.example.myapplication.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.objects.paymentObject;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder>{
    private Context context;
    private ArrayList<paymentObject> list;
    public PaymentAdapter(Context con, ArrayList<paymentObject> objs){
    context=con;
    list=objs;
}


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id,uid,name,amt,status,mode,time;
        public ViewHolder(View view){
            super(view);
            id=view.findViewById(R.id.paymentIdTV);
            uid=view.findViewById(R.id.paymentuserid);
            name=view.findViewById(R.id.paymentuserName);
            amt=view.findViewById(R.id.paymentAmount);
            status=view.findViewById(R.id.paymentStatus);
            mode=view.findViewById(R.id.paymentmode);
            time=view.findViewById(R.id.paymenttime);

        }

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.payment_listview,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        paymentObject current=list.get(i);
        viewHolder.id.setText(String.valueOf(current.getId()));
        viewHolder.uid.setText(String.valueOf(current.getUid()));
        viewHolder.name.setText(current.getName());
        viewHolder.amt.setText(String.valueOf(current.getAmt()));
        viewHolder.status.setText(String.valueOf(current.getStatus()));
        viewHolder.mode.setText(String.valueOf(current.getMode()));
        viewHolder.time.setText(current.getTime());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
