package com.alleviate.attendence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by felix on 15/2/16.
 */
public class AttendAdapter extends RecyclerView.Adapter<AttendAdapter.ViewHolder> {
    ArrayList<CheckActivity.StudentInfo> student_list;
    Context context;

    public AttendAdapter(ArrayList<CheckActivity.StudentInfo> studentInfos, Context applicationContext) {
        this.student_list = studentInfos;
        this.context = applicationContext;
    }

    @Override
    public AttendAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attend_layout,parent,false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final AttendAdapter.ViewHolder holder, final int position) {
        ((TextView) holder.itemView.findViewById(R.id.student_name)).setText(student_list.get(position).name);
        ((TextView) holder.itemView.findViewById(R.id.student_username)).setText(student_list.get(position).uname);
        ((CheckBox) holder.itemView.findViewById(R.id.attend)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add_attendence(context,student_list.get(position).uname,student_list.get(position).semester,student_list.get(position).subject,student_list.get(position).date);

                ((CheckBox) holder.itemView.findViewById(R.id.attend)).setVisibility(View.INVISIBLE);
            }
        });
    }

    private void add_attendence(Context context, String uname, String semester, String subject, String date) {

        SQLiteHelper db = new SQLiteHelper(context);
        SQLiteDatabase dbr = db.getReadableDatabase();

        String[] col = {db.db_attend_username,db.db_attend_sem,db.db_attend_sub};

        String where = db.db_attend_username+"='"+uname+"' AND "+db.db_attend_sem+"='"+semester+"' AND "+db.db_attend_sub+"='"+subject+"' AND "+db.db_attend_date+"='"+date+"'";

        Cursor cur =dbr.query(db.db_Atable, col, where, null, null, null, null);

        int records = cur.getCount();

        if(records==0){
            SQLiteDatabase dbw = db.getWritableDatabase();

            ContentValues insert_usr = new ContentValues();
            insert_usr.put(db.db_attend_username,uname);
            insert_usr.put(db.db_attend_sem,semester);
            insert_usr.put(db.db_attend_sub, subject);
            insert_usr.put(db.db_attend_value, 1);
            insert_usr.put(db.db_attend_date,date);

            long id = dbw.insert(db.db_Atable,null,insert_usr);

            dbw.close();

        }
    }

    @Override
    public int getItemCount() {
        return student_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
