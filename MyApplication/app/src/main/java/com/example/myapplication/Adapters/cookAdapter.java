package com.example.myapplication.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.NetworkUtils;
import com.example.myapplication.R;
import com.example.myapplication.objects.CommonListObject;
import com.example.myapplication.objects.cookObject;
import com.example.myapplication.objects.menuitem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

public class cookAdapter extends ArrayAdapter<cookObject> {
    ArrayList<cookObject> list;
    ArrayList<menuitem> menu;

    public cookAdapter(Context context, ArrayList<cookObject> objects) {
        super(context,0,objects);
        list=objects;
    }


    @Override
    public View getView(int position,View convertView,  ViewGroup parent) {
        View view=convertView;
        if(view==null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.cook_todo,parent,false);
        }
        final cookObject current=list.get(position);
        TextView tables,foodList,QuantityList;
        Button done;
        tables=view.findViewById(R.id.cook_tables);
        foodList=view.findViewById(R.id.cook_foodItem);
        QuantityList=view.findViewById(R.id.cook_quantity);
        done=view.findViewById(R.id.cook_done_btn);

        tables.setText(current.getTables());

        menu=new ArrayList<>();
        getmenuitems(current.getFoodItem(),foodList,QuantityList);
        Log.e("this", "getView: "+current.getFoodItem() );

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callStaff();
                //TODO delete the entry from cooktable where _id=current.getID()
               final RequestQueue queue=Volley.newRequestQueue(getContext());
                String url="http://10.0.2.2/Project/delete_cook.php?_id="+current.getId();
                JsonObjectRequest jreq=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject base=new JSONObject(response.toString());
                            String message=base.getString("message");
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                this.notify();
            }
        }
        );



        return view;
    }
    private void getmenuitems(String foods, final TextView foodTv, TextView QuanTV){

        ArrayList<Integer> foodlist=new ArrayList<>();
        ArrayList<Integer> quantity=new ArrayList<>();

        String[] items=foods.split(" ");
        for(int i=0;i<items.length;i++)
        {
            CharSequence quan=items[i].subSequence(items[i].indexOf("(")+1,items[i].indexOf(")"));
            quantity.add(Integer.parseInt((String) quan));

            String food=items[i].substring(0,items[i].indexOf("("));
            foodlist.add(Integer.parseInt(food));
        }

        final RequestQueue queue=Volley.newRequestQueue(getContext());
        for(int i=0;i<foodlist.size();i++){
            String url="http://10.0.2.2/Project/findFood.php?query="+foodlist.get(i);          //getting name of the food item where _id=foodId[i];
            JsonObjectRequest jreq=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //    Log.e("this", "onResponse: "+response.toString() );
                    try {
                        JSONObject base=new JSONObject(response.toString());
                        JSONArray array=base.getJSONArray("data");
                        JSONObject current=array.getJSONObject(0);
                        String name=current.getString("name");
                        foodTv.append(name+"\n");

                    } catch (JSONException e) {
                        e.printStackTrace();
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
            QuanTV.append(quantity.get(i)+"\n");
        }
    }
    private void callStaff(){
        Toast.makeText(getContext(), "Staff Called", Toast.LENGTH_SHORT).show();
    }
}
