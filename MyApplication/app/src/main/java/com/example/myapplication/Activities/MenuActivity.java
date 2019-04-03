package com.example.myapplication.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Adapters.MenuRecyclerView;
import com.example.myapplication.Fragments.tableFragment;
import com.example.myapplication.R;
import com.example.myapplication.data.Contract_class;
import com.example.myapplication.data.SQL_HELPER;
import com.example.myapplication.data.dbHelper;
import com.example.myapplication.objects.menuitem;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
RecyclerView rView;
Button processBtn;
private ArrayList<menuitem> list;
private RecyclerView.Adapter adapter;
dbHelper helper;
tableFragment fragmentBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        rView=findViewById(R.id.myrecyclerViewMenu);
        processBtn=findViewById(R.id.buttonproceed);
        fragmentBuilder=new tableFragment();
        list=new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(this);
        rView.setLayoutManager(mLayoutManager);
        adapter=new MenuRecyclerView(this,list);
        rView.setAdapter(adapter);
        helper=new dbHelper(this);
        processBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFragment();
            }
        });
        populateMenu();

    }
    private void populateMenu(){
        SQLiteDatabase db=helper.getReadableDatabase();
        String sel=SQL_HELPER.select(Contract_class.entry.TABLE_MENU);
        Cursor table=db.rawQuery(sel,null);
        int nameindex=table.getColumnIndex(Contract_class.entry.MENU_NAME);
        int vegIndex=table.getColumnIndex(Contract_class.entry.MENU_VEG);
        int priceindex=table.getColumnIndex(Contract_class.entry.MENU_PRICE);
        int infoindex=table.getColumnIndex(Contract_class.entry.MENU_INFO);
        int offerindex=table.getColumnIndex(Contract_class.entry.MENU_OFFER);
        table.moveToFirst();
        for(int i=0;i<table.getCount();i++){
            Toast.makeText(this, ""+i, Toast.LENGTH_SHORT).show();
            String name=table.getString(nameindex);
            int veg=table.getInt(vegIndex);
            int price=table.getInt(priceindex);
            String info=table.getString(infoindex);
            int offer=table.getInt(offerindex);
            menuitem item=new menuitem(0,name,veg,price,info,offer);
            list.add(item);
            adapter.notifyDataSetChanged();
            table.moveToNext();
        }
        table.close();
    }
    private void createFragment(){
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().add(R.id.frameLayoutMenu,fragmentBuilder).commit();
    }
    //TODO 1: resolve the Image in the database problem
}
