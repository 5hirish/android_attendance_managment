package com.alleviate.attendence;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        TextView user_name = (TextView)findViewById(R.id.uname);
        TextView detail = (TextView)findViewById(R.id.detail);

        Intent in = getIntent();
        String uname = in.getStringExtra("Uname");
        user_name.setText(uname);

        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase dbr = db.getReadableDatabase();

        String[] col = {db.db_attend_id,db.db_attend_username,db.db_attend_sem,db.db_attend_sub,db.db_attend_date};

        String where = db.db_attend_username+"='"+uname+"'";

        Cursor cur =dbr.query(db.db_Atable, col, where, null, null, null, null);

        if(cur!=null){
            while (cur.moveToNext()){
                int regno = cur.getInt(cur.getColumnIndex(db.db_attend_id));
                String name =  cur.getString(cur.getColumnIndex(db.db_attend_username));
                String sem = cur.getString(cur.getColumnIndex(db.db_attend_sem));
                String sub = cur.getString(cur.getColumnIndex(db.db_attend_sub));
                String date = cur.getString(cur.getColumnIndex(db.db_attend_date));

                detail.append(name+" was present on "+date+" for "+sub+" of "+sem+"\n");



            }cur.close();
        }
    }
}
