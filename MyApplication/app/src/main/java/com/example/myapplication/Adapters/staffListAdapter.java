package com.example.myapplication.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.objects.StaffObject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.Manifest.permission.CALL_PHONE;

public class staffListAdapter extends ArrayAdapter<StaffObject> {
    ArrayList<StaffObject> list;
    Context con;
    public staffListAdapter( Context context, ArrayList<StaffObject> l) {
       super(context,0,l);
        con=context;
       list=l;
    }

    @NotNull
    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
       View view =convertView;
       if(view==null){
           view= LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_todo,parent,false);
       }
        ImageView image,call;
        TextView name,id;
        image=view.findViewById(R.id.staff_list_image);
        call=view.findViewById(R.id.staff_list_callBtn);
        name=view.findViewById(R.id.staff_list_name);
        id=view.findViewById(R.id.staff_list_id);

        final StaffObject current=list.get(position);
        name.setText(current.getName());
        id.setText(String.valueOf(current.getId()));
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+String.valueOf(current.getContact1())));
                if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    con.startActivity(intent);
                } else {
                    ActivityCompat.requestPermissions((Activity) con,new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
            }
        });

       return view;
    }
}
