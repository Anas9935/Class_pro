package com.example.myapplication.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Activities.addStaff;
import com.example.myapplication.data.Contract_class;
import com.example.myapplication.data.SQL_HELPER;
import com.example.myapplication.data.dbHelper;

public class StaffListDialogFragment extends DialogFragment {
    dbHelper helper;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        helper=new dbHelper(getContext());
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= requireActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialogfornewstaff,null);
        final EditText userid=view.findViewById(R.id.dialognewstaffId);
        builder.setView(view);
        builder.setTitle("Select User Profile");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StaffListDialogFragment.this.getDialog().cancel();
            }
        });
        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db=helper.getReadableDatabase();
                String selecting= SQL_HELPER.select(Contract_class.entry.TABLE_USERS,Contract_class.entry.USER_NAME,Integer.valueOf(userid.getText().toString()));
                Cursor cursor=db.rawQuery(selecting,null);
                if(cursor.getCount()==0){
                    Toast.makeText(getContext(), "Id does not Exist", Toast.LENGTH_SHORT).show();
                    cursor.close();
                }else{
                    cursor.close();
                    Intent intent=new Intent(getContext(), addStaff.class);
                    intent.putExtra("uid",Integer.valueOf(userid.getText().toString()));
                    startActivity(intent);
                }
            }
        });
        return builder.create();
    }
}
