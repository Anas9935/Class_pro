package com.example.myapplication.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.myapplication.Adapters.PaymentAdapter;
import com.example.myapplication.R;
import com.example.myapplication.data.Contract_class;
import com.example.myapplication.data.SQL_HELPER;
import com.example.myapplication.data.dbHelper;
import com.example.myapplication.objects.paymentObject;

import java.util.ArrayList;

public class PaymentTableActivity extends AppCompatActivity {
RecyclerView rview;
ArrayList<paymentObject> list;
RecyclerView.Adapter adapter;

dbHelper helper;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_table);

        rview=findViewById(R.id.recViewPayment);
        list=new ArrayList<>();
        adapter=new PaymentAdapter(this,list);
        RecyclerView.LayoutManager mLayout=new LinearLayoutManager(this);
        rview.setLayoutManager(mLayout);
        rview.setAdapter(adapter);
        helper=new dbHelper(this);
   //     recyclerViewOnClickListener();
    //TODO 4 to apply onitemclickListener on the recycler views to provide the capabilities of updating database on run time
        populateList();
    }


    public void populateList(){
        SQLiteDatabase db=helper.getReadableDatabase();
        String sel= SQL_HELPER.select(Contract_class.entry.TABLE_PAYMENT);
        Cursor table=db.rawQuery(sel,null);
       // int nameindex=table.getColumnIndex(Contract_class.entry.USER_NAME);
        int idindex=table.getColumnIndex(Contract_class.entry.PAYMENT_ID);
        int uidindex=table.getColumnIndex(Contract_class.entry.PAYMENT_USER_ID);
        int totalindex=table.getColumnIndex(Contract_class.entry.PAYMENT_TOTAL);
        int modeindex=table.getColumnIndex(Contract_class.entry.PAYMENT_MODE);
        int statusindex=table.getColumnIndex(Contract_class.entry.PAYMENT_STATUS);
        int timeindex=table.getColumnIndex(Contract_class.entry.PAYMENT_TIME);

        table.moveToFirst();
        int id,uid,amt,status,mode;
        String name,time;
        if(table.getCount()==0){
            Log.e("this","empty");
        }else
        for(int i=0;i<table.getCount();i++){
            id=table.getInt(idindex);
            uid=table.getInt(uidindex);
            //name=
            amt=table.getInt(totalindex);
            status=table.getInt(statusindex);
            mode=table.getInt(modeindex);
            String selectionName=SQL_HELPER.select(Contract_class.entry.TABLE_USERS,Contract_class.entry.USER_NAME,uid);
            Cursor nameTable=db.rawQuery(selectionName,null);
            int nameindex=nameTable.getColumnIndex(Contract_class.entry.USER_NAME);
            nameTable.moveToFirst();
            name=nameTable.getString(nameindex);
            list.add(new paymentObject(id,uid,amt,status,mode,name,"1234567"));
            nameTable.close();
            //TODO 3 solve how to get timestamp from a cursor
            table.moveToNext();
        }
        table.close();
    }
}
