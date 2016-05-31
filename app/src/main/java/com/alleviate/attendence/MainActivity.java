package com.alleviate.attendence;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Log In");

        final EditText uname = (EditText)findViewById(R.id.username);
        final EditText pass = (EditText)findViewById(R.id.password);

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = uname.getText().toString();
                String password = pass.getText().toString();
                String type = "";

                if(username.length()>=4 && password.length()>=4){

                    SQLiteHelper db = new SQLiteHelper(getApplicationContext());
                    SQLiteDatabase dbr = db.getReadableDatabase();

                    String[] col = {db.db_user_name,db.db_user_password,db.db_user_type};

                    String where = db.db_user_name+"='"+username+"' AND "+db.db_user_password+"='"+password+"'";

                    Cursor cur =dbr.query(db.db_Utable, col, where, null, null, null, null);

                    /*Cursor curr =dbr.query(db.db_Utable, col, null, null, null, null, null);
                    if(curr!=null){
                        while (curr.moveToNext()){
                            System.out.println(curr.getString(cur.getColumnIndex(db.db_user_name)));
                            System.out.println(curr.getString(cur.getColumnIndex(db.db_user_type)));
                            //Toast.makeText(getApplicationContext(),type,Toast.LENGTH_SHORT).show();

                        }cur.close();
                    }*/

                    int count = cur.getCount();

                    if(count>0){
                        if(cur!=null){
                            while (cur.moveToNext()){
                                type =cur.getString(cur.getColumnIndex(db.db_user_type));
                                //Toast.makeText(getApplicationContext(),type,Toast.LENGTH_SHORT).show();

                            }cur.close();
                        }

                        dbr.close();

                        if(type.equals("Administrator")){

                            Intent in = new Intent(MainActivity.this,AdminActivity.class);
                            startActivity(in);

                        }else if(type.equals("User")){

                            Intent in = new Intent(MainActivity.this,DetailActivity.class);
                            in.putExtra("Username",username);
                            startActivity(in);
                        }

                    }else {
                        Toast.makeText(getApplicationContext(),"Login Failed!",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"Insert more than 4 characters...",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int menu_id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (menu_id){

            case R.id.action_add_user:

                Intent in = new Intent(MainActivity.this,SignActivity.class);
                startActivity(in);

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
