package com.example.myapplication.Authentication;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Activities.ProfileActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.Contract_class;
import com.example.myapplication.data.SQL_HELPER;
import com.example.myapplication.data.dbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CreateAccountActivity extends AppCompatActivity {
EditText name,pass,cPass,email,phone;
Spinner gender;
String n,p,cp,e,ph;
int d,g;
dbHelper helper;
RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        name=findViewById(R.id.usernameCreate);
        pass=findViewById(R.id.passwordCreate);
        cPass=findViewById(R.id.confirmCreate);
        email=findViewById(R.id.emailCreate);
        phone=findViewById(R.id.contactCreate);

        gender=findViewById(R.id.GenderCreate);
        helper=new dbHelper(this);
        setupSpinners();
    }
    private void setupSpinners(){

        ArrayAdapter<CharSequence> gen=ArrayAdapter.createFromResource(this,R.array.gender,android.R.layout.simple_spinner_item);
        gen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(gen);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                g=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                g=-1;
            }
        });

    }
    private void save(){
        n=name.getText().toString();
        p=pass.getText().toString();
        cp=cPass.getText().toString();
        e=email.getText().toString();
        ph=phone.getText().toString();
        long con=Long.parseLong(ph);
        if(n.equals("")||p.equals("")||cp.equals("")||e.equals("")||ph.equals("")){
            Toast.makeText(this, "Fill the Fields", Toast.LENGTH_SHORT).show();
        }else if(!p.equals(cp)){
            Toast.makeText(this, "Password Field Mismatch", Toast.LENGTH_SHORT).show();
        }
        else{//inserting ata in users
            queue= Volley.newRequestQueue(CreateAccountActivity.this);
            String url="http://10.0.2.2/Project/insertUser.php?user_name="+n+"&password="+p+"&email="+e+"&contact="+con+"&designation="+0+"&gender="+g+"&user_img="+null;
            JsonObjectRequest jreq=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject base=new JSONObject(response.toString());
                        String message=base.getString("message");
                        Toast.makeText(CreateAccountActivity.this, message, Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(CreateAccountActivity.this,"Password Mismatch",Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error", "onErrorResponse: Eroorr"+error.getMessage() );
                    queue.stop();
                }
            });
            queue.add(jreq);

        }

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
            default:{
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
