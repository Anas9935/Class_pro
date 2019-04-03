package com.example.myapplication.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.data.Contract_class;
import com.example.myapplication.data.SQL_HELPER;
import com.example.myapplication.data.dbHelper;

public class ProfileActivity extends AppCompatActivity {
int uid,salary;
String name,add1,add2,add3;
Long con1,con2,date;
int pres,abs,sal;

TextView n,u,cn1,cn2,ad1,ad2,a3,dj,prs,ab,salup;
dbHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent=getIntent();
        uid=intent.getIntExtra("userId",0);
        helper=new dbHelper(this);
        n=findViewById(R.id.profile_name);
        u=findViewById(R.id.profile_uid);
        cn1=findViewById(R.id.profileContact1);
        cn2=findViewById(R.id.profileContract2);
        ad1=findViewById(R.id.profileAddress1);
        ad2=findViewById(R.id.profileAddress2);
        a3=findViewById(R.id.profileAddress3);
        dj=findViewById(R.id.profileDOJ);
        prs=findViewById(R.id.profile_presents);
        ab=findViewById(R.id.profileAbsents);
        salup=findViewById(R.id.profileSalaryUptoNow);

        populateView();
    }
    private void populateView(){
        SQLiteDatabase db=helper.getReadableDatabase();
        //String getStaff= SQL_HELPER.select(Contract_class.entry.TABLE_STAFF,"*",uid);
        String getStaff=SQL_HELPER.selectWithJoin(Contract_class.entry.TABLE_STAFF,Contract_class.entry.TABLE_ATTENDENCE,uid);
        Log.e("this",getStaff);
        Cursor staffInfo=db.rawQuery(getStaff,null);
       // Log.e("this",""+staffInfo.getCount());
        if(staffInfo.getCount()>0){
            int nameIndex=staffInfo.getColumnIndex(Contract_class.entry.STAFF_FULL_NAME);
            int con1index=staffInfo.getColumnIndex(Contract_class.entry.STAFF_CONTACT_NUMBER_1);
            int con2index=staffInfo.getColumnIndex(Contract_class.entry.STAFF_CONTACT_NUMBER_2);
            int add1Index=staffInfo.getColumnIndex(Contract_class.entry.STAFF_ADDRESS_1);
            int add2Index=staffInfo.getColumnIndex(Contract_class.entry.STAFF_ADDRESS_2);
            int add3Index=staffInfo.getColumnIndex(Contract_class.entry.STAFF_ADDRESS_3);
            int dojIndex=staffInfo.getColumnIndex(Contract_class.entry.STAFF_DOJ);
            int salaryIndex=staffInfo.getColumnIndex(Contract_class.entry.STAFF_SALARY);
            int presindex=staffInfo.getColumnIndex(Contract_class.entry.ATTENDENCE_UPTO_NOW);
            int absindex=staffInfo.getColumnIndex(Contract_class.entry.ATTENDENCE_ABSENT);
            int salUptoNowindex=staffInfo.getColumnIndex(Contract_class.entry.ATTENDENCE_SALARY_UPTO_NOW);
staffInfo.moveToFirst();
            name=staffInfo.getString(nameIndex);
            add1=staffInfo.getColumnName(add1Index);
            add2=staffInfo.getString(add2Index);
            add3=staffInfo.getColumnName(add3Index);
            con1=staffInfo.getLong(con1index);
            con2=staffInfo.getLong(con2index);
            date=staffInfo.getLong(dojIndex);
            pres=staffInfo.getInt(presindex);
            abs=staffInfo.getInt(absindex);
            sal=staffInfo.getInt(salUptoNowindex);
            salary=staffInfo.getInt(salaryIndex);

            n.setText(name);
            ad1.setText(add1);
            ad2.setText(add2);
            a3.setText(add3);
            cn1.setText(String.valueOf(con1));
            cn2.setText(String.valueOf(con2));
            dj.setText(String.valueOf(date));
            prs.setText(String.valueOf(pres));
            ab.setText(String.valueOf(abs));
            salup.setText(String.valueOf(sal));



        }else{
            Log.e("this","No cursor found");
        }
        staffInfo.close();
    }
}
