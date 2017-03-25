package com.alleviate.attendence;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        setTitle("Administrator");

        RecyclerView rv = (RecyclerView)findViewById(R.id.student_view);
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv.setHasFixedSize(true);

        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        ArrayList<StudentInfo> studentInfos = new ArrayList<StudentInfo>();

        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase dbr = db.getReadableDatabase();

        String[] col = {db.db_info_id,db.db_info_no,db.db_info_name,db.db_info_username,db.db_info_contact,db.db_info_sem,db.db_info_year,db.db_info_address};

        Cursor cur =dbr.query(db.db_Itable, col, null, null, null, null, null);

        if(cur!=null){
            while (cur.moveToNext()){
                int id =cur.getInt(cur.getColumnIndex(db.db_info_id));
                int regno =cur.getInt(cur.getColumnIndex(db.db_info_no));
                String name =cur.getString(cur.getColumnIndex(db.db_info_name));
                String uname =cur.getString(cur.getColumnIndex(db.db_info_username));
                long contact =cur.getLong(cur.getColumnIndex(db.db_info_contact));
                String sem =cur.getString(cur.getColumnIndex(db.db_info_sem));
                String year =cur.getString(cur.getColumnIndex(db.db_info_year));
                String add =cur.getString(cur.getColumnIndex(db.db_info_address));

                studentInfos.add(new StudentInfo(id,regno,name,uname,contact,sem,year,add));

            }cur.close();
        }

        RecyclerView.Adapter adapter = new StudentAdapter(studentInfos,getApplicationContext());
        rv.setAdapter(adapter);

        rv.setItemAnimator(new DefaultItemAnimator());

        dbr.close();


    }

    @Override
    public void onResume(){
        super.onResume();
        setContentView(R.layout.activity_admin);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        setTitle("Administrator");

        RecyclerView rv = (RecyclerView)findViewById(R.id.student_view);
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv.setHasFixedSize(true);

        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        ArrayList<StudentInfo> studentInfos = new ArrayList<StudentInfo>();

        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase dbr = db.getReadableDatabase();

        String[] col = {db.db_info_id,db.db_info_no,db.db_info_name,db.db_info_username,db.db_info_contact,db.db_info_sem,db.db_info_year,db.db_info_address};

        Cursor cur =dbr.query(db.db_Itable, col, null, null, null, null, null);

        if(cur!=null){
            while (cur.moveToNext()){
                int id =cur.getInt(cur.getColumnIndex(db.db_info_id));
                int regno =cur.getInt(cur.getColumnIndex(db.db_info_no));
                String name =cur.getString(cur.getColumnIndex(db.db_info_name));
                String uname =cur.getString(cur.getColumnIndex(db.db_info_username));
                long contact =cur.getLong(cur.getColumnIndex(db.db_info_contact));
                String sem =cur.getString(cur.getColumnIndex(db.db_info_sem));
                String year =cur.getString(cur.getColumnIndex(db.db_info_year));
                String add =cur.getString(cur.getColumnIndex(db.db_info_address));

                studentInfos.add(new StudentInfo(id,regno,name,uname,contact,sem,year,add));

            }cur.close();
        }

        RecyclerView.Adapter adapter = new StudentAdapter(studentInfos,getApplicationContext());
        rv.setAdapter(adapter);

        rv.setItemAnimator(new DefaultItemAnimator());

        dbr.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
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

                Intent in = new Intent(AdminActivity.this,AddActivity.class);
                startActivity(in);

                break;
            case R.id.action_attend:

                Intent ina = new Intent(AdminActivity.this,AttendActivity.class);
                ina.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(ina);

                break;
            case android.R.id.home:
                    finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    class StudentInfo {
        String name, uname, address, semester, year;
        int id,reg_no;
        long contact;

        public StudentInfo(int id, int regno, String name, String uname, long contact, String sem, String year, String add) {
            this.id = id;
            this.reg_no = regno;
            this.name = name;
            this.uname = uname;
            this.contact = contact;
            this.semester = sem;
            this.year = year;
            this.address = add;
        }
    }
}
