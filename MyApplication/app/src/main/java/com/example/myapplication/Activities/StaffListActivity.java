package com.example.myapplication.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Adapters.staffListAdapter;
import com.example.myapplication.R;
import com.example.myapplication.data.Contract_class;
import com.example.myapplication.data.SQL_HELPER;
import com.example.myapplication.data.dbHelper;
import com.example.myapplication.objects.StaffObject;

import java.util.ArrayList;

public class StaffListActivity extends AppCompatActivity {
ListView lv;
ArrayList<StaffObject> list;
ArrayAdapter<StaffObject> adapter;

dbHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_list);

        lv=findViewById(R.id.staffListView);
        list=new ArrayList<>();
        adapter=new staffListAdapter(this,list);
        lv.setAdapter(adapter);
        helper=new dbHelper(this);
        populateListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int userid=list.get(position).getUid();
                Intent intent=new Intent(StaffListActivity.this, ProfileActivity.class);
                intent.putExtra("userId",userid);
                startActivity(intent);
            }
        });

    }
    public void populateListView(){
        SQLiteDatabase db=helper.getReadableDatabase();
        try{
        String selecting= SQL_HELPER.select(Contract_class.entry.TABLE_STAFF);
        Cursor staffTable=db.rawQuery(selecting,null);
        try{if(staffTable.getCount()==0){
            Toast.makeText(this, "Not any staffs yet", Toast.LENGTH_SHORT).show();
        }else{
            int uidindex=staffTable.getColumnIndex(Contract_class.entry.STAFF_USER_ID);
            int idindex=staffTable.getColumnIndex(Contract_class.entry.STAFF_ID);
            int nameindex=staffTable.getColumnIndex(Contract_class.entry.STAFF_FULL_NAME);
            int contactindex=staffTable.getColumnIndex(Contract_class.entry.STAFF_CONTACT_NUMBER_1);
            staffTable.moveToFirst();
            for( int i=0;i<staffTable.getCount();i++) {
                int uid=staffTable.getInt(uidindex);
                String name = staffTable.getString(nameindex);
                int id = staffTable.getInt(idindex);
                Long contact = Long.valueOf(staffTable.getInt(contactindex));
                list.add(new StaffObject(id,uid, name, contact));
                staffTable.moveToNext();
            }
        }
        }catch (IndexOutOfBoundsException e){
            Log.e("Thisindex",e.getLocalizedMessage());
        }
        staffTable.close();
        adapter.notifyDataSetChanged();
    }catch (SQLException e){
            Log.e("stafflist",e.getLocalizedMessage());
        }

    }

}
