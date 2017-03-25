package com.alleviate.attendence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignActivity extends AppCompatActivity {

    String acc_type = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Create User");

        final EditText uname = (EditText)findViewById(R.id.username);
        final EditText pass = (EditText)findViewById(R.id.password);
//        Spinner type = (Spinner)findViewById(R.id.type);

        /*ArrayAdapter<CharSequence> adapter_type = ArrayAdapter.createFromResource(this, R.array.acc_type, android.R.layout.simple_spinner_item);
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter_type);*/

        /*type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                acc_type = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                acc_type = "Administrator";
            }
        });*/

        Button add = (Button)findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = uname.getText().toString();
                String password = pass.getText().toString();
                acc_type = "Administrator";

                if(user_name.length()>=4 && password.length()>=4){

                    SQLiteHelper db = new SQLiteHelper(getApplicationContext());
                    SQLiteDatabase dbr = db.getReadableDatabase();

                    String[] col = {db.db_user_name};

                    String where = db.db_user_name+"='"+user_name+"'";

                    Cursor cur =dbr.query(db.db_Utable, col, where, null, null, null, null);

                    int records = cur.getCount();

                    if(records==0){
                        SQLiteDatabase dbw = db.getWritableDatabase();

                        ContentValues insert_usr = new ContentValues();
                        insert_usr.put(db.db_user_name,user_name);
                        insert_usr.put(db.db_user_password,password);
                        insert_usr.put(db.db_user_type, acc_type);

                        long id = dbw.insert(db.db_Utable,null,insert_usr);

                        dbw.close();

                        Toast.makeText(getApplicationContext(),"User Added!",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(),"Username already exist...!",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"Insert more than 4 characters...",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int menu_id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (menu_id){

            case android.R.id.home:
                finish();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
