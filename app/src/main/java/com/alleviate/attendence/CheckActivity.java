package com.alleviate.attendence;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        setTitle("Attendance");

        Intent in = getIntent();
        String semester = in.getStringExtra("Semester");
        String subject = in.getStringExtra("Subject");
        String date = in.getStringExtra("Date");

        RecyclerView rv = (RecyclerView)findViewById(R.id.student_view);
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv.setHasFixedSize(true);

        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        ArrayList<StudentInfo> studentInfos = new ArrayList<StudentInfo>();

        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase dbr = db.getReadableDatabase();

        String[] col = {db.db_info_id,db.db_info_no,db.db_info_name,db.db_info_username};

        Cursor cur =dbr.query(db.db_Itable, col, null, null, null, null, null);

        if(cur!=null){
            while (cur.moveToNext()){
                int id =cur.getInt(cur.getColumnIndex(db.db_info_id));
                String regno =cur.getString(cur.getColumnIndex(db.db_info_no));
                String name =cur.getString(cur.getColumnIndex(db.db_info_name));
                String uname =cur.getString(cur.getColumnIndex(db.db_info_username));

                studentInfos.add(new StudentInfo(id,regno,name,uname,semester,subject,date));

            }cur.close();
        }

        RecyclerView.Adapter adapter = new AttendAdapter(studentInfos, getApplicationContext());
        rv.setAdapter(adapter);

        rv.setItemAnimator(new DefaultItemAnimator());

        dbr.close();
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    class StudentInfo {
        String name, uname, subject, semester, date, reg_no;
        int id;

        public StudentInfo(int id, String regno, String name, String uname, String sem, String subject, String date) {
            this.id = id;
            this.reg_no = regno;
            this.name = name;
            this.uname = uname;
            this.semester = sem;
            this.subject = subject;
            this.date = date;

        }
    }
}
