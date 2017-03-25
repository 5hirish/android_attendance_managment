package com.alleviate.attendence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    String u_semester="",u_year="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("Add Student");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        final EditText regno = (EditText)findViewById(R.id.regno);
        final EditText name = (EditText)findViewById(R.id.name);
        final EditText uname = (EditText)findViewById(R.id.username);
        final EditText contact = (EditText)findViewById(R.id.contact);
        final EditText address = (EditText)findViewById(R.id.address);

        Spinner semester = (Spinner)findViewById(R.id.semester);

        ArrayAdapter<CharSequence> adapter_sem = ArrayAdapter.createFromResource(this, R.array.semester, android.R.layout.simple_spinner_item);
        adapter_sem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semester.setAdapter(adapter_sem);

        Spinner year = (Spinner)findViewById(R.id.year);

        ArrayAdapter<CharSequence> adapter_year = ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_item);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter_year);

        Button save = (Button)findViewById(R.id.add_user);

        semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                u_semester = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                u_semester = "Semester I";
            }
        });

        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                u_year = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                u_year = "First Year";
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String reg_no = "";
                String u_name = name.getText().toString();
                String u_uname = uname.getText().toString();
                long cont_no = 0;
                String password = "1234";
                String acc_type = "User";
                String add = address.getText().toString();
                if(!contact.getText().toString().equals("") && !regno.getText().toString().equals("")){
                    reg_no = regno.getText().toString();
                    cont_no = Long.parseLong(contact.getText().toString());
                }


                SQLiteHelper db = new SQLiteHelper(getApplicationContext());
                SQLiteDatabase dbw = db.getWritableDatabase();

                ContentValues insert_usr = new ContentValues();
                insert_usr.put(db.db_info_no,reg_no);
                insert_usr.put(db.db_info_name,u_name);
                insert_usr.put(db.db_info_username, u_uname);
                insert_usr.put(db.db_info_contact, cont_no);
                insert_usr.put(db.db_info_sem, u_semester);
                insert_usr.put(db.db_info_year, u_year);
                insert_usr.put(db.db_info_address, add);

                ContentValues insert_usr_sign = new ContentValues();
                insert_usr_sign.put(db.db_user_name,u_uname);
                insert_usr_sign.put(db.db_user_password,password);
                insert_usr_sign.put(db.db_user_type, acc_type);

                SQLiteDatabase dbr = db.getReadableDatabase();

                String[] col = {db.db_user_name};

                String where = db.db_user_name+"='"+u_uname+"'";

                Cursor cur =dbr.query(db.db_Utable, col, where, null, null, null, null);

                int records = cur.getCount();

                if(!u_name.equals("") || !u_uname.equals("") || cont_no>0 || !reg_no.equals("")){
                    if(records==0) {
                        long id = dbw.insert(db.db_Itable, null, insert_usr);
                        id = dbw.insert(db.db_Utable, null, insert_usr_sign);
                        dbw.close();
                        Toast.makeText(getApplicationContext(),"User Added!",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Username already exist...!",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Incorrect Details!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
