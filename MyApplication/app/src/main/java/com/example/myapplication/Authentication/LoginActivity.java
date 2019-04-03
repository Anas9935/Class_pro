package com.example.myapplication.Authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Activities.MenuActivity;
import com.example.myapplication.Activities.WorkerActivity;
import com.example.myapplication.Databases;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Activities.ManagerActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.Contract_class;
import com.example.myapplication.data.SQL_HELPER;
import com.example.myapplication.data.dbHelper;

public class LoginActivity extends AppCompatActivity {
EditText uName,password;
Button login,create;
String name,pass;
int des=-1;
int uid;
dbHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        helper=new dbHelper(this);

        uName=findViewById(R.id.usernameAuth);
        password=findViewById(R.id.passwordAuth);
        login=findViewById(R.id.loginAuth);
        create=findViewById(R.id.createAuth);

        getPrefs();
        uName.setText(name);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent;
                if(isCorrect()){
                    switch (des){
                        case 0:{                        //customer
                            intent=new Intent(LoginActivity.this,MenuActivity.class);
                            break;
                        }
                        case 1:{                    //manager
                            intent=new Intent(LoginActivity.this, ManagerActivity.class);
                            break;}
                        case 2:{                    //receptionist
                            intent=new Intent(LoginActivity.this, MainActivity.class);
                            break;
                            }
                        case 3:{                        //cook
                            intent=new Intent(LoginActivity.this,MainActivity.class);
                            break;
                        }
                        default:{                           //other
                            intent=new Intent(LoginActivity.this, WorkerActivity.class);
                        }
                    }
                    intent.putExtra("uid",uid);
                    startActivity(intent);
                }
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(LoginActivity.this,CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean isCorrect(){
        name=uName.getText().toString();
        pass=password.getText().toString();
        SQLiteDatabase db=helper.getReadableDatabase();
        String passFromDb="";
        Cursor passCursor;
        String code= SQL_HELPER.select(Contract_class.entry.TABLE_USERS,name);
        int pos=0;
        try{
            passCursor=db.rawQuery(code,null);
                if(passCursor.getCount()>0){
                    passCursor.moveToFirst();
                    int passindex=passCursor.getColumnIndex(Contract_class.entry.USER_PASSWORD);
                    int desindex=passCursor.getColumnIndex(Contract_class.entry.USER_DESIGNATION);
                    int uidindex=passCursor.getColumnIndex(Contract_class.entry.USER_ID);
                    for (int i=0;i<passCursor.getCount();i++){
                        des=passCursor.getInt(desindex);
                        uid=passCursor.getInt(uidindex);
                        passFromDb=passCursor.getString(passindex);
                        passCursor.moveToNext();
                        pos=1;
                        setPrefs();
                    }
                    passCursor.close();
                }else{
                    Toast.makeText(this, "Username not exists", Toast.LENGTH_SHORT).show();
                }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        if(pass.equals(passFromDb)&&pos==1){
            return true;
        }else if(pos==1)
            Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
        return false;
    }
    private void setPrefs(){
        SharedPreferences.Editor editor=getSharedPreferences("MyPREFS",MODE_PRIVATE).edit();
        editor.putInt("desig",des);
        editor.putString("user",name);
        editor.apply();
    }
    private void getPrefs(){
        SharedPreferences prefs=getSharedPreferences("MyPREFS",MODE_PRIVATE);
        des=prefs.getInt("desig",-1);
        name=prefs.getString("user","");
    }
}
