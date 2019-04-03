package com.example.myapplication.Activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.Contract_class;
import com.example.myapplication.data.SQL_HELPER;
import com.example.myapplication.data.dbHelper;

public class AddMenuItem extends AppCompatActivity {
EditText name,price,info;
ImageView im1;
boolean type=false;

dbHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_item);

        im1=findViewById(R.id.image1);
        name=findViewById(R.id.add_menu_name);
        price=findViewById(R.id.add_menu_price);
        info=findViewById(R.id.add_menu_info);

        helper=new dbHelper(this);

        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type)
                {im1.setImageResource(R.drawable.veg);
                   type=false;
                }else{
                    im1.setImageResource(R.drawable.nonveg);
                    type=true;
                }
            }
        });

    }
public void save(){
    SQLiteDatabase db=helper.getWritableDatabase();
    String nm=name.getText().toString();
    int prc=Integer.parseInt(price.getText().toString());
    String inf=info.getText().toString();
    String inserting= SQL_HELPER.insert(Contract_class.entry.TABLE_MENU);
    db.execSQL(inserting);
    Toast.makeText(this, "Menu Item Inserted", Toast.LENGTH_SHORT).show();
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_account,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_save_create:{
                save();
                finish();
                return true;
            }
            default:return super.onOptionsItemSelected(item);
        }
    }
}
