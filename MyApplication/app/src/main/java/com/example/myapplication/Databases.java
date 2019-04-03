package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.data.Contract_class;
import com.example.myapplication.data.SQL_HELPER;
import com.example.myapplication.data.dbHelper;

public class Databases extends AppCompatActivity {
Button b1,b2,b3,b4,b5,b6;
TextView tv;
    dbHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_databases);

        b1=findViewById(R.id.Usersdb);
        b2=findViewById(R.id.Selectiondb);
        b3=findViewById(R.id.Paymentdb);
        b4=findViewById(R.id.Menudb);
        b5=findViewById(R.id.Staffdb);
        b6=findViewById(R.id.Attendencedb);
        tv=findViewById(R.id.textview);
        tv.setText("");
        helper=new dbHelper(this);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("");
             SQLiteDatabase db=helper.getReadableDatabase();
                String sel=SQL_HELPER.select(Contract_class.entry.TABLE_MENU);
                Cursor table=db.rawQuery(sel,null);
                if(table.getCount()==0){
                    Log.e("this","Empty table count="+table.getCount());
                }
                int idIndex=table.getColumnIndex(Contract_class.entry.MENU_ID);
                int nameIndex=table.getColumnIndex(Contract_class.entry.MENU_NAME);
                int vegIndex=table.getColumnIndex(Contract_class.entry.MENU_VEG);
                int priceIndex=table.getColumnIndex(Contract_class.entry.MENU_PRICE);
                int infoIndex=table.getColumnIndex(Contract_class.entry.MENU_INFO);
                int offerIndex=table.getColumnIndex(Contract_class.entry.MENU_OFFER);
                table.moveToFirst();
                for(int i=0;i<table.getCount();i++){
                    tv.append(String.valueOf(table.getInt(idIndex))+",");
                    tv.append(table.getString(nameIndex)+",");
                    tv.append(String.valueOf(table.getInt(vegIndex))+",");
                    tv.append(String.valueOf(table.getInt(priceIndex))+",");
                    tv.append(table.getString(infoIndex)+",");
                    tv.append(String.valueOf(table.getInt(offerIndex))+"\n");
                    table.moveToNext();
                }
                table.close();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("");
                SQLiteDatabase db=helper.getReadableDatabase();
                String sel=SQL_HELPER.select(Contract_class.entry.TABLE_USERS);
                Cursor table=db.rawQuery(sel,null);
                int idIndex=table.getColumnIndex(Contract_class.entry.USER_ID);
                int nameIndex=table.getColumnIndex(Contract_class.entry.USER_NAME);
                int passwordIndex=table.getColumnIndex(Contract_class.entry.USER_PASSWORD);
                int emailIndex=table.getColumnIndex(Contract_class.entry.USER_EMAIL);
                int contactIndex=table.getColumnIndex(Contract_class.entry.USER_CONTACT);
                int designationIndex=table.getColumnIndex(Contract_class.entry.USER_DESIGNATION);
                int genderIndex=table.getColumnIndex(Contract_class.entry.USER_GENDER);
                table.moveToFirst();
                for(int i=0;i<table.getCount();i++){
                    tv.append(String.valueOf(table.getInt(idIndex))+",");
                    tv.append(table.getString(nameIndex)+",");
                    tv.append(table.getString(passwordIndex)+",");
                    tv.append(table.getString(emailIndex)+",");
                    tv.append(String.valueOf(table.getInt(contactIndex))+",");
                    tv.append(String.valueOf(table.getInt(designationIndex))+",");
                    tv.append(String.valueOf(table.getInt(genderIndex))+"\n");
                    table.moveToNext();
                }
                table.close();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("");
                SQLiteDatabase db=helper.getReadableDatabase();
                String sel=SQL_HELPER.select(Contract_class.entry.TABLE_PAYMENT);
                Cursor table=db.rawQuery(sel,null);
                int idindex=table.getColumnIndex(Contract_class.entry.PAYMENT_ID);
                int uidindex=table.getColumnIndex(Contract_class.entry.PAYMENT_USER_ID);
                int totalindex=table.getColumnIndex(Contract_class.entry.PAYMENT_TOTAL);
                int modeindex=table.getColumnIndex(Contract_class.entry.PAYMENT_MODE);
                int statusindex=table.getColumnIndex(Contract_class.entry.PAYMENT_STATUS);
                int timeindex=table.getColumnIndex(Contract_class.entry.PAYMENT_TIME);

                table.moveToFirst();
                if(table.getCount()==0){
                    Log.e("this","empty");
                }
                for(int i=0;i<table.getCount();i++){
                    tv.append(String.valueOf(table.getInt(idindex))+",");
                    tv.append(String.valueOf(table.getInt(uidindex))+",");
                    tv.append(String.valueOf(table.getInt(totalindex))+",");
                    tv.append(String.valueOf(table.getInt(modeindex))+",");
                    tv.append(String.valueOf(table.getInt(statusindex))+"\n");
                    //TODO 3 solve how to get timestamp from a cursor
                    table.moveToNext();
                }
                table.close();
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("");
                SQLiteDatabase db=helper.getReadableDatabase();
                String sel=SQL_HELPER.select(Contract_class.entry.TABLE_STAFF);
                Cursor table=db.rawQuery(sel,null);
                int uidindex=table.getColumnIndex(Contract_class.entry.STAFF_USER_ID);
                int nameindex=table.getColumnIndex(Contract_class.entry.STAFF_FULL_NAME);
                int con1index=table.getColumnIndex(Contract_class.entry.STAFF_CONTACT_NUMBER_1);
                int con2index=table.getColumnIndex(Contract_class.entry.STAFF_CONTACT_NUMBER_2);
                int add1index=table.getColumnIndex(Contract_class.entry.STAFF_ADDRESS_1);
                int add2index=table.getColumnIndex(Contract_class.entry.STAFF_ADDRESS_2);
                int add3index=table.getColumnIndex(Contract_class.entry.STAFF_ADDRESS_3);
                int dojindex=table.getColumnIndex(Contract_class.entry.STAFF_DOJ);
                int salaryindex=table.getColumnIndex(Contract_class.entry.STAFF_SALARY);
                table.moveToFirst();
                if(table.getCount()==0){
                    Log.e("this","empty");
                }
                for(int i=0;i<table.getCount();i++){
                    tv.append(String.valueOf(table.getInt(uidindex))+",");
                    tv.append(table.getString(nameindex)+",");
                    tv.append(String.valueOf(table.getInt(con1index))+",");
                    tv.append(String.valueOf(table.getInt(con2index))+",");
                    tv.append(table.getString(add1index)+",");
                    tv.append(table.getString(add2index)+",");
                    tv.append(table.getString(add3index)+",");
                   //TODO 2 solve how to get timestamp from a cursor
                    // tv.append(String.valueOf(table.)+",");
                    tv.append(table.getString(salaryindex)+"\n");
                    table.moveToNext();
                }
                table.close();
            }
        });
    }
}
