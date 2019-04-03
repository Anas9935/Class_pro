package com.example.myapplication.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.Contract_class;
import com.example.myapplication.data.SQL_HELPER;
import com.example.myapplication.data.dbHelper;

public class addStaff extends AppCompatActivity {
EditText name,add1,add2,add3,con1,con2,salary,doj;
Spinner desig;
dbHelper helper;

int uid,designation=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        name=(EditText)findViewById(R.id.staff_nameXML);
        add1=(EditText)findViewById(R.id.staff_add1);
        add2=(EditText)findViewById(R.id.staff_add2);
        add3=findViewById(R.id.staff_add3);
        con1=(EditText)findViewById(R.id.staff_no1);
        con2=(EditText)findViewById(R.id.staff_no2);
        salary=(EditText)findViewById(R.id.staff_salary);
        doj=(EditText)findViewById(R.id.staff_doj);
        desig=findViewById(R.id.add_staff_spinner);

        helper=new dbHelper(this);
        Intent intent=getIntent();
        uid=intent.getIntExtra("uid",-1);
        setupSpinner();
        populateform();
    }
    public void setupSpinner(){
        final ArrayAdapter<CharSequence> des=ArrayAdapter.createFromResource(this,R.array.designation,android.R.layout.simple_spinner_item);
        des.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        desig.setAdapter(des);
        desig.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                designation=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                designation=-1;
            }
        });
    }
    public void populateform(){
        SQLiteDatabase db=helper.getWritableDatabase();
        String selecting=SQL_HELPER.select(Contract_class.entry.TABLE_USERS,"*",uid);
        Cursor table=db.rawQuery(selecting,null);
        int nameindex=table.getColumnIndex(Contract_class.entry.USER_NAME);
        int contact1index=table.getColumnIndex(Contract_class.entry.USER_CONTACT);
        table.moveToFirst();
        String n=table.getString(nameindex);
        Long number=table.getLong(contact1index);
        table.close();

        name.setText(n);
        con1.setText(String.valueOf(number));
    }
private void save(){
//insert in the user table
    SQLiteDatabase db=helper.getWritableDatabase();
    String inserting= SQL_HELPER.insert(Contract_class.entry.TABLE_STAFF);
    db.execSQL(inserting);
    Toast.makeText(this, "Staff entered", Toast.LENGTH_SHORT).show();

    String updateDesig=SQL_HELPER.update(Contract_class.entry.TABLE_USERS,Contract_class.entry.USER_DESIGNATION,designation,uid);
    db.execSQL(updateDesig);
//inserting in the attendence table
    String insertingAttendence=SQL_HELPER.insert(Contract_class.entry.TABLE_ATTENDENCE);
    db.execSQL(insertingAttendence);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.staff_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_save:{
                save();
                finish();
                return true;
            }
            default:{
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
